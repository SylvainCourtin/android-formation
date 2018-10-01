package com.example.courtin.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.courtin.myapplication.utils.Constantes;
import com.example.courtin.myapplication.R;
import com.example.courtin.myapplication.utils.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if(checkPreferences(savedInstanceState)) {
            Intent intent = new Intent(this, ChatActivity.class);
            startActivity(intent);
        } else {
            generate(savedInstanceState);
            signinButton();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("ETAT_MSG_VISIBLE", findViewById(R.id.failConnexion).getVisibility() == View.VISIBLE);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        findViewById(R.id.failConnexion).setVisibility(savedInstanceState.getBoolean("ETAT_MSG_VISIBLE", false) ? View.VISIBLE : View.INVISIBLE);

    }

    private boolean checkPreferences(Bundle savedInstanceState){
        SharedPreferences prefs = getSharedPreferences(Constantes.SECRET, MODE_PRIVATE);
        String restoredText = prefs.getString("username", null);
        return restoredText != null;
    }

    private void generate(Bundle savedInstanceState){
        final Button button = findViewById(R.id.buttonConnexion);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final TextView msgConnexionfail = findViewById(R.id.failConnexion);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(0);
                Singleton.service.login(username.getText().toString(),password.getText().toString()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            Log.d("spy", "success connexion");
                            msgConnexionfail.setVisibility(View.INVISIBLE);
                            saveData(username.getText().toString());
                            LoginActivity.this.loginSuccess();
                        }
                        else
                        {
                            Log.d("spy", "La connexion a échoué " +response.code());
                            msgConnexionfail.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.d("spy", "La connexion a échoué onFailure");
                        msgConnexionfail.setVisibility(View.VISIBLE);
                    }
                });
                progressBar.setVisibility(View.GONE);
            }

            public void saveData(String username){
                SharedPreferences.Editor editor = getSharedPreferences(Constantes.SECRET, MODE_PRIVATE).edit();
                editor.putString("username", username);
                editor.putString("token", "");
                editor.apply();
            }
        });


    }

    public void signinButton() {
        final Button button = findViewById(R.id.buttonSignin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), SigninActivity.class));
            }
        });
    }

    public void loginSuccess() {
        startActivity(new Intent(this, ChatActivity.class));
    }
}
