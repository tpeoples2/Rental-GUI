import java.util.Comparator;

// Class for comparisons based on format

public class MovieComparison implements Comparator<Movie> {
	
	// comparisons based on format
	public int compare(Movie m1, Movie m2) {
		return m1.getFormat().compareTo(m2.getFormat());
	}

}
