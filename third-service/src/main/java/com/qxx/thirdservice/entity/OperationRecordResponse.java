package com.qxx.thirdservice.entity;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OperationRecordResponse implements Serializable {

    private static final long serialVersionUID = 5171507669475281014L;


    private String operatorName;


    private LocalDateTime operationTime;
    

    private List<String> operationContent;
}
