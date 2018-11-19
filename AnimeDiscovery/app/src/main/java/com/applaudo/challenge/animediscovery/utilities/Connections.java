package com.applaudo.challenge.animediscovery.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Connections {

    //Global Variables
    private Context mContext;

    //Constructor, getting context
    public Connections(Context mContext){

        this.mContext = mContext;
    }

    //Verify if internet provider or WIFI is available
    public boolean isConnected(){
        ConnectivityManager connectivity = (ConnectivityManager)  mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected()){
                return true;
            }
        }
        return false;
    }

    //Verificar si esta conectado a Wifi
    public boolean isConnectedToWifi(){
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected() && info.getType()== ConnectivityManager.TYPE_WIFI){
                    return true;
            }
        }
        return false;
    }

    //Verificar si esta conectado a red movil
    public boolean isConnectedToMobile(){
        ConnectivityManager connectivity = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivity != null){
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if(info != null && info.isConnected() && info.getType()== ConnectivityManager.TYPE_MOBILE){
                return true;
            }
        }
        return false;
    }
}
