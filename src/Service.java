import java.util.ArrayList;

public class Service extends Thread {

	static boolean running = false;
	static boolean inProcess = false;

	public Service() {

		while (running) {
			if (Program.db.hasNext()) {
				inProcess = true;

				String keyword = Program.db.getNext();
				ArrayList<Book> booklist;
				booklist = Program.urlP.findBook(keyword);

				for (int i = 0; i < booklist.size(); i++) {
					if (Program.db.add(booklist.get(i))) {
						Program.db.saveImage(booklist.get(i));
					}
					System.out.println(booklist.get(i).title + " added into the database");
				}
				System.out.println(booklist.size() + " total books added into the database");
				Program.db.dequeue(keyword, booklist.size());
				inProcess = false;
			}
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}
}
