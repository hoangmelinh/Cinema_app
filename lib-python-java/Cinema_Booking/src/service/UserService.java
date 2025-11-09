package service;

import model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepo) {
        this.userRepository = userRepo;
    }


    public void registerUser(User user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (userRepository.findByUsername(user.getUsername()) != null) {
            throw new IllegalArgumentException("Username already exists");
        }
        userRepository.insert(user);
    }


    public User login(String username, String password) {
        User user = userRepository.findByUsername(username);

        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }


    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public void updateUser(User user) {
        userRepository.update(user);
    }


    public void deleteUser(String userId) {
        userRepository.delete(userId);
    }
}
