package model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@NoArgsConstructor
@Data

public class Movie {


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;
        @Column(nullable = false)
        private String title;

        @Column(nullable = false)
        private String genre;

        @Column(nullable = false)
        private Integer releaseYear;

        @Column(nullable = false)
        private Double rating;

        @Column(nullable = false)
        private Boolean isOscarWinner;

        @ManyToOne(fetch = FetchType.LAZY) // duomenu uzkrovimas, reikalaujantis papildomu veiksmu
        @JoinColumn(name = "director_id")
        private Director director;

        public Movie(String title, String genre, Integer releaseYear, Double rating, Boolean isOscarWinner) {
            this.title = title;
            this.genre = genre;
            this.releaseYear = releaseYear;
            this.rating = rating;
            this.isOscarWinner = isOscarWinner;
        }


    }
