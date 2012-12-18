package com.saperion.together.android.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.saperion.sdb.client.models.Space;
import com.saperion.together.android.R;

public class SpacesAdapter extends ArrayAdapter<Space> {
	private static LayoutInflater inflater = null;

	public SpacesAdapter(Activity activity, Context context) {
		super(context, R.layout.navigation_layout, R.id.label_item);
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.navigation_layout, null);

		TextView label = (TextView) vi.findViewById(R.id.label_item);
		if (position != ListView.INVALID_POSITION) {
			Space space = this.getItem(position);
			label.setText(space.getName());
		}
		if(vi.isSelected() || vi.isActivated()){
			vi.setSelected(false);
			vi.setActivated(false);
		}
		return vi;
	}
}