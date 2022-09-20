package com.m4rc310.rcp.constants;


import java.util.List;

import com.m4rc310.rcp.constants.security.exceptions.MLoginException;

public interface IMSecurity extends MConst{
	String getAccessToken() throws Throwable;
	String getUsername();
	
	void activate();
	void processLogin(String username, String password) throws MLoginException;
	void processLogout(String username) throws MLoginException;
	void verifyCredentials();
	void processLoginOrLogout();
	
	boolean hasAccessForRoles(List<String> roles);
}
