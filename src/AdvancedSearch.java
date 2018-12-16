import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * AdvancedSearch
 * This class is a GUI used for the advanced search component of the application.
 * @author Amzad
 *
 */
@SuppressWarnings("serial")
public class AdvancedSearch extends JDialog implements ActionListener {
	
	JTable modifyFrame;
	private JTextField textTitle;
	private JTextField textAuthor;
	private JTextField textKeyword;
	private JTextField textISBN;
	private JTextField textPublisher;
	
	/**
	 * Default constructor starts the advanced search GUI.
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public AdvancedSearch() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent arg0) {
				setVisible(false);
			}
		});
		setSize(481,174);
		setLocationRelativeTo(null);
		setTitle("Advanced Search");


		setModal(true);
		getContentPane().setLayout(null);
		
		JLabel lblBookAne = new JLabel("Title");
		lblBookAne.setBounds(10, 11, 46, 14);
		getContentPane().add(lblBookAne);
		
		JLabel lblNewLabel = new JLabel("Author");
		lblNewLabel.setBounds(241, 11, 46, 14);
		getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Publisher");
		lblNewLabel_1.setBounds(10, 36, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblIsbn = new JLabel("ISBN");
		lblIsbn.setBounds(241, 36, 46, 14);
		getContentPane().add(lblIsbn);
		
		JLabel lblKeyword = new JLabel("Keyword");
		lblKeyword.setBounds(10, 61, 46, 14);
		getContentPane().add(lblKeyword);
		
		textTitle = new JTextField();
		textTitle.setBounds(62, 8, 152, 20);
		getContentPane().add(textTitle);
		textTitle.setColumns(10);
		
		textAuthor = new JTextField();
		textAuthor.setBounds(297, 8, 152, 20);
		getContentPane().add(textAuthor);
		textAuthor.setColumns(10);
		
		textKeyword = new JTextField();
		textKeyword.setBounds(62, 58, 152, 20);
		getContentPane().add(textKeyword);
		textKeyword.setColumns(10);
		
		textISBN = new JTextField();
		textISBN.setBounds(297, 33, 152, 20);
		getContentPane().add(textISBN);
		textISBN.setColumns(10);
		
		textPublisher = new JTextField();
		textPublisher.setBounds(62, 33, 152, 20);
		getContentPane().add(textPublisher);
		textPublisher.setColumns(10);
		
		JLabel lblLanguage = new JLabel("Language");
		lblLanguage.setBounds(241, 61, 56, 14);
		getContentPane().add(lblLanguage);
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"English", "French", "Spanish", "German", "Latin", "Italian"}));
		comboBox.setBounds(297, 58, 152, 20);
		getContentPane().add(comboBox);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String searchTerm = "";
				if (textKeyword.getText().length() != 0) {
					searchTerm = searchTerm + "searchTerm=" + textKeyword.getText().trim() + "&";
				}
				if (textTitle.getText().length() != 0) {
					searchTerm = searchTerm + "searchTitle=" + textTitle.getText().trim() + "&";
				}
				if (textAuthor.getText().length() != 0) {
					searchTerm = searchTerm + "searchAuthor=" + textAuthor.getText().trim() + "&";
				}
				if (textPublisher.getText().length() != 0) {
					searchTerm = searchTerm + "searchPublisher=" + textPublisher.getText().trim() + "&";
				}
				if (textISBN.getText().length() != 0) {
					searchTerm = searchTerm + "searchIsbn=" + textISBN.getText().trim() + "&";
				}
				String lang = (String) comboBox.getItemAt(comboBox.getSelectedIndex());
				String comboValue = "123"; // Default language code for English
				
				// The for loops sets the language code according to the website.
				if (lang.equals("English")) comboValue = "123";
				if (lang.equals("French")) comboValue = "137";
				if (lang.equals("Spanish")) comboValue = "404";
				if (lang.equals("German")) comboValue = "150";
				if (lang.equals("Latin")) comboValue = "246";
				if (lang.equals("Italian")) comboValue = "202";
				
				searchTerm = searchTerm + "searchLang=" + comboValue + "&advanced=true";
				setVisible(false); // Hide the gui. Return to main gui.
			}
		});
		btnSearch.setBounds(183, 101, 89, 23);
		getContentPane().add(btnSearch);
		setVisible(true);
	}
	



	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}	
}
