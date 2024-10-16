package edu.poly.mxh.Controller;

import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserProfile> getProfile(@PathVariable Long id) {
        Optional<UserProfile> userOptional = this.userService.findById(id);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserProfile> save(@RequestBody UserProfile users, @PathVariable("id") Long id) {
        Optional<UserProfile> userCurrent = userService.findById(id);
        if (!userCurrent.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
//        users.setUserId(userCurrent.get().getUserId());
//        if (users.getAvatar() == null) {
//            users.setAvatar(userCurrent.get().getAvatar());
//        }
        users.setPassword(userCurrent.get().getPassword());
        userService.save(users);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<UserProfile>> findUsersByFirstNameOrLastName(@RequestParam("search") String name) {
        List<UserProfile> usersList = userService.findUsersByFirstNameOrLastName(name);
        return new ResponseEntity<>(usersList, HttpStatus.OK);
    }

    @GetMapping("/admin")
    public ResponseEntity<Iterable<UserProfile>> showAllUserByAdmin() {
        Iterable<UserProfile> users = userService.findAll();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PutMapping("/block/{id}")
    public ResponseEntity<UserProfile> block(@PathVariable("id") Long id) {
        Optional<UserProfile> userCurrent = userService.findById(id);
        if (!userCurrent.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserProfile userProfile = userCurrent.get();
        userProfile.setIsBlock(1);
        userService.save(userProfile);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    @PutMapping("/unblock/{id}")
    public ResponseEntity<UserProfile> unblock(@PathVariable("id") Long id) {
        Optional<UserProfile> userCurrent = userService.findById(id);
        if (!userCurrent.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        UserProfile userProfile = userCurrent.get();
        userProfile.setIsBlock(0);
        userService.save(userProfile);
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }
}
