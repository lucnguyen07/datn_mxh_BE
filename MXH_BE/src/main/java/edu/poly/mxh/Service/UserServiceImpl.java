package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.Request.ChangePassRequest;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<UserProfile> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void save(UserProfile user) {
        userRepository.save(user);
    }

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean changePassword(ChangePassRequest changePassRequest) {
        UserProfile users_update = userRepository.findById(changePassRequest.getId()).orElse(null);
        if (users_update == null) {
            return false;
        }
        // Lấy danh sách tất cả người dùng
        Iterable<UserProfile> users = this.findAll();
        boolean isCorrectUser = false;
        // Kiểm tra xem email và mật khẩu cũ có đúng không
        for (UserProfile currentUser : users) {
            boolean res = currentUser.getEmail().equals(users_update.getEmail())
                    && passwordEncoder.matches(changePassRequest.getOldPassword(), currentUser.getPassword());
            if (res) {
                isCorrectUser = true;
                break;
            }
        }
        // Nếu thông tin người dùng hợp lệ (mật khẩu cũ đúng)
        if (isCorrectUser) {
            if (changePassRequest.getNewPassword() == null || changePassRequest.getNewPassword().isEmpty()) {
                throw new IllegalArgumentException("New password cannot be null or empty");
            }
            users_update.setPassword(passwordEncoder.encode(changePassRequest.getNewPassword()));
            userRepository.save(users_update);
        }
        return isCorrectUser;
    }


    @Override
    public List<UserProfile> findFriendRequestsByIdAndStatusTrue(Long id) {
        return userRepository.findFriendsByIdAndStatusTrue(id);
    }

    @Override
    public List<UserProfile> getlistFriendRequest(Long id) {
        return userRepository.listFriendRequest(id);
    }

    @Override
    public List<UserProfile> findMemberByConversation(Long id) {
        return userRepository.findMemberByConversation(id);
    }

    @Override
    public List<UserProfile> findUsersByFirstNameOrLastName(String name){
        return userRepository.findUserProfileByFirstNameContainingOrLastNameContaining(name, name);
    }

    @Override
    public Iterable<UserProfile> findAll() {
        return userRepository.findAll();
    }
}
