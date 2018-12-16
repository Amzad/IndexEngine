import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Scanner;

public class Program extends Thread {

	static Scanner input;
	static Database db;
	static URLProcessor urlP;
	static FileReader loadFile;
	static BufferedReader readFile;
	static FileWriter makeFile;
	static BufferedWriter writeFile;
	static DateTimeFormatter format;
	static LocalDateTime timeNow;

	public static void main(String[] args) throws CloneNotSupportedException {
		format = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		timeNow = LocalDateTime.now();
		Service service = new Service();

		new Thread() {
			public void run() {
				urlP = new URLProcessor();
			}
		}.start();

		new Thread() {
			public void run() {
				db = new Database();
			}
		}.start();

		System.out.println("Welcome to the BookFinder Index Engine! Press H for a list of available options.");
		while (true) {
			System.out.println("Enter a command");
			input = new Scanner(System.in); // Start scanner
			String command = input.next(); // Get command

			if (command.toLowerCase().equals("h")) {
				System.out.println(" V - Display database items");
				System.out.println(" C - Check for new entries");
				System.out.println(" U - Update Database");
				System.out.println(" B - Begin Service");
				System.out.println(" E - End Service");
				System.out.println(" Q - Quit");
			}

			else if (command.toLowerCase().equals("c")) { // Check for V
				db.checkQueue();
			}

			else if (command.toLowerCase().equals("u")) { // Check for V
				if (db.hasNext()) {
					String keyword = db.getNext();
					ArrayList<Book> booklist;
					booklist = urlP.findBook(keyword);

					for (int i = 0; i < booklist.size(); i++) {
						db.add(booklist.get(i));
						db.saveImage(booklist.get(i));
						System.out.println(booklist.get(i).title + " added into the database");
					}
					System.out.println(booklist.size() + " total books added into the database");
				}
			}

			else if (command.toLowerCase().equals("v")) { // Check for V
				// db.printDatabase();
			}

			else if (command.toLowerCase().equals("b")) { // Check for B
				service.running = true;
				service = new Service();
				System.out.println("Service started");
			}

			else if (command.toLowerCase().equals("e")) { // Check for E
				service.running = false;
				System.out.println("Waiting for service to end");
				while (service.inProcess == true) {
				}
				System.out.println("Service ended");
			}

			else if (command.toLowerCase().equals("q")) { // Check for Q
				System.out.println("Exiting the program");
				System.exit(0);
			}

			else {
				System.out.println("Invalid input");
			}

		}
	}

}
