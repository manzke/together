package com.saperion.together.android.tasks;

import android.os.AsyncTask;


public abstract class TimedOutAsyncTask<Params, Progress, Result> extends AsyncTask<Params, Progress, Result> {
	protected long lastCall;
	protected long timeout;
	protected boolean ignore;
	protected ProgressPublisher<Progress, Result> publisher;

	public TimedOutAsyncTask(ProgressPublisher<Progress, Result> publisher, long lastCall, long timeout){
		super();
		this.lastCall = lastCall;
		this.timeout = timeout;
		this.publisher = publisher;
	}
	
	public boolean ignore(){
		return ((lastCall + timeout) - System.currentTimeMillis()) > 0;
	}
	
	abstract Result doIt(Params... params);
	
	protected Result doInBackground(Params... params) {
		if(!ignore()){
			ignore = false;
			lastCall = System.currentTimeMillis();
			return doIt(params);
		}
		ignore = true;
		return null;
	};
	
	abstract void changeIt(Result result);
	
	@Override
	protected void onPreExecute() {
		if(publisher != null){
			publisher.start();
		}
	}

	@Override
	protected void onProgressUpdate(Progress... values) {
		if(publisher != null){
			publisher.progress(values[0]);	
		}
		
	}
	
	protected void onPostExecute(Result result) {
		if(!ignore && result != null){
			if(publisher != null){
				publisher.finished(result);
			}
			changeIt(result);
		}
	};
}
