package com.example.gson;

import android.content.Intent;
import android.os.Bundle;
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

public class PostPage extends AppCompatActivity {
    TextView title,text, likes, author, comments;
    EditText com;
    ImageView image;
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
