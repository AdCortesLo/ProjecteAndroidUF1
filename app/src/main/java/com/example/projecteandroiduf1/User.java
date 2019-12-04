package com.example.projecteandroiduf1;

import java.util.ArrayList;
import java.util.HashMap;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject {

    @PrimaryKey
    private String name;
    private String genere;
    private String email;
    private int edat;
    private String password;
    private int profilePic;
    private int money;

    public User() {

    }

    public User(String name, String email, String genere, int edat, String password, int profilePic, int money) {
        this.name = name;
        this.email = email;
        this.genere = genere;
        this.edat = edat;
        this.password = password;
        this.profilePic = profilePic;
        this.money = money;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public int getEdat() {
        return edat;
    }

    public void setEdat(int edat) {
        this.edat = edat;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(int profilePic) {
        this.profilePic = profilePic;
    }

    @Override
    public String toString() {
        return name + " - " + email;
    }
}
