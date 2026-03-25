import com.sun.net.httpserver.HttpServer;
import controller.ReportController;
import model.Movie;
import model.Role;
import model.User;
import org.h2.tools.Server;
import repository.DirectorRepository;
import repository.UserRepository;
import service.DirectorService;
import service.UserService;
import util.HibernateUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException, IOException {
        Server webServer = Server.createWebServer(
                "-web",
                "-webAllowOthers",
                "-webPort",
                "8082"
        ).start();

        DirectorRepository directorRepository = new DirectorRepository(HibernateUtil.getSessionFactory());
        DirectorService directorService = new DirectorService(directorRepository);

        Movie interstellar = new Movie("Interstellar", "Documentary", 2014, 8.7, true);
        Movie oppenheimer = new Movie("Oppenheimer", "Documentary", 2023, 8.9, true);
        Movie zero = new Movie("Zero", "Documentary", 2006, 10.0, true);
        List<Movie> nolanMovies = List.of(interstellar, oppenheimer);
        List<Movie> velyvisMovies = List.of(zero);

        directorService.createDirectorWithMovies("Christopher Nolan", "English", nolanMovies);
        directorService.createDirectorWithMovies("Emilis Velyvis", "Lithuanian", velyvisMovies);

        UserRepository userRepository = new UserRepository(HibernateUtil.getSessionFactory());
        UserService userService = new UserService(userRepository);

        userRepository.save(new User("admin", "adminpass", Role.ADMIN));
        userRepository.save(new User("manager", "managerpass", Role.MANAGER));
        userRepository.save(new User("user1", "userpass", Role.USER));

        HttpServer httpServer = HttpServer.create(new InetSocketAddress(8080), 0);
        httpServer.createContext("/api/reports/users-summary", new ReportController(userService));
        httpServer.start();
        System.out.println("API server started on port 8080");

        Thread.sleep(10 * 60 * 1000); // 10 minutes
        httpServer.stop(0);
        webServer.stop();
        HibernateUtil.getSessionFactory().close();
    }
}
