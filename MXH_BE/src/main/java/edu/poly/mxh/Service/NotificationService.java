package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Notification;

import java.util.List;
import java.util.Optional;

public interface NotificationService {
    void save(Notification notification);

    Optional<Notification> findById(Long id);

    List<Notification> getAll(Long userId);
}
