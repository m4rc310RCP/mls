package com.m4rc310.rcp.graphql;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;

public class Test {

	public String accessToken;
	public String refreshToken;

	public static void main(String[] args) throws Exception {

		Test test = new Test();
		test.testSignin();
		test.testRefreshToken();
//		test.testAccess();
//		test.testRet();
//		test.testLogout();

//		new Test().testSignin();;
//		new Test().testRefreshToken();
//		new Test().testAccess();
//		new Test().testLogout();

//		HttpClient httpClient = HttpClientBuilder.create().build();
//		HttpPost httpPost = new HttpPost("http://localhost:8080/api/auth/refreshtoken");
//		HttpPost httpPost = new HttpPost("http://localhost:8080/api/auth/signin");
//		HttpPost httpPost = new HttpPost("http://localhost:8080//api/test/user");
//		HttpPost httpPost = new HttpPost("http://localhost:8080//api/test/all");

//		String token = "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtbHMiLCJpYXQiOjE2NTk5MTEwNDUsImV4cCI6MTY1OTkxMTY0NX0.cqAEY0xEXWFs1fqg2gCooSF1iVyVpOOTU7_5Vr4OklCdBOoS9-Q1g3_CDhqAN2cD21ueFL4Ej1WcPwxoJNVdTg";
//		String refreshToken = "bc88cd97-1fa9-47bb-9eee-327619cf12db";
//		
//		httpPost.setHeader("Accept", "application/json");
//		httpPost.setHeader("Content-type", "application/json");
//		httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);

//		JSONObject u = new JSONObject();
//		u.put("role", "ROLE_ADMINS");
//		u.put("login", "mls");
//		u.put("email", "marcelo.utfpr@me.com");
//		u.put("password", "1979");
//		u.put("roles", Arrays.asList("ROLE_USER", "ROLE_ADMIN"));
//		u.put("refreshToken", refreshToken);

//		ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
//		httpPost.setEntity(entity);
//		
//		HttpResponse response = httpClient.execute(httpPost);
//        String result = EntityUtils.toString(response.getEntity());
//		
//        System.out.println(result);

	}

	private void testSignin() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/auth/signin");

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		JSONObject u = new JSONObject();
		u.put("login", "mls");
		u.put("password", "1979");

		ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
		httpPost.setEntity(entity);

		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity());

		u = new JSONObject(result);
		this.accessToken = u.get("access_token").toString();
		this.refreshToken = u.get("refresh_token").toString();

		System.out.println(accessToken);

	}

	private void testRefreshToken() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/auth/refreshtoken");

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		System.out.println(refreshToken);

		JSONObject u = new JSONObject();
		u.put("refresh_token", refreshToken);

		ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
		httpPost.setEntity(entity);

		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity());

		u = new JSONObject(result);
		this.accessToken = u.get("access_token").toString();
		System.out.println(accessToken);
	}

	@SuppressWarnings("unused")
	private void testAccess() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/test/user");

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

		JSONObject u = new JSONObject();
		ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
		httpPost.setEntity(entity);

		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity());

		System.out.println(result);
		
		
	}

	@SuppressWarnings("unused")
	private void testLogout() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/auth/logout");

		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");

		JSONObject u = new JSONObject();
		u.put("login", "mls");

		ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
		httpPost.setEntity(entity);

		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity());

		System.out.println(result);
	}
	
	@SuppressWarnings("unused")
	private void testRet() throws Exception {
		HttpClient httpClient = HttpClientBuilder.create().build();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/test/ret");
		
		httpPost.setHeader("Accept", "application/json");
		httpPost.setHeader("Content-type", "application/json");
		
		httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken);

		
		JSONObject u = new JSONObject();
		
		ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
		httpPost.setEntity(entity);
		
		HttpResponse response = httpClient.execute(httpPost);
		String result = EntityUtils.toString(response.getEntity());
		
		System.out.println(result);
	}

}
