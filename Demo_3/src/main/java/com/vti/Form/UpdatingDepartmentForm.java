package com.vti.Form;

import com.vti.validate.NameNotExists;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@Data
@NoArgsConstructor
public class UpdatingDepartmentForm {

    private int id;

    @NotBlank(message = "Name khong duoc de trong")
    @Length(max = 50, message = "Name toi da la 50 ki tu")
    @NameNotExists
    private String name;

    @Positive
    private int totalMember;
}
