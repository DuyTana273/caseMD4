package com.example.demo.config.security;


import com.example.demo.common.EncryptPasswordUtils;

import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class DataSeedingListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private IUserRepository iUserRepository;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        //them admin
        if (iUserRepository.findByUsername("admin") == null) {
            Users admin = new Users();
            admin.setUsername("admin");
            // mã hóa mật khẩu
            admin.setPassword(EncryptPasswordUtils.encryptPasswordUtils("admin"));
            admin.setEmail("admin@gmail.com");
            admin.setPhone("123456789");
            admin.setFullName("Admin");
            admin.setGender(true);
            admin.setAddress("TP.HCM");
            admin.setStatus(true);
            admin.setRole(Role.ADMIN);
            iUserRepository.save(admin);
        }

        //Them customer
        if (iUserRepository.findByUsername("customer") == null) {
            Users customer = new Users();
            customer.setUsername("customer");
            customer.setPassword(EncryptPasswordUtils.encryptPasswordUtils("123"));
            customer.setEmail("customer@gmail.com");
            customer.setPhone("0123456789");
            customer.setFullName("Customer");
            customer.setGender(false);
            customer.setAddress("Hà Nội");
            customer.setStatus(true);
            customer.setRole(Role.CUSTOMER);
            iUserRepository.save(customer);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
