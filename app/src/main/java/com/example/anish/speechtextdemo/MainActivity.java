package com.example.anish.speechtextdemo;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView resultText;
    WebView ourBrow;
    ArrayList<String> result;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView)findViewById(R.id.textView);
        ourBrow = (WebView)findViewById(R.id.webView1);
        ourBrow.getSettings().setJavaScriptEnabled(true);
        ourBrow.loadUrl("file:///android_asset/anish.html");
        ourBrow.addJavascriptInterface(new WebAppInterface(this), "Android");
    }

    public void onButtonClick(View v){
        if(v.getId()==R.id.imageButton4){
            promptSpeechInput();
        }
    }
    public void promptSpeechInput(){
        Intent i = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        i.putExtra(RecognizerIntent.EXTRA_PROMPT,"Say Something");

        try{
            startActivityForResult(i,100);
        }catch(ActivityNotFoundException e){
            Toast.makeText(MainActivity.this,"Sorry Your Phone does not support speech",Toast.LENGTH_LONG).show();
        }
    }

    public void onActivityResult(int request_code,int result_code,Intent i){
        super.onActivityResult(request_code,result_code,i);
        switch(request_code){
            case 100:
                if(result_code==RESULT_OK && i!=null){
                    result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultText.setText(result.get(0));
                }
                break;
        }
    }

    public class WebAppInterface {
        Context mContext;

        /** Instantiate the interface and set the context */
        WebAppInterface(Context c) {
            mContext = c;
        }

        /** Show a toast from the web page */
        @JavascriptInterface
        public String showToast() {
            return result.get(0);
        }
    }
}
