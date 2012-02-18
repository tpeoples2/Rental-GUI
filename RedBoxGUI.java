/** RedBoxGUI.java
 *
 *	RedBox Graphical User Interface
 *
 *	@author 	Taylor Peoples
 *	@version 	alpha-0.2
 *	@date		12/11/2011
 *
 */
 
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import javax.swing.border.*;

public class RedBoxGUI extends JFrame {
	/** PROPERTIES */
	// RedBox to display
	protected RedBox redbox;
	protected Movie[] movies;
	protected Videogame[] videogames;
	protected ArrayList<Customer> customers;
	protected Customer customer;
	protected ArrayList<Rental> searchRentals = new ArrayList<Rental>();
	
	// sorting options
	String[] sortMovieOptions = {"Popularity", "Title", "Rating", "Format", "Release Date"};
	String[] sortVideogameOptions = {"Popularity", "Title", "Rating", "Platform", "Release Date"};
	

	// JComboBox for choosing sorting options
	JComboBox jcboMovieOptions = new JComboBox(sortMovieOptions);
	JComboBox jcboVideogameOptions = new JComboBox(sortVideogameOptions);
	
	// arrayList to hold all JLabels
	protected ArrayList<JLabel> movieLabels = new ArrayList<JLabel>();
	protected ArrayList<JLabel> videogameLabels = new ArrayList<JLabel>();
	protected ArrayList<JLabel> historyLabels = new ArrayList<JLabel>();
	protected ArrayList<JLabel> searchLabels = new ArrayList<JLabel>();
	
	// menu bar
	JMenuBar menuBar;
	// file menu
	JMenu fileMenu;
	// menu items
	JMenuItem closeMenuItem, openMenuItem, saveMenuItem, exitMenuItem;
	// admin menu
	JMenu adminMenu;
	// menu items
	JMenuItem addRentalMenuItem;
	
	// account buttons
	JButton loginButton, logoutButton, createAccountButton;
	
	// searchTextField for Search Tab
	JTextField searchTextField;
	
	// CustomPanel
	CustomPanel movieRentalPanel;
	CustomPanel videogameRentalPanel;
	CustomPanel historyRentalPanel;
	CustomPanel searchRentalPanel;
	
	/** CONSTRUCTOR */
	public RedBoxGUI() {
		// create top menu
		createMenu();
		add(createHeaderPanel(), BorderLayout.NORTH);
		add(new Tabs(), BorderLayout.CENTER);
	}
	
	protected JComponent createHeaderPanel() {
		JPanel header = new JPanel();
		header.setLayout(new BorderLayout());
		
		// add empty JLabels to north & south for spacing
		header.add(new JLabel(" "), BorderLayout.NORTH);
		header.add(new JLabel(" "), BorderLayout.SOUTH);
		
		JPanel logoPanel = new JPanel(new FlowLayout());
		logoPanel.add(new JLabel(" "));
		logoPanel.add(new JLabel(" "));
		logoPanel.add(new JLabel(" "));
		logoPanel.add(new JLabel(" "));
		logoPanel.add(new JLabel(" "));
		logoPanel.add(new JLabel(new ImageIcon("images/redbox.png")), BorderLayout.CENTER);
				
		header.add(logoPanel, BorderLayout.WEST);
		
		JPanel accountPanel = new JPanel(new FlowLayout());
			
		// create accountbuttons listener
		AccountListener accountListener = new AccountListener();
		
		loginButton = new JButton("Login");
		loginButton.addActionListener(accountListener);
		accountPanel.add(loginButton);
			
		logoutButton = new JButton("Logout");
		logoutButton.addActionListener(accountListener);
		accountPanel.add(logoutButton);
		
		createAccountButton = new JButton("Create Account");
		createAccountButton.addActionListener(accountListener);
		accountPanel.add(createAccountButton);
			
		header.add(accountPanel, BorderLayout.CENTER);
		
		return header;
	}
	
	protected void createMenu() {
		// create a menu
		menuBar = new JMenuBar();
		// menus
		fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		adminMenu = new JMenu("Admin");
		menuBar.add(adminMenu);
				
		// create MenuListener
		MenuListener menuListener = new MenuListener();	
		
		// file menu items
		closeMenuItem = new JMenuItem("Close");	fileMenu.add(closeMenuItem);	closeMenuItem.addActionListener(menuListener);
		openMenuItem = new JMenuItem("Open");	fileMenu.add(openMenuItem);		openMenuItem.addActionListener(menuListener);
		saveMenuItem = new JMenuItem("Save");	fileMenu.add(saveMenuItem);		saveMenuItem.addActionListener(menuListener);
		exitMenuItem = new JMenuItem("Exit");	fileMenu.add(exitMenuItem);		exitMenuItem.addActionListener(menuListener);
		
		// admin menu items
		addRentalMenuItem = new JMenuItem("Add new rental");	adminMenu.add(addRentalMenuItem);	addRentalMenuItem.addActionListener(menuListener);
		
		// set the menu bar
		setJMenuBar(menuBar);
	}
	
	/** ACCOUNT LISTENER */
	class AccountListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == loginButton) {
				if (redbox == null) {
					JOptionPane.showMessageDialog(RedBoxGUI.this, "Please load a saved RedBox system before trying to log in.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
										
				JTextField nameField = new JTextField();
				JPasswordField pwField = new JPasswordField();
				JComponent[] inputs = new JComponent[]{new JLabel("Enter your name:"), nameField, new JLabel("Enter your password:"), pwField};
				String[] options = new String[]{"Login", "Cancel"};
				int input = JOptionPane.showOptionDialog(RedBoxGUI.this, inputs, "Logging in", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, nameField);
				
				if (input == 1 || input != 0) {	// cancelling
					return;
				}
					
				// try to find the customer
				for (int i = 0; i < redbox.getCustomers().size(); i++) {
					if (redbox.getCustomers().get(i).getName().equals(nameField.getText())) {
						customer = redbox.getCustomers().get(i);
						JOptionPane.showMessageDialog(RedBoxGUI.this, "You successfully logged in.", "Logged In", JOptionPane.PLAIN_MESSAGE);
						historyRentalPanel.fillPanel();
						return;
					}
				}
				
				// not found
				JOptionPane.showMessageDialog(RedBoxGUI.this, "Your name was not found in the RedBox database.", "Error", JOptionPane.ERROR_MESSAGE);
			}
			else if (e.getSource() == logoutButton) {
				if (customer != null) {
					customer = null;
					JOptionPane.showMessageDialog(RedBoxGUI.this, "You successfully logged out. Thanks for using RedBox today!", "Thank You", JOptionPane.PLAIN_MESSAGE);
					historyRentalPanel.fillPanel();
				}
				else {
					JOptionPane.showMessageDialog(RedBoxGUI.this, "You are not logged in!", "Not Logged In", JOptionPane.PLAIN_MESSAGE);
				}
					
			}
			else if (e.getSource() == createAccountButton) {
				if (redbox == null) {
					JOptionPane.showMessageDialog(RedBoxGUI.this, "Please load a saved RedBox system before trying to create an account.", "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
					
				JTextField nameField = new JTextField();
				JPasswordField pwField = new JPasswordField();
				JComponent[] inputs = new JComponent[]{new JLabel("Enter your name:"), nameField, new JLabel("Enter a password:"), pwField};
				String[] options = new String[]{"Create Account", "Cancel"};
				int input = JOptionPane.showOptionDialog(RedBoxGUI.this, inputs, "Creating account", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, nameField);
				
				if (input == 1 || input != 0) {	// user exited or cancelled
					return;
				}
					
				// create and add the customer
				Customer customer1 = new Customer(nameField.getText());
				redbox.addCustomer(customer1);
				customer = customer1;
				JOptionPane.showMessageDialog(RedBoxGUI.this, "You successfully created an account and are now logged in.", "Successfully created account", JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
		
	/** MENU LISTENER */
	class MenuListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == closeMenuItem) {
				if (redbox != null)
					redbox = null;
				if (customer != null)
					customer = null;
				if (searchRentals.size() != 0) {
					searchRentals.clear();
				}
					
				movieRentalPanel.fillPanel();
				videogameRentalPanel.fillPanel();
				historyRentalPanel.fillPanel();
				searchRentalPanel.fillPanel();
			}
			else if (e.getSource() == openMenuItem) {
				loadFile();
			}
			else if (e.getSource() == saveMenuItem) {
				writeToFile();
			}
			else if (e.getSource() == exitMenuItem) {
				int selectedValue = JOptionPane.showConfirmDialog(RedBoxGUI.this, "Exit without saving?", "Exit without saving?", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
			
				if (selectedValue == JOptionPane.YES_OPTION) {
					WindowEvent wev = new WindowEvent(RedBoxGUI.this, WindowEvent.WINDOW_CLOSING);
					Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
				}
				else if (selectedValue == JOptionPane.NO_OPTION) {
					writeToFile();
					WindowEvent wev = new WindowEvent(RedBoxGUI.this, WindowEvent.WINDOW_CLOSING);
					Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(wev);
				}
				else if (selectedValue == JOptionPane.CANCEL_OPTION) {
					return;
				}
			}
			else if (e.getSource() == addRentalMenuItem) {
				System.out.println("Adding a rental");
			}
		}
	}
	
	/** LOADFILE */
	public void loadFile() {
		FileDialog fd = new FileDialog(this, "Open a RedBox", FileDialog.LOAD);
		fd.setLocation(100, 100);
		fd.setVisible(true);
		String fileDirectory = fd.getDirectory();
		String fileName = fd.getFile();
		
		if (fileName == null)
				return;
		
		Scanner in1 = null;
		try {
			File file = new File(fileDirectory, fileName);
			in1 = new Scanner(file);
			
			// read in movies
			in1.next();	in1.next(); in1.next();
			int numberOfMovies = in1.nextInt();
			movies = new Movie[numberOfMovies];	
			
			in1.nextLine();
			for (int i = 0; i < movies.length; i++) {
				String line = in1.nextLine();
				movies[i] = (Movie) readInRental(line);
			}
			
			// read in videogames
			in1.next();	in1.next(); in1.next();
			int numberOfVideogames = in1.nextInt();
			videogames = new Videogame[numberOfVideogames];
			in1.nextLine();
			for (int i = 0; i < videogames.length; i++) {
				videogames[i] = (Videogame) readInRental(in1.nextLine());
			}
			
			redbox = new RedBox(videogames, movies);
			
			// read in customers
			in1.next();	in1.next(); in1.next();
			int numberOfCustomers = in1.nextInt();
			in1.nextLine();
			customers = new ArrayList<Customer>(numberOfCustomers);
			for (int i = 0; i < numberOfCustomers; i++) {
				customers.add(readInCustomer(in1));
			}
			
			redbox.setVideogames(videogames);
			redbox.setMovies(movies);
			redbox.setCustomers(customers);		
	
			redbox.sortMoviesByPopularity();
			redbox.sortVideogamesByPopularity();
			
			movieRentalPanel.fillPanel();
			videogameRentalPanel.fillPanel();
			historyRentalPanel.fillPanel();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error!", "Error while reading in file!", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (in1 != null)
				in1.close();
		}
	}
	public Rental readInRental(String line) {
		Scanner scanner = new Scanner(line);
		scanner.useDelimiter("[,]");
		Rental rental;	// rental to return
		

		String title = scanner.next();
		Scanner in2 = new Scanner(scanner.next());
		in2.next(); // skip genre
		String genre = in2.next();
		in2 = new Scanner(scanner.next());
		in2.next(); // skip rating
		double averageRating = in2.nextDouble();
		in2 = new Scanner(scanner.next());
		in2.next();	in2.next(); in2.next();
		int numTimesChecked = in2.nextInt();
		in2 = new Scanner(scanner.next());
		in2.next();	in2.next();
		
		String date = in2.next();
		Scanner in3 = new Scanner(date);
		in3.useDelimiter("[/]");
		int month = in3.nextInt();
		int day = in3.nextInt();
		int year = in3.nextInt();
		Date releaseDate = new Date(month, day, year);
		
		in2 = new Scanner(scanner.next());
		in2.next();
		String iconLocation = in2.next();
		ImageIcon icon = new ImageIcon(iconLocation);		

		in2 = new Scanner(scanner.next());
		String typeOfRental = in2.next();
		if (typeOfRental.equals("Format:")) {
			String format = in2.next();
			rental = new Movie(title, genre, averageRating, numTimesChecked, releaseDate, icon, format);
		}
		else {
			String platform = in2.next();
			rental = new Videogame(title, genre, averageRating, numTimesChecked, releaseDate, icon, platform);
		}
		
		return rental;
	}
	public Customer readInCustomer(Scanner in4) {
		Customer customer;	// customer to return
		
		in4.next();	in4.next();
		String name = in4.nextLine().trim();
		
		in4.next();	in4.next();
		double totalCharge = in4.nextDouble();
		
		in4.next();	in4.next(); in4.next();	in4.next(); in4.next();
		int checkedOutSize = in4.nextInt();
		ArrayList<Rental> checkedOut = new ArrayList<Rental>(checkedOutSize);
		in4.next();	in4.next(); in4.next();	in4.next(); in4.next();
		int historySize = in4.nextInt();
		
		// read in currently checked out rentals
		in4.next();
		for (int i = 0; i < checkedOutSize; i++) {
			String typeOfRental = in4.next();
			String title = in4.nextLine().trim();
			
			if (typeOfRental.equals("Movie:")) {
				int index = redbox.findMovieByTitle(title);
				movies[index].setCheckedOut();
				checkedOut.add(movies[index]);
			}
			else {	// "Videogame:"
				int index = redbox.findVideogameByTitle(title);
				videogames[index].setCheckedOut();
				checkedOut.add(videogames[index]);
			}
			
		}
		
		// read in previously checked out rentals
		in4.next();
		ArrayList<Rental> history = new ArrayList<Rental>(historySize);
		for (int i = 0; i < historySize; i++) {
			String typeOfRental = in4.next();
			String title = in4.nextLine().trim();
			
			if (typeOfRental.equals("Movie:")) {
				int index = redbox.findMovieByTitle(title);
				history.add(movies[index]);
			}
			else {	// "Videogame:"
				int index = redbox.findVideogameByTitle(title);
				history.add(videogames[index]);
			}
		}
		
		customer = new Customer(name);
		customer.setCheckedOut(checkedOut);
		customer.setCheckOutHistory(history);
		customer.setTotalCharge(totalCharge);
		
		return customer;
	}	
	
	/** WRITETOFILE */
	public void writeToFile() {
		if (redbox == null) {
			JOptionPane.showMessageDialog(this, "The RedBox is null, no point in saving!", "Information", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		FileDialog fd = new FileDialog(this, "Save the RedBox", FileDialog.SAVE);
		fd.setLocation(100, 100);
		fd.setVisible(true);
		String fileName = fd.getFile();
		
		if (fileName == null)
				return;
		
		PrintWriter pw = null;
		try {			
			File file = new File(fileName);
			pw = new PrintWriter(file);
			
			pw.println("Number of Movies: " + movies.length);
			for (int i = 0; i < movies.length; i++)
				pw.println(movies[i].toString());
			
			pw.println("Number of Videogames: " + videogames.length);
			for (int i = 0; i < videogames.length; i++)
				pw.println(videogames[i].toString());
			
			pw.println("Number of Customers: " + customers.size());
			for (int i = 0; i < customers.size(); i++) {
				Customer customer = customers.get(i);
				pw.println(customer);
				pw.println("CURRENT");
				for (int j = 0; j < customer.getCheckedOut().size(); j++) {
					if (customer.getCheckedOut().get(j) instanceof Movie)
						pw.print("Movie: ");
					else
						pw.print("Videogame: ");
					
					pw.println(customer.getCheckedOut().get(j).getTitle());
				}
				pw.println("PREVIOUS");
				for (int j = 0; j < customer.getCheckOutHistory().size(); j++) {
					if (customer.getCheckOutHistory().get(j) instanceof Movie)
						pw.print("Movie: ");
					else
						pw.print("Videogame: ");
					
					pw.println(customer.getCheckOutHistory().get(j).getTitle());
				}
			}
			
			pw.flush();
		} catch (IOException ioe) {
			ioe.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error!", "Error while writing to file!", JOptionPane.ERROR_MESSAGE);
		} finally {
			if (pw != null)
				pw.close();
		}
	}
	
	/** TABS CLASS */
	class Tabs extends JPanel {	
		public Tabs() {
			super(new GridLayout(1, 1));
			
			JTabbedPane tabbedPane = new JTabbedPane();
			JComponent moviePanel = makeMainPanel("Movies");
			tabbedPane.addTab("Movies", moviePanel);
			JComponent videogamesPanel = makeMainPanel("Games");
			tabbedPane.addTab("Games", videogamesPanel);
			JComponent historyPanel = makeMainPanel("My History");
			tabbedPane.addTab("My History", historyPanel);
			JComponent searchPanel = makeMainPanel("Search");
			tabbedPane.addTab("Search", searchPanel);
			
			add(tabbedPane);
			movieRentalPanel.fillPanel();
			videogameRentalPanel.fillPanel();
			historyRentalPanel.fillPanel();
		}
		
		protected JComponent makeMainPanel(String title) {
			JPanel mainPanel = new JPanel();
			mainPanel.setLayout(new BorderLayout());
			
			mainPanel.add(makeTitlePanel(title), BorderLayout.NORTH);
			
			CustomPanel rentalPanel = (CustomPanel) makeRentalPanel(title);
			JScrollPane scrollPane = new JScrollPane(rentalPanel);
			scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			mainPanel.add(scrollPane);
			
			return mainPanel;
		}
		
		protected JComponent makeTitlePanel(String title) {
			JPanel titlePanel = new JPanel();
			titlePanel.setLayout(new FlowLayout());
			titlePanel.setBackground(Color.RED.darker());
			
			// add north and south empty components for spacing reasons
			titlePanel.add(new JLabel("   "), BorderLayout.NORTH);
			titlePanel.add(new JLabel("   "), BorderLayout.SOUTH);
			
			// create TitleBarListener
			TitleBarListener titleBarListener = new TitleBarListener();
			jcboMovieOptions.addActionListener(titleBarListener);
			jcboMovieOptions.setBackground(Color.RED.darker());
			jcboVideogameOptions.addActionListener(titleBarListener);
			jcboVideogameOptions.setBackground(Color.RED.darker());
			
			JPanel spacingPanel = new JPanel(new FlowLayout());
			spacingPanel.setBackground(Color.RED.darker());
			JLabel titleLabel = new JLabel(title);
			Font titleFont = titleLabel.getFont();
			titleLabel.setFont(new Font(titleFont.getFontName(), Font.PLAIN, 30));
			spacingPanel.add(new JLabel(" "));
			spacingPanel.add(titleLabel, BorderLayout.EAST);
			titlePanel.add(spacingPanel);
			
			JPanel sortPanel = new JPanel(new FlowLayout());
			sortPanel.setBackground(Color.RED.darker());
			JLabel sortLabel = new JLabel("Sort by:");
			Font sortFont = sortLabel.getFont();
			sortLabel.setFont(new Font(sortFont.getFontName(), Font.ITALIC, 12));
									
			if (title.equals("Movies")) {
				//sortPanel.add(sortLabel);
				sortPanel.add(jcboMovieOptions);
			}
			else if (title.equals("Games")) {
				//sortPanel.add(sortLabel);
				sortPanel.add(jcboVideogameOptions);
			}
			else if (title.equals("My History")) {
				// do nothing
			}
			else if (title.equals("Search")) {
				searchTextField = new JTextField("Search...", 20);
				searchTextField.addActionListener(titleBarListener);
				sortPanel.add(searchTextField);
			}
			
			titlePanel.add(sortPanel);
			
			Border lineBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
			titlePanel.setBorder(lineBorder);
			return titlePanel;
		}
		
		/** Title Bar LISTENER */
		class TitleBarListener implements ActionListener {
			public void actionPerformed(ActionEvent e) {		
				if (e.getSource() == jcboMovieOptions) {
					if (redbox == null) {
						return;
					}
					
					String str = (String) jcboMovieOptions.getSelectedItem();
					// sort the rentalPanel based on str
					if (str.equals("Popularity"))
						redbox.sortMoviesByPopularity();
					else if (str.equals("Title"))
						redbox.sortMoviesByTitle();
					else if (str.equals("Rating"))
						redbox.sortMoviesByRating();
					else if (str.equals("Format"))
						redbox.sortMoviesByFormat();
					else if (str.equals("Release Date"))
						redbox.sortMoviesByReleaseDate();
					
					// refill the panel
					movieRentalPanel.fillPanel();
				}
				else if (e.getSource() == jcboVideogameOptions) {
					if (redbox == null) {
						return;
					}
					
					String str = (String) jcboVideogameOptions.getSelectedItem();
					// sort the rentalPanel based on str
					if (str.equals("Popularity"))
						redbox.sortVideogamesByPopularity();
					else if (str.equals("Title"))
						redbox.sortVideogamesByTitle();
					else if (str.equals("Rating"))
						redbox.sortVideogamesByRating();
					else if (str.equals("Platform"))
						redbox.sortVideogamesByPlatform();
					else if (str.equals("Release Date"))
						redbox.sortVideogamesByReleaseDate();
					
					// refill the panel
					videogameRentalPanel.fillPanel();
				}
				else if (e.getSource() == searchTextField) {
					if (redbox == null) {
						return;
					}
					
					String userSearch = searchTextField.getText().trim();
					
					searchRentals.clear();
					
					for (int i = 0; i < redbox.getMovies().length; i++) {
						if (redbox.getMovies()[i].getTitle().toLowerCase().contains(userSearch.toLowerCase())) {
							searchRentals.add(redbox.getMovies()[i]);
						}
					}
					
					for (int i = 0; i < redbox.getVideogames().length; i++) {
						if (redbox.getVideogames()[i].getTitle().toLowerCase().contains(userSearch.toLowerCase())) {
							searchRentals.add(redbox.getVideogames()[i]);
						}
					}
					
					searchRentalPanel.fillPanel();
				}
			}
		}
		
		protected JComponent makeRentalPanel(String title) {		
			if (title.equals("Movies")) {
				movieRentalPanel = new CustomPanel(title);
				return movieRentalPanel;
			}
			else if (title.equals("Games")) {
				videogameRentalPanel = new CustomPanel(title);
				return videogameRentalPanel;
			}
			else if (title.equals("My History")) {
				historyRentalPanel = new CustomPanel(title);
				return historyRentalPanel;
			}
			else if (title.equals("Search")) {
				searchRentalPanel = new CustomPanel(title);
				return searchRentalPanel;
			}
			
			return null;
		}
	}
	
	/** CUSTOMPANEL CLASS */
	class CustomPanel extends JPanel {
		String title = "";
		Border raisedBevel = BorderFactory.createRaisedBevelBorder();
			
		public CustomPanel(String title) {
			//setBackground(Color.WHITE);
			//setLayout(new GridLayout(2, 3, 50, 8));
			setLayout(new GridLayout(0, 4, 0, 0));
			this.fillPanel();
			this.title = title;
		}
		
		public void fillPanel() {
			boolean emptyRedbox = false;
			if (redbox == null)
				emptyRedbox = true;;
			
			this.removeAll();
			
			// RentalListener
			RentalListener rentalListener = new RentalListener();
			
			if (this.title.equals("Movies")) {
				if (emptyRedbox) {
					this.add(new JLabel(""));
					this.add(new JLabel("There are no movies to display."));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.revalidate();
					return;
				}
				
				createMovieLabels();
				
				for (int i = 0; i < movieLabels.size(); i++) {
					movieLabels.get(i).addMouseListener(rentalListener);
					this.add(movieLabels.get(i));
				}
			}
			else if (this.title.equals("Games")) {
				if (emptyRedbox) {
					this.add(new JLabel(""));
					this.add(new JLabel("There are no videogames to display."));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.revalidate();
					return;
				}
				
				createVideogameLabels();
				
				for (int i = 0; i < videogameLabels.size(); i++) {
					videogameLabels.get(i).addMouseListener(rentalListener);
					this.add(videogameLabels.get(i));
				}
			}
			else if (this.title.equals("My History")) {
				if (customer == null) {
					this.add(new JLabel(""));
					this.add(new JLabel("You must log in first to view your history."));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.revalidate();
					return;
				}
				
				createHistoryLabels();
				
				for (int i = 0; i < historyLabels.size(); i++) {
					historyLabels.get(i).addMouseListener(rentalListener);
					this.add(historyLabels.get(i));
				}
				// spacing reasons
				this.add(new JLabel(""));
				this.add(new JLabel(""));
				this.add(new JLabel(""));
				this.add(new JLabel(""));
				this.add(new JLabel(""));
			}
			else if (this.title.equals("Search")) {
				if (searchRentals.size() == 0) {
					this.add(new JLabel(""));
					this.add(new JLabel("No results were found. Try a different query."));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.add(new JLabel(""));
					this.revalidate();
					return;
				}
				
				createSearchLabels();
				
				for (int i = 0; i < searchLabels.size(); i++) {
					searchLabels.get(i).addMouseListener(rentalListener);
					this.add(searchLabels.get(i));
				}
				// spacing reasons
				this.add(new JLabel(""));
				this.add(new JLabel(""));
				this.add(new JLabel(""));
				this.add(new JLabel(""));
				this.add(new JLabel(""));
			}
			
			
			this.revalidate();
		}
		
		public void createMovieLabels() {
			movieLabels.clear();
			
			if (redbox == null)
				return;
			for (int i = 0; i < redbox.getMovies().length; i++) {
				JLabel jlbl = new JLabel(redbox.getMovies()[i].getFormat(), redbox.getMovies()[i].getIcon(), JLabel.CENTER);
				jlbl.setHorizontalTextPosition(JLabel.CENTER);
				jlbl.setVerticalTextPosition(JLabel.BOTTOM);
				jlbl.setBorder(raisedBevel);
				movieLabels.add(jlbl);
			}
		}
		public void createVideogameLabels() {
			videogameLabels.clear();
			
			if (redbox == null)
				return;
			for (int i = 0; i < redbox.getVideogames().length; i++) {
				JLabel jlbl = new JLabel(redbox.getVideogames()[i].getPlatform(), redbox.getVideogames()[i].getIcon(), JLabel.CENTER);
				jlbl.setHorizontalTextPosition(JLabel.CENTER);
				jlbl.setVerticalTextPosition(JLabel.BOTTOM);
				jlbl.setBorder(raisedBevel);
				videogameLabels.add(jlbl);
			}
		}
		public void createHistoryLabels() {
			historyLabels.clear();
		
			if (customer == null)
				return;
			for (int i = 0; i < customer.getCheckOutHistory().size(); i++) {
				JLabel jlbl = new JLabel(customer.getCheckOutHistory().get(i).getTitle(), customer.getCheckOutHistory().get(i).getIcon(), JLabel.CENTER);
				jlbl.setHorizontalTextPosition(JLabel.CENTER);
				jlbl.setVerticalTextPosition(JLabel.BOTTOM);
				jlbl.setBorder(raisedBevel);
				historyLabels.add(jlbl);
			}
		}
		public void createSearchLabels() {
			searchLabels.clear();
			
			if (redbox == null)
				return;
			for (int i = 0; i < searchRentals.size(); i++) {
				JLabel jlbl = new JLabel(searchRentals.get(i).getTitle().substring(0, searchRentals.get(i).getTitle().length() / 2), searchRentals.get(i).getIcon(), JLabel.CENTER);
				jlbl.setHorizontalTextPosition(JLabel.CENTER);
				jlbl.setVerticalTextPosition(JLabel.BOTTOM);
				jlbl.setBorder(raisedBevel);
				searchLabels.add(jlbl);
			}
		}
		
		/** RENTAL LISTENER */
		class RentalListener implements MouseListener {
			private Object[] options = new Object[]{"Check In", "Check Out", "Cancel"};
			private Object[] checkInOptions = new Object[]{"Check In","Cancel"};
			public void mouseClicked(MouseEvent e) {
				JLabel jlbl = (JLabel) e.getSource();
				String type = "";
				int index = 0;
				
				for (int i = 0; i < movieLabels.size(); i++) {
					if (jlbl == movieLabels.get(i)) {
						index = i;
						type = "Movies";
					}
				}
				for (int i = 0; i < videogameLabels.size(); i++) {
					if (jlbl == videogameLabels.get(i)) {
						index = i;
						type = "Games";
					}
				}
				for (int i = 0; i < historyLabels.size(); i++) {
					if (jlbl == historyLabels.get(i)) {
						index = i;
						type = "History";
					}
				}
				for (int i = 0; i < searchLabels.size(); i++) {
					if (jlbl == searchLabels.get(i)) {
						index = i;
						type = "Search";
					}
				}
				
				Rental rental;
				int selection = 2;
				
				if (type.equals("Movies")) {
					rental = redbox.getMovies()[index];
				}
				else if (type.equals("Games")) {
					rental = redbox.getVideogames()[index];
				}
				else if (type.equals("History")) {
					rental = customer.getCheckOutHistory().get(index);
				}
				else if (type.equals("Search")) {
					rental = searchRentals.get(index);
				}
				else {
					return;
				}
				
				JPanel rentalInfo = new JPanel();
				rentalInfo.setLayout(new BorderLayout(13, 0));
				rentalInfo.add(new JLabel(rental.getIcon()), BorderLayout.CENTER);
				JPanel textInfo = new JPanel();
				textInfo.setLayout(new BoxLayout(textInfo, BoxLayout.Y_AXIS));
				textInfo.add(new JLabel("Title: " + rental.getTitle()));
				textInfo.add(new JLabel("Genre: " + rental.getGenre()));
				textInfo.add(new JLabel("Rating: " + rental.getAverageRating()));
				textInfo.add(new JLabel("Release Date: " + rental.getReleaseDate()));
				textInfo.add(new JLabel("Cost Per Day: " + rental.getCostPerDay()));
				rentalInfo.add(textInfo, BorderLayout.EAST);
				
				if (type.equals("History")) {
					JOptionPane.showMessageDialog(RedBoxGUI.this, rentalInfo, rental.getTitle(), JOptionPane.PLAIN_MESSAGE);
					return;
				}
				else {
					selection = JOptionPane.showOptionDialog(RedBoxGUI.this, rentalInfo, rental.getTitle(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
				}
				
				if (selection == 0) {	// trying to check in
					if (customer == null) {
						JOptionPane.showMessageDialog(RedBoxGUI.this, "You must be logged in to do that.", "Error!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					for (int i = 0; i < customer.getCheckedOut().size(); i++) {
						if (customer.getCheckedOut().get(i) == rental) {
							// able to check in, get numOfDays and rating from user
							JTextField numOfDays = new JTextField();
							JTextField rating = new JTextField();
							JComponent[] inputs = new JComponent[]{new JLabel("Enter the number of days you've had the rental:"), numOfDays, new JLabel("Rate the rental: (0-5)"), rating};
							int input = JOptionPane.showOptionDialog(RedBoxGUI.this, inputs, "Checking in " + rental.getTitle(), JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null, checkInOptions, numOfDays);
							
							if (input == 0) { // trying to check in
								double oldTotalCharge = customer.getTotalCharge();
								customer.checkIn(rental, Integer.parseInt(numOfDays.getText()), Double.parseDouble(rating.getText()));
								historyRentalPanel.fillPanel();
								double newTotalCharge = customer.getTotalCharge();
								double amountOwedNow = newTotalCharge - oldTotalCharge;
								
								// display amount owed to user
								JPanel amountOwedPanel = new JPanel();
								amountOwedPanel.setLayout(new BorderLayout(0, 8));
								amountOwedPanel.add(new JLabel("You successfully checked in " + rental.getTitle() + "."), BorderLayout.NORTH);
								amountOwedPanel.add(new JLabel("You owe $" + amountOwedNow + "."), BorderLayout.CENTER);
								amountOwedPanel.add(new JLabel("Thanks for using RedBox today."), BorderLayout.SOUTH);
								JOptionPane.showMessageDialog(RedBoxGUI.this, amountOwedPanel, "Thanks for using RedBox.", JOptionPane.PLAIN_MESSAGE);
								
								return;
							}
							else if (input == 1) {	// cancelled the check in
								return;
							}
						}
					}
					
					JOptionPane.showMessageDialog(RedBoxGUI.this, "You don't have " + rental.getTitle() + " checked out.", "Error!", JOptionPane.ERROR_MESSAGE);
				}
				else if (selection == 1) {	// trying to check out
					if (customer == null) {
						JOptionPane.showMessageDialog(RedBoxGUI.this, "You must be logged in to do that.", "Error!", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					if (rental.isCheckedOut()) {
						JOptionPane.showMessageDialog(RedBoxGUI.this, rental.getTitle() + " is currently checked out.", "Already Checked Out!", JOptionPane.PLAIN_MESSAGE);
						return;
					}
					
					customer.checkOut(rental);
					JOptionPane.showMessageDialog(RedBoxGUI.this, "You have successfully checked out " + rental.getTitle() + ".", "Successfully Checked Out!", JOptionPane.PLAIN_MESSAGE);
				}
				else if (selection == 2) {	// cancel option
					return;
				}
			}
			public void mousePressed(MouseEvent e) {
				// ...
			}
			public void mouseReleased(MouseEvent e) {
				// ...
			}
			public void mouseEntered(MouseEvent e) {
				// ...
			}
			public void mouseExited(MouseEvent e) {
				// ...
			}
			
		}
	}
	
	/** MAIN METHOD */
	public static void main(String args[]) {
		try {
			UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
		} catch (Exception e) {
			System.out.println("Unable to load Windows look and feel");
		}
		
		RedBoxGUI frame = new RedBoxGUI();
		frame.setTitle("RedBox");
		frame.setSize(600, 596);
		frame.setMaximumSize(new Dimension(400, 300));
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}
}
