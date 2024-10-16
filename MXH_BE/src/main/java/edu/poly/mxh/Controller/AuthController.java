package edu.poly.mxh.Controller;
import edu.poly.mxh.Modal.Request.*;
import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Repository.UserRepository;
import edu.poly.mxh.Service.AuthService;
import edu.poly.mxh.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;



@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin("*")
public class AuthController {
    @Autowired
    private AuthService authService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @PostMapping("/register")
    public ResponseEntity<UserProfile> createUser(@RequestBody RegisterRequest registerRequest) {
        try {
            boolean result = authService.registerUser(registerRequest);
            if(!result){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (MessagingException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/verify")
    public  ResponseEntity<?> verify(@RequestBody VerifyRequest verifyRequest){
//        try{
//            authService.verify(verifyRequest.getOtp());
//            return new ResponseEntity<>(HttpStatus.OK);
//        } catch (Exception e){
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
        try {
            boolean result = authService.verify(verifyRequest.getOtp());
            if(!result){
                return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequest loginRequest){
        UserProfile user = userRepository.findByemail(loginRequest.getEmail());
        if(user == null){
            return new ResponseEntity<>("Không tìm thấy người dùng", HttpStatus.BAD_REQUEST);
        } else if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())){
            return new ResponseEntity<>("Mật khẩu không chính xác", HttpStatus.BAD_REQUEST);
        } else if (!"verify".equals(user.getStatus())) {
            return new ResponseEntity<>("Bạn chưa xác thực email", HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @PutMapping("/changePass")
    public ResponseEntity<?> changePassword(@RequestBody ChangePassRequest changePassRequest) {
        if (userService.changePassword(changePassRequest)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/forgotPassword")
    public ResponseEntity<?> forgotPassword(@RequestBody FogotPassRequest request) {

        var user = userRepository.findByemail(request.getEmail());
        if (user != null) {
            // Tạo mật khẩu ngẫu nhiên
            String newPassword = authService.generateRandomPassword();

            // Mã hóa mật khẩu và lưu vào db
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // Gửi mật khẩu mới đến địa chỉ email của người dùng
            authService.sendNewPasswordByEmail(request.getEmail(), newPassword);

            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}
