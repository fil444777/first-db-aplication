package jdbs.dao;

import jdbs.entity.Flight;
import jdbs.entity.Ticket;
import jdbs.exception.DaoException;
import jdbs.utils.ConnectionManager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

public class FlightTicketDao {
    private final static FlightTicketDao INSTANCE = new FlightTicketDao();
    private final FlightDao flightDao = FlightDao.getInstance();

    public static FlightTicketDao getInstance() {
        return INSTANCE;
    }

    private FlightTicketDao() {
    }

    private final static String FIND_NAME_SQL = """
            WITH passenger_counts AS (
                                                                SELECT
                                                                    passenger_name,
                                                                    COUNT(*) AS name_count
                                                                FROM ticket
                                                                GROUP BY passenger_name
                                                            )
                                                            SELECT\s
                                                                passenger_name,
                                                                name_count
                                                            FROM passenger_counts
                                                            ORDER BY name_count DESC
                                                            LIMIT 1;
            """;

    private final static String FIND_TOTAL_NAME_SQL = """
            WITH passenger_counts AS (
                                                                SELECT
                                                                    passenger_name,
                                                                    COUNT(*) AS name_count
                                                                FROM ticket
                                                                GROUP BY passenger_name
                                                            )
                                                            SELECT\s
                                                                passenger_name,
                                                                name_count
                                                            FROM passenger_counts
                                                            ORDER BY name_count DESC
                                                            ;
            """;

    private final static String UPDATE_BY_ID_SQL = """
            UPDATE ticket
            SET 
                passport_no = ?,
                passenger_name = ?, 
                flight_id = ?, 
                seat_no = ?, 
                cost = ?
            WHERE id = ?
            """;


    private Ticket buildTicket(ResultSet result) throws SQLException {
        return new Ticket(result.getLong("id"),
                result.getString("passport_no"),
                result.getString("passenger_name"),
                flightDao.findById(
                        result.getLong("flight_id"),
                        result.getStatement().getConnection()).orElse(null),
                result.getString("seat_no"),
                result.getBigDecimal("cost"));

    }

    public String nameTicket() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_NAME_SQL)) {
            String ticket = null;
            var result = statement.executeQuery();
            if (result.next())
                ticket = result.getString("passenger_name");
            return ticket;


        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public Map<String, String> totalName() {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(FIND_TOTAL_NAME_SQL)) {
            Map<String, String> tickets = new HashMap<>();
            var result = statement.executeQuery();
            while (result.next())
                tickets.put(result.getString("passenger_name"), result.getString("name_count"));
            return tickets;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean updateById(Long id, Ticket ticket) {
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(UPDATE_BY_ID_SQL)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerNo());
            statement.setLong(3, ticket.getFlight().getId());
            statement.setString(4, ticket.getSeatNo());
            statement.setBigDecimal(5, ticket.getCost());
            statement.setLong(6, ticket.getId());
            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    public boolean updateFlightAndTicketsByFlightId(Long flightId, Flight flight, Ticket ticket) {
        try (var connection = ConnectionManager.get()) {
            connection.setAutoCommit(false);

            try {
                boolean flightUpdated = updateFlight(connection, flightId, flight);

                boolean ticketsUpdated = updateTickets(connection, flightId, ticket);

                if (flightUpdated && ticketsUpdated) {
                    connection.commit();
                    return true;
                } else {
                    connection.rollback();
                    return false;
                }
            } catch (SQLException e) {
                connection.rollback();
                throw new DaoException(e);
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        }
    }

    private boolean updateFlight(Connection connection, Long flightId, Flight flight) throws SQLException {
        String sql = """
                UPDATE flight 
                SET 
                    flight_no = ?, 
                    departure_date = ?, 
                    departure_airport_code = ?, 
                    arrival_date = ?, 
                    arrival_airport_code = ?, 
                    aircraft_id = ?, 
                    status = ?
                WHERE id = ?
                """;

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, flight.getFlightNo());
            statement.setTimestamp(2, Timestamp.valueOf(flight.getDepartureDate()));
            statement.setString(3, flight.getDepartureAirportCode());
            statement.setTimestamp(4, Timestamp.valueOf(flight.getArrivalDate()));
            statement.setString(5, flight.getArrivalAirportCode());
            statement.setInt(6, flight.getAircraftId());
            statement.setString(7, String.valueOf(flight.getStatus()));
            statement.setLong(8, flightId);

            return statement.executeUpdate() > 0;
        }
    }

    private boolean updateTickets(Connection connection, Long flightId, Ticket ticket) throws SQLException {
        String sql = """
                UPDATE ticket 
                SET 
                    passport_no = ?, 
                    passenger_name = ?, 
                    seat_no = ?, 
                    cost = ?
                WHERE flight_id = ?
                """;

        try (var statement = connection.prepareStatement(sql)) {
            statement.setString(1, ticket.getPassportNo());
            statement.setString(2, ticket.getPassengerNo());
            statement.setString(3, ticket.getSeatNo());
            statement.setBigDecimal(4, ticket.getCost());
            statement.setLong(5, flightId);

            return statement.executeUpdate() > 0;
        }
    }

}
