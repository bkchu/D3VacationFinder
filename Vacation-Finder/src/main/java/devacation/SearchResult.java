package devacation;

public class SearchResult extends Result {

	private int rating;
	private int ratingCount;
	private double price;
	private int hours;
	private double lat;
	private double lng;
	private boolean hotel;

	public SearchResult(int rating, int ratingcount, double price, int hoursOpenPerDay, double lat, double lng, boolean hotel) {
		this.rating = rating;
		this.ratingCount = ratingcount;
		this.price = price;
		this.hours = hoursOpenPerDay;
		this.lat = lat;
		this.lng = lng;
		this.hotel = hotel;
	}

    @Override
    public String toString() {
        return "<SearchResult score: "+ this.score() +
                ", price: " + this.price() +
                ", rating: " + this.rating() +
                ", rating count: " + this.ratingCount() + ">";
    }

	public String getName() { return "blank"; }

	public int ratingCount() {
		return ratingCount;
	}
	public double price() {
		return price;
	}
	public double getLatitude() {
		return lat;
	}
	public double getLongitude() {
		return lng;
	}
	public double rating() {
		return rating;
	}
    public boolean isHotelOrRestaurant() {
        return hotel;
    }
    public boolean meetsPriceLevel(int level) {
        return this.price <= level;
    }
	
	public String getWebsite() { return ""; }
}
