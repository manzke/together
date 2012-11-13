package com.saperion.sdb.client.helper;

import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64InputStream;

import com.saperion.sdb.client.models.StreamResult;

@Provider
@Consumes("*/*")
public class StreamResultBodyReader implements MessageBodyReader<StreamResult> {
	private static final String CONTENT_LENGHT_HEADER_KEY = "Content-Length";
	private static final String CONTENT_HASH_HEADER_KEY = "Content-MD5";
	private static final String CONTENT_DISPOSITION_HEADER_KEY = "Content-Disposition";
	
	@Override
	public boolean isReadable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return StreamResult.class.isAssignableFrom(type);
	}
	
	@Override
	public StreamResult readFrom(Class<StreamResult> type, Type genericType,
			Annotation[] annotations, MediaType mediaType,
			MultivaluedMap<String, String> httpHeaders, InputStream entityStream)
			throws IOException, WebApplicationException {		
		final String md5;
		if(httpHeaders.containsKey(CONTENT_HASH_HEADER_KEY)){
			md5 = httpHeaders.getFirst(CONTENT_HASH_HEADER_KEY);	
		}else{
			md5 = null;
		}
		
		long size = -1;
		if(httpHeaders.containsKey(CONTENT_LENGHT_HEADER_KEY)){
			try {
				size = Long.parseLong(httpHeaders.getFirst(CONTENT_LENGHT_HEADER_KEY));
			} catch (NumberFormatException e) {
				// do nothing
			}	
		}

		String filename = "binary.dat";
		if(httpHeaders.containsKey(CONTENT_DISPOSITION_HEADER_KEY)){
			filename = httpHeaders.getFirst(CONTENT_DISPOSITION_HEADER_KEY);
			int index;
			if((index = filename.lastIndexOf("name=\"")) > 0){
				filename = filename.substring(index+"name=\"".length(), filename.length() - 1);	
			}
		}
		
		if(CustomMediaType.APPLICATION_BASE64_TYPE.equals(mediaType)){
			entityStream = new Base64InputStream(entityStream, false);
		}
		
		return new StreamResult(entityStream, filename, mediaType.toString(), md5, size);
	}
}
