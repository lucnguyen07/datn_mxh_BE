package edu.poly.mxh.Modal.Request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChangePassRequest {
    private Long id;
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
