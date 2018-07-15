package com.example.dell.mylivnapp.presentation.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.dell.mylivnapp.R;
import com.example.dell.mylivnapp.Utils;
import com.example.dell.mylivnapp.data.cachemodule.PrefUtils;
import com.example.dell.mylivnapp.data.model.Item;
import com.example.dell.mylivnapp.presentation.viewmodel.ItemsViewModel;
import com.example.dell.mylivnapp.presentation.viewmodel.Response;

import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MainActivity extends AppCompatActivity {
    public static final String DUMMY = "dummy";
    public static final String ADD = "add";
    @BindView(R.id.dgv)
    DraggableGridView gridView;
    private ItemsViewModel viewModel;
    List<Item> itemList;
    static HashMap<String, Item> hashMap = new HashMap<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        viewModel = ViewModelProviders.of(MainActivity.this).get(ItemsViewModel.class);

        itemList = viewModel.getItemList();

        for (Item item : itemList) {
            gridView.addView(getImageView(item));
            hashMap.put(item.getUuid(), item);
        }
        addBtnImpl();

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (view.getTag() != null && view.getTag().equals("add")) {


                    addItem(position);
                } else {

                    viewClick(view, position);
                }
            }
        });


    }

    private void addBtnImpl() {
        gridView.addView(getImageView(ADD));
        gridView.addView(getImageView(DUMMY));
        gridView.addView(getImageView(DUMMY));
        gridView.addView(getImageView(DUMMY));
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
//        imageView.setTag(item.getUuid());
        Glide.with(this).load(item.getImageUrlString()).into(imageView);
        imageView.setTag(item.getUuid());
        return imageView;
    }

    private boolean addItem(int position) {

        showAlertDialog(position);
        return true;
    }

    private void showAlertDialog(int position) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);

        alertDialog.setMessage("Enter Image Url");

        final EditText input = new EditText(MainActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setIcon(R.mipmap.zero);

        alertDialog.setPositiveButton("YES",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String url = input.getText().toString().trim();
                        if (url != null && url.length() > 0) {
                            gridView.removeViewAt(position);
                            gridView.removeViewAt(position);
                            gridView.removeViewAt(position);
                            gridView.removeViewAt(position);
                            Item item = new Item(System.currentTimeMillis() + "", url);
                            List arrayList = PrefUtils.getItems(MainActivity.this);
                            arrayList.add(item);
                            PrefUtils.saveItems(arrayList, MainActivity.this);
                            gridView.addView(getImageView(item));
                            addBtnImpl();
                        }
                    }
                });

        alertDialog.show();
    }


    private void viewClick(View view, int position) {
        int left = view.getLeft() + view.getWidth() / 2;
        int right = view.getRight();
        int top = view.getTop();
        int bottom = view.getBottom() - view.getHeight() / 2;

        if (gridView.currentX > left && gridView.currentX < right && gridView.currentY > top && gridView.currentY < bottom) {
            deleteItem(position);
        } else {
            Utils.showAlertDialog("You have selected the item with identifier " + itemList.get(position).getUuid(), MainActivity.this);
        }
    }

    private void deleteItem(int position) {
        Toast.makeText(getApplicationContext(), "Deleted" + position, Toast.LENGTH_SHORT).show();

        showDeleteAlertDialog(position, "Do you really want to delete the item with identifier " + itemList.get(position).getUuid() + "?", MainActivity.this);
    }

    private void processResponse(Response response) {
        switch (response.status) {
            case LOADING:
                renderLoadingState();
                break;

            case SUCCESS:
//                renderDataState(response.data);
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


//        for (Item item : items) {
//            gridView.addView(getView(item.getImageUrlString()));
//        }
    }

    private void renderErrorState(Throwable throwable) {
//        Timber.e(throwable);
//        loadingIndicator.setVisibility(View.GONE);
//        greetingTextView.setVisibility(View.GONE);
//        Toast.makeText(this, R.string.greeting_error, Toast.LENGTH_SHORT).show();
    }


    private ImageView getImageView(String type) {
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
        Bitmap bitmap = null;
        switch (type) {
            case ADD:
                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.add);

                break;

            case DUMMY:
                bitmap = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.add);
                imageView.setVisibility(View.INVISIBLE);
                break;
        }

        imageView.setImageBitmap(Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight()));
        imageView.setTag(type);
        return imageView;
    }

    public void showDeleteAlertDialog(int position, final String message, @NonNull final Activity activity) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(activity);
        alertDialog.setMessage(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);

        alertDialog.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        gridView.removeViewAt(position);
                        itemList.remove(position);
                        PrefUtils.saveItems(itemList, MainActivity.this);

                    }
                });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog.show();
    }

}

