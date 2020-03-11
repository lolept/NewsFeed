package com.example.gson;

import android.content.Intent;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.Bundle;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class PostPage extends AppCompatActivity implements View.OnTouchListener {
    TextView title,text, likes, author, comments;
    EditText com;
    ImageView image;

    private static final String TAG = "Touch" ;


    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    PointF start = new  PointF();
    public static PointF mid = new PointF();


    public static final int NONE = 0;
    public static final int DRAG = 1;
    public static final int ZOOM = 2;
    public static int mode = NONE;

    float oldDist;
    public float finalScale = 1f;

    private float[] matrixValues = new float[9];

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_page);
        Intent intent = getIntent();
        String text_title = intent.getStringExtra("title");
        String image_link = intent.getStringExtra("image link");
        String full_text = intent.getStringExtra("text");
        double text_likes = intent.getDoubleExtra("likes", 0.0);
        String text_author = intent.getStringExtra("author");
        String text_comments = intent.getStringExtra("comments");

        title = (TextView) findViewById(R.id.title);
        text = (TextView) findViewById(R.id.text);
        likes = (TextView) findViewById(R.id.likes);
        author = (TextView) findViewById(R.id.author);
        comments = (TextView) findViewById(R.id.comments);
        image = (ImageView) findViewById(R.id.image);
        com = (EditText) findViewById(R.id.com);
        Button close = (Button) findViewById(R.id.close);

        title.setText(text_title);
        Picasso.get().load(image_link).into(image);
        text.setText(full_text);
        likes.setText(text_likes + "");
        author.setText(text_author);
        comments.setText(text_comments);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        image.setOnTouchListener(this);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {

        ImageView view = (ImageView) v;

        switch (event.getAction() & MotionEvent.ACTION_MASK) {

            case MotionEvent.ACTION_DOWN:

                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG" );
                mode = DRAG;
                break;

            case MotionEvent.ACTION_POINTER_DOWN:

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 10f) {

                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM" );
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG) {

                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y);
                }
                else if (mode == ZOOM) {

                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 10f) {
                        if(finalScale < 2f && finalScale > 0.5f) {
                            matrix.set(savedMatrix);
                            float scale = newDist / oldDist;
                            finalScale *= scale;
                            matrix.postScale(scale, scale, mid.x, mid.y);
                        }
                    }
                }
                break;

            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_POINTER_UP:

                mode = NONE;
                Log.d(TAG, "mode=NONE" );
                break;
        }


        view.setImageMatrix(matrix);

        return true;
    }

    private float spacing(MotionEvent event) {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float)Math.sqrt(x * x + y * y);
    }

    private void midPoint(PointF point, MotionEvent event) {

        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
        com.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}
