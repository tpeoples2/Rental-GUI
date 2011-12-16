// Represents a Rental which is rentable. 
// Will have 2 subclasses: Videogame and Movie

import java.util.Comparator;
import java.awt.Graphics;
import javax.swing.ImageIcon;

public abstract class Rental implements Comparable<Rental>, Comparator<Rental> {
	/*******************
	 * PROPERTIES
	 *******************/
	protected double costPerDay;		// the cost per day for renting the title
	protected String title;			// the name of the Rental
	protected String genre;			// the genre of the movie/videogame
	protected double averageRating;	// the average Rating score given by customers (0.0 - 5.0)
	protected int numTimesChecked;	// the number of times the movie was checked out
	protected ImageIcon icon;
	
	protected boolean checkedOut;		// is the movie currently checked out or not?
	
	// add Date
	protected Date releaseDate;		// The date the Rental was released
	
	/*******************
	 * CONSTRUCTOR(S)
	 *******************/
	public Rental() {
	}
	
	public Rental(double cpd, String title, String genre, double averageRating, int numTimesChecked, Date releaseDate, ImageIcon icon) {
		this.costPerDay = cpd;
		this.title = title;
		this.genre = genre;
		this.averageRating = averageRating;
		this.numTimesChecked = numTimesChecked;
		this.releaseDate = releaseDate;
		this.icon = icon;
	}
	
	public Rental(double cpd, String title, String genre, Date releaseDate, ImageIcon icon) {
		this(cpd, title, genre, 0, 0, releaseDate, icon);
	}
	
	// Implementing Comparable:
	// Comparisons based on title
	public int compareTo(Rental Rental) {
		return title.compareTo(Rental.getTitle());
	}

	// Implementing Comparator
	// comparisons based on popularity (number of times item has been checked out)
        // Sort order: descending 
	public int compare(Rental r1, Rental r2) {
	        int pop1 = r1.getNumTimesChecked();
	        int pop2 = r2.getNumTimesChecked();
                if (pop1 > pop2)
                    return -1;
                else if (pop1 < pop2)
                    return 1;
                else
                    return 0;
	}
	
	// Checkout/Checkin methods
	
	public boolean isCheckedOut() {
		return this.checkedOut;
	}
	
	public void checkOut() {
		this.checkedOut = true;
		this.numTimesChecked++;
	}
	
	public double checkIn(int numDays, double rating) {
		this.checkedOut = false;
		
		// update the average rating TODO:
		this.averageRating = ( (averageRating * (this.numTimesChecked-1)) + rating )/numTimesChecked;
		
		return numDays * costPerDay;
	}
	
	/*******************
	 * Get/set Methods
	 *******************/
	
	public ImageIcon getIcon() {
		return icon;
	}
	public void setIcon(ImageIcon icon) {
		this.icon = icon;
	}
	
	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public double getCostPerDay() {
		return costPerDay;
	}

	public void setCostPerDay(double costPerDay) {
		this.costPerDay = costPerDay;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public int getNumTimesChecked() {
		return numTimesChecked;
	}

	public void setNumTimesChecked(int numTimesChecked) {
		this.numTimesChecked = numTimesChecked;
	}
	
	public void setCheckedOut() {
		this.checkedOut = true;
	}
	
	// toString() 
	public String toString() {
		return title + ",Genre: " + genre + ",Rating: " + this.averageRating + ",Times Checked Out: " + this.numTimesChecked
				+ ",Release Date: " + this.releaseDate + ",ImageIcon: " + this.icon;
	}
}
