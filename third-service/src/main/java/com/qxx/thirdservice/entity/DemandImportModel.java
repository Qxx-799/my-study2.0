package com.qxx.thirdservice.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.handler.inter.IExcelModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.util.Date;


@Data
public class DemandImportModel  implements IExcelModel {

    @Excel(name = "客户名称", isImportField = "true")
    @NotBlank(message = "租客名称不能为空")
    private String name;

//    @Excel(name = "客户类型", replace = {"办公_WORK", "商业_BUSINESS"}, isImportField = "true")
//    @NotBlank(message = "客户类型不能为空")
//    private DemandTypeEnum demandType;
    /**
     * 联系人
     */
    @Excel(name = "联系人", isImportField = "true")
    @NotBlank(message = "联系人不能为空")
    private String contactName;


    @Excel(name = "联系电话")
    private String contactTel;
    /**
     * 微信号
     */
    @Excel(name = "微信号")
    private String weChatNumber;
    /**
     * 职位
     */
    @Excel(name = "职位")
    private String contactPosition;
    /**
     * 跟进状态
     */
    @Excel(name = "跟进状态", isImportField = "true")
    private String followStatus;

    /**
     * 客户行业
     */
    @Excel(name = "客户行业", isImportField = "true")
    @NotBlank(message = "客户行业不能为空")
    private String industryName;

    /**
     * 来访渠道(客户来源)
     */
    @Excel(name = "来访渠道")
    private String clientSource;

    /**
     * 首次来访时间
     */
    @Excel(name = "首次来访时间" , importFormat = "yyyy/MM/dd", isImportField = "true")
    private Date firstVisitDate;

    @Excel(name = "带看房源")
    private String resourceName;

    /**
     * 用来处理失败信息
     */
    private String errorMsg;

    @Override
    public String toString() {
        return "DemandImportModel{" +
                "name='" + name + '\'' +
                ", contactName='" + contactName + '\'' +
                ", contactTel='" + contactTel + '\'' +
                ", weChatNumber='" + weChatNumber + '\'' +
                ", contactPosition='" + contactPosition + '\'' +
                ", followStatus='" + followStatus + '\'' +
                ", industryName='" + industryName + '\'' +
                ", clientSource='" + clientSource + '\'' +
                ", firstVisitDate=" + firstVisitDate +
                '}';
    }

    @Override
    public String getErrorMsg() {
        return errorMsg ;
    }

    @Override
    public void setErrorMsg(String s) {
        this.errorMsg = s;
    }
}
