package hibernate.dao;

import hibernate.entity.Flight;
import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight>{
    private static final FlightDao INSTANCE = new FlightDao();

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    private FlightDao() {
    }

    @Override
    public boolean update(Flight ticket) {
        return false;
    }

    @Override
    public List<Flight> findAll() {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            return session.createQuery("FROM Flight", Flight.class).getResultList();
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Flight save(Flight ticket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
