package com.m4rc310.rcp.graphql;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.apache.http.impl.client.HttpClients;
import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Platform;
import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.di.extensions.Preference;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.m4rc310.rcp.constants.MConst;
import com.m4rc310.rcp.constants.security.IMSecurity;
import com.m4rc310.rcp.constants.security.exceptions.MAccessTokenException;
import com.m4rc310.rcp.graphql.websocket.SpringBootWebSocketClient;
import com.m4rc310.rcp.graphql.websocket.StompMessageListener;
import com.m4rc310.rcp.graphql.websocket.TopicHandler;

@Creatable
@Singleton
public class MGraphQL implements MConst {

	// private final String LOCAL_HOST = "localhost:8080";
	// private final String SERVER_HOST = "ml-br.herokuapp.com";

//	private final String HTTP = "http";
//	private final String HTTPS = "https";

//	private final String LOCAL_URI = String.format("%s://%s/graphql", HTTP, LOCAL_HOST);
//	private final String SERVER_URI = String.format("%s://%s/graphql", HTTP, SERVER_HOST);

	@Inject
	@Preference(value = "is.local.server")
	private boolean isLocalHost;

	@Inject
	@Preference(value = "url.local.server")
	private String urlLocalServer;

	@Inject
	@Preference(value = "url.web.server")
	private String urlWebServer;

	@Inject
	@Preference(value = "url.local.socket")
	private String urlLocalSocket;

	@Inject
	@Preference(value = "url.web.socket")
	private String urlWebSocket;

	@Inject
	// @Named(NAMED$access_token)
	@org.eclipse.e4.core.di.annotations.Optional
	private IMSecurity security;
	// private String accessToken;

//	jsono.get("access_token")

	// private final String HOST = isLocalHost ? LOCAL_HOST : SERVER_HOST;

//	private final String URI = String.format("%s://%s/graphql", isLocalHost ? HTTP : HTTPS, HOST);

	// private final String URI_WEB_SOCKET = String.format("ws://%s/ws/websocket",
	// HOST);
	private JSONObject data;
	private JSONArray errors;

	@Inject
	SpringBootWebSocketClient client;

	private String body;

//	@Inject
//	public void asyncQuery(String query, String ok, String err, StompMessageListener listener) {
//		subscribe(ok, listener);
//	}

	public String getServerUrl() {
		return isLocalHost ? urlLocalServer : urlWebServer;
	}

	public MGraphQL queryInFile(String pluginId, String path, Object... args) {

		try {
			URL bundle = Platform.getBundle(pluginId).getResource(path);
			path = FileLocator.toFileURL(bundle).getFile();
			String query = new String(Files.readAllBytes(Paths.get(path)));
			return query(query, args);
		} catch (Throwable e) {
			e.printStackTrace();
			return null;
		}
	}

	public MGraphQL query(String query, Object... args) throws Throwable {

		if (!isServerAvaliable()) {
			throw new Exception("Servidor indisponível");
		}

		query = String.format(query, args);

		ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
				HttpClients.createDefault());

		RestTemplate restTemplate = new RestTemplate(requestFactory);
		restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

		HttpHeaders headers = new HttpHeaders();
		headers.add("content-type", "application/graphql");
		if (security != null) {
			headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + security.getAccessToken());
		}

		String uri = String.format("%s/graphql", isLocalHost ? urlLocalServer : urlWebServer);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, new HttpEntity<>(query, headers),
				String.class);

		this.body = response.getBody();

		return this;
	}

	@SuppressWarnings("unchecked")
	public MGraphQL query_(String query, Object... args) {
		try {

			if (!isServerAvaliable()) {
				throw new Exception("Servidor indisponível");
			}

			query = String.format(query, args);

			ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(
					HttpClients.createDefault());

			RestTemplate restTemplate = new RestTemplate(requestFactory);
			restTemplate.getMessageConverters().add(0, new StringHttpMessageConverter(StandardCharsets.UTF_8));

			HttpHeaders headers = new HttpHeaders();
			headers.add("content-type", "application/graphql");
			if (Objects.nonNull(security)) {
				headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + security.getAccessToken());
			} else {
				throw new Exception("Usuário não está autenticado!");
			}

			String uri = String.format("%s/graphql", isLocalHost ? urlLocalServer : urlWebServer);

			ResponseEntity<String> response = restTemplate.postForEntity(uri, new HttpEntity<>(query, headers),
					String.class);

			JSONParser parser = new JSONParser();

			JSONObject jsono = (JSONObject) parser.parse(response.getBody());

			this.errors = (JSONArray) jsono.get("errors");

			if (Objects.nonNull(errors)) {
				errors.forEach(item -> {
					JSONObject error = (JSONObject) item;
					String message = (String) error.getOrDefault("message", "");
					throw new UnsupportedOperationException(message);
				});
			}

			this.data = (JSONObject) jsono.get("data");

			return this;
		} catch (MAccessTokenException e) {
			throw new UnsupportedOperationException(e);
		} catch (Throwable e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public String toJson(Object obj) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault());
//			DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault());
			mapper.setDateFormat(df);
			mapper.setTimeZone(TimeZone.getTimeZone("GMT"));
//			final SimpleModule module = new SimpleModule();
//			module.addDeserializer(MEnum.class, new MEnumDeserializer(MEnum.class));
//			mapper.registerModule(module);
//			mapper.enable(SerializationFeature.WRITE_ENUMS_USING_INDEX);
//		    mapper.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new UnsupportedOperationException(e);
		}
	}

	public String toGraph(Object value) {
		try {
			String json = toJson(value);
			json = json.trim();

			Pattern p = Pattern.compile("\"\\w+\":");
			Matcher m = p.matcher(json);

			while (m.find()) {
				String okey = m.group();
				String nkey = okey.substring(1, okey.length() - 2);
				nkey = String.format("%s:", nkey);
				json = json.replace(okey, nkey);
			}

			return json;

		} catch (Exception e) {
			throw new UnsupportedOperationException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMapData(String field) {
		try {

			Object value = data.get(field);

			if (value instanceof JSONObject) {
				JSONObject jsono = (JSONObject) data.get(field);
				return new ObjectMapper().readValue(jsono.toJSONString(), HashMap.class);
			}

			throw new UnsupportedOperationException("Invalid Field.");
		} catch (Exception e) {
			throw new UnsupportedOperationException("Invalid Field.");
		}
	}

	public <T> List<T> getDataList(String field, Class<? extends T> type) {
		try {
			JSONArray array = (JSONArray) data.get(field);

			ObjectMapper mapper = new ObjectMapper();
			StdDateFormat isoDate = new StdDateFormat();
			isoDate.setTimeZone(TimeZone.getDefault());
			mapper.setDateFormat(isoDate);

			Iterator<?> iterator = array.iterator();
			List<T> r = new ArrayList<T>();
			while (iterator.hasNext()) {
				JSONObject jsono = (JSONObject) iterator.next();
				T value = mapper.readValue(jsono.toJSONString(), type);
				r.add(type.cast(value));
			}
			return r;
		} catch (Exception e) {
			e.printStackTrace();
			return Arrays.asList();
		}

	}

	@SuppressWarnings("unchecked")
	public <T> Optional<T> getValue(String field, Class<T> type) throws Exception {
		try {
			JSONObject jsono = (JSONObject) new JSONParser().parse(body);
			if (jsono.containsKey("data")) {
				jsono = (JSONObject) jsono.get("data");
			} else if (jsono.containsKey("errors")) {
				JSONArray array = (JSONArray) jsono.get("errors");
				Iterator<?> itrs = array.iterator();
				StringBuilder sb = new StringBuilder();
				while (itrs.hasNext()) {
					JSONObject error = (JSONObject) itrs.next();
					sb.append(error.getOrDefault("message", "")).append("\n");
				}
				throw new Exception(sb.toString());
			}

			Object value = jsono.get(field);
			return Optional.ofNullable(type.cast(value));
		} catch (Exception e) {
			return Optional.empty();
		}
	}

	public <T> Optional<T> getData(String field, Class<T> type) {
		try {

			Object value = data.get(field);

			if (value instanceof JSONObject) {

				JSONObject jsono = (JSONObject) data.get(field);
				ObjectMapper mapper = new ObjectMapper();

//				Gson g = new Gson();

//				mapper.setDateFormat(new SimpleDateFormat("MM/dd/yyyy"));
//				 mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//				 mapper.configure(com.fasterxml.jackson.core.JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES,
//				   true);
//				mapper.registerModule(new JavaTimeModule());
//				mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//				 mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//				 mapper.setDateFormat(new StdDateFormat().withColonInTimeZone(true));
//				1979-01-18T00:00:00Z

//				DateFormat dateFormat = mapper.getDateFormat();
//				dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
//				
//				mapper.setDateFormat(dateFormat);

//				StdDateFormat isoDate = new StdDateFormat();
//				isoDate.setTimeZone(TimeZone.getTimeZone("GMT+3"));
//				mapper.setDateFormat(isoDate);
////				mapper.setTimeZone(TimeZone.getDefault());
//				mapper.setTimeZone(TimeZone.getTimeZone("GMT+3"));

//				System.out.println(jsono);

//				String json = removeQuotesAndUnescape(jsono.toJSONString());

//				return Optional.ofNullable(g.fromJson(json, type));
				return Optional.ofNullable(mapper.readValue(jsono.toJSONString(), type));
			}

			return Optional.ofNullable(type.cast(value));
		} catch (NullPointerException ex) {
			return Optional.empty();
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

//	private String removeQuotesAndUnescape(String uncleanJson) {
//	    String noQuotes = uncleanJson.replaceAll("^\"|\"$", "");
//
//	    return noQuotes;
//	}

	public <T> Optional<T> jsonToObject(String json, Class<T> type) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			StdDateFormat isoDate = new StdDateFormat();
			isoDate.setTimeZone(TimeZone.getDefault());
			mapper.setDateFormat(isoDate);
			mapper.setTimeZone(TimeZone.getDefault());
			mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);

			T ret = mapper.readValue(json, type);

			return Optional.ofNullable(ret);
		} catch (Exception e) {
			e.printStackTrace();
			return Optional.empty();
		}
	}

	public void subscribe(String topic, StompMessageListener listener) {
		try {

			topic = String.format("/topics/%s", topic);

			TopicHandler handler = client.subscribe(topic);
			handler.addListener(listener);

			if (!client.isConnected()) {
				connectWebSocket();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
//		String uri = String.format("ws://%s/ws/websocket", isLocalHost ? urlLocalServer : urlWebServer);

	}

	public void connectWebSocket() {
		String uri = isLocalHost ? urlLocalSocket : urlWebSocket; // "ws://localhost:8080/ws/websocket";
		client.connect(uri);
		System.out.println("Connected in :" + uri);
	}

	public boolean onError() {
		return errors != null || !errors.isEmpty();
	}

	public GraphQLQueryException getError(Throwable e) {
		try {
			if (errors == null || errors.isEmpty()) {

				if (e != null) {
					return new GraphQLQueryException(e.getMessage());
				}

				return null;
			}

			JSONObject error = (JSONObject) errors.get(0);
			String message = (String) error.get("message");

			int i = message.indexOf(" : ");
			message = message.substring(i);
			message = message.replace(" : ", "");

			return new GraphQLQueryException(message);
		} catch (Exception er) {
			throw new GraphQLQueryException(er);
		}

	}

	public GraphQLQueryException getError() {
		return getError(null);
	}

	public boolean isServerAvaliable() {
		try {
			String surl = String.format("%s/vrf", getServerUrl());
			final URL url = new URL(surl);
			final URLConnection conn = url.openConnection();
			conn.connect();
			conn.getInputStream().close();
			return true;
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			return false;
		}
	}

}
