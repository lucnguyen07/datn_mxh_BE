package edu.poly.mxh.Service;

import com.sun.xml.messaging.saaj.packaging.mime.MessagingException;
import edu.poly.mxh.Modal.Request.RegisterRequest;

public interface AuthService {
    boolean registerUser(RegisterRequest registerRequest) throws MessagingException;
    boolean verify(int otp);
    String generateRandomPassword();
    void sendNewPasswordByEmail(String userEmail, String newPassword);
}
