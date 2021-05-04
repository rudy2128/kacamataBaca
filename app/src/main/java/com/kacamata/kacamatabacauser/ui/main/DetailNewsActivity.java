package com.kacamata.kacamatabacauser.ui.main;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kacamata.kacamatabacauser.R;

public class DetailNewsActivity extends AppCompatActivity {
    TextView tvId,tvTitle,tvDesc;
    ImageView imgMain;
    String imageUrl,proId,title,description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_news);
        imgMain = findViewById(R.id.img_main);
        tvId = findViewById(R.id.tv_proId);
        tvTitle = findViewById(R.id.tv_title);
        tvDesc = findViewById(R.id.tv_description);

        imageUrl = getIntent().getStringExtra("imgMain");
        proId = getIntent().getStringExtra("tvProId");
        title = getIntent().getStringExtra("tvTitle");
        description = getIntent().getStringExtra("tvDesc");

        tvId.setText(proId);
        tvTitle.setText(title);
        tvDesc.setText(description);
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .override(400,400)
                .into(imgMain);

    }
}