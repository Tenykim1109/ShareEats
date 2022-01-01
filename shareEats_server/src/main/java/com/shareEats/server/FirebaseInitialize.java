package com.shareEats.server;

import javax.annotation.PostConstruct;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;

@Service
public class FirebaseInitialize {
	
	@PostConstruct
	public void initialize() {
		try {
			ClassPathResource serviceAccount = new ClassPathResource("/firebase/shareeats-firebase.json");
			
			FirebaseOptions options = FirebaseOptions.builder()
					.setCredentials(GoogleCredentials.fromStream(serviceAccount.getInputStream()))
					.setDatabaseUrl("https://shareeats-5654f-default-rtdb.firebaseio.com/")
					.build();
			
			FirebaseApp.initializeApp(options);
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

}
