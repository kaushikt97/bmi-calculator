package com.android.kaushiktiwari.bmi_calculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Vibrator;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.spark.submitbutton.SubmitButton;


public class RegisterActivity extends AppCompatActivity {

    EditText etName,etAge,etPhone;
    RadioGroup rgGender;
    SubmitButton btnRegister;
    private Vibrator vib;
    Animation animShake;
    int a=0,b=0,c=0,d=0;
    TextInputLayout letName,letAge,letPhone;
     SharedPreferences sp1;
    int id=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


          super.onCreate(savedInstanceState);
          setContentView(R.layout.activity_register);
          int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
          setRequestedOrientation(o);

          etName=(EditText)findViewById(R.id.etName);
          etAge=(EditText)findViewById(R.id.etAge);
          etPhone=(EditText)findViewById(R.id.etPhone);

        letName=(TextInputLayout) findViewById(R.id.letName);
        letAge=(TextInputLayout) findViewById(R.id.letAge);
        letPhone=(TextInputLayout) findViewById(R.id.letPhone);
       animShake=AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
        vib=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

          rgGender=(RadioGroup)findViewById(R.id.rgGender);
          sp1=getSharedPreferences("MYP1",MODE_PRIVATE);
          btnRegister=(SubmitButton)findViewById(R.id.btnRegister);
          String n1=sp1.getString("n","");
           if(n1.length()!=0)
           {
            Intent i=new Intent(RegisterActivity.this,MainActivity.class);
            startActivity(i);
            finish();
           }
          else {
            btnRegister.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String name = etName.getText().toString();
                    String age = etAge.getText().toString();
                    String mob = etPhone.getText().toString();

                    id = rgGender.getCheckedRadioButtonId();
                    a = 0;b=0;c=0;d=0;

                    if (name.length() == 0) {
                        etName.setError("Invalid");
                        letName.setError("plz Enter a Name");
                        etName.requestFocus();
                        etName.setAnimation(animShake);
                        etName.startAnimation(animShake);
                        vib.vibrate(120);
                        a = 1;
                        b=1;
                    }
                    if (age.length() == 0 ) {
                        etAge.setError("Invalid");
                        letAge.setError("plz Enter a Age");
                        etAge.setAnimation(animShake);
                        etAge.startAnimation(animShake);
                        vib.vibrate(120);
                        a = 1;
                        c=1;
                        if(b!=1)
                            etAge.requestFocus();
                    }
                    if (etPhone.length() == 0 || etPhone.length()!=10) {
                        etPhone.setError("Invalid");
                        letPhone.setError("plz Enter a 10digit Mobile Number");
                        etPhone.setAnimation(animShake);
                        etPhone.startAnimation(animShake);
                        vib.vibrate(120);
                        a = 1;
                        if(b!=1&&c!=1)
                            etPhone.requestFocus();
                    }

                    if(a!=1) {
                        letPhone.setErrorEnabled(false);
                        letAge.setErrorEnabled(false);
                        letName.setErrorEnabled(false);

                        RadioButton rb = rgGender.findViewById(id);
                        String gen = rb.getText().toString();
                        SharedPreferences.Editor editor = sp1.edit();
                        editor.putString("n", name);
                        editor.putString("a", age);
                        editor.putString("p", mob);
                        editor.putString("g", gen);
                        editor.commit();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(4000);
                                    Intent i=new Intent(RegisterActivity.this,MainActivity.class);
                                    startActivity(i);
                                    finish();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                    }
                }

            });

        }
    }
    public void onBackPressed(){
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setMessage("DO U WANT TO EXIT1");
        ab.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        ab.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        ab.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog a = ab.create();
        a.setTitle("BYE BYE !!!!");
        a.show();

    }
}
