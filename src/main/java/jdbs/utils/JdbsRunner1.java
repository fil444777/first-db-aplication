package jdbs.utils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbsRunner1 {

    public static List<Long> getTicketsByFlightId(Long flightId) {
        List<Long> tickets = new ArrayList<>();
        String sql = """
                select * from ticket
                where flight_id = ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            statement.setFetchSize(2);
            statement.setMaxRows(2);
            statement.setQueryTimeout(1);
            statement.setLong(1, flightId);
            var result = statement.executeQuery();
            while (result.next()) {
                tickets.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return tickets;
    }

    public static List<Long> getFlightsBetween(LocalDateTime start, LocalDateTime end) {
        List<Long> flights = new ArrayList<>();
        String sql = """
                select * from flight
                where departure_date BETWEEN ? and ?;
                """;
        try (var connection = ConnectionManager.get();
             var statement = connection.prepareStatement(sql)) {
            statement.setTimestamp(1, Timestamp.valueOf(start));
            statement.setTimestamp(2, Timestamp.valueOf(end));
            var result = statement.executeQuery();
            while (result.next()) {
                flights.add(result.getLong("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return flights;
    }

    public static void checkMetaData() throws SQLException {
        try (var connection = ConnectionManager.get()) {
            var metaData = connection.getMetaData();
            var catalogs = metaData.getCatalogs();
            while (catalogs.next()) {
                System.out.println(catalogs.getString(1));
            }
        }
    }
}
