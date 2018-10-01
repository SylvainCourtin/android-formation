package com.example.courtin.myapplication.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courtin.myapplication.R;
import com.example.courtin.myapplication.exception.ExceptionSharedData;
import com.example.courtin.myapplication.utils.Constantes;
import com.example.courtin.myapplication.utils.Singleton;
import com.example.courtin.myapplication.views.MyAdapter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity {

    private MyAdapter mAdapter;
    private String username;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        try {
            username = getData();
            TextView textView = findViewById(R.id.welcome);
            textView.setText("Bienvenue "+ username);
            buildLayout();
            initActionSendMessage();
        } catch (ExceptionSharedData e) {
            logout();
        }

    }

    private String getData() throws ExceptionSharedData {
        SharedPreferences prefs = getSharedPreferences(Constantes.SECRET, MODE_PRIVATE);
        String restoredText = prefs.getString("username", null);
        if (restoredText != null)
            return restoredText;
        else
            throw new ExceptionSharedData();
    }

    private void logout() {
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

    private void buildLayout(){
        RecyclerView mRecyclerView = findViewById(R.id.reyclerViewChat);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new MyAdapter();
        mRecyclerView.setAdapter(mAdapter);

    }

    private void initActionSendMessage(){
        findViewById(R.id.sendMessage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               sendMessage();
            }

            private void sendMessage(){
                EditText message = findViewById(R.id.myMessage);
                Singleton.service.sendMessage("",message.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            Log.d("spy", "success message send");
                            Toast.makeText(getApplicationContext(),"Send", Toast.LENGTH_SHORT).show();
                        }

                        else {
                            try {
                                Log.d("spy", "echec send "+ response.errorBody().string());
                                Toast.makeText(getApplicationContext(),"Error :"+response.code(), Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("spy", "echec onFailure send message ");
                        Toast.makeText(getApplicationContext(),"Error", Toast.LENGTH_SHORT).show();
                    }
                });
                mAdapter.notifyDataSetChanged();
            }
        });
    }
}
