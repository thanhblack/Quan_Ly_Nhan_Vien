package com.vti.DTO;

import com.vti.Entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class AccountDTO {
    @NonNull
    private int id;
    @NonNull
    private Role role;

    @NonNull
    private String firstName;

    @NonNull
    private String lastName;

    @NonNull
    private String email;

    @NonNull
    private String userName;

    @NonNull
    private String departmentName;
}
