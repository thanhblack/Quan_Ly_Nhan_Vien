package com.vti.Form;

import com.vti.Entity.Role;
import com.vti.validate.EmailNotExists;
import com.vti.validate.NameNotExists;
import com.vti.validate.UserNameNotExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.util.List;

@Data
@NoArgsConstructor
public class CreatingDepartmentForm {

    @NotBlank(message = "Name khong duoc de trong")
    @Length(max = 50, message = "Name toi da la 50 ki tu")
    @NameNotExists
    private String name;

    @Positive
    private int totalMember;

    @Pattern(regexp = "Dev|Test|ScrumMaster|PM")
    private String type;

    private List<AccountForm> accounts;
    @Data
    @NoArgsConstructor
    public static class AccountForm{
        private Integer id;
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

        @Pattern(regexp = "ADMIN|EMPLOYEE|MANAGER")
        private Role role;

        @NotBlank(message = "Password khong duoc de trong")
        @Length(max = 800, message = "Password toi da la 50 ki tu")
        private String password;

        @Positive
        private int departmentId;
    }
}
