package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Friendship;
import edu.poly.mxh.Modal.Notification;
import edu.poly.mxh.Repository.FriendRepository;
import edu.poly.mxh.Repository.NotificationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class FriendServiceImpl implements FriendService{
    @Autowired
    private FriendRepository friendRepository;
    @Autowired
    private NotificationsRepository notificationsRepository;

    @Transactional
    @Override
    public void save(Friendship friendship) {
        friendship.setStatus(0); // 0 là trạng thái chờ xác nhận
        notificationsRepository.createNotificationFriend(friendship.getUserIdSender(),friendship.getUserIdRecipient()
                ," đã gửi lời mời kết bạn", "friend");
        friendRepository.save(friendship);

    }

    @Override
    public Optional<Friendship> findRequest(Long id1, Long id2) {
        return friendRepository.findRequest(id1,id2);
    }

    @Override
    public Optional<Friendship> checkFriend(Long id1, Long id2) {
        return friendRepository.checkFriend(id1,id2);
    }

    @Override
    @Transactional
    public void deleteFriendRequest(Long id1, Long id2) {
        friendRepository.deleteFriendRequest(id1, id2);
        friendRepository.deleteFriendRequest(id2, id1);
        notificationsRepository.deleteNotificationFriend(id2, id1, "friend");
    }

    @Override
    @Transactional
    public void acceptFriendRequest(Long id1, Long id2) {
        deleteFriendRequest(id1, id2);
        friendRepository.acceptFriendRequest(id1, id2);
        friendRepository.acceptFriendRequest(id2, id1);
        notificationsRepository.createNotificationFriend(id2,id1
                ," đã chấp nhận lời mời kết bạn", "friendacp");
        notificationsRepository.deleteNotificationFriend(id1, id2, "friend");
    }
}
