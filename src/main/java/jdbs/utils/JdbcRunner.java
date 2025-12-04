package jdbs.utils;

import jdbs.Dao.FlightDao;
import jdbs.Dao.FlightTicketDao;
import jdbs.Dao.TicketDao;
import jdbs.dto.TicketFilter;
import jdbs.entity.Flight;
import jdbs.entity.Ticket;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jdbs.entity.FlightStatus.CANCELLED;

public class JdbcRunner {
    public static void main(String[] args) throws SQLException {
        var flightTicketDao = FlightTicketDao.getInstance();
        var ticketDao = TicketDao.getInstance();
        var flightDao = FlightDao.getInstance();
        // System.out.println(flightTicketDao.nameTicket());
        //System.out.println(flightTicketDao.totalName());
        // System.out.println(ticketDao.findById(3L));
        LocalDateTime time = LocalDateTime.of(2025, 6, 15, 14, 30);
        LocalDateTime time1 = LocalDateTime.of(2025, 6, 16, 14, 30);
        BigDecimal cost = new BigDecimal("123.45");
        Flight flight = new Flight(9L, "MN3002", time,
                "MSK", time1, "BSL", 2, CANCELLED);
        Ticket ticket = new Ticket(50L, "SDFQWQ123",
                "Dmitri Воснецов", flight, "B1", cost);
        System.out.println(ticketDao.findById(50L));
        System.out.println(flightDao.findById(9L));
        System.out.println(flightTicketDao.updateTicketAndFlightById(9L, flight, ticket));
        System.out.println("----------------------");
        System.out.println(ticketDao.findById(50L));
        System.out.println(flightDao.findById(9L));
//            var flightDao = FlightDao.getInstance();
//            System.out.println(flightDao.findAll());

    }

}