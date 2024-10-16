package edu.poly.mxh.Controller;

import edu.poly.mxh.Modal.Notification;
import edu.poly.mxh.Service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/notification")
@CrossOrigin("*")
public class NotificationController {
    @Autowired
    private NotificationService notificationService;

    @MessageMapping("/sendNotification")
    @SendTo("/topic/notifications")
    public String sendNotification(String id) {
        // Xử lý logic ở đây
        return "hello";
    }

    @GetMapping("/seen/{id}")
    public ResponseEntity<?> seenNotification(@PathVariable Long id) {
        Notification notification = notificationService.findById(id).get();
        notification.setIsRead(1);
        notificationService.save(notification);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<List<Notification>> getAll(@PathVariable Long id) {
        List<Notification> notifications = notificationService.getAll(id);
        Collections.sort(notifications, Comparator.comparing(Notification::getCreateAt).reversed());
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
}
