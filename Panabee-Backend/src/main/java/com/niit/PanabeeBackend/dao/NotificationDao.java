package com.niit.PanabeeBackend.dao;

import java.util.List;

import com.niit.PanabeeBackend.model.Notification;

public interface NotificationDao {
	
	
		void addNotification(Notification notification);
		
		//select * from notification where userToBeNotified.email=? and viewed=0
		List<Notification> getAllNotificationsNotViewed(String userId);//glyphicon globe
		
		//select * from notification where notificationId=?
		Notification getNotification(int notificationId);
		
		//update notification set viewed=1 where notificationId=?
		void updateNotificactionViewedStatus(int notificationId);

}
