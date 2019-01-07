package com.example.anish.speechtextdemo;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    TextView resultText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultText = (TextView)findViewById(R.id.textView);
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
                    ArrayList<String> result = i.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    resultText.setText(result.get(0));
                }
                break;
        }
    }
}
