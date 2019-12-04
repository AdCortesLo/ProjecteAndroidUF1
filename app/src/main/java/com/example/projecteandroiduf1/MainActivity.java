package com.example.projecteandroiduf1;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    EditText etUser;
    EditText etPass;
    public static String sharedPreferencesFile = "SharedPreferencesProjecte";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = findViewById(R.id.editTextUsuariLogin);
        etPass = findViewById(R.id.editTextPasswordLogin);

        Button btNewUser = findViewById(R.id.btNewUser);
        btNewUser.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                new AlertDialog.Builder(MainActivity.this).setTitle("Hold up.").setMessage("Are you really an Admin?")
                        .setPositiveButton("Yes!", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                startActivity(new Intent(MainActivity.this, AdminActivity.class));
                            }
                        })
                        .setNegativeButton("Not really, sorry.", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(MainActivity.this, "Don't do that.", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
                return true;
            }
        });
    }

    public void onClickLogin(View view) {
        etUser = findViewById(R.id.editTextUsuariLogin);
        etPass = findViewById(R.id.editTextPasswordLogin);
        SharedPreferences preferences = getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE);
        String password = preferences.getString(etUser.getText().toString(), "");
        if (!password.equals("") && !etPass.getText().toString().equals(""))
            checkLogin(password, etPass.getText().toString());
        else
            Toast.makeText(this, "You need to type user and password.", Toast.LENGTH_SHORT).show();
    }

    public void checkLogin(String passwordGuardada, String passwordIntent){
        if (passwordGuardada.equals(passwordIntent)){
            Intent i = new Intent(this, LoggedUserActivity.class);
            i.putExtra("user", etUser.getText().toString());
            startActivity(i);
        }
        else {
            Toast.makeText(this, "User or password incorrect.", Toast.LENGTH_SHORT).show();
        }
    }

    public void onClickNewUser(View view) {
        startActivityForResult(new Intent(this, NewUserActivity.class), 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            if (requestCode == 1) {
                etUser.setText(data.getStringExtra("User"));
                etPass.setText("");
            }
        }
        else {
            if (requestCode == 1) {
                etUser.setText("");
                etPass.setText("");
            }
        }
    }

    private void initRealm(){
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder().schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
}
