package repository;

import lombok.AllArgsConstructor;
import model.Director;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
@AllArgsConstructor
public class DirectorRepository {
    // Repository methods for Director entity
    private final SessionFactory sessionFactory;


    public void save(final Director director) {
        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(director);
            session.getTransaction().commit();
        }
    }

    public List<Director> findAll() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                    "select distinct d from Director d left join fetch d.movies",
                    Director.class
            ).getResultList();
        }
    }

    public List<Director> findByNationality(String nationality) {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery(
                            "select distinct d from Director d left join fetch d.movies where d.nationality = :nationality",
                            Director.class
                    ).setParameter("nationality", nationality)
                    .getResultList();
        }
    }
}