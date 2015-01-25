package backend.testing;

import backend.Itinerary;
import backend.managers.FlightInfo;

public class test {
	public static void main(String[] args){
		FlightInfo flight1 = new FlightInfo("235", "2014-11-03 05:16","2014-11-03 08:26", "AC", "Tornto", "Montreal", 1235,1);
		FlightInfo flight2 = new FlightInfo("532", "2014-11-03 10:37","2014-11-04 08:15", "AC", "Montreal", "Detroit" , 980,2);
		FlightInfo flight3 = new FlightInfo("666", "2014-11-04 10:26","2014-11-04 13:01", "AC", "Detroit", "Dallas" , 687,3);
		Itinerary iter = new Itinerary();
		Itinerary iter2 = new Itinerary();
		iter.appendFlight(flight1);
		iter.appendFlight(flight2);
		iter.appendFlight(flight3);
		System.out.println(iter.getTime());
		System.out.println(iter.getTotalCost());
		System.out.println(iter.getOrigin());
		System.out.println(iter.getDestination());
		System.out.println(iter2);
		System.out.println(iter);
	}
}
