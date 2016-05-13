package GMaps;

import com.google.maps.model.GeocodingResult;
import devacation.Result;

/**
 * Created by tantan on 2/28/16.
 */
public class SearchResult extends Result {

    public GeocodingResult place;

    public SearchResult(GeocodingResult r) {
        this.place = r;
    }

    @Override
    public String toString() {
        return "<SearchResult address: " + place.formattedAddress + ", lat: " + getLatitude() + ", long: " + getLongitude() + ">";
    }

    public String getName() { return place.formattedAddress; }
    public int ratingCount() { return 0; }
    public double price() { return -1; }
    public double getLatitude() { return place.geometry.location.lat; }
    public double getLongitude() { return place.geometry.location.lng; }
    public boolean meetsPriceLevel(int level) { return true; }
    public boolean isHotelOrRestaurant() { return true; } // send to bottom
    public double score() { return 0; }
    public double rating() { return -1; };

    public String getWebsite() { return ""; }

}
