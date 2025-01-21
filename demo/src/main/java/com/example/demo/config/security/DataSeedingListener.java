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
            admin.setPassword(EncryptPasswordUtils.EncryptPasswordUtils("admin"));
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
            customer.setPassword(EncryptPasswordUtils.EncryptPasswordUtils("123"));
            customer.setEmail("customer@gmail.com");
            customer.setPhone("0123456789");
            customer.setFullName("Customer");
            customer.setGender(false);
            customer.setAddress("Hà Nội");
            customer.setStatus(true);
            customer.setRole(Role.CUSTOMER);
            iUserRepository.save(customer);
        }

        //Them employee
        if (iUserRepository.findByUsername("employee") == null) {
            Users employee = new Users();
            employee.setUsername("employee");
            employee.setPassword(EncryptPasswordUtils.EncryptPasswordUtils("123"));
            employee.setEmail("employee@gmail.com");
            employee.setPhone("01234567890");
            employee.setFullName("Employee");
            employee.setGender(false);
            employee.setAddress("Đà Nẵng");
            employee.setStatus(true);
            employee.setRole(Role.EMPLOYEE);
            iUserRepository.save(employee);
        }
    }

    @Override
    public boolean supportsAsyncExecution() {
        return ApplicationListener.super.supportsAsyncExecution();
    }
}
