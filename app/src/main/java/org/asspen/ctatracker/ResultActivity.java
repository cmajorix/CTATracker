package org.asspen.ctatracker;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONObject;

public class ResultActivity extends AppCompatActivity {
    TextView time;
    int stopCode;
    String stopURL;
    JSONObject j;
    TextView routeNum;
    Toolbar tb;
    ActionBar ab;
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
        stopCode = getIntent().getIntExtra("stopcode", 0);
        stopURL = "http://www.ctabustracker.com/bustime/api/v2/getpredictions?key=Hv9D3gWpkmUJv5a4QX5Mu55xM&format=json&stpid=" + stopCode;
        if (getIntent().getStringExtra("timeMin").equals("DUE")) {
            time.setText("Bus due!");
        } else {
            time.setText(getIntent().getStringExtra("timeMin") + " min");
        }
        routeNum.setText("Route " + getIntent().getStringExtra("routeNum"));
        time.setText(getIntent().getStringExtra("timeMin") + " min");
    }
}
