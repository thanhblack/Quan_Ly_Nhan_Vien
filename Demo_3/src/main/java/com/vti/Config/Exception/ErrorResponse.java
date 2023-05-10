package com.vti.Config.Exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private String detailMessage;
    private Object error;
    private Integer code;
    private String moreInformation;

}
