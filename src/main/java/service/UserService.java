package service;

import lombok.AllArgsConstructor;
import model.Role;
import model.User;
import model.UserSummary;
import repository.UserRepository;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserSummary getUsersSummary() {
        List<User> users = userRepository.findAll();
        long total = users.size();
        long adminCount = users.stream().filter(u -> u.getRole() == Role.ADMIN).count();
        long managerCount = users.stream().filter(u -> u.getRole() == Role.MANAGER).count();
        long userCount = users.stream().filter(u -> u.getRole() == Role.USER).count();
        return new UserSummary(total, adminCount, managerCount, userCount);
    }

    public Optional<User> authenticate(String username, String password) {
        return userRepository.findByUsername(username)
                .filter(u -> u.getPassword().equals(password));
    }
}
