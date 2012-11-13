package com.saperion.together.android.broadcast;

import java.util.ArrayList;
import java.util.List;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class ConnectivityBroadcaster extends BroadcastReceiver{	
	private final static List<NetworkAvailabilityListener> LISTENERS = new ArrayList<ConnectivityBroadcaster.NetworkAvailabilityListener>(0); 
	
	@Override
	public void onReceive(Context context, Intent intent) {
		boolean connected = isNetworkAvailable(context);
		for(NetworkAvailabilityListener listener : LISTENERS){
			listener.networkStateChanged(connected);
		}
	}
	
	public static boolean isNetworkAvailable(Context context) {
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] networks = manager.getAllNetworkInfo();
		boolean connected = false;
		if(networks != null){
			for(NetworkInfo network : networks){
				if(network.isConnected()){
					connected = true;
					break;
				}
			}
		}
		
		return connected;
	}
	
	public static void register(NetworkAvailabilityListener listener){
		LISTENERS.add(listener);
	}
	
	public static void unregister(NetworkAvailabilityListener listener){
		LISTENERS.remove(listener);
	}
	
	public static interface NetworkAvailabilityListener{
		void networkStateChanged(boolean available);
	}
}
