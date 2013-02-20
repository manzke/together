package com.saperion.sdb.client.models;

import java.util.Collection;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.spi.rights.UserRight;

public abstract class OwnedItem<ClientType, BackendType extends com.saperion.sdb.rs.models.CreatedTypedIdentifiable>
		extends Item<ClientType, BackendType> {
	private User owner;
	
	public OwnedItem(BackendType delegate, String resource, Together together) {
		super(delegate, resource, together);
		com.saperion.sdb.rs.models.User user = new com.saperion.sdb.rs.models.User();
		user.setId(delegate.getOwnerId());
		user.setDisplayname(delegate.getOwnerName());
		//TODO Replace with Interceptor by Guice and @Lazyiness(exclude={"id","name"} 
		owner = new User(user, together){
			private boolean fullyLoaded;
			public void lazyLoad() {
				if(!fullyLoaded){
					try {
						reload();
					} catch (AuthenticationException e) {
						throw new IllegalStateException("User object couldn't be loaded.", e);
					} catch (ConnectException e) {
						throw new IllegalStateException("User object couldn't be loaded.", e);
					}
					fullyLoaded = true;
				}
			}
			
			@Override
			public User addRight(UserRight right) {
				lazyLoad();
				return super.addRight(right);
			}

			@Override
			public void recycle() {
				lazyLoad();
				super.recycle();
			}

			@Override
			public void restore() {
				lazyLoad();
				super.restore();
			}

			@Override
			public String getEmail() {
				lazyLoad();
				return super.getEmail();
			}

			@Override
			public String getCompany() {
				lazyLoad();
				return super.getCompany();
			}

			@Override
			public boolean isGuest() {
				lazyLoad();
				return super.isGuest();
			}

			@Override
			public boolean isInRecycleBin() {
				lazyLoad();
				return super.isInRecycleBin();
			}

			@Override
			public User removeRight(UserRight right) {
				lazyLoad();
				return super.removeRight(right);
			}

			@Override
			public Collection<UserRight> getRights() {
				lazyLoad();
				return super.getRights();
			}
		};
	}
	
	public User getOwner(){
		return owner;
	}

	public String getCreationDate() {
		return delegate.getCreationDate();
	}	
}
