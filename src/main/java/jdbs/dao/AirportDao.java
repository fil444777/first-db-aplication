package jdbs.dao;

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

public class AirportDao implements Dao <String, Airport> {
    private static final AirportDao INSTANCE = new AirportDao();

    public AirportDao() {
    }

    public static AirportDao getInstance() {
        return INSTANCE;
    }

    private final static String UPDATE_SQL = """
            UPDATE airport
            SET 
                code = ?,
                country = ?,
                city = ?
            """;

    private final static String FIND_ALL_SQL = """
            SELECT a.code, a.country, a.city
            FROM airport a
           
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE a.code = ?
            """;


    private Airport buildAirport(ResultSet result) throws SQLException {
        return new Airport(result.getString("code"),
                result.getString("country"),
                result.getString("city"));
    }

    private final static String SAVE_SQL = """
            INSERT INTO airport 
            (code, country, city)
            VALUES (?, ?, ?)
            """;

    private final static String DELETE_SQL = """
            DELETE FROM airport
            where code = ?
            """;

    @Override
    public boolean update(Airport airport) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_SQL)) {
            statement.setString(1, airport.getCode());
            statement.setString(2, airport.getCountry());
            statement.setString(3, airport.getCity());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public List<Airport> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Airport> airports = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next())
                airports.add(buildAirport(result));
            return airports;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Airport> findById(String code) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setString(1, code);
            Airport airport = null;
            var result = statement.executeQuery();
            if (result.next())
                airport = buildAirport(result);
            return Optional.ofNullable(airport);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Airport save(Airport airport) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            statement.setString(1, airport.getCode());
            statement.setString(2, airport.getCountry());
            statement.setString(3, airport.getCity());
            statement.executeUpdate();
            return airport;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public boolean delete(String code) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(DELETE_SQL)) {
            statement.setString(1, code);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }
}
