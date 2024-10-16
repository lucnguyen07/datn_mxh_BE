package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Friendship;

import java.util.Optional;

public interface FriendService {
    void save(Friendship friendship);
    Optional<Friendship> findRequest(Long id1, Long id2);

    Optional<Friendship> checkFriend(Long id1, Long id2);

    void deleteFriendRequest(Long id1, Long id2);

    void acceptFriendRequest(Long id1, Long id2);
}
