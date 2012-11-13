package com.saperion.sdb.client.helper;

import java.net.URI;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Cookie;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.Variant.VariantListBuilder;
import javax.ws.rs.ext.RuntimeDelegate;

import com.saperion.common.lang.unsafe.Unsafe;
import com.sun.jersey.api.uri.UriBuilderImpl;
import com.sun.jersey.core.spi.factory.ResponseBuilderImpl;
import com.sun.jersey.core.spi.factory.VariantListBuilderImpl;
import com.sun.jersey.spi.HeaderDelegateProvider;

public class ClientRuntimeDelegate extends RuntimeDelegate {
	final private Set<HeaderDelegateProvider<?>> hps =
            new HashSet<HeaderDelegateProvider<?>>();
	
	final private Map<Class<?>, HeaderDelegate<?>> map =
            new WeakHashMap<Class<?>, HeaderDelegate<?>>();
	
	public ClientRuntimeDelegate(){
		hps.add(new com.sun.jersey.core.impl.provider.header.LocaleProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.EntityTagProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.MediaTypeProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.CacheControlProvider());
		
		hps.add(new com.sun.jersey.core.impl.provider.header.NewCookieProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.CookieProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.URIProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.DateProvider());
		hps.add(new com.sun.jersey.core.impl.provider.header.StringProvider());	
		
		map.put(Locale.class, _createHeaderDelegate(Locale.class));
		map.put(EntityTag.class, _createHeaderDelegate(EntityTag.class));
		map.put(MediaType.class, _createHeaderDelegate(MediaType.class));
		map.put(CacheControl.class, _createHeaderDelegate(CacheControl.class));
		map.put(NewCookie.class, _createHeaderDelegate(NewCookie.class));
		map.put(Cookie.class, _createHeaderDelegate(Cookie.class));
		map.put(URI.class, _createHeaderDelegate(URI.class));
		map.put(Date.class, _createHeaderDelegate(Date.class));
		map.put(String.class, _createHeaderDelegate(String.class));
	}

	@Override
	public UriBuilder createUriBuilder() {
		return new UriBuilderImpl();
	}

	@Override
	public ResponseBuilder createResponseBuilder() {
		return new ResponseBuilderImpl();
	}

	@Override
	public VariantListBuilder createVariantListBuilder() {
		return new VariantListBuilderImpl();
	}

	@Override
	public <T> T createEndpoint(Application application, Class<T> endpointType)
			throws IllegalArgumentException, UnsupportedOperationException {
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> HeaderDelegate<T> createHeaderDelegate(Class<T> type) {
        if (type == null)
            throw new IllegalArgumentException("type parameter cannot be null");
        
        HeaderDelegate<T> h = Unsafe.cast(map.get(type));
        if (h != null) return h;

        return _createHeaderDelegate(type);
	}
	
	private <T> HeaderDelegate<T> _createHeaderDelegate(Class<T> type) {
        for (HeaderDelegateProvider<?> hp: hps)
            if (hp.supports(type))
                return Unsafe.cast(hp);
        
        return null;
    }

}
