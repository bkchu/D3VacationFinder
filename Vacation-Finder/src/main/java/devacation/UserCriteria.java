package devacation;

import Frontend.controllers.PlacesQuery;
import com.google.common.collect.ImmutableMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Created by tantan on 2/28/16.
 */
public class UserCriteria {
    
    public enum Category {
        Landmarks(107, "Landmarks"),
        Historic(110, "Historic"),
        Natural(112, "Natural"),
    	Beaches(113, "Beaches"),
    	Parks(118, "Parks"),
    	NaturalParks(119, "Natural Parks"),
    	Food(149, "Food"),
    	Social(308, "Social"),
    	Museums(311, "Museums"),
    	Bars(312, "Bars"),
        WineBars(316, "Wine Bars"),
    	AmusementParks(319, "Amusement Parks"),
    	Casinos(324, "Casinos"),
    	NightClubs(334, "Night Clubs"),
    	Restaurants(347, "Restaurants"),
    	Zoos(371, "Zoos"),
    	Outdoors(387, "Outdoors"),
    	SnowSports(403, "Snow Sports"),
    	WaterSport(408, "Watersport"),
    	Airports(425, "Airports"),
    	Lodging(432, "Lodging"),
        Adult(318, "Adult Entertainment"),
        MovieTheaters(332, "Movie Theaters"),
    	Entertainment(317, "Entertainment");

        public int id;
        public String name;
        Category(int id, String n) { this.id = id; this.name = n; }
    }

    public UserCriteria() {}
    public UserCriteria(double latitude, double longitude, int radius) {
        // No more than 25000, no less than 1000
        this.maxDistance = radius < 1000 ? 1000 : radius > 50000 ? 50000 : radius;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public UserCriteria(PlacesQuery query) {
        this.country = "US";
        this.maxDistance = query.distance;
        this.budget = query.budget;
        this.isFamily = query.isFamily == null ? false : query.isFamily;
        this.state = query.state;
        this.categories = categoriesFromIdentifiers(query.getCategories());
        this.city = query.city;
    }

    public boolean shouldFilterByDistance() { return (this.latitude != null && this.longitude != null && this.maxDistance != null); }

    // Objects (ie, Integer as opposed to int) are optional
    public Double latitude;
    public Double longitude;
    private Integer maxDistance; // aka radius, in meters
    public Double getLatitude() { return latitude; }
    public Double getLongitude() { return longitude; }
    public Integer getMaxDistance() { return maxDistance; }
    public Integer budget;
    public boolean isFamily;
    public String country;
    public String city;
    public String state;
    public String region;
    public Category[] categories = {};

    private static ImmutableMap<Integer, Category> identifierToCategory;
    static {
        Category[] categories = {
                Category.Landmarks, Category.Historic, Category.Natural, Category.Beaches,
                Category.Parks, Category.NaturalParks, Category.Food, Category.Social,
                Category.Museums, Category.Bars, Category.WineBars, Category.AmusementParks,
                Category.Casinos, Category.NightClubs, Category.Restaurants, Category.Zoos,
                Category.Outdoors, Category.SnowSports, Category.WaterSport, Category.Airports,
                Category.Lodging, Category.Adult,  Category.MovieTheaters
        };

        HashMap mutable = new HashMap();

        for (Category c : categories) {
            mutable.put(c.id, c);
        }

        identifierToCategory = ImmutableMap.copyOf(mutable);
    }

    private static Category[] categoriesFromIdentifiers(Integer[] ids) {
        ArrayList categories = new ArrayList();

        for (Integer id : ids) {
            Object o = identifierToCategory.get(id);
            if (o != null) {
                categories.add(o);
            }
        }

        Object[] tmp = categories.toArray();
        return Arrays.copyOf(tmp, tmp.length, Category[].class);
    }

    public static Integer[] identifiersFromCategoryes(Category[] categories) {
        ArrayList identifiers = new ArrayList();

        for (Category c : categories) {
            identifiers.add(c.id);
        }

        Object[] tmp = identifiers.toArray();
        return Arrays.copyOf(tmp, tmp.length, Integer[].class);
    }
}
