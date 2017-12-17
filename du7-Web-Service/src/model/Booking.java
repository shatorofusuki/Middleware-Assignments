package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Booking {
  private static Integer count = 0;

  private final Integer id;
  private String passengerName = "";

  private Date departure = null;
  private String airportDeparture = "";

  private Date arrival = null;
  private String airportArrival = "";
  
  public Booking() {
	  //not implemented
	  id = count++;
  }

  public Booking(
		  String passengerName, 
		  String departureDate, 
		  String airportDeparture, 
		  String arrivalDate, 
		  String airportArrival ) throws ParseException {
    id = count++;
    this.setPassengerName(passengerName);
    this.setDepartureDate(departureDate);
    this.setAirportDeparture(airportDeparture);
    this.setArrivalDate(arrivalDate);
    this.setAirportArrival(airportArrival);
  }

  public Integer getID() {
    return id;
  }

  public String getDeparture() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    String date = sdf.format(this.departure);
    return date;
  }

  public String getAirportDeparture() {
    return airportDeparture;
  }

  public String getArrival() {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    String date = sdf.format(this.arrival);
    return date;
  }

  public String getAirportArrival() {
    return airportArrival;
  }

  public void setDepartureDate(String departure) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    Date date = sdf.parse(departure);
    this.departure = date;
  }

  public void setAirportDeparture(String airportDeparture) {
    this.airportDeparture = airportDeparture;
  }

  public void setArrivalDate(String arrival) throws ParseException {
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm");
    Date date = sdf.parse(arrival);
    this.arrival = date;
  }

  public void setAirportArrival(String airportArrival) {
    this.airportArrival = airportArrival;
  }

  public String getPassengerName() {
    return passengerName;
  }

  public void setPassengerName(String passengerName) {
    this.passengerName = passengerName;
  }

  public String toString() {
    return String.format("%d : %s is departing on %s from %s, arriving at %s on %s, ", getID(),
        getPassengerName(), getDeparture(), getAirportDeparture(), getArrival(), getAirportArrival());
  }

}