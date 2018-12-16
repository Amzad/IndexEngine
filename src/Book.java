/**
 * Book
 * This class is as an object to store information about books used in the database.
 * @author Amzad
 *
 */
public class Book implements Cloneable {
	protected String ISBN13 = null;    // ISBN10 number of the book
	protected String ISBN10 = null;    // ISBN13 number of the book
	protected String title = null;		   // Name of the book
	protected String author = null;    // Author of the book
	protected String year = null;	   // Year the book was published
	protected String publisher = null; // Publisher of the book
	protected String link = null;
	protected String pages = null;
	protected String type = null;
	protected String imgLink = null;
	protected double price = 0.0d;
	protected String location = "None";
	
	
	/**
	 * Default Constructor to create an empty book. Use setters to set values.
	 */
	public Book() {
		
	}
	/**
	 * One of the constructors that allows you to create a book object with the title of the book. 
	 * In the event the parameter is an ISBN10, it will be converted to ISBN13.
	 * @param newISBN13 ISBN13 number of the book
	 * 
	 */
	public Book(String newISBN13) {
		if (newISBN13.length() == 10) {
			newISBN13 = convertToISBN13(newISBN13);
		}
		setISBN13(newISBN13);
	}
	
	/** 
	 * The second constructor that allows you to create a book object with the title and ISBN13 of the book.
	 * @param name Title of the book
	 * @param ISBN13 ISBN13 number of the book
	 */
	public Book(String title, String ISBN13) {
		setTitle(title);
        setISBN13(ISBN13);
	}

	/**
	 * This method is used to convert ISBN10 number to ISBN13.
	 * @param ISBN10
	 * @return ISBN13 number
	 */
	public String convertToISBN13(String ISBN10) {
		String ISBN13 = ISBN10;
		ISBN13 = "978" + ISBN13.substring(0,9);
	    int checkDigit;
	    int sum = 0;
	    
	    // Following for loop verifies the checksum of the isbn13
	    for (int i = 0; i < ISBN13.length(); i++) {
	        if (i % 2 == 0) {
	        	checkDigit = 1;
	        }
	        else {
	        	checkDigit = 3;
	        }
	        sum += ((((int) ISBN13.charAt(i)) - 48) * checkDigit);
	    }
	    
	    sum = 10 - (sum % 10);
	    ISBN13 = ISBN13 + sum;
	    return ISBN13;
			
	}
	
	/**
	 * This method allows to you clone a Book object.
	 * @return new Book object
	 */
	public Book clone() throws CloneNotSupportedException {
		return (Book) super.clone();
	}
	
	/**
	 * Returns the ISBN13.
	 * @return ISBN13 number
	 */
	public String getISBN13() {
		return ISBN13;
	}
	
	/**
	 * Sets the ISBN13 of the Book
	 * @param newISBN13 The ISBN13 to be set.
	 * @return The new ISBN13 number.
	 */
	public String setISBN13(String newISBN13) {
		if (newISBN13.length() == 10) {
			newISBN13 = convertToISBN13(newISBN13);
		}
		ISBN13 = newISBN13;
		return ISBN13;
	}
	
	/**
	 * Returns the ISBN10.
	 * @return ISBN10 number
	 */
	public String getISBN10() {
		return ISBN10;
	}
	
	/**
	 * Sets the ISBN10 number
	 * @param newISBN10
	 * @return The new ISBN10 number
	 */
	public String setISBN10(String newISBN10) {
		ISBN10 = newISBN10;
		return ISBN10;
	}

	/**
	 * Returns the title
	 * @return title
	 */
	public String getTitle() {
		return title;
	}
	
	/**
	 * Sets the title 
	 * @param newTitle
	 * @return The new title
	 */
	public String setTitle(String newTitle) {
		title = newTitle;
		return title;
	}

	/**
	 * Returns the author
	 * @return author
	 */
	public String getAuthor() {
        return author;
    }
	
	/**
	 * Sets the author
	 * @param newAuthor
	 * @return The new author
	 */
    public String setAuthor(String newAuthor) {
        author = newAuthor;
        return author;
    }

	/**
	 * Returns year
	 * @return year
	 */
	public String getYear() {
		return year;
	}
	
	/**
	 * Sets the year
	 * @param newYear
	 * @return The new year
	 */
	public String setYear(String newYear) {
		year = newYear;
		return year;
	}

	/**
	 * Returns the publisher
	 * @return publisher
	 */
	public String getPublisher() {
		return publisher;
	}
	
	/**
	 * Sets the publisher
	 * @param newPublisher
	 * @return The new publisher
	 */
	public String setPublisher(String newPublisher) {
		publisher = newPublisher;
		return publisher;
	}

	/**
	 * Returns the link
	 * @return link
	 */
	public String getLink() {
		return link;
	}
	
	/**
	 * Sets the link
	 * @param newLink
	 * @return The new link
	 */
	public String setLink(String newLink) {
		link = newLink;
		return link;
	}

	/**
	 * Returns the pages
	 * @return pages
	 */
	public String getPages() {
		return pages;
	}
	
	/**
	 * Sets the pages
	 * @param newPages
	 * @return The new pages
	 */
	public String setPages(String newPages) {
		pages = newPages;
		return pages;
	}

	/**
	 * Returns the type
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * Sets the type
	 * @param newType
	 * @return The new type
	 */
	public String setType(String newType) {
		type = newType;
		return type;
	}

	/**
	 * Returns the price
	 * @return price
	 */
	public double getPrice() {
		return price;
	}
	
	/**
	 * Sets the price
	 * @param newPrice
	 * @return The new price
	 */
	public double setPrice(double newPrice) {
		price = newPrice;
		return price;
	}

	/**
	 * Returns the Image link
	 * @return Image link
	 */
	public String getImage() {
		return imgLink;
	}
	
	/**
	 * Sets the Image link
	 * @param link
	 * @return The new Image link
	 */
	public String setImage(String link) {
		imgLink = link;
		return imgLink;
	}

	/**
	 * Returns the location
	 * @return location
	 */
	public String getLocation() {
		return location;
	}
	
	/**
	 * Sets the location
	 * @param location
	 * @return The new location
	 */
	public String setLocation(String location) {
		this.location = location;
		return location;
	}
	
	

}
