package com.example.User.and.Role.Management.service;

import com.example.User.and.Role.Management.dto.request.UserUpdateRequestDTO;
import com.example.User.and.Role.Management.exception.UserNotFoundException;
import com.example.User.and.Role.Management.model.User;
import com.example.User.and.Role.Management.repository.RoleRepo;
import com.example.User.and.Role.Management.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

    @Autowired
    UserRepo userRepo;

    @Autowired
    RoleRepo roleRepo;
    public User register(User user,Long adminId) {
        user.setPassword(encoder.encode(user.getPassword()));
        user.setCreatedBy(adminId);
        return userRepo.save(user);
    }

    public User getUserById(Long id){
        return userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("With the given ID there is no USER"));
    }

    public User getByUsername(String username) {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("With the given ID there is no USER"));
    }

    public List<User> getAll() {
        return userRepo.findAll();
    }

    public User deactivateUser(Long id, Long adminId) {
        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User with the given id doesn't exists"));

        user.setUpdatedBy(adminId);
        user.setStatus("INACTIVE");

        return userRepo.save(user);
    }

    public User updateUser(Long id, UserUpdateRequestDTO dto, Long adminId) {

        User user = userRepo.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        user.setEmail(dto.getEmail());
        user.setStatus(dto.getStatus());
        user.setUpdatedBy(adminId);

        return userRepo.save(user);
    }
}
