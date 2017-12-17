package service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import model.*;

@WebService(serviceName="BookingService")
public class WService {
	
  private final static HashMap<Integer, Booking> bookings = new HashMap<Integer, Booking>();
  
  @WebMethod(operationName="createNewBooking")
  public Integer create( 
      @WebParam(name="PassengerName")String passengerName, 
      @WebParam(name="DepartureDate")String departureDate, 
      @WebParam(name="AirportOfDeparture")String airportOfDeparture, 
      @WebParam(name="ArrivalDate")String arrivalDate,
      @WebParam(name="AirportOfArrival")String airportOfArrival) throws ParseException {
    Booking b = new Booking(passengerName, departureDate, airportOfDeparture, arrivalDate, airportOfArrival);
    bookings.put(b.getID(), b);
    return b.getID();
  }
   
  @WebMethod(operationName="updatePassengerInfo")
  public Booking updatePassengerInfo(
		  @WebParam(name="ID")Integer ID, 
		  @WebParam(name="PassengerName")String passengerName, 
	      @WebParam(name="DepartureDate")String departureDate, 
	      @WebParam(name="AirportOfDeparture")String airportFrom, 
	      @WebParam(name="ArrivalDate")String arrivalDate,
	      @WebParam(name="AirportOfArrival")String airportTo) throws ParseException {
	Booking b = getBookingByID(ID);
    b.setPassengerName(passengerName);
    b.setDepartureDate(departureDate);
    b.setAirportDeparture(airportFrom);
    b.setArrivalDate(arrivalDate);
    b.setAirportArrival(airportTo);
    return b;
  }
  
  @WebMethod(operationName="listAllBookings")
  public ArrayList<String> getList() {
    ArrayList<String> list = new ArrayList<String>();
    for (Integer x : bookings.keySet())
      list.add(bookings.get(x).toString());
    return list;
  }
  
  @WebMethod(operationName="getBookingByID")
  public Booking getBookingByID(Integer id) {
    return bookings.get(id);
  }
  
  @WebMethod(operationName="removeByID")
  public boolean removeBooking(Integer id) {
	  if (bookings.containsKey(id)){
		  bookings.remove(id);
		  return true;
	  } else {
		  return false;
	  }
  }
  
}