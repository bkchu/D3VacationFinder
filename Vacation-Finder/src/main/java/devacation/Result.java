package devacation;

/**
 * Created by tantan on 2/28/16.
 */
public abstract class Result implements Comparable<Result> {

    public abstract String getName();

    public abstract int ratingCount();
    public abstract double price();
    public abstract double getLatitude();
    public abstract double getLongitude();
    public abstract double rating();
    public abstract boolean isHotelOrRestaurant();
    public abstract boolean meetsPriceLevel(int level);
    public abstract String getWebsite();

    @Override
    public String toString() { return ""; }

    private UserCriteria criteria;
    public void setCriteria(UserCriteria criteria) {
        this.criteria = criteria;
        score = Integer.MAX_VALUE;
    }

    // score() will throw an exception if you try to access it if you haven't set a criteria
    private double score = Integer.MAX_VALUE;
    public double score() {
        if (this.score != Integer.MAX_VALUE) {
            return this.score;
        }

        this.score = 0;
        double max_score = 0;

        // Ratio of price to budget
        if (this.criteria.budget != null && this.price() > 0) {
        	double tmpBudget = this.criteria.budget * 100;
            this.score = this.price() / tmpBudget;
            max_score += 1;
        }

        // Ratio of rating using 5 point scale
        // only hotels and restaurants have ratings
        if (this.isHotelOrRestaurant()) {

            // get the ratio of the rating
            this.score += this.rating();
            max_score += 5;

            // For each 10 reviews, the rating is valued better
            if (this.ratingCount() > 0) {
            	max_score += 5;
                if (this.ratingCount() < 50) {
                    this.score += this.ratingCount() / 10;
                }
                else {
                	this.score += 5;
                }
            }
        }
        // Places closer to the original destination receive more points
    	if (criteria.shouldFilterByDistance()) {
			double milesInLongitudeDegree = 54.6;
			double milesInLatitudeDegree = 69;
			double miles = criteria.getMaxDistance()/1609;

			double critLatitude = criteria.getLatitude();
			double critLongitude = criteria.getLongitude();
			double resultLatitude = this.getLatitude();
			double resultLongitude = this.getLongitude();
			
			// Make the negative values positive for calculation purposes
			if (critLatitude < 0) {
				critLatitude *= -1;
			}

			if (critLongitude < 0) {
				critLongitude *= -1;
			}
			
			if (resultLatitude < 0) {
				resultLatitude *= -1;
			}

			if (resultLongitude < 0) {
				resultLongitude *= -1;
			}
			
			double latitudeDifference = Math.abs(critLatitude - resultLatitude);
			double longitudeDifference = Math.abs(critLongitude - resultLongitude);
			
			latitudeDifference = latitudeDifference * milesInLatitudeDegree;
			longitudeDifference = longitudeDifference * milesInLongitudeDegree;
			
			latitudeDifference = latitudeDifference / miles;
			latitudeDifference = 1 - latitudeDifference;
			
			longitudeDifference = longitudeDifference / miles;
			longitudeDifference = 1 - longitudeDifference;
			
			this.score += latitudeDifference;
			this.score += longitudeDifference;
			max_score += 2;
			
			
    	}
        
        
        if (max_score != 0) {
            this.score = this.score / max_score;
            this.score = this.score * 10;
        }
        else {
        	this.score = 0;
        }

        return this.score;
    }
    
    public int compareTo(Result r) {
    	return Double.valueOf(r.score()).compareTo(Double.valueOf(this.score()));
    }

    @Override
    public int hashCode() { return this.getName().hashCode(); }
    @Override
    public boolean equals(Object r) {
        if (r instanceof Result) {
            return this.getName().equals(((Result)r).getName());
        } else {
            return super.equals(r);
        }
    }
}
