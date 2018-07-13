package com.example.dell.mylivnapp;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */


    List integers = new ArrayList();
    DraggableGridView gridView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        for (int i = 0; i < 50; i++) {
            integers.add(i);
        }

        gridView = (DraggableGridView) findViewById(R.id.dgv);

        gridView.addView(getImageView(0));
        gridView.addView(getImageView(1));
        gridView.addView(getImageView(2));
        gridView.addView(getImageView(3));
        gridView.addView(getImageView(4));
        gridView.addView(getImageView(5));
        gridView.addView(getImageView(6));
        gridView.addView(getImageView(7));
        gridView.addView(getImageView(8));
//        gridView.addView(getImageView(10));
        gridView.addView(getImageView(11));
        gridView.addView(getImageView(12));
        gridView.addView(getImageView(12));
        gridView.addView(getImageView(12));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() != null && view.getTag().equals("add")) {
                    Toast.makeText(getApplicationContext(), "Add", Toast.LENGTH_LONG).show();
                    gridView.removeViewAt(position);
                    gridView.removeViewAt(position);
                    gridView.removeViewAt(position);
                    gridView.removeViewAt(position);
                    gridView.addView(getImageView(10));
                    gridView.addView(getImageView(11));
                    gridView.addView(getImageView(12));
                    gridView.addView(getImageView(12));
                    gridView.addView(getImageView(12));
                } else {

                    //*  left,top    right,top
                    //   left,bottom right,bottom
                    // */
                    int left = view.getLeft() + view.getWidth() / 2;
                    int right = view.getRight();
                    int top = view.getTop();
                    int bottom = view.getBottom() - view.getHeight() / 2;
//x left to roght
                    //y top to bottom
                    if (gridView.currentX > left && gridView.currentX < right && gridView.currentY > top && gridView.currentY < bottom){
                        Toast.makeText(getApplicationContext(),"Close"+position,Toast.LENGTH_SHORT).show();
                        gridView.removeViewAt(position);
                    }
                        Log.d("", "position" + position + " left" + left + " top" + top + " right" + right + " bottom" + bottom);
                }
            }
        });

//        gridView.removeViewAt();


    }

    private View getImageView(int image) {
        // create a new textview
    /*    final TextView rowTextView = new TextView(this);

        // set some properties of rowTextView or something
        rowTextView.setText("This is row #" + image);
        return rowTextView;
*/
        ImageView imageView = new AppCompatImageView(MainActivity.this) {
            @Override
            protected void onMeasure(int widthMeasureSpec,
                                     int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
            }

            @Override
            protected void onDraw(Canvas canvas) {
                Bitmap   bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ten);
                 canvas.drawBitmap(bitmap, new Rect(0,0,400,400), new Rect(0,0,240,135) , null);

                super.onDraw(canvas);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setTextSize(36);
//                canvas.drawText(getTag().toString(), getWidth() / _2, getHeight() / _2, paint);
            }
        };
    /*MyView myView=new MyView(this){
            @Override
            protected void onMeasure(int widthMeasureSpec,
                                     int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
            }

        };
        return myView;
    */

//        ImageView imageView1= myView.getImageView();
        Bitmap bitmap = null;
//        switch (image) {
//            case 0:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.zero);
//                break;
//            case 1:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.one);
//                break;
//            case 2:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.two);
//                break;
//            case 3:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.three);
//                break;
//            case 4:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.four);
//                break;
//            case 5:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.five);
//                break;
//            case 6:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.six);
//                break;
//            case 7:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.seven);
//                break;
//            case 8:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.eight);
//                break;
//            case 9:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.nine);
//                break;
//            case 10:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ten);
//                break;
//            case 11:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.add);
//                imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()));
//                imageView.setTag("add");
//                return imageView;
//            case 12:
//                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.add);
//                imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()));
//                imageView.setTag("dummy");
//                imageView.setVisibility(View.INVISIBLE);
//                return imageView;

//        }

        Glide.with(this).load("https://vignette2.wikia.nocookie.net/simpsons/images/1/11/Homersimpson.jpg/revision/latest?cb=20121229201104").into(imageView);
//        imageView1.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()));
        return imageView;
    }

    class GridViewAdaptor extends BaseAdapter {

        @Override
        public int getCount() {
            return integers.size() + 3;
        }

        @Override
        public Object getItem(int arg0) {
            return arg0;
        }

        @Override
        public long getItemId(int arg0) {
            return arg0;
        }
        Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
        @Override
        public View getView(int arg0, View arg1, ViewGroup arg2) {
            AppCompatImageView imageView;
            if (arg1 == null) {
                imageView = new AppCompatImageView(MainActivity.this) {
                    @Override
                    protected void onMeasure(int widthMeasureSpec,
                                             int heightMeasureSpec) {
                        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
                    }

                    @Override
                    protected void onDraw(Canvas canvas) {
                        canvas.drawColor(Color.TRANSPARENT);
    /*instantiate a bitmap and draw stuff here, it could well be another
    class which you systematically update via a different thread so that you can get a fresh updated
    bitmap from, that you desire to be updated onto the custom ImageView.
   That will happen everytime onDraw has received a call i.e. something like:*/

                        super.onDraw(canvas);
                        Paint paint = new Paint();
                        paint.setColor(Color.RED);
                        paint.setTextSize(36);
                        canvas.drawText(getTag().toString(), getWidth() / 2, getHeight() / 2, paint);
                        canvas.drawBitmap(bitmap,getWidth()/2,getHeight()/2,paint);
                    }
                };
            } else {
                imageView = (AppCompatImageView) arg1;
            }

            imageView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            imageView.setBackgroundColor(Color.BLUE);
            imageView.setScaleType(ScaleType.FIT_XY);
            Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
//            Context context = getApplicationContext();
//            Drawable drawable = //context.getResources().getDrawable(R.drawable.ic_launcher);
//                    ContextCompat.getDrawable(MainActivity.this, R.mipmap.ic_launcher);
//            Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
            switch (arg0) {
                case 0:
                    imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2));
                    imageView.setBackgroundColor(Color.RED);
                    imageView.setTag(integers.get(0));
                    return imageView;
                case 1:
                    imageView.setImageBitmap(Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, 0, bitmap.getWidth() / 2, bitmap.getHeight() / 2));
                    imageView.setBackgroundColor(Color.GREEN);
                    imageView.setTag(integers.get(0));
                    return imageView;
                case 3:
                    imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, bitmap.getHeight() / 2, bitmap.getWidth() / 2, bitmap.getHeight() / 2));
                    imageView.setBackgroundColor(Color.YELLOW);
                    imageView.setTag(integers.get(0));
                    return imageView;
                case 4:
                    imageView.setImageBitmap(Bitmap.createBitmap(bitmap, bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getWidth() / 2, bitmap.getHeight() / 2));
                    imageView.setBackgroundColor(Color.MAGENTA);
                    imageView.setTag(integers.get(0));
                    return imageView;
                default:
                    if (arg0 > 1 && arg0 <= 4) {
                        imageView.setTag(integers.get(arg0 - 1));
                    } else {
                        imageView.setTag(integers.get(arg0 - 3));
                    }

                    imageView.setImageResource(R.mipmap.ic_launcher);
                    return imageView;
            }
        }

    }
}

