package edu.poly.mxh.Service;

import edu.poly.mxh.Modal.UserProfile;
import edu.poly.mxh.Modal.Request.RegisterRequest;
import edu.poly.mxh.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailService emailService;
//    @Autowired
//    private JavaMailSender javaMailSender;

    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public boolean registerUser(RegisterRequest registerRequest) throws MessagingException {
//        UserProfile users = userRepository.findByEmail(registerRequest.getEmail());
//            if (users.getEmail().equals(registerRequest.getEmail())) {
//                return false;
//            }
        UserProfile user = new UserProfile();
        user.setFirstName(registerRequest.getFirst_name());
        user.setLastName(registerRequest.getLast_name());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setStatus("notVerify");
        user.setAddress("https://phongreviews.com/wp-content/uploads/2022/11/avatar-facebook-mac-dinh-19.jpg");
        user.setIsAdmin(0);
        user.setGender(0);
        user.setIsBlock(0);
        int otp = generateOTP();
        user.setOtp(otp);
        UserProfile users = userRepository.save(user);
        sendVerificationEmail(users.getEmail(), otp);
        return true;
    }
    private void sendVerificationEmail(String email, Integer otp) {
        String subject = "Email verification";
        String body = "Mã opt của bạn là:" + otp;
        emailService.sendEmail(email, subject, body);
    }

    @Override
    public boolean verify(int otp) {
        UserProfile userOtp = userRepository.findByotp(otp);
        if(userOtp != null && otp == userOtp.getOtp()){
            userOtp.setStatus("verify");
            userOtp.setOtp(null);
            userRepository.save(userOtp);
            return true;
        }
        return false;
    }

    private Integer generateOTP() {
        Random random = new Random();
        int otpvalue = 100000 + random.nextInt(900000);
        return otpvalue;
    }

    private final JavaMailSender javaMailSender;

    @Override
    public String generateRandomPassword() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder newPassword = new StringBuilder();
        int length = 8;
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            newPassword.append(characters.charAt(random.nextInt(characters.length())));
        }

        return newPassword.toString();
    }

    @Override
    public void sendNewPasswordByEmail(String userEmail, String newPassword) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(userEmail);
        mailMessage.setSubject("Mật khẩu mới cho Mạng xã hội NTL_VN");
        mailMessage.setText("Xin chào, mật khẩu mới của bạn là: " + newPassword);
        javaMailSender.send(mailMessage);
    }
}
