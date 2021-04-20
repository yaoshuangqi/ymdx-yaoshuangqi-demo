package com.xxcx.pay.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PayServerController {

    @RequestMapping(value = "/jdDkPay/{userOrderId}", method = RequestMethod.POST)
    public String jdDKPay(@PathVariable("userOrderId") String userOrderId){

        return "success";
    }

}
