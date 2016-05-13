package devacation;

import org.apache.commons.lang.ArrayUtils;

/**
 * Created by tantan on 3/21/16.
 */
public class Client {
    public static Result[] search(String query, UserCriteria criteria) {
    	if (criteria == null) {
            criteria = new UserCriteria();
        }
    	
        Result[] facts  = Factual.Client.search(query, criteria);
        Result[] places = GPlaces.Client.search(query, criteria);
        Result[] maps   = GMaps.Client.search(query);
        
        System.out.println("factual: " + facts.length);
        System.out.println("places: " + places.length);
        System.out.println("maps: " + maps.length);

        // Join 3 arrays into 1 array
        Result[] results = (Result[]) ArrayUtils.addAll(facts, places);
        results = (Result[]) ArrayUtils.addAll(results, maps);

        return results;
    }
}
