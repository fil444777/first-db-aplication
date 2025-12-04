package jdbs.Dao;

import jdbs.entity.Flight;
import jdbs.entity.FlightStatus;
import jdbs.entity.Ticket;
import jdbs.exception.DaoException;
import jdbs.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FlightDao implements Dao<Long, Flight> {

    private final static FlightDao INSTANCE = new FlightDao();

    private final static String FIND_ALL_SQL = """
            SELECT id, flight_no, departure_date, departure_airport_code, arrival_date, arrival_airport_code, aircraft_id, status
                FROM flight
            """;

    private final static String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            WHERE id = ?
            """;

    @Override
    public boolean update(Flight ticket) {
        return false;
    }

    @Override
    public List<Flight> findAll() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_ALL_SQL)) {
            List<Flight> flights = new ArrayList<>();

            var result = statement.executeQuery();
            while (result.next())
                flights.add(buildFlight(result));


            return flights;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Flight> findById(Long id) {
        try
            (var connection = ConnectionManager.get()){
            return findById(id, connection);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private Flight buildFlight(ResultSet result) throws SQLException {
        return new Flight(
                result.getLong("id"),
                result.getString("flight_no"),
                result.getTimestamp("departure_date").toLocalDateTime(),
                result.getString("departure_airport_code"),
                result.getTimestamp("arrival_date").toLocalDateTime(),
                result.getString("arrival_airport_code"),
                result.getInt("aircraft_id"),
                FlightStatus.valueOf(result.getString("status"))
        );
    }


    public Optional<Flight> findById(Long id, Connection connection) {
        try (var statement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            statement.setLong(1, id);
            Flight flight = null;
            var result = statement.executeQuery();
            if (result.next())
                flight = buildFlight(result);
            return Optional.ofNullable(flight);
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public Flight save(Flight ticket) {
        return null;
    }

    @Override
    public boolean delete(Long id) {
        return false;
    }

    public static FlightDao getInstance() {
        return INSTANCE;
    }

    private FlightDao() {
    }
}
