package Factual;

import com.factual.driver.Circle;
import com.factual.driver.Factual;
import com.factual.driver.Query;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import devacation.Factory;
import devacation.Result;
import devacation.UserCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by tantan on 2/28/16.
 */
public class Client {

    static String clientKey    = "8obK0fJ3MGblNpl79Tq29cLnBc6rQ73fyqrmPHEj";
    static String clientSecret = "7vL7ZTQdrvazXMeOE6Fpv0BuIteNMvgW460Uth8l";

    static Cache<String, Result[]> cache = CacheBuilder.newBuilder().build();

    public static void main(String[] args) {
        UserCriteria criteria = new UserCriteria();
        criteria.country = "us";

        List categories = new ArrayList<Integer>(Arrays.asList(107, 110, 112, 113, 118, 119, 149, 311, 312, 316, 324, 334, 371, 387, 403, 408, 425));
        Factual fact = new Factual(clientKey, clientSecret);

        Query query = makeQuery(null, criteria);
        query.field("category_ids").includesAnyList(categories);

        String json = fact.fetch("places", query).toString();
        System.out.println(json);
    }

    public static Result[] search(String text, UserCriteria criteria) {
        // Check cache first
        String key = cacheKey(text, criteria);
        Result[] results = cache.getIfPresent(key);
        if (results != null) {
            System.out.println("Returning cached results from Factual");
            return results;
        }

        Factual fact = new Factual(clientKey, clientSecret);
        Query query = makeQuery(text, criteria);

        System.out.print("Querying Factual . . . ");
        String json = fact.fetch("places", query).toString();
        System.out.println("Done.");

        // The last parameter here is an instance of an "anonymous inner class" which basically
        // means that when you write "new SomeInterface()" Java creates an anonymous class
        // that extends SomeInterface for you. So everything following this first line
        // is an implementation of that interface, which just returns whether the given
        // JSON (as a Map) indicates a successful API call. Factory uses this to determine
        // whether or not to try to convert the JSON to model objects.
        //
        // This is useful because every API will have different ways of determining whether
        // or not we made a successful API call with model objects in the response.
        results = Factory.objectsFromJSON(SearchResult.class, json, new Factory.ResponseChecker() {
            public boolean didSucceed(Map<String, Object> map) {
                return map.get("status").equals("ok");
            }
        });

        // Cache results
        cache.put(key, results);
        
        return results;
    }

    public static void reverseGeocodeUserCriteria(UserCriteria criteria) {
        try {
            GeocodingResult[] geoResults = GeocodingApi.geocode(GMaps.Client.context, criteria.city + ", " + criteria.state).await();
            GeocodingResult r = geoResults[0];
            criteria.latitude = r.geometry.location.lat;
            criteria.longitude = r.geometry.location.lng;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String cacheKey(String text, UserCriteria criteria) {
        String ret = text;

        if (criteria.country != null) {
            ret += criteria.country;
        }
        if (criteria.city != null) {
            ret += criteria.city;
        }
        if (criteria.state != null) {
            ret += criteria.state;
        } else if (criteria.region != null) {
            ret += criteria.region;
        }

        if (criteria.shouldFilterByDistance()){
        	ret += criteria.getLatitude() + criteria.getLongitude();
            ret += criteria.getMaxDistance();
        }

        for (UserCriteria.Category c : criteria.categories) {
            // No idea why c would ever be null...
            // it was once null when the array was full
            if (c != null) {
                ret += c.name;
            }
        }

        return ret;
    }

    private static Query makeQuery(String text, UserCriteria criteria) {
        Query query = new Query().limit(50);
        query.search("");
        if (text != null && text.length() > 0) {
            query.search(text);
        }
        if (criteria.country != null) {
            query.field("country").isEqual(criteria.country);
        }
        if (criteria.city != null) {
            query.field("locality").isEqual(criteria.city);
        }
        if (criteria.state != null) {
            query.field("region").isEqual(criteria.state);
        } else if (criteria.region != null) {
            query.field("region").isEqual(criteria.region);
        }

        if (criteria.getLatitude() == null || criteria.getLongitude() == null) {
            reverseGeocodeUserCriteria(criteria);
        }

        int radius = 20000;
        if (criteria.getMaxDistance() != null) {
            radius = criteria.getMaxDistance() > 20000 ? 20000 : criteria.getMaxDistance();
        }
        query.within(new Circle(criteria.getLatitude(), criteria.getLongitude(), radius));

        // Add categories
        if (criteria.categories != null && criteria.categories.length > 0) {
            List categorires = Arrays.asList(UserCriteria.identifiersFromCategoryes(criteria.categories));
            query.field("category_ids").includesAnyList(categorires);
        }

        return query;
    }
}
