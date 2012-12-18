package com.saperion.together.android.constants;

import java.util.HashMap;
import java.util.Map;

import android.content.res.AssetManager;
import android.graphics.Typeface;

public class Assets {
	private Map<String, Object> resources = new HashMap<String, Object>(0);
	private AssetManager assets;
	
	public Assets(AssetManager assets) {
		this.assets = assets;
	}

	public Typeface font(String name){
		String fonts = "fonts/"+name;
		if(resources.containsKey(fonts)){
			return Typeface.class.cast(resources.get(fonts));
		}
		Typeface font = Typeface.createFromAsset(assets, "fonts/"+name);
		if(font != null){
			resources.put(fonts, font);
			return font;
		}
		throw new IllegalStateException("Font ["+name+"] was not found.");
	}
}
