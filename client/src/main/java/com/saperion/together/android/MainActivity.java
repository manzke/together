package com.saperion.together.android;

import java.util.List;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.saperion.common.lang.string.Strings;
import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.Together.Protocol;
import com.saperion.sdb.client.models.ClientModel;
import com.saperion.sdb.client.models.ClientModel.ClientModelType;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.Folder;
import com.saperion.sdb.client.models.Space;
import com.saperion.together.android.adapter.ItemAdapter;
import com.saperion.together.android.adapter.SpacesAdapter;
import com.saperion.together.android.animations.ExpandAnimation;
import com.saperion.together.android.broadcast.ConnectivityBroadcaster;
import com.saperion.together.android.broadcast.ConnectivityBroadcaster.NetworkAvailabilityListener;
import com.saperion.together.android.constants.Assets;
import com.saperion.together.android.constants.Messages;
import com.saperion.together.android.constants.Preferences;
import com.saperion.together.android.exceptions.ConnectivityException;
import com.saperion.together.android.settings.SettingsActivity;
import com.saperion.together.android.tasks.Download;
import com.saperion.together.android.tasks.FetchFolder;
import com.saperion.together.android.tasks.FetchSpace;
import com.saperion.together.android.tasks.FetchSpaces;
import com.saperion.together.android.tasks.Login;
import com.saperion.together.android.tasks.ProgressPublisher;
import com.sun.jersey.api.client.Client;

public class MainActivity extends Activity implements
		NetworkAvailabilityListener {

	public final static String EXTRA_MESSAGE = "com.saperion.together.android.MESSAGE";

	public static Context CONTEXT;
	public static MainActivity INSTANCE;
	public static Together together;
	public static FetchSpaces fetchSpaces;
	public static FetchSpace fetchSpace;
	public static FetchFolder fetchFolder;
	public static Handler main;
	public static Assets assets;

	public static boolean connected;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		INSTANCE = this;
		
//		getActionBar().setDisplayHomeAsUpEnabled(true);

		StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
				.detectNetwork().penaltyLog().build());
		StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder().detectAll()
				.penaltyLog().build());
		
		assets = new Assets(getAssets());

		PreferenceManager.setDefaultValues(this, R.xml.together_preferences,
				false);
		connected = ConnectivityBroadcaster
				.isNetworkAvailable(getApplicationContext());
		ConnectivityBroadcaster.register(this);
		CONTEXT = getApplicationContext();
		main = new Handler();

		Intent intent = getIntent();
		String data = intent.getDataString();
		if (intent != null && !Strings.isBlank(data)) {
			displaySettings(data);
		}

		setContentView(R.layout.activity_main);
		
		initFonts();

		ListView spacesView = (ListView) findViewById(R.id.view_space);
		spacesView.setSelector(R.drawable.navigation_bg_selector);
		
		SpacesAdapter spacesAdapter = new SpacesAdapter(this,
				getApplicationContext());
		spacesView.setAdapter(spacesAdapter);
		
		fetchSpaces = new FetchSpaces(spacesView, new ProgressPublisher<List<Space>, List<Space>>() {
			private ProgressDialog progress;
			@Override
			public void start() {
				progress = new ProgressDialog(MainActivity.this);
				progress.setMessage("Please wait...");
				progress.setIndeterminate(true);
				
				// and now the magic
				progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				// not needed for this example, but allows the progress dialog to appear near the bottom, controlled by the float value.
				progress.getWindow().setGravity(Gravity.BOTTOM);
				progress.getWindow().getAttributes().verticalMargin = 0.1f;
				

				progress.show();
			}
			
			@Override
			public void failed(String message) {
				progress.dismiss();
			}
			
			public void finished(List<Space> result) {
				progress.dismiss();
			};
			
			public void progress(List<Space> progress) {
				
			};
		});

		spacesView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				SpacesAdapter adapter = (SpacesAdapter) parent.getAdapter();
				Space item = adapter.getItem(position);

				FetchSpace fetchSpace = MainActivity.fetchSpace.clone();
				fetchSpace.execute(item);
			}
		});

		final AutoCompleteTextView editText = (AutoCompleteTextView) findViewById(R.id.box_filter);
		editText.setAdapter(spacesAdapter);

		ListView contentView = (ListView) findViewById(R.id.view_content);
		contentView.setAdapter(new ItemAdapter(this, getApplicationContext()));
		contentView.setSelector(R.drawable.navigation_bg_selector);
		
		ProgressPublisher<List<ClientModel<?>>, List<ClientModel<?>>> publisher = new ProgressPublisher<List<ClientModel<?>>, List<ClientModel<?>>>() {
			private ProgressDialog progress;
			@Override
			public void start() {
				progress = new ProgressDialog(MainActivity.this);
				progress.setMessage("Please wait...");
				progress.setIndeterminate(true);
				
				// and now the magic
				progress.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
				// not needed for this example, but allows the progress dialog to appear near the bottom, controlled by the float value.
				progress.getWindow().setGravity(Gravity.BOTTOM);
				progress.getWindow().getAttributes().verticalMargin = 0.1f;
				
				progress.show();
			}
			
			@Override
			public void failed(String message) {
				progress.dismiss();
			}
			
			public void finished(List<ClientModel<?>> result) {
				progress.dismiss();
			};
			
			public void progress(List<ClientModel<?>> progress) {
			};
		};

		fetchSpace = new FetchSpace(contentView, publisher);
		fetchFolder = new FetchFolder(contentView, publisher);
		
		main.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				Login login = new Login();
				login.execute();
				
				try {
					login.get(3000, TimeUnit.MILLISECONDS);
					fetchSpaces.execute();
				} catch (Exception e) {
					// ignore
				} 
				
				
			}
		}, 250);
	}
	
	private void initFonts(){
		Typeface font = assets.font("saperionsdb.ttf");
		
		((TextView) this.findViewById(R.id.button_add_document)).setTypeface(font);
		((TextView) this.findViewById(R.id.button_add_folder)).setTypeface(font);
		((TextView) this.findViewById(R.id.button_add_space)).setTypeface(font);
	}
	
	public void expandItem(View view){
		View toolbar = view.findViewById(R.id.layout_button_bar);
		changeItemState(toolbar.findViewById(R.id.button_edit), false);
		
        // Creating the expand animation for the item
        ExpandAnimation expandAni = new ExpandAnimation(toolbar, 500);

        // Start the animation on the toolbar
        toolbar.startAnimation(expandAni);
	}
	
	public void showItem(View view){
		ListView contentView = (ListView) findViewById(R.id.view_content);
		int position = contentView.getPositionForView(view);
		ClientModel<?> item = (ClientModel<?>) contentView.getItemAtPosition(position);
		if (item.getType() == ClientModelType.FOLDER) {
			FetchFolder fetchFolder = MainActivity.fetchFolder.clone();
			fetchFolder.execute((Folder) item);
		} else {
			final Document document = (Document) item;			
			final Download task = new Download(new ProgressPublisher<Long, Intent>() {
				private ProgressDialog progress;
				@Override
				public void start() {
					progress = new ProgressDialog(MainActivity.this);
					progress.setMessage("Downloading started.");
					progress.setIndeterminate(false);
					progress.setMax(100);
					progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);

					progress.show();
				}
				
				@Override
				public void progress(Long value) {
					progress.setProgress(value.intValue());
				}
				
				@Override
				public void finished(Intent intent) {
					progress.dismiss();
					MainActivity.notify(MainActivity.class, "Downloaded file \"" + document.getName()
					+ "\" successfully.");
					MainActivity.CONTEXT.startActivity(intent);
				}
				
				@Override
				public void failed(String message) {
					progress.dismiss();
					MainActivity.notify(MainActivity.class, message);
				}
			});
			task.execute(document);
		}
	}
	
	public void editItem(View view){
		changeItemState(view, true);
	}
	
	public void cancelEditItem(View view){
		changeItemState(view, false);
	}
	
	private void changeItemState(View view, boolean enabled){
		View parent = ((GridLayout)view.getParent().getParent());
		
		EditText editName = (EditText)parent.findViewById(R.id.edit_text_name);
		editName.setFocusable(enabled);
		editName.setFocusableInTouchMode(enabled);
		if(!enabled){
			editName.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					MainActivity.INSTANCE.showItem(v);
				}
			});
		}else{
			editName.setOnClickListener(null);
		}
		
		EditText editDescription = (EditText)parent.findViewById(R.id.edit_text_description);
		editDescription.setEnabled(enabled);
		
		EditText editTags = (EditText)parent.findViewById(R.id.edit_text_tags);
		editTags.setEnabled(enabled);
	}

	public static Together getTogether() throws ConnectivityException {
		return getTogether(false);
	}

	public synchronized static Together getTogether(boolean restart)
			throws ConnectivityException {
		log(MainActivity.class, "trying to start together connection");
		if (!MainActivity.connected) {
			throw new ConnectivityException("Network is not available.");
		}
		try {
			if (restart && together != null) {
				try {
					together.close();
				} catch (Exception e) {
					// ignore
				} finally {
					together = null;
				}
			}
			if (together == null) {
				SharedPreferences preferences = PreferenceManager
						.getDefaultSharedPreferences(CONTEXT);
				boolean useSSL = preferences.getBoolean(
						Preferences.USE_SSL_KEY, false);
				String host = preferences.getString(Preferences.HOST_KEY,
						"together.cloudapp.net");
				int port = 80;
				try {
					port = Integer.parseInt(preferences.getString(
							Preferences.PORT_KEY, "80"));
				} catch (Exception e) {
					log(MainActivity.class, e.getMessage());
					port = 80;
				}
				String resource = preferences.getString(
						Preferences.RESOURCE_KEY, "sdb-service-rest");

				Together.Config config = new Together.Config();
				together = config
						.protocol((useSSL ? Protocol.HTTPS : Protocol.HTTP))
						.host(host).port(port).resource(resource).client(Client.class).build();
				// TODO ping if connection can be established

				String username = preferences.getString(
						Preferences.USERNAME_KEY, "");
				String password = preferences.getString(
						Preferences.PASSWORD_KEY, "");

				together.login(username, password);
			}
			return together;
		} catch (Throwable t) {
			handle(MainActivity.class, null, t);
			throw new ConnectivityException(t.getMessage());
		}
	}

	public void displaySettings() {
		displaySettings(null);
	}

	public void displaySettings(String data) {
		Intent intent = new Intent(this, SettingsActivity.class);
		intent.putExtra("data", data);
		startActivity(intent);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_refresh:
			FetchSpaces fetchSpaces = MainActivity.fetchSpaces.clone();
			fetchSpaces.execute();
			FetchSpace fetchSpace = MainActivity.fetchSpace.clone();
			fetchSpace.execute();
			return true;
		case R.id.menu_settings:
			displaySettings();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	public static void handle(final Class<?> caller, String message,
			final Throwable ex) {
		Log.e(caller.getSimpleName(), ex.getMessage(), ex);
		if (!Strings.isBlank(message)) {
			main.post(new Runnable() {
				@Override
				public void run() {
					Toast.makeText(CONTEXT, ex.getMessage(), Toast.LENGTH_SHORT)
							.show();
				}
			});
		}
	}
	
	public static void handle(final Class<?> caller, final Throwable ex) {
		handle(caller, null, ex);
	}

	public static void notify(final Class<?> caller, final String message) {
		Log.i(caller.getSimpleName(), message);
		main.post(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(CONTEXT, message, Toast.LENGTH_SHORT).show();
			}
		});
	}

	public static void log(Class<?> caller, String message) {
		Log.i(caller.getSimpleName(), message);
	}

	@Override
	public void networkStateChanged(boolean available) {
		connected = available;
		notify(ConnectivityBroadcaster.class,
				(connected ? Messages.NETWORK_IS_AVAILABLE
						: Messages.NETWORK_IS_NOT_AVAILABLE));
	}
	
	@Override
	protected void onDestroy() {
		ConnectivityBroadcaster.unregister(this);
		super.onDestroy();
	}
}
