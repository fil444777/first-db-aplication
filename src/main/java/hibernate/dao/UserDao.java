package hibernate.dao;

import hibernate.entity.User;
import hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Optional;

public class UserDao implements Dao<Long, User> {
    private static final UserDao INSTANCE = new UserDao();

    private UserDao() {
    }

    public static UserDao getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean update(User ticket) {
        return false;
    }

    public Optional<User> findByEmailAndPassword(String email, String password) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            User user = session.createQuery(
                            "SELECT u FROM User u WHERE u.email = :email AND u.password = :password",
                            User.class
                    )
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .uniqueResult();

            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public User save(User user) {
        try (SessionFactory sessionFactory = HibernateUtil.buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            session.persist(user);
            session.getTransaction().commit();
        }
        return user;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }
}
