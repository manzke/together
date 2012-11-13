package com.saperion.together.android.tasks;

public interface ProgressPublisher<Progress, Result>{
	void start();
	void progress(Progress progress);
	void failed(String message);
	void finished(Result result);
}