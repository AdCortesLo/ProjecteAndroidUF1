package com.example.projecteandroiduf1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class CustomAdapter extends ArrayAdapter <User>{
    Context ctx;
    int plantillaLayout;
    List<User> llistaUsers;


    public CustomAdapter(@NonNull Context context, int resource, @NonNull List<User> objects) {
        super(context, resource, objects);

        this.ctx = context;
        this.plantillaLayout = resource;
        this.llistaUsers = objects;
    }

    //Aquest mètode es llança automaticament cada vegada per element
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = LayoutInflater.from(ctx).inflate(plantillaLayout,parent,false);
        //parent: el carga dins de l'elemnt pare, el listView que rep de l'activity

        //Obtenim el elements de la llista
        User user = llistaUsers.get(position);

        //Rescatem els elements de la IU de la plantillaLayout
        TextView name = v.findViewById(R.id.textViewName);
        TextView email = v.findViewById(R.id.textViewTelefon);
        ImageView profilePic = v.findViewById(R.id.imageViewUser);

        //Fem un set de la info de l'element actual

        name.setText(user.getName());
        email.setText(user.getEmail());

        int [] profilePics = {R.drawable.pic1, R.drawable.pic2, R.drawable.pic3, R.drawable.pic4, R.drawable.pic5,
                R.drawable.pic6, R.drawable.pic7, R.drawable.pic8, R.drawable.pic16, R.drawable.pic9, R.drawable.pic10, R.drawable.pic11, R.drawable.pic12,
                R.drawable.pic13, R.drawable.pic14, R.drawable.pic15 };

        profilePic.setImageResource(profilePics[user.getProfilePic()]);
        return v;
    }
}
