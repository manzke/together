package com.saperion.together.android.tasks;

import java.util.Collections;
import java.util.List;

import android.widget.ListView;

import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.client.models.ClientModel;
import com.saperion.sdb.client.models.Folder;
import com.saperion.together.android.MainActivity;
import com.saperion.together.android.adapter.ItemAdapter;

public class FetchFolder extends
		TimedOutAsyncTask<Folder, List<ClientModel<?>>, List<ClientModel<?>>> {
	private ListView view;

	public FetchFolder(ListView view, ProgressPublisher<List<ClientModel<?>>, List<ClientModel<?>>> publisher) {
		super(publisher, 0, 0);
		this.view = view;
	}
	
	public FetchFolder(ListView view, long lastCall, ProgressPublisher<List<ClientModel<?>>, List<ClientModel<?>>> publisher) {
		super(publisher, lastCall, 0);
		this.view = view;
	}
	
	@Override
	protected void onPreExecute() {
		ItemAdapter adapter = (ItemAdapter) view.getAdapter();
		adapter.clear();
		adapter.notifyDataSetChanged();
		
		super.onPreExecute();
	}

	@Override
	protected List<ClientModel<?>> doIt(Folder... params) {
		Folder folder = params[0];
		MainActivity.log(FetchFolder.class,
				"doInBackground and find children for folder: " + folder);
		try {
			return folder.children();
		} catch (AuthenticationException ex) {
			MainActivity.handle(FetchFolder.class, ex);
			publisher.failed("Please check the settings, because it couldn't be logged in.");
			return Collections.emptyList();
		} catch (ConnectException e) {
			MainActivity.handle(FetchFolder.class, e);
			publisher.failed("Folder couldn't be loaded, because the network is not available.");
			return Collections.emptyList();
		} catch (Throwable t) {
			MainActivity.handle(FetchFolder.class, t);
			publisher.failed("Folder couldn't be loaded.");
			return Collections.emptyList();
		}
	}

	@Override
	protected void changeIt(List<ClientModel<?>> result) {
		ItemAdapter adapter = (ItemAdapter) view.getAdapter();
		adapter.clear();
		adapter.addAll(result);
		adapter.notifyDataSetChanged();
	}

	@Override
	public FetchFolder clone() {
		return new FetchFolder(view, lastCall, publisher);
	}
}