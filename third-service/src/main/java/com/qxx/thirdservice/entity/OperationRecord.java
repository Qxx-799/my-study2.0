package com.qxx.thirdservice.entity;


import lombok.*;


import java.time.LocalDateTime;

@Data
public class OperationRecord {


    private Long id;
    /**
     * 业务id
     */
    private Long bizId;
//    /**
//     * 操作类型
//     */
//    private OperationTypeEnum operationType;
    /**
     * 字段名称
     */
    private String fieldName;
    /**
     * 操作前的值
     */
    private String beforeValue;
    /**
     * 操作后的值
     */
    private String afterValue;
    /**
     * 操作人id
     */
    private Long operatorId;
    /**
     * 操作人姓名
     */
    private String operatorName;
    /**
     * 操作时间
     */
    private LocalDateTime operationTime;


}
