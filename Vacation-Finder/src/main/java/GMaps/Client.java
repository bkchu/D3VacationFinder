package GMaps;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.maps.GeoApiContext;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import devacation.Result;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tantan on 2/28/16.
 */
public class Client {

    static String clientKey = "AIzaSyASkAJGEQ4d0ycIUnW8a71VeBPpzZXJ2Jk";
    public static GeoApiContext context = new GeoApiContext();

    static Cache<String, Result[]> cache = CacheBuilder.newBuilder().build();

    static {
        context.setApiKey(clientKey);
    }

    public static Result[] search(String query) {
        // Check cache first
        String key = cacheKey(query);
        Result[] cached = cache.getIfPresent(key);
        if (cached != null) {
            System.out.println("Returning cached results from Google Maps");
            return cached;
        }
        try {
            System.out.print("Querying Google Maps . . . ");
            GeocodingResult[] geoResults = GeocodingApi.geocode(context, query).await();
            System.out.println("Done.");
            ArrayList<SearchResult> results = new ArrayList<SearchResult>(geoResults.length);

            for (GeocodingResult g : geoResults) {
                results.add(new SearchResult(g));
            }

            // Cache results
            Object[] tmp = results.toArray();
            cached = Arrays.copyOf(tmp, tmp.length, Result[].class);
            cache.put(key, cached);

            return cached;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String cacheKey(String text) {
        String ret = text;
        // In the future we may use UserCriteria, so do not delete this method
        return ret;
    }
}
