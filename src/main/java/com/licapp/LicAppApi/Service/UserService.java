package com.licapp.LicAppApi.Service;

import com.licapp.LicAppApi.Entity.User;
import com.licapp.LicAppApi.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    @Lazy
    private BCryptPasswordEncoder passwordEncoder;

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println("Saving user: " + user.getUsername());
        userRepo.save(user);
        System.out.println("User saved: " + user.getUsername());
    }

    public List<User> getUser() {
        List<User> users = userRepo.findAll();
        System.out.println("Retrieved users: " + users);
        return users;
    }

    public Optional<User> getuserUsername(String username) {
        System.out.println("Querying user by username: " + username);
        Optional<User> user = userRepo.findByUsername(username);
        if (user.isPresent()) {
            System.out.println("User found: " + user.get().getUsername());
        } else {
            System.out.println("User not found for username: " + username);
        }
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Loading user: " + username);
        Optional<User> optionalUser = userRepo.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("User found: " + user.getUsername() + ", Password: " + user.getPassword());
            return new org.springframework.security.core.userdetails.User(
                    user.getUsername(),
                    user.getPassword(),
                    new ArrayList<>()
            );
        } else {
            System.out.println("User not found: " + username);
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
    }
}