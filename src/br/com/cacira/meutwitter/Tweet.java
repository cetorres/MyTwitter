package br.com.cacira.meutwitter;

public class Tweet {
	
	String mText;
	String mDateTime;
	String mUserName;
	String mUserScreenName;
	String mUserImage;
	String mUserLocation;
	
	public Tweet(String userName, String userScreenName, String userImage, String userLocation, String text, String dateTime)
	{
		mUserName = userName;
		mUserScreenName = userScreenName;
		mUserImage = userImage;
		mText = text;
		mDateTime = dateTime;
		mUserLocation = userLocation;
	}
	
	public String getText() {
		return mText;
	}
	
	public String getDateTime() {
		return mDateTime;
	}
	
	public String getUserName() {
		return mUserName;
	}
	
	public String getUserScreenName() {
		return mUserScreenName;
	}
	
	public String getUserImage() {
		return mUserImage;
	}
	
	public String getUserLocation() {
		return mUserLocation;
	}
}
