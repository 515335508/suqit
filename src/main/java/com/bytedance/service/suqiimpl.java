package com.bytedance.service;

import com.bytedance.mapper.BankMapper;
import com.bytedance.pojo.Bank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class suqiimpl implements testint {

    @Autowired
    private BankMapper bankMapper;

    @Override
    public List<Bank> getbank() {

        String path = this.getClass().getResource("/").getPath();
        System.out.println(path);
        return bankMapper.selectbank();
    }
}
