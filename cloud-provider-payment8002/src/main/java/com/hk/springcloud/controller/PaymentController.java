package com.hk.springcloud.controller;

import com.hk.springcloud.entities.CommonResult;
import com.hk.springcloud.entities.Payment;
import com.hk.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/payment")
@Slf4j
public class PaymentController {

    @Resource
    private PaymentService paymentService;

    @Resource
    private DiscoveryClient discoveryClient;

    @Value("${server.port}")
    private String serverPort;

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("********result:" + result);
        if (result > 0){
            return new CommonResult(200,"success, port:" + serverPort,result);
        }else{
            return new CommonResult(444,"failure");
        }
    }

    @GetMapping(value = "/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment result = paymentService.getPaymentById(id);
        log.info("********result:" + result);
        if (result != null){
            return new CommonResult(200,"success, port:" + serverPort,result);
        }else{
            return new CommonResult(445,"failure");
        }
    }

    @GetMapping(value = "/discovery")
    public Object getDiscoveryClient(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info(service);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        log.info(instances.toString());
        return discoveryClient;
    }
    @GetMapping(value = "/lb")
    public String getPaymentLB() {
        return serverPort;
    }


    @GetMapping(value = "/feign/timeout")
    public String paymentFeignTimeout() {
        try {
            // 暂停3秒钟
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }
}
