package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button member1;
    Button member2;
    Button member3;
    Button member4;
    Button member5;
    Button member6;

    String member1Details = "Name: John Smith\nAge: 21\nGender: Male\nHeight: 5'10\"\nWeight: 70kg\nBMI: 25.0";
    String member2Details = "Name: Jane Smith\nAge: 20\nGender: Female\nHeight: 5'5\"\nWeight: 55kg\nBMI: 20.0";
    String member3Details = "Name: John Doe\nAge: 22\nGender: Male\nHeight: 6'0\"\nWeight: 80kg\nBMI: 25.0";
    String member4Details = "Name: Jane Doe\nAge: 21\nGender: Female\nHeight: 5'7\"\nWeight: 60kg\nBMI: 20.0";
    String member5Details = "Name: John Smith\nAge: 23\nGender: Male\nHeight: 5'11\"\nWeight: 75kg\nBMI: 25.0";
    String member6Details = "Name: Jane Smith\nAge: 22\nGender: Female\nHeight: 5'6\"\nWeight: 65kg\nBMI: 20.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        member1 = (Button) findViewById(R.id.member1);
        member1.setOnClickListener(this);
        member2 = (Button) findViewById(R.id.member2);
        member2.setOnClickListener(this);
        member3 = (Button) findViewById(R.id.member3);
        member3.setOnClickListener(this);
        member4 = (Button) findViewById(R.id.member4);
        member4.setOnClickListener(this);
        member5 = (Button) findViewById(R.id.member5);
        member5.setOnClickListener(this);
        member6 = (Button) findViewById(R.id.member6);
        member6.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.member1){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("key", member1Details);
            startActivity(intent);
        }
        if(view.getId() == R.id.member2){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("key", member2Details);
            startActivity(intent);
        }
        if(view.getId() == R.id.member3){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("key", member3Details);
            startActivity(intent);
        }
        if(view.getId() == R.id.member4){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("key", member4Details);
            startActivity(intent);
        }
        if(view.getId() == R.id.member5){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("key", member5Details);
            startActivity(intent);
        }
        if(view.getId() == R.id.member6){
            Intent intent = new Intent(this, DetailsActivity.class);
            intent.putExtra("key", member6Details);
            startActivity(intent);
        }
    }
}