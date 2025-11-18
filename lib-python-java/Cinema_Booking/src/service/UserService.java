package service;

import model.User;
import repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt; // <-- Cần import
import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepo) {
        this.userRepository = userRepo;
    }



    public void registerUser(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty");
        }
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        // Sửa: Kiểm tra trùng lặp bằng email
        if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new IllegalArgumentException("Email already exists");
        }

        // Mã hóa mật khẩu trước khi lưu
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        userRepository.insert(user);
    }



    public User login(String email, String password) {
        // Sửa: Tìm người dùng bằng email
        User user = userRepository.findByEmail(email);


        if (user != null && BCrypt.checkpw(password, user.getPassword())) {
            return user;
        }

        return null;
    }



    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public User getUserById(int userId) {
        return userRepository.findById(userId);
    }



    public List<User> getAllUsers() {
        return userRepository.findAll();
    }



    public void updateUser(User user) {

        String password = user.getPassword();
        if (password != null && !password.isEmpty() && !password.startsWith("$2a$")) {
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }

        userRepository.update(user);
    }


    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }
}