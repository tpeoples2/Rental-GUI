// Date class to be used in project 2 COP 3503
// Students have to implement the compareTo(..) method


public class Date implements Comparable<Date>, Cloneable {
	/*******************
	 * PROPERTIES
	 *******************/
	private int day, month, year;
	
	/*******************
	 * CONSTRUCTOR(S)
	 *******************/
	public Date() {
		// empty constructor
	}
	
	public Date(int month, int day, int year) {
		this.day = day;
		this.month = month;
		this.year = year;
	}
	
	// Implementing Comparable
	// Reverse order - if this date is smaller return 1, else -1 or 0
	public int compareTo(Date date) {
		// first do comparisons based on year
		if (this.year < date.getYear())
			return 1;
		else if (this.year > date.getYear())
			return -1;
		else {
			// Now compare based on months
			if (this.month < date.getMonth())
				return 1;
			else if (this.month > date.getMonth())
				return -1;
			else {
				// Now compare based on days
				if (this.day < date.getDay())
					return 1;
				else if (this.day > date.getDay())
					return -1;
				else 
					return 0; // equal!
			}
		}
	}

	// Implementing Cloneable
	// Should return a deep copy
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	/*******************
	 * Get/set Methods
	 *******************/
	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}
	
	/*******************
	 * toString
	 *******************/
	public String toString() {
		return month + "/" + day + "/" + year;
	}

}
