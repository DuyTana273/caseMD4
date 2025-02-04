package com.example.demo.service.impl;

import com.example.demo.common.EncryptPasswordUtils;
import com.example.demo.model.Role;
import com.example.demo.model.Users;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserService implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Autowired
    private EncryptPasswordUtils encryptPasswordUtils;

    @Override
    public Page<Users> findAll(Pageable pageable) {
        return iUserRepository.findAll(pageable);
    }

    @Override
    public Users getById(Long id) {
        return iUserRepository.getById(id);
    }

    @Override
    public void save(Users user) {
        try {
            if (user.getRole() == null) {
                user.setRole(Role.CUSTOMER);
            }
            if (user.getStatus() == null) {
                user.setStatus(true);
            }
            user.setPassword(encryptPasswordUtils.encryptPasswordUtils(user.getPassword()));
            iUserRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("Có lỗi khi thêm người dùng : " + e.getMessage());
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        return iUserRepository.existsByUsername(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }

    @Override
    public void deleteById(Long id) {
        iUserRepository.deleteById(id);
    }

    @Override
    public List<Users> findAllByRole(Role role) {
        return iUserRepository.findAllByRole(role);
    }

    @Override
    public Page<Users> searchUsers(String keyword, Role role, Pageable pageable) {
        return iUserRepository.searchByKeywordAndRole(keyword, role, pageable);
    }

    @Override
    public String encryptPassword(String password) {
        return "";
    }

    @Override
    public Users findByUsername(String username) {
        return iUserRepository.findByUsername(username);
    }

    @Override
    public Users findByEmail(String email) {
        return iUserRepository.findByEmail(email);
    }
}
