package com.shuki.myapplication.entities;


/**
 * Created by User on 12/04/2018.
 */

public enum Gearbox {
    AUTOMATIC("Automatic"), MANUAL("Manual");

    private final String friendlyName;

    private Gearbox(String friendlyName){
        this.friendlyName = friendlyName;
    }
    @Override public String toString(){
        return friendlyName;
    }
}


