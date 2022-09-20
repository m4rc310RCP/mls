package com.m4rc310.rcp.graphql.http;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MHttpUtils {
	public enum TypeMethod {
		GET, POST, PUT, DELETE
	}

	private HttpUriRequest request;
	private final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);

	private static MHttpUtils instance = null;

	private MHttpUtils(TypeMethod type, String uri, Object... params) {

		uri = String.format(uri, params);

		switch (type) {
		case GET:
			request = new HttpGet(uri);
			break;
		case POST:
			request = new HttpPost(uri);
			break;
		case PUT:
			request = new HttpPut(uri);
			break;
		case DELETE:
			request = new HttpDelete(uri);
			break;
		}
	}

	public static MHttpUtils getInstance(TypeMethod type, String uri, Object... params) {
//		if (instance == null) {
		instance = new MHttpUtils(type, uri, params);
//		}
		return instance;
	}

	public static MHttpUtils getInstance(TypeMethod type, String uri, String mapping, Object... params) {
//		if (instance == null) {
		if (mapping.startsWith("/")) {
			mapping = mapping.substring(1);
		}

		if (uri.endsWith("/")) {
			uri = uri.substring(0, uri.length() - 1);
		}

		uri = String.format("%s/%s", uri, mapping);

		instance = new MHttpUtils(type, uri, params);
//		}
		return instance;
	}

	public void setHeader(String name, String value) {
		request.setHeader(name, value);
	}

	public void addValuePairs(String name, String value) {
		BasicNameValuePair bnvp = new BasicNameValuePair(name, value);
		nameValuePairs.add(bnvp);
	}

	private JSONObject jsono;

	public void addJsonBody(String key, Object value) {
		if (jsono == null) {
			jsono = new JSONObject();
		}
		jsono.put(key, value);
	}

//	public void setBody(String value) throws UnsupportedEncodingException {
//		StringEntity entity = new StringEntity(value);
//		if (request instanceof HttpPost) {
//			((HttpPost) request).setEntity(entity);
//		}
//
//		if (request instanceof HttpPut) {
//			((HttpPut) request).setEntity(entity);
//		}
//	}

	public <T> T execute(Class<T> type) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(execute(), type);
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public Object executeBody() throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = executeBody(jsono.toString());
		return mapper.readValue(json, Object.class);
		
	}
	public <T> T executeBody(Class<T> type) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		String json = executeBody(jsono.toString());
		return mapper.readValue(json, type);
		
	}

	public String executeBody(String body) throws IOException {
		StringEntity entity = new StringEntity(body);
		if (request instanceof HttpPost) {
			((HttpPost) request).setEntity(entity);
		}

		if (request instanceof HttpPut) {
			((HttpPut) request).setEntity(entity);
		}

		try (CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse response = httpClient.execute(request)) {

			return EntityUtils.toString(response.getEntity());
		}
	}

	public String execute() {
		try {
			if (request instanceof HttpPost) {
				((HttpPost) request).setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}

			if (request instanceof HttpPut) {
				((HttpPut) request).setEntity(new UrlEncodedFormEntity(nameValuePairs));
			}
			if (request instanceof HttpDelete) {
//				((HttpDelete) request).seC
			}

			try (CloseableHttpClient httpClient = HttpClients.createDefault();
					CloseableHttpResponse response = httpClient.execute(request)) {

				return EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

}
