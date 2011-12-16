
import java.util.Comparator;

// does comparisons based on average rating

public class RatingComparison implements Comparator<Rental> {
	
	// comparisons based on average rating
	public int compare(Rental r1, Rental r2) {
		double rating1 = r1.getAverageRating();
		double rating2 = r2.getAverageRating();
		
		// Sorting based on Descending order (so 1 = -1 and so on)
		if (rating1 == rating2)
			return 0;
		else if (rating1 > rating2)
			return -1;
		else
			return 1;
		
	}

}
