package devacation;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/**
 * Created by tantan on 2/28/16.
 */
public class Algorithm {

    // Testing
    public static void main (String[] args) {
        // Create criteria
        // UserCriteria testCriteria = new UserCriteria ();
        // Testing testMakeCategories()
        // testCriteria.testMakeCategories();
    	int temp_radius = 1609 * 50;
        
		UserCriteria testCriteria = new UserCriteria (39.7392, -104.9903, temp_radius);
		testCriteria.budget = 2;
		Result[] results = Factory.mockResults1();
		results = filter (results, testCriteria);
		results = rank (results, testCriteria);
        System.out.println(resultsString(results));
        System.out.println(results.length);
        
    }

	@Test
	public void testAlgorithm1 () {
		UserCriteria testCriteria = new UserCriteria (39.7392, -104.9903, 50);
		testCriteria.budget = 1;
		Result[] results = Factory.mockResults1();
		results = filter (results, testCriteria);
		results = rank (results, testCriteria);

        assert (results.length == 1);
	}
	
	@Test
	public void testAlgorithm2 () {
		UserCriteria testCriteria = new UserCriteria (39.7392, -104.9903, 25000);
		testCriteria.budget = 3;
		Result[] results = Factory.mockResults2();
		results = filter (results, testCriteria);
		results = rank (results, testCriteria);

        assert (results.length == 1);
	}
	
	/**
	 * Filters out results that our not within budget or the radius.
	 * @param results
	 * @param criteria
	 * @return Result array not containing the filtered results
	 */
    public static Result[] filter(Result[] results, UserCriteria criteria) {
    	ArrayList<Result> tmpResults = new ArrayList<Result>(Arrays.asList(results));
    	
    	if (criteria.shouldFilterByDistance()) {
			double milesInLongitudeDegree = 54.6;
			double milesInLatitudeDegree = 69;
			double miles = criteria.getMaxDistance()/1609;
			double longitudeDifference = miles / milesInLongitudeDegree;
			double latitudeDifference = miles / milesInLatitudeDegree;

			double critLatitude = criteria.getLatitude();
			double critLongitude = criteria.getLongitude();

			if (critLatitude < 0) {
				critLatitude *= -1;
			}

			if (critLongitude < 0) {
				critLongitude *= -1;
			}
			double maxLatitude = critLatitude + latitudeDifference;
			double minLatitude = critLatitude - latitudeDifference;
			double maxLongitude = critLongitude + longitudeDifference;
			double minLongitude = critLongitude - longitudeDifference;

			// tmpResults count
			for (Result tmp : results) {
				double latitude = tmp.getLatitude();
				double longitude = tmp.getLongitude();
				// Check if getLatitude or getLongitude are negative and convert for radius purposes.
				if (latitude < 0) {
					latitude *= -1;
				}

				if (longitude < 0) {
					longitude *= -1;
				}
				// Check getLatitude to see if it is within the interval.
				if (!(latitude <= maxLatitude && latitude >= minLatitude)) {
					tmpResults.remove(tmp);
				}
				// Check longitude to see if it is in the interval
				if (!(longitude <= maxLongitude && longitude >= minLongitude)) {
					tmpResults.remove(tmp);
				}
				// Check budget to make sure the price is not too much.
				if (criteria.budget != null && !tmp.meetsPriceLevel(criteria.budget)) {
					tmpResults.remove(tmp);
				}
			}
    	}
    	else {
    		// filter by budget only
    		for (Result tmp : results) {
				// Check budget to make sure the price is not too much.
				if (criteria.budget != null && !tmp.meetsPriceLevel(criteria.budget)) {
					tmpResults.remove(tmp);
				}
    		}
    	}

		tmpResults = new ArrayList<Result>(new HashSet(tmpResults));

    	// Convert ArrayList<Result> to Result[]
    	Object[] tmp = tmpResults.toArray();
        return Arrays.copyOf(tmp, tmp.length, Result[].class);
    }

    public static Result[] rank(Result[] results, UserCriteria criteria) {
    	for (Result result : results) {
    		result.setCriteria(criteria);
    	}

    	ArrayList<Result> tmpResults = new ArrayList<>(Arrays.asList(results));
    	Collections.sort(tmpResults);
    	Object[] tmp = tmpResults.toArray();
        return Arrays.copyOf(tmp,  tmp.length, Result[].class);
    }
    
    public static String resultsString(Result[] results) {
    	String s = "";
    	int i = 0;
    	for (Result r : results) {
    		if (i >= 10 || r.score() == 0) {
    			// break;
    		}
    		String format = String.format("%.1f", r.score());
    		if (format.equals("0.0")) {
    			// break;
    		}
    		s += r.toString() + "     " + format;
    		s += '\n';
    		i++;
    	}
    	
    	return s;
    }
}
