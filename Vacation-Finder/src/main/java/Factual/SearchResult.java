package Factual;

import com.google.gson.annotations.SerializedName;
import devacation.Result;

import java.net.URL;
import java.util.Map;

/**
 * Created by tantan on 2/8/16.
 */

public class SearchResult extends Result {

    @SerializedName("address")
    public String address;

    @SerializedName("category_ids")
    public int[] categoryIdentifiers;

    @SerializedName("category_labels")
    public String[][] categoryLabels;

    @SerializedName("country")
    public String country;

    @SerializedName("email")
    public String email;

    @SerializedName("factual_identifier")
    public String factualIdentifier;

    @SerializedName("hours")
    public Map<String, Object> hoursOfOperation;

    @SerializedName("hours_display")
    public String hoursDisplayString;

    @SerializedName("latitude")
    public double latitude;

    @SerializedName("longitude")
    public double longitude;

    @SerializedName("locality")
    public String locality;

    @SerializedName("name")
    public String name;

    @SerializedName("neighborhood")
    public String[] neighborhood;

    @SerializedName("postcode")
    public String postcode;

    @SerializedName("region")
    public String region;

    @SerializedName("tel")
    public String phoneNumber;

    @SerializedName("website")
    public URL website;

    @SerializedName("rating")
    public Integer rating;

    @Override
    public String toString() {
        return "<SearchResult name: " + name + ", lat: " + latitude + ", long: " + longitude + ", URL: " + website + ">";
    }

    public String getName() { return name; }
    public int ratingCount() { return 0; }
    public double price() { return -1; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public double rating() { return rating; }
    public boolean isHotelOrRestaurant() { return rating != null; }
    public boolean meetsPriceLevel(int level) { return true; }

    public String getWebsite() { return website != null ? website.toString() : null; }
}
