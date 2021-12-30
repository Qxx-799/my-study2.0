package com.qxx.thirdservice.entity;


import com.qxx.thirdservice.annotation.CompareField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class DemandRequest {
    private Long id;

    /**
     * 客户名称
     */
    @CompareField(name = "客户名称")
    private String name;
    /**
     * 联系人
     */
    @CompareField(name = "联系人")
    private String contactName;
    /**
     * 联系电话
     */
    @CompareField(name = "联系电话")
    private String contactTel;
    /**
     * 职位
     */
    @CompareField(name = "职位")
    private String contactPosition;

    /**
     * 来访渠道(客户来源)
     */
    @CompareField(name = "来访渠道")
    private String clientSource;

    @CompareField(name = "经纪公司")
    private String agencyName;

    @CompareField(name = "经纪人")
    private String agentName;

    @CompareField(name = "微信号")
    private String wechatId;

}
