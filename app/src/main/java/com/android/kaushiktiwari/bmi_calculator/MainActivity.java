package com.android.kaushiktiwari.bmi_calculator;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.AppCompatTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener {
    TextView tvinfo2, tvCity, tvWeather;
    SharedPreferences sp;
    Spinner spinInch, spinFeet;
    double i1, f1, h1, w1, bmi1;
    Button btnCalc, btnView;
    EditText etWeight;
    GoogleApiClient gac;
    String msg;
    TextClock textClock;
    Location loc;
    static MeraDbHandler db;
    TextToSpeech tts;
     private Vibrator vib1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        int o= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
        setRequestedOrientation(o);

        btnCalc = (Button) findViewById(R.id.btnCalc);
        btnView = (Button) findViewById(R.id.btnView);
        tvCity = (TextView) findViewById(R.id.tvCity);
        db = new MeraDbHandler(MainActivity.this);
        tts=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                tts.setLanguage(Locale.ENGLISH);
            }
        });
        tvWeather = (TextView) findViewById(R.id.tvWeather);
        etWeight = (EditText) findViewById(R.id.etWeight);
            spinInch = (Spinner) findViewById(R.id.spinInch);
            spinFeet = (Spinner) findViewById(R.id.spinFeet);
            tvinfo2 = (TextView) findViewById(R.id.tvInfo2);
        vib1=(Vibrator)getSystemService(Context.VIBRATOR_SERVICE);

        sp = getSharedPreferences("MYP1", MODE_PRIVATE);
            String name = sp.getString("n", "");
            tvinfo2.setText("Welcome " + name);


            ArrayList<Integer> feet = new ArrayList<>();
            feet.add(1);
            feet.add(2);
            feet.add(3);
            feet.add(4);
            feet.add(5);
            feet.add(6);
            feet.add(7);
            feet.add(8);
            feet.add(9);
            feet.add(10);
            ArrayAdapter<Integer> feetadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, feet);
            spinFeet.setAdapter(feetadapter);

            ArrayList<Integer> inch = new ArrayList<>();
            inch.add(0);
            inch.add(1);
            inch.add(2);
            inch.add(3);
            inch.add(4);
            inch.add(5);
            inch.add(6);
            inch.add(7);
            inch.add(8);
            inch.add(9);
            inch.add(10);
            inch.add(11);
            ArrayAdapter<Integer> inchadapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_item, inch);
            spinInch.setAdapter(inchadapter);

            spinFeet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    f1 = Double.parseDouble(adapterView.getItemAtPosition(i).toString());

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            spinInch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    i1 = Double.parseDouble(adapterView.getItemAtPosition(i).toString());
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });

            btnCalc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (etWeight.length() == 0) {
                        try {
                            etWeight.setError("Invalid");
                            Snackbar.make(view,"enter",Snackbar.LENGTH_SHORT);
                            vib1.vibrate(120);
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(MainActivity.this, "ISSUE= "+e, Toast.LENGTH_LONG).show();
                        }
                    }
                    else {
                        i1 = i1 * 0.0254;
                        f1 = f1 * 0.3048;
                        h1 = i1 + f1;
                        w1 = Double.parseDouble(etWeight.getText().toString());
                        bmi1 = w1 / (h1 * h1);
                     String temp3=String.format("%.2f",bmi1);

                       tts.speak(" YOUR BODY MASS INDEX IS "+temp3,TextToSpeech.QUEUE_ADD,null);

                        Intent i = new Intent(MainActivity.this, CalcActivity.class);
                        i.putExtra("bmi", bmi1 + "");
                        startActivity(i);
                    }
                }
            });

            GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this);
            builder.addApi(LocationServices.API);
            builder.addOnConnectionFailedListener(this);
            builder.addConnectionCallbacks(this);
            gac = builder.build();

            btnView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(MainActivity.this, ViewActivity.class));
                }
            });


        }//end of on create


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.m1, menu);//to show menu on gui
        return true;

    }


    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == R.id.about) {
            Toast.makeText(this, "app developed by kaushik", Toast.LENGTH_SHORT).show();
        }

        if (item.getItemId() == R.id.website) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("http://" + "www.heartfoundation.org.au/your-heart/know-your-risks/healthy-weight/bmi-calculator"));
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(Bundle bundle) {
        loc =LocationServices.FusedLocationApi.getLastLocation(gac);
        if (loc != null) {
            double lat = loc.getLatitude();
            double lon = loc.getLongitude();
            Geocoder g = new Geocoder(this, Locale.ENGLISH);
            try {
                List<Address> la = g.getFromLocation(lat, lon, 1);
                android.location.Address add = la.get(0);
                msg = add.getLocality();
                tvCity.setText("City:" + msg);

                String url = "http://api.openweathermap.org/data/2.5/weather?units=metric";
                String api = "c2cd23c5998c28a3a5d3373de5cdc7f6";
                MyTask mt = new MyTask();
                mt.execute(url + "&q=" + msg + "&appid=" + api);


            } catch (IOException e) {
                Toast.makeText(this, "issue", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Toast.makeText(this, "connection suspended", Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Toast.makeText(this, "connection failed", Toast.LENGTH_SHORT).show();
    }

    public void onResume() {
        super.onResume();
        if (gac != null)
            gac.connect();
    }


    public void onPause() {
        super.onPause();
        if (gac != null)
            gac.disconnect();
    }


    class MyTask extends AsyncTask<String, Void, Double> {
        double temp;

        @Override
        protected Double doInBackground(String... strings) {

            try {
                URL url = new URL(strings[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.connect();

                InputStreamReader isr = new InputStreamReader(conn.getInputStream());
                BufferedReader br = new BufferedReader(isr);
                String json = "", line = "";

                while ((line = br.readLine()) != null) {
                    json = json + line + "\n";
                }

                JSONObject j = new JSONObject(json);
                JSONObject p = j.getJSONObject("main");
                temp = p.getDouble("temp");
            } catch (Exception e) {
                Log.d("KK123", e.toString());
                Toast.makeText(MainActivity.this, "issue", Toast.LENGTH_SHORT).show();
            }
            return temp;
        }

        @Override
        protected void onPostExecute(Double aDouble) {
            super.onPostExecute(aDouble);
            tvWeather.setText("Temp: " + aDouble);
        }


    }
    public void onBackPressed(){
        AlertDialog.Builder ab=new AlertDialog.Builder(this);
        ab.setMessage("DO U WANT TO EXIT");
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
