package edu.poly.mxh.Controller;

import edu.poly.mxh.Modal.Friendship;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Service.FriendService;
import edu.poly.mxh.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/friend")
@CrossOrigin("*")
public class FriendController {
    @Autowired
    private FriendService friendService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<?> requestFriend(@RequestBody Friendship friendRequest) {
//        if (iFriendService.findRequest(friendRequest.getUserReceive().getId(), friendRequest.getUserRequest().getId()).isPresent()) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
        friendService.save(friendRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Kiểm tra đã gửi kết bạn chưa  
    @GetMapping("/checkRequest/{id1}/{id2}")
    public ResponseEntity<Boolean> checkFriendRequest(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        return new ResponseEntity<>(friendService.findRequest(id1, id2).isPresent(), HttpStatus.OK);
    }
    //Kiểm tra đã là bạn bè chưa
    @GetMapping("/checkFriend/{id1}/{id2}")
    public ResponseEntity<Boolean> checkFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        return new ResponseEntity<>(friendService.checkFriend(id1, id2).isPresent(), HttpStatus.OK);
    }
    //Lấy id để láy danh sách bạn bè

    //Lấy danh sách bạn bè của người dùng khác
//    @GetMapping("/{id}")
//    public ResponseEntity<List<UserProfile>> getListFriend(@PathVariable Long id) {
//        List<UserProfile> usersList = userService.findFriendRequestsByIdAndStatusTrue(id);
//        return new ResponseEntity<>(usersList, HttpStatus.OK);
//    }
    // Xóa yêu cầu kết bạn
    @DeleteMapping("/{id1}/{id2}")
    public ResponseEntity<?> deleteFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        friendService.deleteFriendRequest(id1, id2);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    // Chấp nhận kết bạn
    @GetMapping("/accept/{id1}/{id2}")
    public ResponseEntity<?> acceptFriend(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        friendService.acceptFriendRequest(id1, id2);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Lấy danh sách bạn bè theo id người dùng
    @GetMapping("/{id}")
    public ResponseEntity<List<UserProfile>> getListFriend(@PathVariable Long id) {
        List<UserProfile> usersList = userService.findFriendRequestsByIdAndStatusTrue(id);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
    //Lấy danh sách bạn chung
    @GetMapping("/mutual/{friendId}/{loginId}")
    public ResponseEntity<List<UserProfile>> mutualFriends(@PathVariable("friendId") Long friendId, @PathVariable("loginId") Long loginId) {
        //id1 cua friend, id2 cua nguoi dang nhap
        List<UserProfile> usersList = new ArrayList<>();
        for (UserProfile u : userService.findFriendRequestsByIdAndStatusTrue(loginId)) {
            Optional<Friendship> friendRequest = friendService.checkFriend(friendId, u.getUserId());
            if (friendRequest.isPresent()) {
                usersList.add(u);
            }
        }
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }
    //Lấy danh sách lời mời kết bạn
    @GetMapping("/list/request/{id}")
    public ResponseEntity<List<UserProfile>> listFriendRequest(@PathVariable Long id) {
        List<UserProfile> list = userService.getlistFriendRequest(id);
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
}
