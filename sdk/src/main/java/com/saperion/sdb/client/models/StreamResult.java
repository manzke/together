package com.saperion.sdb.client.models;

import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import com.saperion.sdb.client.utils.StreamUtil;


public class StreamResult implements Closeable{
	private final InputStream stream;
	private final String name;
	private final String mime;
	private final String hash;
	private final long size;

	public StreamResult(InputStream stream, String name, String mime, String hash, long size) {
		this.stream = stream;
		this.name = name;
		this.mime = mime;
		this.hash = hash;
		this.size = size;
	}
	
	public void saveAs(String filename) throws FileNotFoundException, IOException{
		StreamUtil.copyInToOut(getStream(), new FileOutputStream(filename), 8192, true);
	}

	public InputStream getStream() {
		return this.stream;
	}

	public String getFileName() {
		return this.name;
	}

	public String getMimeType() {
		return this.mime;
	}

	public String getHash() {
		return hash;
	}

	public long getSize() {
		return size;
	}
	
	@Override
	public void close() throws IOException {
		this.stream.close();
	}

	@Override
	public String toString() {
		return "StreamResult [name=" + name + ", mime=" + mime + ", hash=" + hash + ", size="
				+ size + "]";
	}
}
