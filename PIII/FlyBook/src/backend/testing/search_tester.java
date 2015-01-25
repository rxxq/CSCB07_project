package backend.testing;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
//import java.util.ArrayList;

import backend.Itinerary;
import backend.Search;
import backend.managers.FlightInfo;

public class search_tester {

	public static void main(String[]args){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		String date_1 = "1995-12-21 07:23";
		String date_2 = "1995-12-21 18:20";
		Date date = null;
		Date other = null;
		Calendar sc = new GregorianCalendar();
		Calendar s2 = new GregorianCalendar();
		try {
			date = sdf.parse(date_1);
			other = sdf.parse(date_2);
			sc.setTime(date);
			s2.setTime(other);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(sc.getTime());
		System.out.println(s2.getTime());
		System.out.println((Math.abs(sc.getTimeInMillis() - s2.getTimeInMillis())) /(3600000.00));
		
		
		/* works for a single flight! .. */
		/* ********************************************************************************* */
		FlightInfo f1 = new FlightInfo("13234", date_1, date_2, "AC", 
				"TORONTO", "VANCOUVER", 1005, 1);
		FlightInfo f2 = new FlightInfo("23424", "1995-12-21 20:20", "1995-12-22 01:00", "AV",
				"VANCOUVER", "Bombay", 1923, 2);
		ArrayList<FlightInfo> itin_1 =  new ArrayList<FlightInfo>();
		itin_1.add(f1);
		itin_1.add(f2);
		Search two_flights = new Search(itin_1, date_1 ,"TORONTO", "Bombay");
		System.out.println(two_flights);
		/* ********************************************************************************** */
		FlightInfo f3 = new FlightInfo("324324", "1995-12-21 21:20", "1995-12-22 01:20", "DAF",
				"VANCOUVER", "Bombay", 100, 3);
		ArrayList<FlightInfo> itin_2 =  new ArrayList<FlightInfo>();
		itin_2.add(f1);
		itin_2.add(f2);
		itin_2.add(f3);
		Search three_flights = new Search(itin_2, date_1, "TORONTO", "Bombay");
		System.out.println(three_flights);
		/* ********************************************************************************** */
		FlightInfo f4 = new FlightInfo("56463", "1995-12-22 00:01", "1995-12-22 00:40", "CRU",
				"VANCOUVER", "Bombay", 275, 4);
		
		FlightInfo f5 = new FlightInfo("3946", "1995-12-21 21:20", "1995-12-22 01:00", "FAA",
				"VANCOUVER", "Beruit",857, 5);
		ArrayList<FlightInfo> itin_3 =  new ArrayList<FlightInfo>();
		itin_3.add(f1);
		itin_3.add(f2);
		itin_3.add(f3);
		itin_3.add(f4);
		itin_3.add(f5);
		ArrayList<Itinerary> iter = new ArrayList<Itinerary>();
		Itinerary tempIter = new Itinerary();
		Search five_flights = new Search(itin_3, date_1, "TORONTO", "Bombay");
		System.out.println(five_flights);
		for(ArrayList<FlightInfo> itinerary: five_flights.getItineraries()){
			tempIter = new Itinerary();
			for(FlightInfo flight: itinerary){
				tempIter.appendFlight(flight);
			}
			iter.add(tempIter);
		} 
		
		for(Itinerary itineraries : iter){
			System.out.println(itineraries);
		}
		List<Itinerary> itin2 = five_flights.sortByCost();
		List<Itinerary> itin1 = five_flights.sortByTime();
		System.out.println(itin1.get(0).getTime());
		System.out.println(itin1.get(1).getTime());
		System.out.println(itin1.get(2).getTime());
		System.out.println(itin2.get(0).getTotalCost());
		System.out.println(itin2.get(1).getTotalCost());
		System.out.println(itin2.get(2).getTotalCost());
		System.out.println(itin1);
		System.out.println(itin2);
		//System.out.println(iter.get(0).getTotalCost());
		//System.out.println(iter.get(0).getOrigin());
		//System.out.println(iter.get(0).getDestination());
		
		
		
	}
}
