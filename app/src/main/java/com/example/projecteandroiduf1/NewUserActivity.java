package com.example.projecteandroiduf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class NewUserActivity extends AppCompatActivity {

    public static String sharedPreferencesFile = "SharedPreferencesProjecte";
    Realm realm;
    Spinner spinner;
    public int [] profilePics = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
            R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic16, R.drawable.pic9, R.drawable.pic10, R.drawable.pic11, R.drawable.pic12,
            R.drawable.pic13, R.drawable.pic14, R.drawable.pic15 };
    ImageView ivProfilePic;
    EditText etUser;
    EditText etPass;
    EditText etEdat;
    EditText etMail;
    int contadorPic = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);

        etUser = findViewById(R.id.editTextNewUser);
        etPass = findViewById(R.id.editTextNewPassword);
        etEdat = findViewById(R.id.editTextEdad);
        etMail = findViewById(R.id.editTextEmail);

        spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.genres, android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        ivProfilePic =  findViewById(R.id.imageViewProfilePic);
        initRealm();
        realm = Realm.getDefaultInstance();
    }

    public void onClickProfilePic(View v) {
        if (contadorPic < profilePics.length-1)
            contadorPic++;
        else {
            contadorPic = 0;
        }
        ivProfilePic.setImageResource(profilePics[contadorPic]);
    }

    public void onClickCreate(View view) {
        try {

            SharedPreferences.Editor editor = getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE).edit();
            editor.putString(etUser.getText().toString(), etPass.getText().toString());
            editor.commit();

            insert(null);
        } catch (Exception ex) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
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

    public void insert(View v) {

        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    User user = new User();
                    user.setName(etUser.getText().toString());
                    user.setEmail(etMail.getText().toString());
                    user.setGenere(spinner.getSelectedItem().toString());
                    user.setEdat(Integer.parseInt(etEdat.getText().toString()));
                    user.setPassword(etPass.getText().toString());
                    user.setProfilePic(contadorPic);
                    user.setMoney(1000);

                    realm.copyToRealm(user);

                    Toast.makeText(NewUserActivity.this, "User " + user.getName() + " inserted.", Toast.LENGTH_SHORT).show();

                    Intent i = new Intent();
                    i.putExtra("User", user.getName());
                    setResult(RESULT_OK, i);
                    finish();
                }
            });

        } catch (Exception ex) {
            Toast.makeText(NewUserActivity.this, "There is already a user with that name. Please, choose any other one.", Toast.LENGTH_SHORT).show();
        }
    }

    public void clearLayout(View v) {
        spinner.setSelection(0);
        etUser.setText("");
        etPass.setText("");
        etEdat.setText("");
        etMail.setText("");
    }
}
