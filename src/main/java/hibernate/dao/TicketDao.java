package hibernate.dao;

import hibernate.entity.Flight;
import hibernate.entity.Ticket;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TicketDao implements Dao<Long, Ticket>{
    private static final TicketDao INSTANCE = new TicketDao();

    private TicketDao() {
    }

    public static TicketDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(Ticket ticket) {
        return false;
    }

    @Override
    public List<Ticket> findAll() {
        return List.of();
    }

    @Override
    public Optional<Ticket> findById(Long id) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            Ticket flight = session.find(Ticket.class, id);
            return Optional.ofNullable(flight);
        }
    }

    @Override
    public Ticket save(Ticket ticket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public List<Ticket> findAllByFlightId(Long flightId) {
        try (Session session = HibernateUtil.buildSessionFactory().openSession()) {
            return session.createQuery(
                            "SELECT t FROM Ticket t WHERE t.flight.id = :flightId",
                            Ticket.class
                    )
                    .setParameter("flightId", flightId)
                    .getResultList();
        }
    }
}
