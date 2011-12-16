import java.util.ArrayList;

// Represents a Customer


public class Customer {
    /*******************
     * PROPERTIES
     *******************/
    private String name;			// the name 
    private ArrayList<Rental> checkedOut;	// list of Rentals currently checked out
    private ArrayList<Rental> checkOutHistory;	// list of Rentals previously checked out 

    private double totalCharge;		        // the total amount the customer has been charged throughout

    /*******************
     * CONSTRUCTOR(S)
     *******************/
    public Customer(String name) {
        this.name = name;
        this.totalCharge = 0;

        // initalize the ArrayLists
        checkedOut = new ArrayList<Rental>();
        checkOutHistory = new ArrayList<Rental>();
    }

    // Checkout/CheckIn methods
    public boolean checkOut(Rental rental) {
        // see if it's available first
        if (rental.isCheckedOut())
            return false;

        // check out the Rental
        rental.checkOut();

        // add it to the list of Rentals checkedout
        checkedOut.add(rental);

        return true;
    }

    // check in
    public boolean checkIn(Rental rental, int numDays, double rating) {
        // first see if Rental is checked out
        if (checkedOut.contains(rental)) {
            // check it in (with number of days and rating), and then remove it from the customer's checked out list
            double cost = rental.checkIn(numDays, rating);

            // increment the total charge
            totalCharge += cost;

            checkedOut.remove(rental);

            // a list of all the Rentals checked out
            checkOutHistory.add(rental);

            return true;
        }
        else
            return false;
    }

    /*******************
     * Get/set Methods
     *******************/
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Rental> getCheckedOut() {
        return checkedOut;
    }

    public void setCheckedOut(ArrayList<Rental> checkedOut) {
        this.checkedOut = checkedOut;
    }

    public ArrayList<Rental> getCheckOutHistory() {
        return checkOutHistory;
    }

    public void setCheckOutHistory(ArrayList<Rental> checkOutHistory) {
        this.checkOutHistory = checkOutHistory;
    }

    public double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(double totalCharge) {
        this.totalCharge = totalCharge;
    }


    // toString()
    public String toString() {
        String str = "Customer Name: " + this.name;
        str += "\nTotal Charge: " + this.totalCharge;
        str += "\nTotal Rentals Currently Checked Out: " + checkedOut.size();
        //for (int i = 0; i < checkedOut.size(); i++)
        //    str += "\n" + checkedOut.get(i).getTitle();

        str += "\nTotal Rentals Previously Checked Out: " + checkOutHistory.size();
        //for (int i = 0; i < checkOutHistory.size(); i++)
        //    str += "\n" + checkOutHistory.get(i).getTitle();

        return str;
    }

}
