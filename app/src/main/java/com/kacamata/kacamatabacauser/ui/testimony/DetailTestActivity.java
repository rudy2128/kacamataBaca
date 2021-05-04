package com.kacamata.kacamatabacauser.ui.testimony;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kacamata.kacamatabacauser.R;

public class DetailTestActivity extends AppCompatActivity {
    TextView tvId,tvDesc;
    ImageView imgTest;
    String testId,description,imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_test);
        imgTest = findViewById(R.id.img_test);
        tvId = findViewById(R.id.tv_testId);
        tvDesc = findViewById(R.id.tv_description);

        imageUrl = getIntent().getStringExtra("imgTest");
        testId = getIntent().getStringExtra("tvTestId");
        description = getIntent().getStringExtra("tvDesc");

        tvId.setText(testId);
        tvDesc.setText(description);
        Glide.with(getApplicationContext())
                .load(imageUrl)
                .override(400,400)
                .into(imgTest);
    }
}