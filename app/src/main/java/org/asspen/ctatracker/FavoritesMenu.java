package org.asspen.ctatracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

public class FavoritesMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites_menu);
        Context context = this;
        SharedPreferences sharedPref = context.getSharedPreferences("favorites", Context.MODE_PRIVATE);
    }
}
