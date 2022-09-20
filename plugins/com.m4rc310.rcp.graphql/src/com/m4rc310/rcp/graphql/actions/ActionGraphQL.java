package com.m4rc310.rcp.graphql.actions;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.ui.di.UISynchronize;
import org.json.JSONObject;

import com.m4rc310.rcp.graphql.MGraphQL;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
@Singleton
public class ActionGraphQL extends MAction implements ActionGraphQLParams {

	@Inject
	MGraphQL graphQL;

	@Inject
	UISynchronize sync;

	@Inject
	IEclipseContext context;

	public static final String TOKEN_REQUEST_URL = "%s/login";

	public void requestParams(String user, String password) {
		Job.create("Request params", monitor -> {

			context.set("access_token", "");
			
			try {
				

			String tokenEndpoint = String.format(TOKEN_REQUEST_URL, graphQL.getServerUrl());
			
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost httpPost = new HttpPost(tokenEndpoint);
			
			JSONObject u = new JSONObject();
			u.put("login", user);
			u.put("hash", password);
			
			
			ByteArrayEntity entity = new ByteArrayEntity(u.toString().getBytes("UTF-8"));
			httpPost.setEntity(entity);
			
			HttpResponse response = httpClient.execute(httpPost);
	        String result = EntityUtils.toString(response.getEntity());
	        
	        context.set("access_token", result);
			

			} catch (Exception e) {
				e.printStackTrace();
			}
//			String client_id = user;
//			String client_secret = password;
//			String grant_type = "client_credentials";
//			String scope = "password";
//
//			HttpClient httpClient = HttpClientBuilder.create().build();
//			HttpPost httpPost = new HttpPost(tokenEndpoint);
//
//			String base64Credentials = Base64.getEncoder().encodeToString((client_id + ":" + client_secret).getBytes());
//
//			
//			JSONObject u = new JSONObject();
//			u.put("login", "mlsilva");
//			u.put("hash", "1234");
//			
//			System.out.println(u.toString());
//			
//			
//			httpPost.addHeader("Authorization", u.toString());
////			httpPost.addHeader("Authorization", "Basic " + base64Credentials);
////			httpPost.addHeader("Content-Type", "application/x-www-form-urlencoded");
//			httpPost.addHeader("Content-Type", "application/raw");
//
//			/* PROXY CONFIG */
////			HttpHost target = new HttpHost("proxy", 8080, "http");
////			RequestConfig config = RequestConfig.custom().setProxy(target).build();
////			httpPost.setConfig(config);
//
//			/* OAUTH PARAMETERS ADDED TO BODY */
//			StringEntity input = null;
//			try {
//				input = new StringEntity("grant_type=" + grant_type + "&scope=" + scope);
//				httpPost.setEntity(input);
//			} catch (UnsupportedEncodingException e) {
//				e.printStackTrace();
//			}
//
//			/* SEND AND RETRIEVE RESPONSE */
//			HttpResponse response = null;
//			try {
//				response = httpClient.execute(httpPost);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
//
//			/* RESPONSE AS STRING */
//			String result = null;
//			try {
//				result = IOUtils.toString(response.getEntity().getContent(), "UTF-8");
////				JSONObject jsono = new JSONObject(result);
//				
//				System.out.println(result);
				
//				context.set("access_token", jsono.get("access_token"));
//				graphQL.setAccessToken(jsono.get("access_token").toString());
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

		}).schedule();
	}

	public void requestParams(String key) {
		Job.create("requesting params...", monitor -> {
			sync.asyncExec(() -> {
				fire(EVENT$change_to_wait);

				if (!graphQL.isServerAvaliable()) {
					fire(EVENT$on_error, "Servidor indisponível!");
				}

			});
		}).schedule();
	}
}
