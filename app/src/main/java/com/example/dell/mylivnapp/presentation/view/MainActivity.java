package com.example.dell.mylivnapp.presentation.view;

import android.arch.lifecycle.ViewModelProviders;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.mylivnapp.R;
import com.example.dell.mylivnapp.Utils;
import com.example.dell.mylivnapp.data.model.Item;
import com.example.dell.mylivnapp.presentation.viewmodel.ItemsViewModel;
import com.example.dell.mylivnapp.presentation.viewmodel.Response;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    @BindView(R.id.dgv)
    DraggableGridView gridView;
    private ItemsViewModel viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(MainActivity.this).get(ItemsViewModel.class);

        List<Item> itemList = viewModel.getItemList();
        for (Item item : itemList) {
            getImageView()
        }
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
//                    gridView.removeViewAt(position);
//                    gridView.removeViewAt(position);
//                    gridView.removeViewAt(position);
//                    gridView.removeViewAt(position);
//                    gridView.addView(getImageView(10));
//                    gridView.addView(getImageView(11));
//                    gridView.addView(getImageView(12));
//                    gridView.addView(getImageView(12));
//                    gridView.addView(getImageView(12));

                    addItem();
                } else {

                    deleteItem(view, position);
                }
            }
        });


    }

    ImageView getImageView(Item item) {
        ImageView imageView = new AppCompatImageView(MainActivity.this) {
            @Override
            protected void onMeasure(int widthMeasureSpec,
                                     int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
            }

            @Override
            protected void onDraw(Canvas canvas) {
                super.onDraw(canvas);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setTextSize(36);

            }


        };
        Glide.with(this).load(item.getImageUrlString()).into(imageView);
        return imageView
    }

    private boolean addItem() {
        //show dialog
        //crete item object
        //update the the viewmoidel
        Utils.showAlertDialog("Add", "Enter url", MainActivity.this);
//        Glide.with(this).load("https://vignette2.wikia.nocookie.net/simpsons/images/1/11/Homersimpson.jpg/revision/latest?cb=20121229201104").
//                into(imageView);
//
        return true;
    }

    private void deleteItem(View view, int position) {
        int left = view.getLeft() + view.getWidth() / 2;
        int right = view.getRight();
        int top = view.getTop();
        int bottom = view.getBottom() - view.getHeight() / 2;

        if (gridView.currentX > left && gridView.currentX < right && gridView.currentY > top && gridView.currentY < bottom) {
            Toast.makeText(getApplicationContext(), "Close" + position, Toast.LENGTH_SHORT).show();
            gridView.removeViewAt(position);
        }
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
                renderDataState(response.data);
                break;

            case ERROR:
                renderErrorState(response.error);
                break;
        }
    }

    private void renderLoadingState() {
//        greetingTextView.setVisibility(View.GONE);
//        loadingIndicator.setVisibility(View.VISIBLE);
    }

    private void renderDataState(List<Item> items) {
//        loadingIndicator.setVisibility(View.GONE);
//        greetingTextView.setVisibility(View.VISIBLE);
//        greetingTextView.setText(greeting);


        for (Item item : items) {
            gridView.addView(getView(item.getImageUrlString()));
        }
    }

    private View getView(String imageUrlString) {
        ImageView imageView = new AppCompatImageView(MainActivity.this) {
            @Override
            protected void onMeasure(int widthMeasureSpec,
                                     int heightMeasureSpec) {
                super.onMeasure(widthMeasureSpec, heightMeasureSpec);
                setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth());
            }

            @Override
            protected void onDraw(Canvas canvas) {
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ten);
                canvas.drawBitmap(bitmap, new Rect(0, 0, 400, 400), new Rect(0, 0, 240, 135), null);

                super.onDraw(canvas);
                Paint paint = new Paint();
                paint.setColor(Color.RED);
                paint.setTextSize(36);
//                canvas.drawText(getTag().toString(), getWidth() / _2, getHeight() / _2, paint);

            }


        };
        Glide.with(this).load(imageUrlString).into(imageView);

        return imageView;
    }

    private void renderErrorState(Throwable throwable) {
//        Timber.e(throwable);
//        loadingIndicator.setVisibility(View.GONE);
//        greetingTextView.setVisibility(View.GONE);
//        Toast.makeText(this, R.string.greeting_error, Toast.LENGTH_SHORT).show();
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
                Bitmap bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ten);
                canvas.drawBitmap(bitmap, new Rect(0, 0, 400, 400), new Rect(0, 0, 240, 135), null);

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
        if (image == 0) {
            bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.add);
            imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()));
            imageView.setTag("add");
        } else
//

            Glide.with(this).load("https://vignette2.wikia.nocookie.net/simpsons/images/1/11/Homersimpson.jpg/revision/latest?cb=20121229201104").into(imageView);
//        imageView1.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()));
        return imageView;
    }

}

