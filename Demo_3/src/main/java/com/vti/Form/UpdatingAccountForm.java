package com.vti.Form;

import com.vti.validate.EmailNotExists;
import com.vti.validate.UserNameNotExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class UpdatingAccountForm {

    private int id;
    @NotBlank(message = "UserName khong duoc de trong")
    @Length(max = 50, message = "UserName toi da la 50 ki tu")
    @UserNameNotExists
    private String userName;

    @NotBlank(message = "FirstName khong duoc de trong")
    @Length(max = 50, message = "FirstName toi da la 50 ki tu")
    private String firstName;

    @NotBlank(message = "LastName khong duoc de trong")
    @Length(max = 50, message = "LastName toi da la 50 ki tu")
    private String lastName;

    @NotBlank(message = "Email khong duoc de trong")
    @Length(max = 50, message = "Email toi da la 50 ki tu")
    @Email(message = "Email không đúng định dạng. VD: abc@gmail.com")
    @EmailNotExists
    private String email;

    @Positive
    private int departmentId;

    @NotBlank(message = "Password khong duoc de trong")
    @Length(max = 800, message = "Password toi da la 800 ki tu")
    private String password;

    @Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER")
    private String role;
}
