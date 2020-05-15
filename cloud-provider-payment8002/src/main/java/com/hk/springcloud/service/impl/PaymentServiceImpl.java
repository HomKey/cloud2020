package com.hk.springcloud.service.impl;

import com.hk.springcloud.entities.Payment;
import com.hk.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;
import com.hk.springcloud.dao.PaymentDao;

import javax.annotation.Resource;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
