package com.saperion.sdb.client.helper;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;

import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.apache.commons.codec.binary.Base64InputStream;

import com.saperion.common.lang.string.Strings;
import com.saperion.sdb.client.models.StreamResult;
import com.saperion.sdb.client.utils.StreamUtil;

@Provider
@Produces("*/*")
public class StreamResultBodyWriter implements MessageBodyWriter<StreamResult> {
	private static final String CONTENT_DISPOSITION_HEADER_VALUE = "attachment; filename=\"%s\"";
	private static final String CONTENT_DISPOSITION_HEADER_KEY = "Content-Disposition";
	private static final String CONTENT_TYPE_HEADER_KEY = "Content-Type";
	private static final String CONTENT_HASH_HEADER_KEY = "Content-MD5";
	private static final String ETAG_HEADER_KEY = "ETag";
	
	@Override
	public boolean isWriteable(Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		return StreamResult.class.isAssignableFrom(type);
	}

	@Override
	public long getSize(StreamResult t, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType) {
		if(CustomMediaType.APPLICATION_BASE64_TYPE.equals(mediaType)){
			//we can't determine the size, because the original content will be base64 encoded
			return -1;
		}
		return (t.getSize() > 0 ? t.getSize() : -1);
	}

	@Override
	public void writeTo(StreamResult result, Class<?> type, Type genericType, Annotation[] annotations,
			MediaType mediaType, MultivaluedMap<String, Object> httpHeaders,
			OutputStream entityStream) throws IOException, WebApplicationException {
		String dispositionHeader = String.format(CONTENT_DISPOSITION_HEADER_VALUE, result.getFileName());
		httpHeaders.put(CONTENT_DISPOSITION_HEADER_KEY, Collections.<Object>singletonList(dispositionHeader));
				
		if(!Strings.isBlank(result.getHash())){
			httpHeaders.put(ETAG_HEADER_KEY, Collections.<Object>singletonList(result.getHash()));
			httpHeaders.put(CONTENT_HASH_HEADER_KEY, httpHeaders.get(ETAG_HEADER_KEY));
		}
		
		String mimeType;
		if(!Strings.isBlank(result.getMimeType())){
			mimeType = result.getMimeType();
		}else{
			mimeType = MediaType.APPLICATION_OCTET_STREAM;
		}
		httpHeaders.put(CONTENT_TYPE_HEADER_KEY, Collections.<Object>singletonList(mimeType));
		
		InputStream in = result.getStream();				
		if(CustomMediaType.APPLICATION_BASE64.equals(mimeType)){
			in = new Base64InputStream(in, true);
		}
		
		StreamUtil.copyInToOut(in, entityStream, 8192, false);
	}
}
