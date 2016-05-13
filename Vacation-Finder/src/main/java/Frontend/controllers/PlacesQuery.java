package Frontend.controllers;

import devacation.UserCriteria;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by brand on 3/1/2016.
 */
public class PlacesQuery {
    // Public because the getters are just for Thymeleaf really,
    // and will return "N/A" for null values.
    public String city;
    public String state;
    public Integer budget; // 1, 2, 3, defaults to 1
    public Integer distance;
    public Boolean isFamily;
    public List<Integer> checkedPOI;
    public List<Integer> checkedFun;
    public List<Integer> checkedSocial;
    public List<Integer> checkedEntertainment;
    public List<Integer> checkedMisc;

    public String toString() {
        return "" + city + ", " + state + ", " + budget + ", " + distance + ", " + isFamily;
    }

    public Integer[] getCategories() {
        ArrayList ints = new ArrayList() {{
            addAll(checkedPOI);
            addAll(checkedFun);
            addAll(checkedSocial);
            addAll(checkedEntertainment);
            addAll(checkedMisc);
        }};

        Object[] tmp = ints.toArray();
        return Arrays.copyOf(tmp, tmp.length, Integer[].class);
    }

    public String getCityPlaceholder() {
        return fromField(city);
    }
    public String getStatePlaceholder() {
        return fromField(state);
    }
    public String getCity() {
        return city == null ? "" : city;
    }
    public String getState() {
        return state == null ? "" : state;
    }
    public String getBudget() {
        return fromField(budget);
    }
    public String getDistance() {
        return fromField(distance);
    }
    public String getIsFamily() {
        return fromField(isFamily);
    }

    // Boilerplate getters and setters for the categories
    public List<Integer> getCheckedPOI() { return checkedPOI; }
    public List<Integer> getCheckedFun() { return checkedFun; }
    public List<Integer> getCheckedSocial() { return checkedSocial; }
    public List<Integer> getCheckedEntertainment() { return checkedEntertainment; }
    public List<Integer> getCheckedMisc() { return checkedMisc; }
    public void setCheckedPOI(List<Integer> categories) {
        categories = categories == null ? new ArrayList<Integer>() : categories;
        System.out.println(categories.toString());
        this.checkedPOI = categories;
    }
    public void setCheckedFun(List<Integer> categories) {
        categories = categories == null ? new ArrayList<Integer>() : categories;
        System.out.println(categories.toString());
        this.checkedFun = categories;
    }
    public void setCheckedSocial(List<Integer> categories) {
        categories = categories == null ? new ArrayList<Integer>() : categories;
        System.out.println(categories.toString());
        this.checkedSocial = categories;
    }
    public void setCheckedEntertainment(List<Integer> categories) {
        categories = categories == null ? new ArrayList<Integer>() : categories;
        System.out.println(categories.toString());
        this.checkedEntertainment = categories;
    }
    public void setCheckedMisc(List<Integer> categories) {
        categories = categories == null ? new ArrayList<Integer>() : categories;
        System.out.println(categories.toString());
        this.checkedMisc = categories;
    }

    public void setCity(String city) {
        if (city != null && !city.isEmpty()) {
            this.city = city;
        }
    }
    public void setState(String state) {
        if (state != null && !state.isEmpty()) {
            this.state = state;
        }
    }
    public void setBudget(String budget) {
        this.budget = Integer.parseInt(budget.replaceAll("\\$\\$\\$", "3").replaceAll("\\$\\$", "2").replaceAll("\\$", "1"));
    }
    public void setDistance(String distance) {
        this.distance = Integer.parseInt(distance) * 1609;
        distance = distance;
    }
    public void setIsFamily(String isFamily) {
        this.isFamily = fromString(isFamily);
    }
    static boolean fromString(String s) {
        return s.equalsIgnoreCase("true");
    }
    static String fromField(Object o) { return o == null ? "N/A" : o.toString(); }


    static List<UserCriteria.Category> sectionPOI() {
        return new ArrayList<UserCriteria.Category>() {{
            add(UserCriteria.Category.Landmarks);
            add(UserCriteria.Category.Historic);
            add(UserCriteria.Category.Natural);
            add(UserCriteria.Category.Beaches);
            add(UserCriteria.Category.Parks);
            add(UserCriteria.Category.NaturalParks);
            add(UserCriteria.Category.Museums);
        }};
    }

    static List<UserCriteria.Category> sectionFun() {
        return new ArrayList<UserCriteria.Category>() {{
            add(UserCriteria.Category.AmusementParks);
            add(UserCriteria.Category.Zoos);
            add(UserCriteria.Category.Outdoors);
            add(UserCriteria.Category.SnowSports);
            add(UserCriteria.Category.WaterSport);
        }};
    }

    static List<UserCriteria.Category> sectionSocial() {
        return new ArrayList<UserCriteria.Category>() {{
            add(UserCriteria.Category.Social);
            add(UserCriteria.Category.Bars);
            add(UserCriteria.Category.WineBars);
        }};
    }

    static List<UserCriteria.Category> sectionEntertainment() {
        return new ArrayList<UserCriteria.Category>() {{
            add(UserCriteria.Category.Entertainment);
            add(UserCriteria.Category.MovieTheaters);
            add(UserCriteria.Category.Casinos);
            add(UserCriteria.Category.NightClubs);
            add(UserCriteria.Category.Adult);
        }};
    }

    static List<UserCriteria.Category> sectionMisc() {
        return new ArrayList<UserCriteria.Category>() {{
            add(UserCriteria.Category.Food);
            add(UserCriteria.Category.Restaurants);
            add(UserCriteria.Category.Airports);
            add(UserCriteria.Category.Lodging);
        }};
    }
}
