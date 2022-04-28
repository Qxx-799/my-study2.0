package com.qxx.thirdservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.qxx.thirdservice.entity.ContractDeserializeModel;

import java.util.*;

public class JieBaUtils {
    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\"contractNo\":\"北京蓝天航空科技股份有限公司-CO-SFIC-2107-0021\",\"resourceNames\":\"远远国际-1-101,远远国际-2-201,远远国际-1-101,远远国际-3-301,远远国际-2-202,远远国际-1-102,远远国际-2-201\",\"dealCount\":2800,\"areaSize\":2800,\"spaceUnit\":\"P\",\"signDate\":\"2022-04-25\",\"tenantName\":\"北京蓝天航空科技股份有限公司\",\"totalTheoryPayMoney\":1022000.0400000002,\"contractStartDate\":\"2022-04-25\",\"contractEndDate\":\"2023-04-24\",\"firstRentTermPrice\":1,\"price\":\"1元/㎡·天\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        ContractDeserializeModel contractDeserializeModel = objectMapper.readValue(json, ContractDeserializeModel.class);
        System.out.println(contractDeserializeModel.toString());


    }
}
