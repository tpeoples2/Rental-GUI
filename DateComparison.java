
import java.util.Comparator;

// does comparisons based on average rating

public class DateComparison implements Comparator<Rental> {
	
	// comparisons based on release dates
	public int compare(Rental r1, Rental r2) {
		return r1.getReleaseDate().compareTo(r2.getReleaseDate());
	}

}
