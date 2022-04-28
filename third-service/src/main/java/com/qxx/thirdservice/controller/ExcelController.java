package com.qxx.thirdservice.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.qxx.thirdservice.entity.DemandImportModel;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.time.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

@RestController
public class ExcelController {
    private static final ThreadLocal<String> THREAD_LOCAL = ThreadLocal.withInitial(()-> {
        return "hello";
    });

    @PostMapping("excel/upload")
    public String uploadExcel(@RequestParam("file")MultipartFile file) throws Exception {

        ImportParams importParams = new ImportParams();
        importParams.setNeedVerify(true);
        importParams.setHeadRows(1);
        importParams.setTitleRows(4);
        importParams.setStartRows(1);
        final ExcelImportResult<DemandImportModel> excelImportResult = ExcelImportUtil.importExcelMore(file.getInputStream(),
                DemandImportModel.class,
                importParams);


        final List<DemandImportModel> successList = excelImportResult.getList();
        final List<DemandImportModel> failList = excelImportResult.getFailList();
        int total = successList.size() + failList.size();
        List<DemandImportModel> totalRoomInfo = new ArrayList<>(total);
        totalRoomInfo.addAll(successList);
        totalRoomInfo.addAll(failList);
        if (CollectionUtils.isNotEmpty(successList)){
            successList.forEach(demand -> System.out.println(demand.toString()));
        }
        System.out.println("总条数为："+total);
        if (CollectionUtils.isNotEmpty(failList)){
            failList.forEach(demand -> System.out.println("导入失败数据为：" + demand.toString() +"，\n 失败原因为: "+ demand.getErrorMsg()));
        }

        return "ok";
    }

    @GetMapping("/test")
    public String testThreadLocal(@RequestParam("id") Integer id ){
        String s = THREAD_LOCAL.get();
        THREAD_LOCAL.set(id.toString());

        return THREAD_LOCAL.get();
    }


    public void testAspose(){

    }
}
