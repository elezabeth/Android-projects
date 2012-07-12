package com.qburst.eshop.pojo;

public class UserObject {
	private User user;
	public static UserObject _userObject;

	public static UserObject getUserObject(){
		if( _userObject == null){
			_userObject = new UserObject();
			//return _classObject;
		}
		return _userObject;
	}
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
