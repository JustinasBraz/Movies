import model.Movie;
import org.h2.tools.Server;
import repository.DirectorRepository;
import service.DirectorService;
import util.HibernateUtil;

import java.sql.SQLException;
import java.util.List;

public class Main {
    public static void main(String[] args) throws InterruptedException, SQLException {
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

        directorService.createDirectorWithMovies ("Christopher Nolan", "English", nolanMovies);
        directorService.createDirectorWithMovies ("Emilis Velyvis", "Lithuanian", velyvisMovies);
        Thread.sleep(10 * 60 * 1000); // 10 minutes
        webServer.stop();
        HibernateUtil.getSessionFactory().close();
    }
}
