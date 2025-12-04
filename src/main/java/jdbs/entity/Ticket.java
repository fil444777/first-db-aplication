package jdbs.entity;

import java.math.BigDecimal;
import java.util.Objects;

public class Ticket {
    private Long id;
    private String passportNo;
    private String passengerNo;
    private Flight flight;
    private String seatNo;
    private BigDecimal cost;

    public Ticket() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    public String getPassengerNo() {
        return passengerNo;
    }

    public void setPassengerNo(String passengerNo) {
        this.passengerNo = passengerNo;
    }

    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public String getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(String seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(passportNo, ticket.passportNo) && Objects.equals(passengerNo, ticket.passengerNo) && Objects.equals(flight, ticket.flight) && Objects.equals(seatNo, ticket.seatNo) && Objects.equals(cost, ticket.cost);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, passportNo, passengerNo, flight, seatNo, cost);
    }

    @Override
    public String toString() {
        return "Ticket{" +
               "id=" + id +
               ", passportNo='" + passportNo + '\'' +
               ", passengerNo='" + passengerNo + '\'' +
               ", flightId=" + flight +
               ", seatNo='" + seatNo + '\'' +
               ", cost=" + cost +
               '}';
    }

    public Ticket(Long id, String passportNo, String passengerNo, Flight flightId, String seatNo, BigDecimal cost) {
        this.id = id;
        this.passportNo = passportNo;
        this.passengerNo = passengerNo;
        this.flight = flightId;
        this.seatNo = seatNo;
        this.cost = cost;






    }
}
