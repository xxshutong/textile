package org.opentaps.noteapp;

import android.os.Bundle;
import com.phonegap.*;

public class NoteApp extends DroidGap {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.loadUrl("file:///android_asset/www/index.html");
    }
}