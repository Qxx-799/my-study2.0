package com.qxx.thirdservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ContractDeserializeModel {

    private String tenantName;

    private String contractNo;

    private String areaSize;

    private String signDate;

    private String totalTheoryPayMoney;

    private String startDate;

    private String endDate;

    private String firstRentTermPrice;
}
