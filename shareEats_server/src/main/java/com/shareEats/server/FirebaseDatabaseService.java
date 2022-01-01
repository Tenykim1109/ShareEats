package com.shareEats.server;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

@Component
public class FirebaseDatabaseService {
	
	private FirebaseDatabase database;
	private DatabaseReference mRef;
	private static final Logger logger = LoggerFactory.getLogger(FirebaseDatabaseService.class);

	@Scheduled(fixedDelay = 5000)
	public void checkUser() throws Exception {
		
		logger.info("remove method: called!");
		
		database = FirebaseDatabase.getInstance();
		mRef = database.getReference();
		
		Long current = System.currentTimeMillis();
		
		Date temp = new Date(current);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.KOREA);
		String today = format.format(temp);
		
		mRef.addValueEventListener(new ValueEventListener() {
			
			@Override
			public void onDataChange(DataSnapshot snapshot) {
				for (DataSnapshot postSnapshot : snapshot.child("Post").getChildren()) {
					Long dead_date = postSnapshot.child("date").getValue(Long.class);
					
					Date temp2 = new Date(dead_date);
					String deadLine = format.format(temp2);
					int compareDate = today.compareTo(deadLine);
					
					if (compareDate > 0) {
						String status = postSnapshot.child("completed").getValue(String.class);
						if (status.equals("모집중")) {
							logger.info("current date : " + current + " deadLine date : " + deadLine.toString());
							int postId = postSnapshot.child("postId").getValue(Integer.class);
							logger.info("postId : " + String.valueOf(postId));
							mRef.child("Post").child(String.valueOf(postId)).removeValue(null);
							
							for (DataSnapshot userSnapshot : snapshot.child("User").getChildren()) {
								
								for (DataSnapshot listSnapshot : userSnapshot.child("postList").getChildren()) {
									int post = listSnapshot.child("postId").getValue(Integer.class);
									logger.info(String.valueOf(post));
									if (post == postId) {
										String userId = userSnapshot.child("id").getValue(String.class);
										mRef.child("User").child(userId).child("postList").child(String.valueOf(postId)).removeValue(null);
										logger.info("user postList remove check: remove postList done!");
									}
								}
								
							}
						}
					}
						
				}
			}
			
			@Override
			public void onCancelled(DatabaseError error) {
				logger.error(error.getMessage());
			}
		});
		
	}
	
	
}
