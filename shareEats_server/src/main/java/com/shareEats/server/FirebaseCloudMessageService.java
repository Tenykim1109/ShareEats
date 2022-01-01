package com.shareEats.server;


import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;


@Service
public class FirebaseCloudMessageService {

	private static final Logger logger = LoggerFactory.getLogger(FirebaseCloudMessageService.class);
	
	
	public String sendMessage(String subscribe) throws FirebaseMessagingException, IOException {
		logger.info("send count check: check");
		
//		ClassPathResource serviceAccount = new ClassPathResource("/firebase/shareeats-firebase.json");
//		
//		FirebaseApp firebaseApp = null;
//		List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
//		
//		if (firebaseApps != null && !firebaseApps.isEmpty()) {
//			for (FirebaseApp app : firebaseApps) {
//				if (app.getName().equals(FirebaseApp.DEFAULT_APP_NAME))
//					firebaseApp = app;
//			}
//		} else {
//		FirebaseOptions options = FirebaseOptions.builder()
//				.setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
//				.setDatabaseUrl("https://shareeats-5654f-default-rtdb.firebaseio.com/")
//				.build();
//
//			firebaseApp = FirebaseApp.initializeApp(options);
//		}
		
		String title = "주문 알림";
		String body = "주문이 완료되었습니다.";
		
		Notification notification = Notification.builder().setTitle(title).setBody(body).build();
		
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
	
	
}
