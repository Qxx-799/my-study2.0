package com.qxx.thirdservice.service;

import com.qxx.thirdservice.annotation.CompareField;
import com.qxx.thirdservice.entity.DemandRequest;
import com.qxx.thirdservice.entity.OperationRecord;
import com.qxx.thirdservice.entity.OperationRecordResponse;
import org.apache.commons.collections4.CollectionUtils;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


public class OperationRecordService {

    /**
     * 生成并保存操作记录
     * 调用时需要先把@CompareField注解标注在需要对比的字段上，目前只支持简单类型：前缀为java.lang的字段，例如String、Integer、Long等
     * @param oldData 旧的数据（传入实体类）
     * @param newData 新的数据（传入实体类）
     * @param bizId 业务id
     * @param <T>
     * @return true 成功，false 失败
     * @throws Exception 自行捕获处理
     */
    public static <T> List<OperationRecord> generateAndSaveOperationRecord(T oldData, T newData , Long bizId) throws Exception {
        LocalDateTime now = LocalDateTime.now();
        List<OperationRecord> compareResultList = new ArrayList<>();
        Field[] declaredFields = newData.getClass().getDeclaredFields();
        for (Field newDateField : declaredFields) {
            newDateField.setAccessible(true);
            CompareField annotation = newDateField.getAnnotation(CompareField.class);
            if (annotation == null){
                continue;
            }
            String fieldName = annotation.name();
            Object newValue = newDateField.get(newData);
            Object oldValue = newDateField.get(oldData);
            // 判断是否为简单类型

            OperationRecord operationRecord = new OperationRecord();
            operationRecord.setBizId(bizId);
            operationRecord.setOperationTime(now);
            operationRecord.setFieldName(fieldName);
            operationRecord.setOperatorName("东亚悍匪");

            if (Objects.isNull(newValue) && Objects.isNull(oldValue)){
                continue;
            } else if (Objects.isNull(newValue)){
                operationRecord.setBeforeValue(String.valueOf(oldValue));
                operationRecord.setAfterValue("");
            } else if (Objects.isNull(oldValue)){
                operationRecord.setBeforeValue("");
                operationRecord.setAfterValue(String.valueOf(newValue));
            } else if (!Objects.equals(newValue,oldValue)){
                operationRecord.setBeforeValue(String.valueOf(oldValue));
                operationRecord.setAfterValue(String.valueOf(newValue));
            }
            compareResultList.add(operationRecord);
        }

        if (CollectionUtils.isEmpty(compareResultList)){
            return Collections.emptyList();
        }
        // 保存操作记录
        compareResultList.forEach(System.out::println);
        return compareResultList;
    }

    public static List<OperationRecordResponse> listByBizIdAndType(List<OperationRecord> operationRecords) {

        if (CollectionUtils.isEmpty(operationRecords)){
            return Collections.emptyList();
        }
        // 根据操作时间+操作人姓名分组
        Map<String, List<OperationRecord>> operationRecordMap = operationRecords.stream()
                .collect(Collectors.groupingBy(operationRecord -> operationRecord.getOperatorName() + "_" + operationRecord.getOperationTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))));

        List<OperationRecordResponse> operationRecordResponses = new ArrayList<>();
        operationRecordMap.forEach((key,value)->{
            OperationRecordResponse recordResponse = new OperationRecordResponse();
            String[] keys = key.split("_");
            recordResponse.setOperatorName(keys[0]);
            recordResponse.setOperationTime(LocalDateTime.parse(keys[1], DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

            List<String> operationContent = value.stream().map(record -> new StringBuilder().append("编辑 ")
                            .append(record.getFieldName()).append(" 变更前: ")
                            .append(record.getBeforeValue()).append(" 变更后: ")
                            .append(record.getAfterValue()).toString())
                    .collect(Collectors.toList());
            recordResponse.setOperationContent(operationContent);
            operationRecordResponses.add(recordResponse);
        });

        return operationRecordResponses;
    }


    public static void main(String[] args) throws Exception {
        DemandRequest oldData = new DemandRequest();
        oldData.setName("张三");
        oldData.setAgencyName("张三经纪公司");
        oldData.setContactName("老张");
        oldData.setContactTel("13233333333");

        DemandRequest newData = new DemandRequest();
        newData.setName("李四");
        newData.setAgencyName("李四经纪公司");
        newData.setContactName("老李");
        newData.setContactTel("13233332222");

        List<OperationRecord> operationRecords = generateAndSaveOperationRecord(oldData, newData, 3L);

        List<OperationRecordResponse> operationRecordResponses = listByBizIdAndType(operationRecords);
        operationRecordResponses.forEach(response -> {
            System.out.println(response.getOperationTime());
            System.out.println(response.getOperatorName());
            List<String> operationContent = response.getOperationContent();
            operationContent.forEach(System.out::println);
        });


    }
}
