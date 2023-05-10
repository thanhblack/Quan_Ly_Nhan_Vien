package com.vti.filter;

import com.vti.Entity.Type;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import java.util.Date;

@Data
@NoArgsConstructor
public class DepartmentFilterForm {

    //    chon kieu integer, string, enum, Date
    private String searchName;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minCreatedDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxCreatedDate;

    private Type type;
}
