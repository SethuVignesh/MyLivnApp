package com.example.dell.mylivnapp.presentation.view;

import java.util.Collections;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.os.Handler;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;


public class DraggableGridView extends ViewGroup implements View.OnTouchListener, View.OnClickListener, View.OnLongClickListener {
    //layout vars
    public static float childRatio = .9f;
    protected int colCount, childSize, padding, dpi, scroll = 0;
    protected float lastDelta = 0;
    protected Handler handler = new Handler();
    //dragging vars
    protected int dragged = -1, lastX = -1, lastY = -1, lastTarget = -1;
    protected boolean enabled = true, touching = false;
    //anim vars
    public static int animT = 150;
    protected ArrayList<Integer> newPositions = new ArrayList<Integer>();
    //listeners
    protected OnRearrangeListener onRearrangeListener;
    protected OnClickListener secondaryOnClickListener;
    private OnItemClickListener onItemClickListener;

    //CONSTRUCTOR AND HELPERS
    public DraggableGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setListeners();
        handler.removeCallbacks(updateTask);
        handler.postAtTime(updateTask, SystemClock.uptimeMillis() + 500);
        setChildrenDrawingOrderEnabled(true);

        DisplayMetrics metrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(metrics);
        dpi = metrics.densityDpi;
    }

    protected void setListeners() {
        setOnTouchListener(this);
        super.setOnClickListener(this);
        setOnLongClickListener(this);
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        secondaryOnClickListener = l;
    }

    protected Runnable updateTask = new Runnable() {
        public void run() {
            if (dragged != -1) {
                if (lastY < padding * 3 && scroll > 0)
                    scroll -= 20;
                else if (lastY > getBottom() - getTop() - (padding * 3) && scroll < getMaxScroll())
                    scroll += 20;
            } else if (lastDelta != 0 && !touching) {
                scroll += lastDelta;
                lastDelta *= .9;
                if (Math.abs(lastDelta) < .25)
                    lastDelta = 0;
            }
            clampScroll();
            onLayout(true, getLeft(), getTop(), getRight(), getBottom());

            handler.postDelayed(this, 25);
        }
    };

    //OVERRIDES
    @Override
    public void addView(View child) {

        super.addView(child);
        newPositions.add(-1);
    }

    @Override
    public void removeViewAt(int index) {
        super.removeViewAt(index);
        newPositions.remove(index);
    }


    //LAYOUT
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //compute width of view, in dp
        float w = (r - l) / (dpi / 160f);

        //determine number of columns, at least _2
        colCount = 2;
        int sub = 240;
        w -= 280;
        while (w > 0) {
            colCount++;
            w -= sub;
            sub += 40;
        }

        //determine childSize and padding, in px
        childSize = (r - l) / colCount;
        childSize = Math.round(childSize * childRatio);
        padding = ((r - l) - (childSize * colCount)) / (colCount + 1);
        for (int i = 0; i < getChildCount(); i++)
            if (i != dragged) {
                Point xy = getCoorFromIndex(i);
                if (i == 0)
                    getChildAt(i).layout(xy.x, xy.y, xy.x + (2 * childSize), xy.y + (2 * childSize));
                else getChildAt(i).layout(xy.x, xy.y, xy.x + childSize, xy.y + childSize);
            }
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (dragged == -1)
            return i;
        else if (i == childCount - 1)
            return dragged;
        else if (i >= dragged)
            return i + 1;
        return i;
    }

    int currentX=0;
    int currentY=0;
    public int getIndexFromCoor(int x, int y) {
        currentX=x;
        currentY=y;
        int col = getColOrRowFromCoor(x), row = getColOrRowFromCoor(y + scroll);
        if (col == -1 || row == -1) //touch is between columns or rows
            return -1;
        int index = row * colCount + col;
        if (index >= getChildCount() + 5)
            return -1;
        Log.d("indexMapping", "index: "+index+"  X:"+x+" y: " +y);

        switch (index) {
            case 0:
            case 1:
            case 3:
            case 4:
                index = 0;
                break;

            case 2:
                index = 1;
                break;
            case 5:
                index = 2;
                break;
            default:
                boolean executed = false;
                for (int i = 6, j = 8; i < 30; i = i + 6, j = j + 6) {
                    if (i == index) {
                        index = (i - 1);
                        executed = true;
                        break;
                    }
                    if (j == index) {
                        index = (j - 5);
                        executed = true;
                        break;
                    }
                }

                if (!executed) {
                    index = index - 3;
                }

//                return index
        }
        Log.d("indexMapping", "index clicked" + index);
        return index;
    }

    protected int getColOrRowFromCoor(int coor) {
        coor -= padding;
        for (int i = 0; coor > 0; i++) {
            if (coor < childSize)
                return i;
            coor -= (childSize + padding);
        }
        return -1;
    }

    protected int getTargetFromCoor(int x, int y) {
        Log.d("target", "targetBefore:getColOrRowFromCoor(y + scroll)" + getColOrRowFromCoor(y + scroll));
        if (getColOrRowFromCoor(y + scroll) == -1) //touch is between rows
            return -1;
        //if (getIndexFromCoor(x, y) != -_5) //touch on top of another visual
        //return -_5;

        int leftPos = getIndexFromCoor(x - (childSize / 4), y);
        int rightPos = getIndexFromCoor(x + (childSize / 4), y);

        Log.d("target", "leftPos::rightPos" + leftPos+"::"+rightPos);
        if (leftPos == -1 && rightPos == -1) //touch is in the middle of nowhere
            return -1;
        if (leftPos == rightPos) //touch is in the middle of a visual
            return -1;

        int target = -1;
        if (rightPos > -1)
            target = rightPos;
        else if (leftPos > -1)
            target = leftPos + 1;
        Log.d("target", "dragged < target" + dragged+"::"+target);
        if (dragged < target)
            return target - 1;
        Log.d("target", "targetBefore" + target);
        //Toast.makeText(getContext(), "Target: " + target + ".", Toast.LENGTH_SHORT).show();

                for (int i = 3, j = 5; i < 30; i = i + 6, j = j + 6) {
                    if (i == target) {
                        target = (target + 2 - 3);

                    }
                    if (j == target) {
                        target = target - 2 ;

                    }
                }

        Log.d("target", "target" + target);
        return target;
    }

    protected Point getCoorFromIndex(int index) {
        int col = index % colCount;
        int row = index / colCount;
        /*
         * _0 _1 _2
         * _3 _4 _5
         * _6 _7 _8
         * 9 10 11
         * 12 13 14
         * */
        switch (index) {
            case 0:
//            case 1:
//            case 3:
//            case 4:
                break;

            case 1:
                col = 2 % colCount;
                row = 2 / colCount;
                break;
            case 3:
                col = (5 + 3) % colCount;
                row = (5 + 3) / colCount;
                break;
            case 5:
                col = (3 + 3) % colCount;
                row = (3 + 3) / colCount;
                break;

            default:
                boolean skip = false;
                for (int i = 3, j = 5; i < 30; i = i + 6, j = j + 6) {
                    if (i == index) {
                        col = (i + 2 + 3) % colCount;
                        row = (i + 2 + 3) / colCount;
                        skip = true;
                        break;
                    }
                    if (j == index) {
                        col = (j - 2 + 3) % colCount;
                        row = (j - 2 + 3) / colCount;
                        skip = true;
                        break;
                    }
                }
                if (!skip) {
                    col = (index + 3) % colCount;
                    row = (index + 3) / colCount;
                }
                break;

        }
//        Log.d("getCoorFromIndex", "getCoorFromIndex()" + index);
        return new Point(padding + (childSize + padding) * col,
                padding + (childSize + padding) * row - scroll);
    }

    public int getIndexOf(View child) {
        for (int i = 0; i < getChildCount(); i++)
            if (getChildAt(i) == child)
                return i;
        return -1;
    }

    //EVENT HANDLERS
    public void onClick(View view) {
        if (enabled) {
            if (secondaryOnClickListener != null)
                secondaryOnClickListener.onClick(view);
            if (onItemClickListener != null && getLastIndex() != -1)
                onItemClickListener.onItemClick(null, getChildAt(getLastIndex()), getLastIndex(), getLastIndex() / colCount);
        }
    }

    public boolean onLongClick(View view) {
        if (!enabled)
            return false;
        int index = getLastIndex();
        if (index != -1) {
            dragged = index;
            Log.d("dragged", "draggedOnLong Click" + dragged);
            animateDragged();
            return true;
        }
        return false;
    }

    public boolean onTouch(View view, MotionEvent event) {
        int action = event.getAction();
        switch (action & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                enabled = true;
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                touching = true;
                break;
            case MotionEvent.ACTION_MOVE:
                int x = (int) event.getX(), y = (int) event.getY();
                Log.d("coordinates", "coordinates x::y" + x + "::" + y);
                int delta = lastY - (int) event.getY();
                if (dragged != -1) {
//                    for (int i = 3, j = 5; i < 30; i = i + 6, j = j + 6) {
//                        if (i == dragged) {
//                            dragged = (dragged + 2);
//
//                        }
//                        if (j == dragged) {
//                            dragged = dragged - 2;
//
//                        }
//                    }

                    //change draw location of dragged visual
                    x = (int) event.getX();
                    y = (int) event.getY();
                    int l = x - (3 * childSize / 4), t = y - (3 * childSize / 4);

                    if (getChildAt(dragged) != null)
                        getChildAt(dragged).layout(l, t, l + (childSize * 3 / 2), t + (childSize * 3 / 2));

                    //check for new target hover
                    int target = getTargetFromCoor(x, y);
                    if (lastTarget != target) {
                        if (target != -1) {
                            animateGap(target);
                            lastTarget = target;
                        }
                    }
                } else {
                    scroll += delta;
                    clampScroll();
                    if (Math.abs(delta) > 2)
                        enabled = false;
                    onLayout(true, getLeft(), getTop(), getRight(), getBottom());
                }
                lastX = (int) event.getX();
                lastY = (int) event.getY();
                lastDelta = delta;
                break;
            case MotionEvent.ACTION_UP:
                if (dragged != -1) {
//                    switch (dragged) {
//                        case 0:
//                        case 1:
//                        case 3:
//                        case 4:
//                            dragged = 0;
//                            break;
//                        case 2:
//                            dragged = 1;
//                            break;
//                        default:
//                            dragged = dragged - 3;
//                            break;
//                    }
                    View v = getChildAt(dragged);
                    if (v.getTag()!=null&& v.getTag().equals("add")) { break;}
                    if (lastTarget != -1)
                        reorderChildren();
                    else {
                        Point xy = getCoorFromIndex(dragged);
                        if (v != null)
                            v.layout(xy.x, xy.y, xy.x + childSize, xy.y + childSize);
                    }
                    if (v != null) {
                        v.clearAnimation();
                        if (v instanceof ImageView)
                            ((ImageView) v).setAlpha(255);
                    }
                    lastTarget = -1;
                    dragged = -1;
                }
                touching = false;
                break;
        }
        if (dragged != -1)
            return true;
        return false;
    }

    //EVENT HELPERS
    protected void animateDragged() {
        Log.d("dragged", "dragged" + dragged);
        View v = getChildAt(dragged);
        if (v == null) return;
        if (v.getTag() != null && v.getTag().equals("add")) return;
        int x = getCoorFromIndex(dragged).x + childSize / 2, y = getCoorFromIndex(dragged).y + childSize / 2;
        int l = x - (3 * childSize / 4), t = y - (3 * childSize / 4);
        v.layout(l, t, l + (childSize * 3 / 2), t + (childSize * 3 / 2));
        AnimationSet animSet = new AnimationSet(true);
        ScaleAnimation scale = new ScaleAnimation(.667f, 1, .667f, 1, childSize * 3 / 4, childSize * 3 / 4);
        scale.setDuration(animT);
        AlphaAnimation alpha = new AlphaAnimation(1, .5f);
        alpha.setDuration(animT);

        animSet.addAnimation(scale);
        animSet.addAnimation(alpha);
        animSet.setFillEnabled(true);
        animSet.setFillAfter(true);

        v.clearAnimation();
        v.startAnimation(animSet);
    }

    protected void animateGap(int target) {
        for (int i = 0; i < getChildCount(); i++) {
            View v = getChildAt(i);
            if (v == null) return;
            if (i == dragged)
                continue;
            int newPos = i;
            if (dragged < target && i >= dragged + 1 && i <= target)
                newPos--;
            else if (target < dragged && i >= target && i < dragged)
                newPos++;

            //animate
            int oldPos = i;
            if (newPositions.get(i) != -1)
                oldPos = newPositions.get(i);
            if (oldPos == newPos)
                continue;

            Point oldXY = getCoorFromIndex(oldPos);
            Point newXY = getCoorFromIndex(newPos);
            Point oldOffset = new Point(oldXY.x - v.getLeft(), oldXY.y - v.getTop());
            Point newOffset = new Point(newXY.x - v.getLeft(), newXY.y - v.getTop());

            TranslateAnimation translate = new TranslateAnimation(Animation.ABSOLUTE, oldOffset.x,
                    Animation.ABSOLUTE, newOffset.x,
                    Animation.ABSOLUTE, oldOffset.y,
                    Animation.ABSOLUTE, newOffset.y);
            translate.setDuration(animT);
            translate.setFillEnabled(true);
            translate.setFillAfter(true);
            v.clearAnimation();
            v.startAnimation(translate);

            newPositions.set(i, newPos);
        }
    }

    protected void reorderChildren() {
        //FIGURE OUT HOW TO REORDER CHILDREN WITHOUT REMOVING THEM ALL AND RECONSTRUCTING THE LIST!!!
        if (onRearrangeListener != null)
            onRearrangeListener.onRearrange(dragged, lastTarget);
        ArrayList<View> children = new ArrayList<View>();
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).clearAnimation();
            children.add(getChildAt(i));
        }
        removeAllViews();
        while (dragged != lastTarget)
            if (lastTarget == children.size()) // dragged and dropped to the right of the last element
            {
                children.add(children.remove(dragged));
                dragged = lastTarget;
            } else if (dragged < lastTarget) // shift to the right
            {
                Collections.swap(children, dragged, dragged + 1);
                dragged++;
            } else if (dragged > lastTarget) // shift to the left
            {
                Collections.swap(children, dragged, dragged - 1);
                dragged--;
            }
        for (int i = 0; i < children.size(); i++) {
            newPositions.set(i, -1);
            addView(children.get(i));
        }
        onLayout(true, getLeft(), getTop(), getRight(), getBottom());
    }

    public void scrollToTop() {
        scroll = 0;
    }

    public void scrollToBottom() {
        scroll = Integer.MAX_VALUE;
        clampScroll();
    }

    protected void clampScroll() {
        int stretch = 3, overreach = getHeight() / 2;
        int max = getMaxScroll();
        max = Math.max(max, 0);

        if (scroll < -overreach) {
            scroll = -overreach;
            lastDelta = 0;
        } else if (scroll > max + overreach) {
            scroll = max + overreach;
            lastDelta = 0;
        } else if (scroll < 0) {
            if (scroll >= -stretch)
                scroll = 0;
            else if (!touching)
                scroll -= scroll / stretch;
        } else if (scroll > max) {
            if (scroll <= max + stretch)
                scroll = max;
            else if (!touching)
                scroll += (max - scroll) / stretch;
        }
    }

    protected int getMaxScroll() {
        int rowCount = (int) Math.ceil((double) getChildCount() / colCount), max = rowCount * childSize + (rowCount + 1) * padding - getHeight();
        return max;
    }

    public int getLastIndex() {
        return getIndexFromCoor(lastX, lastY);
    }

    //OTHER METHODS
    public void setOnRearrangeListener(OnRearrangeListener l) {
        this.onRearrangeListener = l;
    }

    public void setOnItemClickListener(OnItemClickListener l) {
        this.onItemClickListener = l;
    }

}