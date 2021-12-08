package com.shareEats.server;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.firebase.messaging.FirebaseMessagingException;

@RestController	
@RequestMapping("/subscribe")
@CrossOrigin("*")
public class SubscribeController {

	private static final Logger logger = LoggerFactory.getLogger(SubscribeController.class);
	
	@Autowired
	FirebaseCloudMessageService service;
	
	@GetMapping("/{subscribe}")
	public String broadCast(@PathVariable String subscribe) throws FirebaseMessagingException, IOException {
		return service.sendMessage(subscribe);
	}
	
}
