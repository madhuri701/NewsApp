package com.google.newsapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTitle;
    private TextView mDescription;
    private TextView mContent;
    private Button mButton;



    String title,desc,content,imageURL,url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        content = getIntent().getStringExtra("content");
        imageURL = getIntent().getStringExtra("image");
        url = getIntent().getStringExtra("url");

        mTitle = findViewById(R.id.idtitle);
        mDescription = findViewById(R.id.idDescription);

        mContent = findViewById(R.id.idContent);
        mImageView = findViewById(R.id.idImage);
        mButton = findViewById(R.id.idButton);
        mTitle.setText(title);
        mDescription.setText(desc);
        mContent.setText(content);
        Picasso.get().load(imageURL).into(mImageView);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });
    }
}