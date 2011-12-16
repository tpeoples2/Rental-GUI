// Represents a Movie 
// Subclass of 

import javax.swing.ImageIcon;

public class Movie extends Rental implements Cloneable {
	/*******************
	 * PROPERTIES
	 *******************/
	private String format; // DVD/Blu-Ray
	
	/*******************
	 * CONSTRUCTOR(S)
	 *******************/
	public Movie() {
	}


	public Movie(String title, String genre, double averageRating, int numTimesChecked, Date releaseDate, ImageIcon icon, String format) {
		super(1.0, title, genre, averageRating, numTimesChecked, releaseDate, icon);
		setFormat(format);
	}
	
	public Movie(String title, String genre, Date releaseDate, ImageIcon icon, String format) {
		this(title, genre, 0, 0, releaseDate, icon, format);
	}
	
	// Clone
	public Object clone() throws CloneNotSupportedException {
		Movie clone = (Movie)super.clone(); 				// get a shallow copy first
		clone.releaseDate = (Date)this.releaseDate.clone(); // clone the date to get a deep copy
		
		return clone;
	}
	
	/*******************
	 * Get/set Methods
	 *******************/
	public String getFormat() {
		return format;
	}
	
	public void setFormat(String format) {
		this.format = format;
	}
	
	// toString
	public String toString() {
		return super.toString() + ",Format: " + this.format;
	}
}
