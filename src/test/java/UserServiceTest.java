import model.Role;
import model.User;
import model.UserSummary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import repository.UserRepository;
import service.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        UserRepository stubRepo = new UserRepository(null) {
            private final List<User> users = List.of(
                    new User("admin", "adminpass", Role.ADMIN),
                    new User("manager", "managerpass", Role.MANAGER),
                    new User("user1", "userpass", Role.USER)
            );

            @Override
            public List<User> findAll() {
                return users;
            }

            @Override
            public Optional<User> findByUsername(String username) {
                return users.stream().filter(u -> u.getUsername().equals(username)).findFirst();
            }
        };
        userService = new UserService(stubRepo);
    }

    @Test
    void getUsersSummary_returnsCounts() {
        UserSummary summary = userService.getUsersSummary();
        assertEquals(3, summary.getTotalUsers());
        assertEquals(1, summary.getAdminCount());
        assertEquals(1, summary.getManagerCount());
        assertEquals(1, summary.getUserCount());
    }

    @Test
    void authenticate_validCredentials_returnsUser() {
        Optional<User> result = userService.authenticate("admin", "adminpass");
        assertTrue(result.isPresent());
        assertEquals(Role.ADMIN, result.get().getRole());
    }

    @Test
    void authenticate_wrongPassword_returnsEmpty() {
        Optional<User> result = userService.authenticate("admin", "wrongpass");
        assertTrue(result.isEmpty());
    }

    @Test
    void authenticate_unknownUser_returnsEmpty() {
        Optional<User> result = userService.authenticate("nobody", "pass");
        assertTrue(result.isEmpty());
    }

    @Test
    void userRole_isDenied() {
        Optional<User> result = userService.authenticate("user1", "userpass");
        assertTrue(result.isPresent());
        assertEquals(Role.USER, result.get().getRole());
    }

    @Test
    void managerRole_isAllowed() {
        Optional<User> result = userService.authenticate("manager", "managerpass");
        assertTrue(result.isPresent());
        assertEquals(Role.MANAGER, result.get().getRole());
    }
}
