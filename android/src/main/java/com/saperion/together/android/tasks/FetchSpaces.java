package com.saperion.together.android.tasks;

import java.util.Collections;
import java.util.List;

import android.widget.ListView;

import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.client.models.Space;
import com.saperion.together.android.MainActivity;
import com.saperion.together.android.adapter.SpacesAdapter;
import com.saperion.together.android.exceptions.ConnectivityException;

public class FetchSpaces extends
		TimedOutAsyncTask<String, List<Space>, List<Space>> {
	private ListView view;

	public FetchSpaces(ListView spacesView, ProgressPublisher<List<Space>, List<Space>> publisher) {
		super(publisher,0, 0);
		view = spacesView;
	}
	
	public FetchSpaces(ListView spacesView, long lastCall, ProgressPublisher<List<Space>, List<Space>> publisher) {
		super(publisher, lastCall, 0);
		view = spacesView;
	}

	@Override
	protected void onPreExecute() {
		SpacesAdapter adapter = (SpacesAdapter) view.getAdapter();
		adapter.clear();
		adapter.notifyDataSetChanged();
		
		super.onPreExecute();
	}

	@Override
	protected List<Space> doIt(String... params) {
		MainActivity.log(FetchSpaces.class, "doInBackground and find Spaces!");
		try {
//			retrieve only one item
//			if(params != null && params.length > 0){
//				return Collections.singletonList(MainActivity.getTogether().find(Space.class).byId(params[0]).result());
//			}

			return MainActivity.getTogether().mySpaces();
		} catch (AuthenticationException ex) {
			MainActivity.handle(FetchSpaces.class, ex);
			publisher.failed("Please check the settings, because it couldn't be logged in.");
			return Collections.emptyList();
		} catch (ConnectivityException e) {
			MainActivity.handle(FetchSpaces.class, e);
			publisher.failed("Spaces couldn't be loaded, because the network is not available.");
			return Collections.emptyList();
		} catch (ConnectException e) {
			MainActivity.handle(FetchSpaces.class, e);
			publisher.failed("Spaces couldn't be loaded, because the network is not available.");
			return Collections.emptyList();
		} catch (Throwable t) {
			MainActivity.handle(FetchSpaces.class, t);
			publisher.failed("Spaces couldn't be loaded.");
			return Collections.emptyList();
		}
	}

	@Override
	protected void changeIt(List<Space> result) {
		MainActivity.log(FetchSpaces.class, "onPostExecute! Spaces: " + result.size());
		
		SpacesAdapter adapter = (SpacesAdapter) view.getAdapter();
		adapter.clear();
		adapter.addAll(result);
		adapter.notifyDataSetChanged();
	}

	@Override
	public FetchSpaces clone() {
		return new FetchSpaces(view, lastCall, publisher);
	}
}