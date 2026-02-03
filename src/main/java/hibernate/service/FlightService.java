package hibernate.service;



import hibernate.dao.FlightDao;
import hibernate.entity.Flight;

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

    public List<Flight> findAll (){
        return flightDao.findAll();
    }
}
