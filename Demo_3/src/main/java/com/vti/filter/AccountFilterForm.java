package com.vti.filter;

import com.vti.Entity.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AccountFilterForm {

//    chon kieu integer, string, enum, Date

    private String search;

    private Integer departmentId;

    private Role role;
}
