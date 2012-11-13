package com.saperion.together.android.tasks;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Environment;

import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.StreamResult;
import com.saperion.sdb.client.utils.StreamUtil;
import com.saperion.sdb.client.utils.StreamUtil.ProgressListener;
import com.saperion.together.android.MainActivity;
import com.saperion.together.android.mime.MIME;

public class Download extends TimedOutAsyncTask<Document, Long, Intent> {
	public Download(ProgressPublisher<Long, Intent> publisher) {
		super(publisher, 0, 0);
	}

	@Override
	protected Intent doIt(Document... params) {
		Document document = params[0];

		Intent intent = null;
		try {
			File path = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
			File file = new File(path, document.getName());
			path.mkdirs();

			intent = new Intent();
			intent.setAction(android.content.Intent.ACTION_VIEW);
			intent.setFlags(android.content.Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.setDataAndType(Uri.fromFile(file), MIME.parse(file.getName()).getMime());
			
			List<ResolveInfo> intents = MainActivity.CONTEXT.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
			if (intents == null || intents.size() == 0) {
				//no viewer found
				publisher.failed("No Viewer was found for file \"" + file.getName()
						+ "\".");
				return null;
			}

			final StreamResult result = document.download();
			OutputStream out = new BufferedOutputStream(new FileOutputStream(
					file));

			StreamUtil.copyInToOut(result.getStream(), out, 8192, true,
					new ProgressListener() {
						private long fileLength = result.getSize();
						@Override
						public void alreadyRead(long bytes) {
							publishProgress((bytes * 100 / fileLength));
						}
					});
		} catch (AuthenticationException ex) {
			publisher.failed("Please check the settings, because it couldn't be logged in.");
			return null;
		} catch (ConnectException e) {
			publisher.failed("Document couldn't be downloaded, because the network is not available.");
			return null;
		} catch (Throwable t) {
			publisher.failed("Document couldn't be downloaded.");
			return null;
		}

		return intent;
	}
	
	@Override
	void changeIt(Intent result) {}
}