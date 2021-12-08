package com.shareEats.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.common.net.HttpHeaders;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class FirebaseCloudMessageService {

	private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessageService.class);
	
//	public final ObjectMapper objectMapper;
//	
//	private final String API_URL = "https://fcm.googleapis.com/v1/projects/shareeats-5654f/messages:send";
//	
//	public FirebaseCloudMessageService(ObjectMapper objectMapper) {
//		this.objectMapper = objectMapper;
//	}
	
//	private List<String> clientTokens = new ArrayList<>();
//	
//	public void addToken(String token) {
//		clientTokens.add(token);
//	}
//	
//	private String getAccessToken() throws IOException {
//		String firebaseConfigPath = "firebase/shareeats-firebase.json";
//		
//		GoogleCredentials googleCredentials = GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())
//				.createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
//		
//		googleCredentials.refreshIfExpired();
//		String token = googleCredentials.getAccessToken().getTokenValue();
//		
//		return token;
//	}
	
	public String sendMessage(String subscribe) throws FirebaseMessagingException, IOException {
		logger.info("send count check: check");
		
		String title = "주문 알림";
		String body = "주문이 완료되었습니다.";
		
		Notification notification = Notification.builder().setTitle(title).setBody(body).build();
		
		ClassPathResource serviceAccount = new ClassPathResource("/firebase/shareeats-firebase.json");
		
		FirebaseApp firebaseApp = null;
		List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
		
		if (firebaseApps != null && !firebaseApps.isEmpty()) {
			for (FirebaseApp app : firebaseApps) {
				if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
					firebaseApp = app;
			}
		} else {
			FirebaseOptions options = new FirebaseOptions.Builder()
		            .setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
		            .setDatabaseUrl("https://shareeats-5654f-default-rtdb.firebaseio.com/")
		            .build();

			firebaseApp = FirebaseApp.initializeApp(options);
		}
		
		Message msg = Message.builder()
				.setTopic(subscribe)
				.setNotification(notification)
				.build();
		
		String response = FirebaseMessaging.getInstance().send(msg);
		
		if (response.equals("null") || response.equals("")) {
			return "전송 실패";
		}
		return "전송 완료";
		
	}
	
	
//	private String makeMessage(String targetToken, String title, String body) throws JsonProcessingException {
//		Notification noti = new FcmMessage.Notification(title, body, null);
//		
//		Message message = new FcmMessage.Message(null, targetToken);
//		Map<String, String> data = new HashMap<>();
//		data.put("title", title);
//		data.put("body", body);
//		message.setData(data);
//		
//		FcmMessage fcmMessage = new FcmMessage(false, message);
//		
//		return objectMapper.writeValueAsString(fcmMessage);
//	}
	
//	public void sendMessageTo(String targetToken, String title, String body) throws IOException {
//		String message = makeMessage(targetToken, title, body);
//		logger.info("message: {}", message);
//		OkHttpClient client = new OkHttpClient();
//		RequestBody requestBody = RequestBody.create(message, MediaType.get("application/json; charset=utf-8"));
//		Request request = new Request.Builder()
//				.url(API_URL)
//				.post(requestBody)
//				.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + getAccessToken())
//				.addHeader(HttpHeaders.CONTENT_TYPE, "application/json; UTF-8")
//				.build();
//		
//		Response response = client.newCall(request).execute();
//		
//		System.out.println(response.body().string());
//	}
	
	
//	public int broadCastMessage(String title, String body) throws IOException {
//		for (String token : clientTokens) {
//			logger.debug("broadcastmessage : {},{},{}", token, title, body);
//			sendMessageTo(token, title, body);
//		}
//		return clientTokens.size();
//	}
}
