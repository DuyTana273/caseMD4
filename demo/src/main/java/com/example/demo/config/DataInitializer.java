//package com.example.demo.config;
//
//import com.example.demo.model.Role;
//import com.example.demo.model.Users;
//import com.example.demo.repository.IUserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//@Component
//public class DataInitializer implements CommandLineRunner {
//    @Autowired
//    private IUserRepository iUserRepository;
//
//    @Override
//    public void run(String... args) throws Exception {
//       String adminUsername = "admin";
//       String adminPassword = "admin";
//       String adminEmail = "admin@gmail.com";
//
//       if(iUserRepository.findByUsername(adminUsername).isEmpty()) {
//           Users admin = new Users();
//           admin.setUsername(adminUsername);
//           admin.setPassword(adminPassword);
//           admin.setEmail(adminEmail);
//           admin.setFullName("Admin");
//           admin.setAddress("TP.HCM");
//           admin.setGender(true);
//           admin.setPhone("123456789");
//           admin.setRole(Role.ADMIN);
//           admin.setStatus(true);
//
//           iUserRepository.save(admin);
//           System.out.println("Admin được tạo thành công!");
//       } else {
//           System.out.println("Admin account already exists!");
//       }
//    }
//}
