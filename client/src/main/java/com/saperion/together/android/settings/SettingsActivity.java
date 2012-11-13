package com.saperion.together.android.settings;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

import com.saperion.common.lang.string.Strings;
import com.saperion.together.android.MainActivity;
import com.saperion.together.android.R;
import com.saperion.together.android.constants.Preferences;
import com.saperion.together.android.exceptions.ConnectivityException;

public class SettingsActivity extends Activity {
	private static final String TOGETHER_CONFIG_SERVICE = "together://config?service?";
	private static final String HTTP_PROTOCOL = "http://";
	private static final String HTTPS_PROTOCOL = "https://";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		String data = intent.getStringExtra("data");
		if (!Strings.isBlank(data)) {
			SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(MainActivity.CONTEXT);
			Editor editor = settings.edit();
			
			MainActivity.notify(SettingsActivity.class, data.toString());
			String uri = data.toString();
			// together://config?service?http://213.61.60.88:8600/sdb-service-rest?daniel.manzke@saperion.com
			if (uri.contains(TOGETHER_CONFIG_SERVICE)) {
				String stripped = uri.substring(TOGETHER_CONFIG_SERVICE
						.length());
				if (stripped.contains(HTTP_PROTOCOL)) {
					MainActivity.log(SettingsActivity.class, "Using a ssl secured connection.");
					editor.putBoolean(Preferences.USE_SSL_KEY, false);
					stripped = stripped.substring(HTTP_PROTOCOL.length());
				} else if (stripped.contains(HTTPS_PROTOCOL)) {
					MainActivity.log(SettingsActivity.class, "Using a non-secure connection.");
					editor.putBoolean(Preferences.USE_SSL_KEY, true);
					stripped = stripped.substring(HTTPS_PROTOCOL.length());
				}
				
				int index = stripped.indexOf("/");
				if(index > 0){
					String hostAndPort = stripped.substring(0, index);
					int dotIndex = hostAndPort.indexOf(":");
					String host = hostAndPort;
					String port = "80";
					if(dotIndex > 0){
						host = hostAndPort.substring(0, dotIndex);
						port = hostAndPort.substring(dotIndex+1, hostAndPort.length());
					}
					MainActivity.log(SettingsActivity.class, "Using a domain ["+host+"] and port ["+port+"] for connection.");
					editor.putString(Preferences.HOST_KEY, host);
					editor.putString(Preferences.PORT_KEY, port);
					
					stripped = stripped.substring(index);
					int questionMarkIndex = stripped.indexOf("?");
					if(questionMarkIndex > 0){
						String resource = stripped.substring(1, questionMarkIndex);
						MainActivity.log(SettingsActivity.class, "Using a resource ["+resource+"]  for connection.");
						editor.putString(Preferences.RESOURCE_KEY, resource);
						
						String user = stripped.substring(questionMarkIndex+1);
						String oldUser = settings.getString(Preferences.USERNAME_KEY, "");
						
						if(!oldUser.equalsIgnoreCase(user)){
							MainActivity.log(SettingsActivity.class, "Using a user ["+user+"]  for connection.");
							editor.putString(Preferences.USERNAME_KEY, user);							
							editor.putString(Preferences.PASSWORD_KEY, "");
						}
					}
				}else{
					//we have a problem
					MainActivity.log(SettingsActivity.class, "No slash found in the url? Illegal URL was used. Stripped ["+stripped+"]");
				}				 
			}
			
			editor.commit();
		}

		getFragmentManager().beginTransaction()
				.replace(android.R.id.content, new SettingsFragment()).commit();
	}

	public static class SettingsFragment extends PreferenceFragment {
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// Load the preferences from an XML resource
			addPreferencesFromResource(R.xml.together_preferences);
		}
		
		@Override
		public void onDestroy() {
			super.onDestroy();
			
			try {
				MainActivity.getTogether(true);
			} catch (ConnectivityException e) {
				MainActivity.notify(SettingsFragment.class, "Please check the settings, because no connection could be established.");
			}
		}
	}
}
