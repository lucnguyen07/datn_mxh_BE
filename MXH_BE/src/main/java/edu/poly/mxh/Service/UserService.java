package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Request.ChangePassRequest;
import edu.poly.mxh.Modal.UserProfile;

import java.util.List;
import java.util.Optional;

public interface UserService {
    void save(UserProfile user);

    boolean changePassword(ChangePassRequest changePassRequest);
    Iterable<UserProfile> findAll();
    Optional<UserProfile> findById(Long id);

    List<UserProfile> findFriendRequestsByIdAndStatusTrue(Long id);

    List<UserProfile> getlistFriendRequest(Long id);

    List<UserProfile> findMemberByConversation(Long id);

    List<UserProfile> findUsersByFirstNameOrLastName(String name);
}
