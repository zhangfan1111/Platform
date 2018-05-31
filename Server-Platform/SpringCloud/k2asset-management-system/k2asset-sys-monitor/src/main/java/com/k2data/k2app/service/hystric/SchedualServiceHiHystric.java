package com.k2data.k2app.service.hystric;

import org.springframework.stereotype.Component;

import com.k2data.k2app.service.SchedualServiceHi;

@Component
public class SchedualServiceHiHystric implements SchedualServiceHi {
    @Override
    public String sayHiFromClientOne(String name) {
        return "sorry "+name;
    }
}