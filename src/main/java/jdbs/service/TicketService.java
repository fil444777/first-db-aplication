package jdbs.service;


import jdbs.dao.TicketDao;
import jdbs.dto.TicketDto;

import java.util.List;
import java.util.stream.Collectors;

public class TicketService {
    private final static TicketService INSTANCE = new TicketService();
    private final TicketDao ticketDao = TicketDao.getInstance();

    public List<TicketDto> findAllByFlightId(Long flightId){
        return ticketDao.findAllByFlightId(flightId).stream().map(
                ticket -> new TicketDto(ticket.getId(), ticket.getFlight().getId(), ticket.getSeatNo())
        ).collect(Collectors.toList());
    }


    private TicketService() {
    }

    public static TicketService getInstance(){
        return INSTANCE;
    }
}
