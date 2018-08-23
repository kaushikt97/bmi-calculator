package com.android.kaushiktiwari.bmi_calculator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.spark.submitbutton.SubmitButton;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import static com.android.kaushiktiwari.bmi_calculator.R.color.colorAccent;

public class CalcActivity extends AppCompatActivity

{
    TextView tvData1,tvData2,tvData3,tvData4,tvData5;
    Button btnBack,btnShare;
    double x;
    SubmitButton btnSave;
    FloatingActionButton fabCall;
    String temp,name,age,gender,phone,temp2;
    SharedPreferences sp2;
//    TextToSpeech tts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calc);
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);
        tvData1=(TextView)findViewById(R.id.tvData1);
        tvData2=(TextView)findViewById(R.id.tvData2);
        tvData3=(TextView)findViewById(R.id.tvData3);
        tvData4=(TextView)findViewById(R.id.tvData4);
        tvData5=(TextView)findViewById(R.id.tvData5);
        btnBack=(Button)findViewById(R.id.btnBack);
        btnSave=(SubmitButton)findViewById(R.id.btnSave);
        btnShare=(Button)findViewById(R.id.btnShare);
        fabCall=(FloatingActionButton)findViewById(R.id.fabCall);
        Intent i=getIntent();
        String b=i.getStringExtra("bmi");
        x=Double.parseDouble(b);
        temp2=String.format("%.2f",x);
        sp2=getSharedPreferences("MYP1",MODE_PRIVATE);
        name= sp2.getString("n","");
        age= sp2.getString("a","");
        phone= sp2.getString("p", "");
        gender= sp2.getString("g","");
        x=Double.parseDouble(temp2);
        if(x<18.5) {
            temp = "Underweight";
            tvData2.setTextColor(android.graphics.Color.RED);

        }
        else if(x>=18.5&x<=24.9) {
            temp = "Normalweight";
            tvData3.setTextColor(android.graphics.Color.RED);

        }
        else if(x>=25&&x<=29.9) {
            temp = "Overweight";
            tvData4.setTextColor(android.graphics.Color.RED);

        }
                    else
        {
            temp="Obese";
            tvData5.setTextColor(android.graphics.Color.RED);

        }

        tvData1.setText("Your BMI is "+temp2+" & you are "+temp);

        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_DIAL);
                i.setData(Uri.parse("tel:"+"9004457005"));
                startActivity(i);
            }
        });


        btnBack.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  startActivity(new Intent(CalcActivity.this,MainActivity.class));
                  finish();
              }
          });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");

                i.putExtra(Intent.EXTRA_TEXT,"Name : "+name+"\n"+"Age : "+age+"\n"+"Phone :"+phone+"\n"+"Gender : "+gender+"\n"+"BMI :"+x+"\n"+temp);
                 startActivity(i);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                String y= DateFormat.getDateTimeInstance().format(new Date());

                MainActivity.db.addperson(temp2,temp,y);


            }
        });

     /*   tts=new TextToSpeech(CalcActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });*/



    }
}
