package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    TextView memberDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        memberDetails = (TextView) findViewById(R.id.memberDetails);

        String details = getIntent().getStringExtra("key");

        if(details != null){
            memberDetails.setText(details);
        }
    }
}