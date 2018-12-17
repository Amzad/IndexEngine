import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
/**
 * This class is responsible for all web related processing based on a URL.
 * @author Amzad
 *
 */
public class URLProcessor {
	
	/**
	 * Default constructor
	 */
	public URLProcessor() {
	}

	/**
	 * This method creates a URL object with the search term. 
	 * @param searchTerm Search Term.
	 * @return URL
	 */
	private URL generateSearchURL(String searchTerm) {
		String defaultURL = "https://www.bookdepository.com/search?searchTerm=";
		String[] term = searchTerm.split(" ");
		URL url = null;
;
		for (int i = 0; i < term.length; i++) {
			defaultURL = defaultURL + term[i];
			if(!(i == term.length-1)) {
				defaultURL = defaultURL + "+";
			}
		}
		//defaultURL = defaultURL + "&search=Find+book";
		try {
			url = new URL(defaultURL);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * This method creates a URL object with the search term.
	 * @param searchTerm Search Term
	 * @param page Which page to go to.
	 * @return URL Object
	 */
	private URL generateSearchURL(String searchTerm, int page) {
		String defaultURL = "https://www.bookdepository.com/search?searchTerm=";
		String[] term = searchTerm.split(" ");
		URL url = null;
;
		for (int i = 0; i < term.length; i++) {
			defaultURL = defaultURL + term[i];
			if(!(i == term.length-1)) {
				defaultURL = defaultURL + "+";
			}
		}
		defaultURL = defaultURL + "&page=" + page;
		try {
			url = new URL(defaultURL);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * This method creates a URL object with the search term. 
	 * @param searchTerm Search Term.
	 * @return URL
	 */
	private URL generateAdvancedSearchURL(String searchTerm) {
		String defaultURL = "https://www.bookdepository.com/search?";
		URL url = null;

		defaultURL = defaultURL + searchTerm;
		System.out.println(defaultURL);
		try {
			url = new URL(defaultURL);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * This method creates a URL object with the search term.
	 * @param searchTerm Search Term
	 * @param page Which page to go to.
	 * @return URL Object
	 */
	private URL generateAdvancedSearchURL(String searchTerm, int page) {
		String defaultURL = "https://www.bookdepository.com/search?";
		URL url = null;

		defaultURL = defaultURL + searchTerm + "&page=" + page;
		try {
			url = new URL(defaultURL);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * Generates a url for a specific Book.
	 * @param uri Book link
	 * @return URL Object
	 */
	private URL generateBookURL(String uri) {
		URL url = null;

		try {
			url = new URL(uri);
		} catch (MalformedURLException e) {
			
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	 * This method finds the results for the search team. 
	 * @param searchTerm Term
	 * @return ArrayList containing the results.
	 */
	public ArrayList<Book> findBook(String searchTerm) {
		
		ArrayList<Book> bookList = new ArrayList<Book>();

		URL url = generateSearchURL(searchTerm);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader (new InputStreamReader(content));
		
		String resultCount = "0";
		String pageCount = "0";
		String isbn13 = null;
		String isbn10 = null;
		String bookName = null;
		String bookAuthor = null;
		String bookPublisher = null;
		double price = 0;
		String type;
		String imgLink = null;
		String uri = null;
		String bookYear = null;
		String bookPages = null;
		

		String dimage = "<img class=\"lazy\" data-lazy=";
		String resultNum = "of  <span class=\"search-count\">";
		String title = "<meta itemprop=\"name\" content=";				
		String dauthor = "<span itemprop=\"author\"";
		String dformat = "<p class=\"format\">";
		String disbn13 = "<meta itemprop=\"isbn\" content=\"";
		String dpage = "Showing 1 to";
		String durl = "<div class=\"item-img\">";
		String dprice = "<div class=\"price-wrap\">";
		
		try {
			String line = bReader.readLine();
				

			while (line != null) {
				
				if (line.trim().startsWith(dpage)) {
					pageCount = line.trim().split(" ")[3];

					
				}
				if (line.trim().startsWith(resultNum)) { // Find the results section.
					resultCount = line.substring(line.indexOf(">")+1, line.lastIndexOf("<")); // Gets the result number
					if (Integer.parseInt(resultCount) >= 10000) {
						resultCount = "10000";
					}
					int count = 0;
					
					while (count < Integer.parseInt(pageCount)) {
						if (line.trim().startsWith(durl)) {
							line = bReader.readLine();
							uri = "https://www.bookdepository.com" + line.substring(line.indexOf("<a href=\"") + 9, line.indexOf("\">"));
							Book temp = getBookInfo(uri);
							bookPages = temp.getPages();
							bookPublisher = temp.getPublisher();
							bookYear = temp.getYear();
							isbn10 = temp.getISBN10();
							
						}
						
						if (line.trim().startsWith(dimage)) {
							if (line.contains(".jpg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpg") + 4);
							} else if (line.contains(".jpeg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpeg") + 5);
							} else if (line.contains(".png")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".png") + 4);
							}

						}

						if (line.trim().startsWith(disbn13)) {
							isbn13 = line.substring(line.indexOf(disbn13) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(title)) {
							bookName = line.substring(line.indexOf(title) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dauthor)) {
							bookAuthor = line.substring(line.indexOf("itemscope=\"") + 11, line.lastIndexOf("\""));
						}


						if (line.trim().startsWith(dformat)) {
							line = bReader.readLine();
							line = line.trim();
							type = line.substring(0, line.lastIndexOf("<"));
							line = bReader.readLine();
							line = bReader.readLine();
							if (line.trim().startsWith(dprice)) {
								line = bReader.readLine();
								line = bReader.readLine();
								if (line.trim().equals("unavailable")) {
									price = 0.0;
								}
								else {
									int lastIndex;
									if (line.endsWith("</p>")) {
										lastIndex = line.trim().lastIndexOf("<");
									}
									else {
										lastIndex = line.trim().length();
									}

									
									price = Double.parseDouble(line.trim().substring(line.trim().indexOf("$")+1, lastIndex ));
								}
								
							}
							Book searchBook = new Book();	
							searchBook.setISBN13(isbn13);
							searchBook.setISBN10(isbn10);
							searchBook.setTitle(bookName);
							searchBook.setAuthor(bookAuthor);
							searchBook.setYear(bookYear);
							searchBook.setPublisher(bookPublisher);
							searchBook.setLink(uri);
							searchBook.setPages(bookPages);
							searchBook.setType(type);
							searchBook.setPrice(price);
							searchBook.setImage(imgLink);
							bookList.add(searchBook);
							count++;
							//System.out.println("New result: " + searchBook.title);
							
						}
						
						line = bReader.readLine();  // Goto next line

					}
				}	
				line = bReader.readLine();  // Goto next line
			}	
		} catch (IOException e) {

			e.printStackTrace();
		} 
		return bookList;
	}
	
public int getResultCount(String searchTerm) {
		int count = 0;

		URL url = generateSearchURL(searchTerm);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader (new InputStreamReader(content));
		
		String resultCount = "0";
		String pageCount = "0";

		String resultNum = "of  <span class=\"search-count\">";
		String dpage = "Showing 1 to";

		try {
			String line = bReader.readLine();
				

			while (line != null) {
				
				if (line.trim().startsWith(dpage)) {
					pageCount = line.trim().split(" ")[3];	
				}
				
				if (line.trim().startsWith(resultNum)) { // Find the results section.
					resultCount = line.substring(line.indexOf(">")+1, line.lastIndexOf("<")); // Gets the result number
					count = Integer.parseInt(resultCount);
				}
				
				line = bReader.readLine();  // Goto next line
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return count;
	}
	

	/**
	 * This method finds the results for the search team on a specific page.
	 * @param index Page
	 * @param searchTerm Term
	 * @return ArrayList containing the results.
	 */
	public ArrayList<Book> changePage(int index, String term) {
		
		ArrayList<Book> bookList = new ArrayList<Book>();

		URL url = generateSearchURL(term, index);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader (new InputStreamReader(content));
		
		int itemCount = 0;
		String isbn13 = null;
		String isbn10 = null;
		String bookName = null;
		String bookAuthor = null;
		String bookPublisher = null;
		double price = 0;
		String type;
		String imgLink = null;
		String uri = null;
		String bookYear = null;
		String bookPages = null;
		
		String dimage = "<img class=\"lazy\" data-lazy=";
		String resultNum = "of  <span class=\"search-count\">";
		String title = "<meta itemprop=\"name\" content=";				
		String dauthor = "<span itemprop=\"author\"";
		String dformat = "<p class=\"format\">";
		String disbn13 = "<meta itemprop=\"isbn\" content=\"";

		String dpage = "Showing ";
		String durl = "<div class=\"item-img\">";
		String dprice = "<div class=\"price-wrap\">";
		
		
		try {
			String line = bReader.readLine();
				

			while (line != null) {
				
				if (line.trim().startsWith(dpage)) {	
					itemCount = Integer.parseInt(line.trim().split(" ")[3]) - Integer.parseInt((line.trim().split(" ")[1])) + 1;
				}
				
				if (line.trim().startsWith(resultNum)) { // Find the results section.

					int count = 0;

					while (count < itemCount) {
						
						if (line.trim().startsWith(durl)) {
							line = bReader.readLine();
							uri = "https://www.bookdepository.com" + line.substring(line.indexOf("<a href=\"") + 9, line.indexOf("\">"));
							Book temp = getBookInfo(uri);
							bookPages = temp.getPages();
							bookPublisher = temp.getPublisher();
							bookYear = temp.getYear();
							isbn10 = temp.getISBN10();
							
						}
						
						if (line.trim().startsWith(dimage)) {
							if (line.contains(".jpg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpg") + 4);
							} else if (line.contains(".jpeg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpeg") + 5);
							} else if (line.contains(".png")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".png") + 4);
							}

						}

						if (line.trim().startsWith(disbn13)) {
							isbn13 = line.substring(line.indexOf(disbn13) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(title)) {
							bookName = line.substring(line.indexOf(title) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dauthor)) {
							bookAuthor = line.substring(line.indexOf("itemscope=\"") + 11, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dformat)) {
							line = bReader.readLine();
							line = line.trim();
							type = line.substring(0, line.lastIndexOf("<"));
							line = bReader.readLine();
							line = bReader.readLine();
							if (line.trim().startsWith(dprice)) {
								line = bReader.readLine();
								line = bReader.readLine();
								if (line.trim().equals("unavailable")) {
									price = 0.0;
								}
								else {
									int lastIndex;
									if (line.endsWith("</p>")) {
										lastIndex = line.trim().lastIndexOf("<");
									}
									else {
										lastIndex = line.trim().length();
									}

									
									price = Double.parseDouble(line.trim().substring(line.trim().indexOf("$")+1, lastIndex ));
								}
								
							}
							Book searchBook = new Book();	
							searchBook.setISBN13(isbn13);
							searchBook.setISBN10(isbn10);
							searchBook.setTitle(bookName);
							searchBook.setAuthor(bookAuthor);
							searchBook.setYear(bookYear);
							searchBook.setPublisher(bookPublisher);
							searchBook.setLink(uri);
							searchBook.setPages(bookPages);
							searchBook.setType(type);
							searchBook.setPrice(price);
							searchBook.setImage(imgLink);
							bookList.add(searchBook);
							count++;
						}
						
						line = bReader.readLine();  // Goto next line

					}
				}
				
				
				
				line = bReader.readLine();  // Goto next line
			}
			
			
			
		} catch (IOException e) {

			e.printStackTrace();
		} 
		return bookList;
	
	}
	
	/**
	 * This method returns a book object containing specific information of a book from its page.
	 * @param uri Link to the Book
	 * @return Book object with data
	 */
	public Book getBookInfo(String uri) {

		URL url = generateBookURL(uri);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader (new InputStreamReader(content));
		
		String publisher = null;
		String yearPublished = null;
		String pages = null;
		String isbn10 = null;
		
		String dpublisher = "<span itemprop=\"publisher\" itemtype=";
		String dyear = "<span itemprop=\"datePublished\">";
		String dpages = "| <span itemprop=\"numberOfPages\">";
		String disbn10 = "<label>ISBN10</label>";

		try {		
			String line = bReader.readLine();
			while (line != null) {
				if (line.trim().startsWith(dpages)) {
					pages = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<")).split(" ")[0];
				}
				if (line.trim().startsWith(dyear)) {
					yearPublished = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<")).split(" ")[2];

				}
				if (line.trim().startsWith(dpublisher)) {
					publisher = line.substring(line.indexOf("itemscope=\"") + 11, line.lastIndexOf(">")-1);
				}
				
				if (line.trim().startsWith(disbn10)) {
					line = bReader.readLine();
					isbn10 = line.substring(line.indexOf(">") +1, line.lastIndexOf("<"));
				}
				
				
				
				line = bReader.readLine();
			}	
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		Book temp = new Book();
		temp.setPages(pages);
		temp.setYear(yearPublished);
		temp.setPublisher(publisher);
		temp.setISBN10(isbn10);
		return temp;
	}
	
	/**
	 * This method finds the results for the search team. 
	 * @param searchTerm Term
	 * @return ArrayList containing the results.
	 */
	public ArrayList<Book> findBookAdvanced(String searchTerm) {

		ArrayList<Book> bookList = new ArrayList<Book>();

		URL url = generateAdvancedSearchURL(searchTerm);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(content));

		String resultCount = "0";
		String pageCount = null;
		String isbn13 = null;
		String isbn10 = null;
		String bookName = null;
		String bookAuthor = null;
		String bookPublisher = null;
		double price = 0;
		String type;
		String imgLink = null;
		String uri = null;
		String bookYear = null;
		String bookPages = null;

		String dimage = "<img class=\"lazy\" data-lazy=";
		String resultNum = "of  <span class=\"search-count\">";
		String title = "<meta itemprop=\"name\" content=";
		String dauthor = "<span itemprop=\"author\"";
		String dformat = "<p class=\"format\">";
		String disbn13 = "<meta itemprop=\"isbn\" content=\"";
		String dpage = "Showing 1 to";
		String durl = "<div class=\"item-img\">";
		String dprice = "<div class=\"price-wrap\">";

		try {
			String line = bReader.readLine();

			while (line != null) {

				if (line.trim().startsWith(dpage)) {
					pageCount = line.trim().split(" ")[3];

				}
				if (line.trim().startsWith(resultNum)) { // Find the results section.
					resultCount = line.substring(line.indexOf(">") + 1, line.lastIndexOf("<")); // Gets the result
																								// number
					if (Integer.parseInt(resultCount) >= 10000) {
						resultCount = "10000";
					}
					int count = 0;

					while (count < Integer.parseInt(pageCount)) {
						if (line.trim().startsWith(durl)) {
							line = bReader.readLine();
							uri = "https://www.bookdepository.com"
									+ line.substring(line.indexOf("<a href=\"") + 9, line.indexOf("\">"));
							Book temp = getBookInfo(uri);
							bookPages = temp.getPages();
							bookPublisher = temp.getPublisher();
							bookYear = temp.getYear();
							isbn10 = temp.getISBN10();

						}

						if (line.trim().startsWith(dimage)) {
							if (line.contains(".jpg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpg") + 4);
							} else if (line.contains(".jpeg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpeg") + 5);
							} else if (line.contains(".png")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".png") + 4);
							}

						}

						if (line.trim().startsWith(disbn13)) {
							isbn13 = line.substring(line.indexOf(disbn13) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(title)) {
							bookName = line.substring(line.indexOf(title) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dauthor)) {
							bookAuthor = line.substring(line.indexOf("itemscope=\"") + 11, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dformat)) {
							line = bReader.readLine();
							line = line.trim();
							type = line.substring(0, line.lastIndexOf("<"));
							line = bReader.readLine();
							line = bReader.readLine();
							if (line.trim().startsWith(dprice)) {
								line = bReader.readLine();
								line = bReader.readLine();
								if (line.trim().equals("unavailable")) {
									price = 0.0;
								} else {
									int lastIndex;
									if (line.endsWith("</p>")) {
										lastIndex = line.trim().lastIndexOf("<");
									} else {
										lastIndex = line.trim().length();
									}

									price = Double.parseDouble(
											line.trim().substring(line.trim().indexOf("$") + 1, lastIndex));
								}

							}
							Book searchBook = new Book();
							searchBook.setISBN13(isbn13);
							searchBook.setISBN10(isbn10);
							searchBook.setTitle(bookName);
							searchBook.setAuthor(bookAuthor);
							searchBook.setYear(bookYear);
							searchBook.setPublisher(bookPublisher);
							searchBook.setLink(uri);
							searchBook.setPages(bookPages);
							searchBook.setType(type);
							searchBook.setPrice(price);
							searchBook.setImage(imgLink);
							bookList.add(searchBook);
							count++;
						}

						line = bReader.readLine(); // Goto next line

					}
				}

				line = bReader.readLine(); // Goto next line
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return bookList;
	}
	
	/**
	 * This method finds the results for the search team on a specific page.
	 * @param index Page
	 * @param searchTerm Term
	 * @return ArrayList containing the results.
	 */
	public ArrayList<Book> changePageAdvanced(int index, String term) {

		ArrayList<Book> bookList = new ArrayList<Book>();

		URL url = generateAdvancedSearchURL(term, index);

		InputStream content = (InputStream) getURLInputStream(url);
		BufferedReader bReader = new BufferedReader(new InputStreamReader(content));

		int itemCount = 0;
		String isbn13 = null;
		String isbn10 = null;
		String bookName = null;
		String bookAuthor = null;
		String bookPublisher = null;
		double price = 0;
		String type;
		String imgLink = null;
		String uri = null;
		String bookYear = null;
		String bookPages = null;

		String dimage = "<img class=\"lazy\" data-lazy=";
		String resultNum = "of  <span class=\"search-count\">";
		String title = "<meta itemprop=\"name\" content=";
		String dauthor = "<span itemprop=\"author\"";
		String dformat = "<p class=\"format\">";
		String disbn13 = "<meta itemprop=\"isbn\" content=\"";

		String dpage = "Showing ";
		String durl = "<div class=\"item-img\">";
		String dprice = "<div class=\"price-wrap\">";

		try {
			String line = bReader.readLine();

			while (line != null) {

				if (line.trim().startsWith(dpage)) {
					itemCount = Integer.parseInt(line.trim().split(" ")[3])
							- Integer.parseInt((line.trim().split(" ")[1])) + 1;
				}

				if (line.trim().startsWith(resultNum)) { // Find the results section.

					int count = 0;

					while (count < itemCount) {
						if (line.trim().startsWith(durl)) {
							line = bReader.readLine();
							uri = "https://www.bookdepository.com"
									+ line.substring(line.indexOf("<a href=\"") + 9, line.indexOf("\">"));
							Book temp = getBookInfo(uri);
							bookPages = temp.getPages();
							bookPublisher = temp.getPublisher();
							bookYear = temp.getYear();
							isbn10 = temp.getISBN10();

						}

						if (line.trim().startsWith(dimage)) {
							if (line.contains(".jpg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpg") + 4);
							} else if (line.contains(".jpeg")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".jpeg") + 5);
							} else if (line.contains(".png")) {
								imgLink = line.substring(line.indexOf(dimage) + 29, line.indexOf(".png") + 4);
							}

						}

						if (line.trim().startsWith(disbn13)) {
							isbn13 = line.substring(line.indexOf(disbn13) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(title)) {
							bookName = line.substring(line.indexOf(title) + 31, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dauthor)) {
							bookAuthor = line.substring(line.indexOf("itemscope=\"") + 11, line.lastIndexOf("\""));
						}

						if (line.trim().startsWith(dformat)) {
							line = bReader.readLine();
							line = line.trim();
							type = line.substring(0, line.lastIndexOf("<"));
							line = bReader.readLine();
							line = bReader.readLine();
							if (line.trim().startsWith(dprice)) {
								line = bReader.readLine();
								line = bReader.readLine();
								if (line.trim().equals("unavailable")) {
									price = 0.0;
								} else {
									int lastIndex;
									if (line.endsWith("</p>")) {
										lastIndex = line.trim().lastIndexOf("<");
									} else {
										lastIndex = line.trim().length();
									}

									price = Double.parseDouble(
											line.trim().substring(line.trim().indexOf("$") + 1, lastIndex));
								}

							}
							Book searchBook = new Book();
							searchBook.setISBN13(isbn13);
							searchBook.setISBN10(isbn10);
							searchBook.setTitle(bookName);
							searchBook.setAuthor(bookAuthor);
							searchBook.setYear(bookYear);
							searchBook.setPublisher(bookPublisher);
							searchBook.setLink(uri);
							searchBook.setPages(bookPages);
							searchBook.setType(type);
							searchBook.setPrice(price);
							searchBook.setImage(imgLink);
							bookList.add(searchBook);
							count++;
						}

						line = bReader.readLine(); // Goto next line

					}
				}

				line = bReader.readLine(); // Goto next line
			}

		} catch (IOException e) {

			e.printStackTrace();
		}
		return bookList;

	}

	/**
	 * Generates the inputstream of data for the application
	 * @param url Link to get data from
	 * @return InputStream
	 */
	private InputStream getURLInputStream(URL url) {
        URLConnection openConnection;
		try {
			openConnection = url.openConnection();	
			String USER_AGENT = "Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.9.2) Gecko/20100115 Firefox/3.6"; 
			openConnection.setRequestProperty("User-Agent", USER_AGENT); 
			return openConnection.getInputStream(); 
		} catch (IOException e) {
			e.printStackTrace();
		}
        
		
		return null;
	}

}
	
