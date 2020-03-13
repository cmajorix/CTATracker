package org.asspen.ctatracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class ResultActivity extends AppCompatActivity {
    TextView time;
    int stopCode;
    String stopURL;
    JSONObject j;
    TextView routeNum;
    Toolbar tb;
    ActionBar ab;
    ProgressBar pb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        routeNum = findViewById(R.id.routeNum);
        time = findViewById(R.id.time);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        stopCode = getIntent().getIntExtra("stopcode", 0);
        stopURL = "http://www.ctabustracker.com/bustime/api/v2/getpredictions?key=Hv9D3gWpkmUJv5a4QX5Mu55xM&format=json&stpid=" + stopCode;
        new JsonTask().execute(stopURL + stopCode);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private class JsonTask extends AsyncTask<String, String, String> {
        protected void onPreExecute() {
            super.onPreExecute();
            pb.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            pb.setVisibility(View.INVISIBLE);
            try {
                j = new JSONObject(result);
                Log.d("JsonTask", "JSON object parsed successfully");
                //Prediction: j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("prdctdn") + " min"
                //Route Number: "Route " + j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("rt")
                if (j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("prdctdn").equals("DUE")) {
                    time.setText("Bus due!");
                } else {
                    time.setText(j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("prdctdn") + " min");
                }
                routeNum.setText("Route " + j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("rt"));
            } catch (JSONException j) {
                Log.e("JsonTask", "JSON object couldn't be parsed");
            }
        }
    }
}
