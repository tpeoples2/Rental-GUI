// COP 3503 Fall 2011 Project 2

import java.util.Arrays;
import java.util.ArrayList;
import java.io.*;
import javax.swing.*;

public class RedBox {
    /*******************
     * PROPERTIES
     *******************/

    private Videogame[] videogames;
    private Movie[] movies;

    private ArrayList<Customer> customers;

    /*******************
     * CONSTRUCTOR(S)
     *******************/

    public RedBox(Videogame[] vg, Movie[] m) {
        this.videogames = vg;
        this.movies = m;
        customers = new ArrayList<Customer>();
    }
	
    /*******************
     * Customer add
     *******************/
    public void addCustomer(Customer customer) {
        this.customers.add(customer);
    }

    /*******************
     * SEARCHING METHODS
     *******************/
    public int findVideogameByTitle(String title) {
        for(int i = 0; i < videogames.length; i++) {
            if (videogames[i].getTitle().equals(title))
                return i;
        }

        return -1;
    }

    public int findMovieByTitle(String title) {
        for(int i = 0; i < movies.length; i++) {
            if (movies[i].getTitle().equals(title))
                return i;
        }

        return -1;
    }

    // Returns the videogame at a certain index
    public Videogame getVideogameByIndex(int index) {
        if (index < videogames.length && index >= 0)
            return videogames[index];
        else
            return null;
    }

    // Returns the movie at a certain index
    public Movie getMovieByIndex(int index) {
        if (index < movies.length && index >= 0)
            return movies[index];
        else
            return null;
    }

    /*******************
     * SORTING METHODS
     *******************/

    /*************
      For Videogames:
     *************/
    public void sortVideogamesByPopularity() {
        Arrays.sort(videogames, new Videogame());
    }

    public void sortVideogamesByTitle() {
        Arrays.sort(videogames);
    }

    public void sortVideogamesByRating() {
        Arrays.sort(videogames, new RatingComparison());
    }

    public void sortVideogamesByPlatform() {
        Arrays.sort(videogames, new VideogameComparison());
    }

    // TODO:
    public void sortVideogamesByReleaseDate() {
        Arrays.sort(videogames, new DateComparison());
    }

    /*************
      For Movies:
     *************/

    public void sortMoviesByPopularity() {
        Arrays.sort(movies, new Movie());
    }

    public void sortMoviesByTitle() {
        Arrays.sort(movies);
    }

    public void sortMoviesByRating() {
        Arrays.sort(movies, new RatingComparison());
    }

    public void sortMoviesByFormat() {
        Arrays.sort(movies, new MovieComparison());
    }

    // TODO:
    public void sortMoviesByReleaseDate() {
        Arrays.sort(movies, new DateComparison());
    }

    /*******************
     * Get/set Methods
     *******************/
    public Videogame[] getVideogames() {
        return videogames;
    }

    public void setVideogames(Videogame[] videogames) {
        this.videogames = videogames;
    }

    public Movie[] getMovies() {
        return movies;
    }

    public void setMovies(Movie[] movies) {
        this.movies = movies;
    }

    public ArrayList<Customer> getCustomers() {
        return customers;
    }

    public void setCustomers(ArrayList<Customer> customers) {
        this.customers = customers;
    }

    /*******************
     * Print methods
     *******************/
    public void printVideogames() {
        for (int i = 0; i < videogames.length; i++) {
            System.out.print((i + 1) + ". " + videogames[i]); //videogames[i].getTitle());
            if (i < videogames.length - 1)
                System.out.print("\n");
        }
    }

    public void printMovies() {
        for (int i = 0; i < movies.length; i++) {
            System.out.print((i + 1) + ". " + movies[i]); //.getTitle());
            if (i < movies.length - 1)
                System.out.print("\n");
        }
    }

}
