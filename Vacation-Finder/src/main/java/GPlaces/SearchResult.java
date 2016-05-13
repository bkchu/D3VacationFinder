package GPlaces;

import devacation.Result;
import net.sf.sprockets.google.Place;

import java.util.List;

/**
 * Created by tantan on 2/28/16.
 */
public class SearchResult extends Result {

    public Place place;

    public SearchResult(Place p) {
        if (p == null) {
            throw new NullPointerException();
        }

        this.place = p;
    }
    
    @Override
    public String toString() {
        return "<SearchResult name: "+ place.getName() +
                ", lat: " + place.getLatitude() +
                ", long: " + place.getLongitude() +
                ", price: " + place.getPriceLevel() +
                ", rating: " + place.getRating() +
                ", URL: " + getWebsite() + ">";
    }

    public String getName() { return place.getName(); }
    public int ratingCount() { return place.getRatingCount(); }
    public double rating() { return place.getRating(); }
    public double price() { return place.getPriceLevel(); }
    public double getLatitude() { return place.getLatitude(); }
    public double getLongitude() { return place.getLongitude(); }
    public int hoursOpenPerDay() {
        List<Place.OpeningHours> hours = place.getOpeningHours();

        int total = 0;
        for (Place.OpeningHours h : hours) {
            total += h.getCloseHour() - h.getOpenHour();
        }

        return total;
    }
    public boolean meetsPriceLevel(int level) { return ((double)this.place.getPriceLevel())*0.6 <= (double)level; }
    public boolean isHotelOrRestaurant() { return place.getRating() > -1; }

    public boolean hasURL() {
        return place.getUrl() != null || place.getWebsite() != null;
    }

    public String getWebsite() {
        return place.getWebsite() != null ? place.getWebsite() : place.getUrl();
    }
}
