package com.qxx.thirdservice.service;

import com.aspose.words.*;
import com.qxx.thirdservice.util.AsposeLicenseUtil;
import com.qxx.thirdservice.util.FileChangeUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AsposeService {

    public void testReplaceWord(){

        File templateWordFile = new File("/Users/qxx/Downloads/CREAMS催缴模板.doc");

        File newFile = new File("/Users/qxx/Downloads/CREAMS催缴单模板"+ "1000-.doc");


        try (FileInputStream inputStream = new FileInputStream(templateWordFile)) {

            if (!AsposeLicenseUtil.getLicense()) {
                return;
            }

            Document baseDoc = new Document(inputStream);
            Document document = new Document();
            document.removeAllChildren();
            document.appendDocument(baseDoc, ImportFormatMode.KEEP_SOURCE_FORMATTING);



            FindReplaceOptions options = new FindReplaceOptions();
            options.setMatchCase(false);
            options.setFindWholeWordsOnly(false);
            Map<String, String> filledMap = new HashMap<>();
            filledMap.put("房源信息","8-8081");
            filledMap.put("计费周期","2022-03-01 至 2022-04-30");
            filledMap.put("费用类型","佣金");
            filledMap.put("费用总计","20999");
            filledMap.put("币种（单位）","人民币");
            filledMap.put("费用总计(大写)","二万零九十九");
            filledMap.put("首笔应付时间","2022-03-01");
            filledMap.put("通知单生成时间","2022-04-15 16:33");
            for (Map.Entry<String, String> entry : filledMap.entrySet()) {
                if (StringUtils.isBlank(entry.getValue())) {
                    document.getRange().replace("${" + entry.getKey() + "}", "", options);
                } else {
                    document.getRange().replace("${" + entry.getKey() + "}", entry.getValue() + "", options);
                }
            }

            document.save(newFile.getAbsolutePath());

        } catch (Exception e) {

        }
    }


    public static void main(String[] args) {
        AsposeService asposeService = new AsposeService();
        asposeService.testReplaceWord();
    }

}
