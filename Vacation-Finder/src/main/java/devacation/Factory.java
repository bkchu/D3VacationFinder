package devacation;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tantan on 2/28/16.
 */
public class Factory {

    /// Used to determine whether the JSON indicates a success response.
    /// Since we have multiple APIs, this is easier than writing multiple functions
    public interface ResponseChecker {
        boolean didSucceed(Map<String, Object> map);
    }
    public static Result fromJSON(Class cls, String json) {
        return (Result) new Gson().fromJson(json, cls);
    }

    public static Result[] objectsFromJSON(Class cls, String json, ResponseChecker checker) {
        // Get calling class, convert string to map
        Map<String, Object> map = jsonObjectFromString(json);

        // Check if response was OK
        if (checker.didSucceed(map)) {
            // Get the array of Maps of model objects
            Map<String, Object> response = (Map<String, Object>)map.get("response");
            ArrayList<Map<String, Object>> modelMaps = (ArrayList<Map<String, Object>>)(response).get("data");

            // Parse the Maps in to model objects
            Gson gson = new Gson();
            ArrayList<Object> objects = new ArrayList<Object>();
            for (Map<String, Object> obj : modelMaps) {
                objects.add(fromJSON(cls, gson.toJson(obj)));
            }

            return objects.toArray(new Result[objects.size()]);
        }

        // Response was not OK
        return null;
    }

    private static Map<String, Object> jsonObjectFromString(String json) {
        return new Gson().fromJson(json, new TypeToken<HashMap<String, Object>>() {}.getType());
    }

    public static Result[] mockResults1() {
        // setup mock results for unit testing, return as array
    	// int rating, int ratingcount, double price, int hoursOpenPerDay, double lat, double lng, boolean hotel
        Result[] results = new Result[10];
        results[0] = new SearchResult(5, 50, 100, 5, 39.7392, -104.9903, true);
        results[1] = new SearchResult(4, 50, 100, 5, 40.1, -104.9903, true);
        results[2] = new SearchResult(3, 50, 100, 5, 39.6392, -104.9903, true);
        results[3] = new SearchResult(2, 50, 100, 5, 39.8392, -104.9903, true);
        results[4] = new SearchResult(1, 50, 100, 5, 39.9392, -104.9903, true);
        results[5] = new SearchResult(5, 1, 100, 5, 39.7392, -104.9903, false);
        results[6] = new SearchResult(4, 1, 100, 5, 39.7992, -104.9903, false);
        results[7] = new SearchResult(3, 1, 100, 5, 39.8592, -104.9903, false);
        results[8] = new SearchResult(2, 1, 100, 5, 39.8492, -104.9903, false);
        results[9] = new SearchResult(1, 1, 100, 5, 39.8192, -104.9903, false);
        return results;
    }
    
    public static Result[] mockResults2() {
        // setup mock results for unit testing, return as array
    	// int rating, int ratingcount, double price, int hoursOpenPerDay, double lat, double lng, boolean hotel
        Result[] results = new Result[10];
        results[0] = new SearchResult(3, 10, 300, 5, 39.7392, -104.9903, true);
        results[1] = new SearchResult(5, 50, 200, 5, 40.1, -104.9903, true);
        results[2] = new SearchResult(1, 5, 50, 5, 39.6392, -104.9903, true);
        results[3] = new SearchResult(5, 1, 100, 5, 39.8392, -104.9903, true);
        results[4] = new SearchResult(1, 50, 150, 5, 39.9392, -104.9903, true);
        results[5] = new SearchResult(4, 10, 200, 5, 39.7392, -104.9903, false);
        results[6] = new SearchResult(5, 50, 10, 5, 39.7992, -104.9903, false);
        results[7] = new SearchResult(3, 10, 200, 5, 39.8592, -104.9903, false);
        results[8] = new SearchResult(3, 10, 300, 5, 39.8492, -104.9903, false);
        results[9] = new SearchResult(3, 10, 200, 5, 39.8192, -104.9903, false);
        return results;
    }
}
