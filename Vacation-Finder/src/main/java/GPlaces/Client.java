package GPlaces;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.maps.GeocodingApi;
import com.google.maps.model.GeocodingResult;
import devacation.Algorithm;
import devacation.Result;
import devacation.UserCriteria;
import net.sf.sprockets.Sprockets;
import net.sf.sprockets.google.Place;
import net.sf.sprockets.google.Places;
import net.sf.sprockets.google.PlacesParams;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by tantan on 2/28/16.
 */
public class Client {

    static Cache<String, Result[]> cache = CacheBuilder.newBuilder().build();
    static int reqCount = 0;

    static {
		Sprockets.getConfig().setProperty("google.api-key", "AIzaSyASkAJGEQ4d0ycIUnW8a71VeBPpzZXJ2Jk");//"AIzaSyDlH6-MACgIkzNCOaOCejgKmCUzGxOHRK4");
	}

    public static void main(String[] args) {
        UserCriteria criteria = new UserCriteria();
        criteria.region = "europe";
        System.out.println(Algorithm.resultsString(search("skiing", criteria)));
    }
	
    public static Result[] search(String query, UserCriteria criteria) {
        // Setup request params / cache key
        // Since we're making one request per category, we need
        // an array of param objects; one for each request
        PlacesParams[] params = makeParams(query, criteria);

        // We're going to get an array of cached responses, and
        // an array of requests to make that didn't have cached results
        ArrayList<PlacesParams> requestsToMake = new ArrayList();
        ArrayList<Result[]> requestResults = new ArrayList();

        for (PlacesParams p : params) {
            // Check cache first
            Result[] cached = cache.getIfPresent(p.toString());
            if (cached != null) {
                requestResults.add(cached);
                System.out.print("Using Places cache . . . ");
            }
            // Otherwise we will make the request
            else {
                requestsToMake.add(p);
            }
        }

        // Make each request synchronously (this could be done async somehow...
        reqCount = requestsToMake.size();
        if (requestsToMake.size() > 0) {
            System.out.print("Querying Google Places . ");
        }
        for (PlacesParams param : requestsToMake) {
            (new Thread() {
                public void run() {
                    try {
                        (new Thread() {
                            public void run() {
                                // do api call
                            }
                        }).run();
                        // API call
                        Object[] tmp = Places.textSearch(param).getResult().toArray();
                        Place[] places = Arrays.copyOf(tmp, tmp.length, Place[].class);

                        // Convert to our model
                        ArrayList<SearchResult> results = new ArrayList<>(places.length);
                        for (Place p : places) {
                            // Get extended details about the place (includes rating count, etc)
                            p = Places.details(Places.Params.create().placeId(p.getPlaceId().getId())).getResult();
                            results.add(new SearchResult(p));
                        }

                        // Cache results
                        tmp = results.toArray();
                        Result[] cached = Arrays.copyOf(tmp, tmp.length, Result[].class);
                        cache.put(param.toString(), cached);

                        // Add results to the list of results
                        requestResults.add(cached);
                        System.out.print(". ");

                        // Dec counter
                        decrementRequestCount();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        // Wait for threads
        while (reqCount > 0) { try { Thread.sleep(10); } catch (Exception e) { e.printStackTrace(); } }
        System.out.println("Done.");
        return flattenResults(requestResults);
    }

    public static PlacesParams[] makeParams(String query, UserCriteria criteria) {
        ArrayList params = new ArrayList();

        for (UserCriteria.Category c : criteria.categories) {
            params.add(makeParamsForCategory(c.name, criteria));
        }

        Object[] tmp = params.toArray();
        return Arrays.copyOf(tmp, tmp.length, PlacesParams[].class);
    }

    public static PlacesParams makeParamsForCategory(String query, UserCriteria criteria) {
        boolean needsLocale = true;
        if (criteria.city != null && criteria.city.length() > 0) {
            query += " " + criteria.city;
            needsLocale = false;
        }
        if (criteria.state != null && criteria.state.length() > 0) {
            query += " " + criteria.state;
            needsLocale = false;
        }
        if (criteria.country != null && criteria.country.length() > 0) {
            query += " " + criteria.country;
            needsLocale = false;
        }
        if (needsLocale && criteria.region != null && criteria.region.length() > 0) {
            query += " " + criteria.region;
        }

        PlacesParams params = PlacesParams.create().query(query);

        if (criteria.getLatitude() == null || criteria.getLongitude() == null) {
            reverseGeocodeUserCriteria(criteria);
        }
        params.latitude(criteria.getLatitude()).longitude(criteria.getLongitude());

        if (criteria.getMaxDistance() != null) {
            params.radius(criteria.getMaxDistance());
        }


        return params;
    }

    public static Result[] flattenResults(ArrayList<Result[]> results) {
        ArrayList tmp = new ArrayList();
        for (Result[] r : results) {
            tmp.addAll(Arrays.asList(r));
        }

        Object[] intermediate = tmp.toArray();
        return Arrays.copyOf(intermediate, intermediate.length, Result[].class);
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

    public static void decrementRequestCount() {
        reqCount--;
    }
}
