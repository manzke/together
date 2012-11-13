package com.saperion.sdb.client.models;

import java.util.Collection;

import com.saperion.sdb.client.Together;
import com.saperion.sdb.client.exceptions.AuthenticationException;
import com.saperion.sdb.client.exceptions.ConnectException;
import com.saperion.sdb.spi.rights.ShareRight;
import com.saperion.sdb.spi.rights.UserRight;

public class Share extends OwnedItem<Share, com.saperion.sdb.rs.models.Share> {
	private Space space;
	private User receiver;
	
	public Share(Space space, Together together) {
		this(new com.saperion.sdb.rs.models.Share(), space, together);
	}

	public Share(com.saperion.sdb.rs.models.Share share, Space space, Together together) {
		super(share, space.getResource()+"/"+space.getId()+"/shares", together);
		this.space = space;
		com.saperion.sdb.rs.models.User user = new com.saperion.sdb.rs.models.User();
		user.setId(delegate.getReceiverId());
		user.setName(delegate.getReceiverName());
		//TODO Replace with Interceptor by Guice and @Lazyiness(exclude={"id","name"} 
		receiver = new User(user, together){
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

	@Override
	public ClientModelType getType() {
		return ClientModelType.SHARE;
	}
	
	public Space getSpace() {
		return space;
	}
	
	public User getReceiver() {
		return receiver;
	}

	//delegates
	public String getName() {
		return delegate.getName();
	}

	public Share setName(String name) {
		delegate.setName(name);
		return this;
	}

	public Collection<ShareRight> getRights() {
		return delegate.getRights();
	}

	public Share setRights(Collection<ShareRight> rights) {
		delegate.setRights(rights);
		return this;
	}

	public Share addRight(ShareRight right) {
		delegate.addRight(right);
		return this;
	}
}
