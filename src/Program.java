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
					long startTime = System.nanoTime();
					
					String keyword = db.getNext();
					ArrayList<Book> booklist;
					booklist = urlP.findBook(keyword);
					int resultCount = urlP.getResultCount(keyword);

					for (int i = 0; i < booklist.size(); i++) {
						if (db.add(booklist.get(i)) == true) {
							db.saveImage(booklist.get(i));
						}
					}
					db.updateResult(keyword, resultCount);
					System.out.println(booklist.size() + " total books added into the database");
					long stopTime = System.nanoTime();
					long elapsedTime = (stopTime - startTime)/1000000000;
					System.out.println(elapsedTime);
					Program.db.dequeue(keyword, booklist.size(), Long.toString(elapsedTime));
				}
			}

			else if (command.toLowerCase().equals("v")) { // Check for V
				// db.printDatabase();
			}

			else if (command.toLowerCase().equals("b")) { // Check for B
				Service.running = true;
				new Thread() {
					public void run() {
						Service service = new Service();
					}
				}.start();
				System.out.println("Service started");
			}

			else if (command.toLowerCase().equals("e")) { // Check for E
				Service.running = false;
				System.out.println("Waiting for service to end");
				while (Service.inProcess == true) {
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
