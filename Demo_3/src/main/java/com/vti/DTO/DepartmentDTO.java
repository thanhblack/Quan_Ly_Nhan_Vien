package com.vti.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
public class DepartmentDTO {
    private int id;

    private String name;

    private String type;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private List<AccountDTO> accounts;

    @Data
    @NoArgsConstructor
    public static class AccountDTO  {
        private int id;

        private String userName;
    }
}
