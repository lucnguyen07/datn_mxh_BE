package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Notification;
import edu.poly.mxh.Repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationsRepository notificationsRepository;

    @Override
    public void save(Notification notification) {
        notificationsRepository.save(notification);
    }

    @Override
    public Optional<Notification> findById(Long id) {
        return notificationsRepository.findById(id);
    }

    @Override
    public List<Notification> getAll(Long userId) {
        List<Notification> notifications = notificationsRepository.getAll(userId);
        return notifications;
    }
}
