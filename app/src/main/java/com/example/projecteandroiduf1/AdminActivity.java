package com.example.projecteandroiduf1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class AdminActivity extends AppCompatActivity {

    public static String sharedPreferencesFile = "SharedPreferencesProjecte";
    Realm realm;
    RealmResults<User> realmResults;
    ArrayList<User> userList;
    ArrayAdapter<User> adapter;
    ArrayList<String> userListNoms = new ArrayList<>();
    ArrayAdapter<String> adapterNoms;
    CustomAdapter customAdapter;
    Spinner sp;
    String userEsborrar;
    ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        initRealm();
        realm = Realm.getDefaultInstance();
        sp = findViewById(R.id.spinnerDelete);
        ImageView iv = findViewById(R.id.imageViewFragment);
        TextView tv = findViewById(R.id.textViewUserName);
        iv.setImageResource(R.drawable.admin);
        tv.setText("Admin");

        realmResults = realm.where(User.class).findAll();
        userList = new ArrayList<>();
        for (User user : realmResults) {
            userList.add(user);
        }

        customAdapter = new CustomAdapter(
                this,
                R.layout.user_item,
                userList
        );

        adapterNoms = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userListNoms);

        listView = findViewById(R.id.listView);
        listView.setAdapter(customAdapter);
        sp.setAdapter(adapterNoms);
        selectAll(null);
    }

    private void initRealm() {
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration
                .Builder().schemaVersion(1)
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }

    public void selectAll(View v) {
        realmResults = realm.where(User.class).findAll();

        userList.clear();
        for (User user : realmResults) {
            userList.add(user);
        }
        customAdapter.notifyDataSetChanged();

        userListNoms.clear();
        for (User user : userList) {
            userListNoms.add(user.getName());
        }
        adapterNoms.notifyDataSetChanged();
    }

    public void onEsborrarClick(View view) {

        Log.i("zzz", "aaaa");

        try {
            userEsborrar = sp.getSelectedItem().toString();

            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    User user = realm.where(User.class).equalTo("name", userEsborrar).findFirst();

                    Log.i("zzz", user.getName());
                    user.deleteFromRealm();

                    Log.i("zzz", user.getName());
                    SharedPreferences.Editor editor = getSharedPreferences(sharedPreferencesFile, MODE_PRIVATE).edit();
                    editor.remove(user.getName());
                    Log.i("zzz", user.getName());
                    editor.commit();
                    Log.i("zzz", user.getName());
                    Toast.makeText(AdminActivity.this, userEsborrar + " deleted succesfully.", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception ex) {
            Toast.makeText(this, "There are no users in the database.", Toast.LENGTH_SHORT).show();
            Log.i("zzz", ex.toString());
        }

        selectAll(null);
    }
}
