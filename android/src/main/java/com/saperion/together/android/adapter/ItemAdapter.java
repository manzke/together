package com.saperion.together.android.adapter;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.saperion.sdb.client.models.ClientModel;
import com.saperion.sdb.client.models.ClientModel.ClientModelType;
import com.saperion.sdb.client.models.Document;
import com.saperion.sdb.client.models.Folder;
import com.saperion.together.android.MainActivity;
import com.saperion.together.android.R;

public class ItemAdapter extends ArrayAdapter<ClientModel<?>> {
	private static LayoutInflater inflater = null;

	public ItemAdapter(Activity activity, Context context) {
		super(context, R.layout.content_item_layout, R.id.edit_text_name);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final View vi;
		View toolbar;
		if (convertView == null) {
			vi = inflater.inflate(R.layout.content_item_layout, null);

			toolbar = vi.findViewById(R.id.layout_button_bar);
			Typeface font = MainActivity.assets.font("saperionsdb.ttf");
			((TextView) toolbar.findViewById(R.id.button_edit))
					.setTypeface(font);
			((TextView) toolbar.findViewById(R.id.button_link))
					.setTypeface(font);
			((TextView) toolbar.findViewById(R.id.button_download))
					.setTypeface(font);
			((TextView) toolbar.findViewById(R.id.button_recycle))
					.setTypeface(font);
		} else {
			vi = convertView;
			toolbar = vi.findViewById(R.id.layout_button_bar);
		}

		TextView nameLabel = (TextView) vi.findViewById(R.id.edit_text_name);
		nameLabel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.INSTANCE.showItem(v);
			}
		});
		
		final TextView descriptionLabel = (TextView) vi
				.findViewById(R.id.edit_text_description);

		TextView tagsView = (TextView) vi.findViewById(R.id.edit_text_tags);
		if (position != ListView.INVALID_POSITION) {
			ClientModel<?> item = this.getItem(position);
			if (item.getType() == ClientModelType.FOLDER) {
				Folder folder = (Folder) item;
				nameLabel.setText(folder.getName());
				descriptionLabel.setText(folder.getDescription());
				if (tagsView != null) {
					StringBuilder b = new StringBuilder();
					List<String> tags = folder.getTags();
					for (String tag : tags) {
						b.append(tag).append(" ");
					}
					tagsView.setText(b.toString());
				}
			} else {
				Document document = (Document) item;
				nameLabel.setText(document.getName());
				descriptionLabel.setText(document.getDescription());
				if (tagsView != null) {
					StringBuilder b = new StringBuilder();
					List<String> tags = document.getTags();
					for (String tag : tags) {
						b.append(tag).append(" ");
					}
					tagsView.setText(b.toString());
				}
			}
		}

		// Resets the toolbar to be closed
		LayoutParams layoutParams = toolbar.getLayoutParams();
		((GridLayout.LayoutParams) layoutParams).bottomMargin = -50;
		toolbar.setVisibility(View.GONE);

		return vi;
	}
}