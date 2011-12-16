// Represents a Videogame 
// Subclass of Rental

import javax.swing.ImageIcon;

public class Videogame extends Rental implements Cloneable {
	/*******************
	 * PROPERTIES
	 *******************/
	private String platform; // "PS3"/"Xbox360"/"Wii"
	
	/*******************
	 * CONSTRUCTOR(S)
	 *******************/
	public Videogame() {
	}


	public Videogame(String title, String genre, double averageRating, int numTimesChecked, Date releaseDate, ImageIcon icon, String platform) {
		super(2.0, title, genre, averageRating, numTimesChecked, releaseDate, icon);
		setPlatform(platform);
	}
	
	public Videogame(String title, String genre, Date releaseDate, ImageIcon icon, String platform) {
		this(title, genre, 0, 0, releaseDate, icon, platform);
	}
	
	// Clone
	public Object clone() throws CloneNotSupportedException {
		Videogame clone = (Videogame)super.clone(); 		// get a shallow copy first
		clone.releaseDate = (Date)this.releaseDate.clone(); // clone the date to get a deep copy
		
		return clone;
	}
	
	/*******************
	 * Get/set Methods
	 *******************/
	public String getPlatform() {
		return platform;
	}
	
	public void setPlatform(String platform) {
		this.platform = platform;
	}
	
	// toString
	public String toString() {
		return super.toString() + ",Platform: " + this.platform;
	}
}
