package org.asspen.ctatracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    EditText e;
    TextView t;
    Button b;
    ProgressBar pb;
    JSONObject j;
    Intent i;
    Toolbar tb;
    ActionBar ab;
    FloatingActionButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e = findViewById(R.id.editText);
        t = findViewById(R.id.textView);
        b = findViewById(R.id.button);
        pb = findViewById(R.id.progressBar);
        pb.setVisibility(View.INVISIBLE);
        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        fab = findViewById(R.id.floating_action_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!e.getText().toString().equals("")) {
                    new JsonTask().execute("http://www.ctabustracker.com/bustime/api/v2/getpredictions?key=Hv9D3gWpkmUJv5a4QX5Mu55xM&format=json&stpid=" + Integer.parseInt(e.getText().toString()));
                } else {
                    Toast.makeText(MainActivity.this, "No code inputted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    if (!e.getText().toString().equals("")) {
                        new JsonTask().execute("http://www.ctabustracker.com/bustime/api/v2/getpredictions?key=Hv9D3gWpkmUJv5a4QX5Mu55xM&format=json&stpid=" + Integer.parseInt(e.getText().toString()));
                    } else {
                        Toast.makeText(MainActivity.this, "No code inputted", Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FavoritesMenu.class));
            }
        });
    }
    //The JsonTask class is sourced from https://stackoverflow.com/questions/33229869/get-json-data-from-url-using-android from user https://stackoverflow.com/users/6011291/aadigurung
    //TODO: move this damn class back over to the ResultActivity ya bonehead
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
                i = new Intent(MainActivity.this, ResultActivity.class);
                i.putExtra("timeMin", j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("prdctdn"));
                i.putExtra("routeNum", j.getJSONObject("bustime-response").getJSONArray("prd").getJSONObject(0).getString("rt"));

                startActivity(i);
            } catch (JSONException j) {
                Log.e("JsonTask", "JSON object couldn't be parsed");
            }
        }
    }
}
