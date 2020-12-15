package com.locker.model;

public class Credentials implements Comparable<Credentials>{
  private String socialNetwork;
  private String userName;
  private String password;
public Credentials(String socialNetwork, String userName, String password) {
	this.socialNetwork = socialNetwork;
	this.userName = userName;
	this.password = password;
}
public String getSocialNetwork() {
	return socialNetwork;
}
public void setSocialNetwork(String socialNetwork) {
	this.socialNetwork = socialNetwork;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getPassword() {
	return password;
}
public void setPassword(String password) {
	this.password = password;
}
@Override
public int compareTo(Credentials c) {
	// TODO Auto-generated method stub
	return c.userName.compareTo(this.userName);
}
  
}
