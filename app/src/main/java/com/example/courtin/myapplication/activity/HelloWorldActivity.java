package com.example.courtin.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.courtin.myapplication.utils.Constantes;
import com.example.courtin.myapplication.exception.ExceptionSharedData;
import com.example.courtin.myapplication.R;


public class HelloWorldActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helloworld);
        try{
            final String username = getData();
            TextView textView = findViewById(R.id.hello);
            textView.setText("Hello "+ username);
        } catch (ExceptionSharedData e)
        {
            finish();
        }

    }

    private String getData() throws ExceptionSharedData{
        SharedPreferences prefs = getSharedPreferences(Constantes.SECRET, MODE_PRIVATE);
        String restoredText = prefs.getString("username", null);
        if(restoredText != null)
            return restoredText;
        else
            throw new ExceptionSharedData();
    }

    private void logout(){
        SharedPreferences.Editor editor = getSharedPreferences(Constantes.SECRET, MODE_PRIVATE).edit();
        editor.clear();
        editor.apply();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.men_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
