
import java.util.Comparator;

// Class for comparisons based on platform

public class VideogameComparison implements Comparator<Videogame> {
	
	// comparisons based on platform
	public int compare(Videogame v1, Videogame v2) {
		return v1.getPlatform().compareTo(v2.getPlatform());
	}

}
