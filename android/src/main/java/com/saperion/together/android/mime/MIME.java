package com.saperion.together.android.mime;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum MIME {

	APP_WORD("doc", "application/msword"),

	APP_VND_WORD("docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document"),

	APP_RTF("rtf", "application/rtf"),

	APP_PPT("ppt", "application/mspowerpoint"),

	APP_VND_PPTX("pptx", "application/vnd.openxmlformats-officedocument.presentationml.presentation"),

	APP_XLS("xls", "application/msexcel"),

	APP_VND_XLSX("xlsx", "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"),

	TEXT_RTF("rtf", "text/rtf"),

	APP_PDF("pdf", "application/pdf"),

	APP_OUTLOOK("msg", "application/msoutlook"),

	TEXT_PLAIN("txt", "text/plain"),

	IMAGE_TIFF("tif", "image/tiff"),

	IMAGE_BMP("bmp", "image/bmp"),
	
	IMAGE_PNG("png", "image/png"),

	IMAGE_JPEG("jpg", "image/jpeg"),
	
	UNKNOWN("*", "application/octet-stream");
	
	static Map<String, MIME> MAPPING;
	
	static {
		MAPPING = new HashMap<String, MIME>();
		EnumSet<MIME> allOf = EnumSet.allOf(MIME.class);
		for(MIME mime : allOf){
			MAPPING.put(mime.extension, mime);
		}
	}

	String mime;
	String extension;

	private MIME(String extension, String mime) {
		this.extension = extension;
		this.mime = mime;
	}

	public String getMime() {
		return mime;
	}
	
	public String getExtension() {
		return extension;
	}
	
	public static MIME parse(String filename){
		int index = filename.lastIndexOf(".");
		if(index > 0){
			String extension = filename.substring(index+1);
			extension = extension.toLowerCase(Locale.ENGLISH);
			if(MAPPING.containsKey(extension)){
				return MAPPING.get(extension);
			}
		}
		return UNKNOWN;
	}
}
