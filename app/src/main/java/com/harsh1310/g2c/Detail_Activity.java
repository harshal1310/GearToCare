package com.harsh1310.g2c;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;

public class Detail_Activity extends AppCompatActivity {

ImageView about;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_);
        about=findViewById(R.id.about);

        Bitmap bMap = BitmapFactory.decodeResource(getResources(), R.drawable.about1);

        Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, 500, 800, true);

        about.setImageBitmap(bMapScaled);

    }
}