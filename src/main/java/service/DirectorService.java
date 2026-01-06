package service;

import lombok.AllArgsConstructor;
import model.Director;
import model.Movie;
import org.h2.engine.Session;
import repository.DirectorRepository;

import java.util.ArrayList;
import java.util.List;
@AllArgsConstructor
public class DirectorService {
    private final DirectorRepository directorRepository;
    public void createDirectorWithMovies(String name, String nationality, List<Movie> movies) {
        Director director = new Director(name, nationality);



        for (Movie movie : movies) {
            director.addMovie(movie);
        }

        directorRepository.save(director);
    }

    public List<Director> getDirectorsWithMoreThanOneMovie() {
        List<Director> directors = new ArrayList<>();

        for (Director director : directorRepository.findAll()) {
            if (director.getMovies().size() > 1) {
                directors.add(director);
            }
        }
        return directors;
    }

    public List<Director> getDirectorsByNationality (String nationality) {
        List<Director> directorByNationality = new ArrayList<>();
        for (Director director : directorRepository.findAll()) {
            if (director.getNationality().equalsIgnoreCase(nationality)) {
                directorByNationality.add(director);
            }
        }
        return directorByNationality;
    }
}
