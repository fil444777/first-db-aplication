package jdbs.dao;

import jdbs.entity.Aircraft;
import jdbs.entity.Airport;
import jdbs.entity.Ticket;
import jdbs.exception.DaoException;
import jdbs.utils.ConnectionManager;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AircraftDao implements Dao<Long, Aircraft>{
    private static final AircraftDao INSTANCE = new AircraftDao();

    public AircraftDao() {
    }

    public static AircraftDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE aircraft
            SET 
                model = ?
            WHERE id = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT a.id, a.model
            FROM aircraft a
           
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE a.id = ?
            """;


    private final static String SAVE_SQL = """
            INSERT INTO aircraft 
            (model)
            VALUES (?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM aircraft
            where id = ?
            """;

    private Aircraft buildAircraft(ResultSet result) throws SQLException {
        return new Aircraft(result.getLong("id"),
                result.getString("model"));
    }

    @Override
    public boolean update(Aircraft aircraft) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, aircraft.getModel());
            statement.setLong(2, aircraft.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Aircraft> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Aircraft> aircrafts = new ArrayList<>();
            var result = statement.executeQuery();
            while (result.next())
                aircrafts.add(buildAircraft(result));
            return aircrafts;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Aircraft> findById(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            Aircraft aircraft = null;
            var result = statement.executeQuery();
            if (result.next())
                aircraft = buildAircraft(result);
            return Optional.ofNullable(aircraft);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Aircraft save(Aircraft aircraft) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, aircraft.getModel());
            statement.executeUpdate();
            var keys = statement.getGeneratedKeys();
            if (keys.next())
                aircraft.setId(keys.getLong("id"));

            return aircraft;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setLong(1, id);
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
