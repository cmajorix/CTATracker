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

        tb = findViewById(R.id.toolbar);
        setSupportActionBar(tb);
        ab = getSupportActionBar();
        fab = findViewById(R.id.floating_action_button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!e.getText().toString().equals("")) {
                    i = new Intent(MainActivity.this, ResultActivity.class);
                    i.putExtra("stopcode", e.getText().toString());
                    startActivity(i);
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
                        i = new Intent(MainActivity.this, ResultActivity.class);
                        i.putExtra("stopcode", e.getText().toString());
                        startActivity(i);
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

}
