package com.example.courtin.myapplication.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.courtin.myapplication.R;
import com.example.courtin.myapplication.models.User;
import com.example.courtin.myapplication.utils.Constantes;
import com.example.courtin.myapplication.utils.Singleton;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        final Button signin = findViewById(R.id.createAccount);
        final LinearLayout layoutProgressBar = findViewById(R.id.layoutProgressBar);
        final EditText username = findViewById(R.id.username);
        final EditText password = findViewById(R.id.password);
        final EditText passwordConfirm = findViewById(R.id.passwordConfirm);
        final TextView msgConnexionfail = findViewById(R.id.failConnexion);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layoutProgressBar.setVisibility(View.VISIBLE);

                Singleton.service.signin(new User(username.getText().toString(), password.getText().toString())).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful())
                        {
                            msgConnexionfail.setVisibility(View.INVISIBLE);
                            saveData(username.getText().toString());
                            //HelloWorldActivity.this.loginSuccess();
                        }
                        else
                        {
                            Log.d("spy", "La connexion a échoué");
                            msgConnexionfail.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {

                    }
                });

                layoutProgressBar.setVisibility(View.GONE);
            }


            public void saveData(String username){
                SharedPreferences.Editor editor = getSharedPreferences(Constantes.SECRET, MODE_PRIVATE).edit();
                editor.putString("username", username);
                editor.apply();
            }
        });

        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void loginSuccess() {
        startActivity(new Intent(this, HelloWorldActivity.class));
    }
}
