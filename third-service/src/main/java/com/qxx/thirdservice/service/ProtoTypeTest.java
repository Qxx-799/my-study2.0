package com.qxx.thirdservice.service;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
public class ProtoTypeTest {

    private Integer i = 1;

    public Integer getI() {
        return i;
    }

    public void setI(Integer i) {
        this.i = i;
    }
}
