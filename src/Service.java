import java.util.ArrayList;

public class Service extends Thread {

	static boolean running = false;
	static boolean inProcess = false;

	public Service() {

		while (running) {
			if (Program.db.hasNext()) {
				long startTime = System.nanoTime();
				inProcess = true;

				String keyword = Program.db.getNext();
				ArrayList<Book> booklist;
				booklist = Program.urlP.findBook(keyword);
				int resultCount = Program.urlP.getResultCount(keyword);

				for (int i = 0; i < booklist.size(); i++) {
					if (Program.db.add(booklist.get(i))) {
						Program.db.saveImage(booklist.get(i));
					}
					System.out.println(booklist.get(i).title + " added into the database");
				}
				Program.db.updateResult(keyword, resultCount);
				System.out.println(booklist.size() + " total books added into the database");
				long stopTime = System.nanoTime();
				long elapsedTime = (stopTime - startTime)/1000000000;
				Program.db.dequeue(keyword, booklist.size(), Long.toString(elapsedTime));
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
