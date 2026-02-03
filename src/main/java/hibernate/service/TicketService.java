package hibernate.service;


import hibernate.dao.TicketDao;
import hibernate.entity.Ticket;


import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private final static TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    public List<Ticket> findAllByFlightId(Long flightId) {
        return ticketDao.findAllByFlightId(flightId);
    }


    private TicketService() {
    }

    public static TicketService getInstance() {
        return INSTANCE;
    }
}
