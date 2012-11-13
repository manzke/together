package com.saperion.together.android.tasks;

import com.saperion.sdb.client.Together;
import com.saperion.together.android.MainActivity;
import com.saperion.together.android.exceptions.ConnectivityException;

public class Login extends
		TimedOutAsyncTask<String, Void, Together> {

	public Login() {
		super(null, 0, 0);
	}
	
	public Login(ProgressPublisher<Void, Together> publisher) {
		super(publisher, 0, 0);
	}

	@Override
	protected Together doIt(String... params) {
		try {
			return MainActivity.getTogether();
		} catch (ConnectivityException e) {
			MainActivity.handle(FetchSpaces.class, "Spaces couldn't be loaded, because the network is not available.", e);
			return null;
		}
	}

	@Override
	protected void changeIt(Together together) {
		MainActivity.log(Login.class, "Together successfully initialized? "+(together != null));
	}
}