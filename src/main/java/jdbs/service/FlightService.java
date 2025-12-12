package jdbs.service;

import jdbs.dao.FlightDao;
import jdbs.dto.FlightDto;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService {
    private static final FlightService INSTANCE = new FlightService();
    private FlightDao flightDao = FlightDao.getInstance();


    private FlightService() {
    }

    public static FlightService getInstance(){
        return INSTANCE;
    }

    public List<FlightDto> findAll (){
        return flightDao.findAll().stream().map(fliht ->
                new FlightDto(fliht.getId(), "%s - %s - %s".formatted(
                        fliht.getArrivalAirportCode(),
                        fliht.getDepartureAirportCode(),
                        fliht.getStatus()))).collect(Collectors.toList());
    }
}
