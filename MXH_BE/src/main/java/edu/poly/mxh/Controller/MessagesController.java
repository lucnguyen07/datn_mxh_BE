package edu.poly.mxh.Controller;

import edu.poly.mxh.Modal.Conversations;
import edu.poly.mxh.Modal.Messages;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Service.ConversationService;
import edu.poly.mxh.Service.MessageService;
import edu.poly.mxh.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/message")
@CrossOrigin("*")
public class MessagesController {
    @Autowired
    private ConversationService conversationService;
    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;
    //Lấy tất cả các cuộc trò chuyện trong bảng conversation
    @GetMapping("/room/{id1}/{id2}")
    public ResponseEntity<Conversations> getPersonalConversation(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
        return new ResponseEntity<>(conversationService.findPersonalConversation(id1, id2), HttpStatus.OK);
    }

    @GetMapping("/room/{id}")
    public ResponseEntity<List<Conversations>> getAllPersonalConversation(@PathVariable Long id){
        return new ResponseEntity<>(conversationService.getAllPersonalConversation(id), HttpStatus.OK);
    }

    @PostMapping("/member")
    public ResponseEntity<List<Object>> findAllMemberInConversation(@RequestBody List<Conversations> conversations){
        List<Object> users = new ArrayList<>();
        for (Conversations conversation: conversations){
            users.add(userService.findMemberByConversation(conversation.getConversationId()));
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    //Lấy tất cả tin nhắn trong 1 cuộc trò chuyện
    @GetMapping("/messages/{id}")
    public ResponseEntity<List<Messages>> getMessage(@PathVariable Long id){
        Conversations conversation = conversationService.findById(id).get();
        List<Messages> messages = messageService.findAllByConversations(conversation);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }

    //Lấy tất cả tin nhắn mới nhất
    @GetMapping("/messages/lastest/{id}")
    public ResponseEntity<Optional<Messages>> getLasteseMessage(@PathVariable Long id){
        Conversations conversation = conversationService.findById(id).get();
        Optional<Messages> messages = messageService.getLatestMessage(conversation.getConversationId());
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
    //Nhắn tin
    @PostMapping("/send")
    public ResponseEntity<?> createMessage(@RequestBody Messages message){
        messageService.save(message);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Tạo nhóm chat
    @PostMapping("/group")
    public ResponseEntity<?> createGroupChat(@RequestBody List<UserProfile> users){
        conversationService.createGroupConversation(users);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    //Lấy tất cả các nhóm chat
    @GetMapping("/room/group/{id}")
    public ResponseEntity<List<Conversations>> getAllGroupConversation(@PathVariable Long id){
        return new ResponseEntity<>(conversationService.getAllGroupConversation(id), HttpStatus.OK);
    }
    //Lấy tất cả conversations đơn và nhóm
    @GetMapping("/room/all/{id}")
    public ResponseEntity<List<Conversations>> getAllConversation(@PathVariable Long id){
        return new ResponseEntity<>(conversationService.getALlConversation(id), HttpStatus.OK);
    }
    //Đổi tên nhóm
    @PutMapping("/changeNameGr")
    public ResponseEntity<?> changeNameGr(@RequestBody Conversations conversation){
        conversationService.save(conversation);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
