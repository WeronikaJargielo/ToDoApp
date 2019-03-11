package ToDoApplication;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.*;
import static javax.swing.GroupLayout.Alignment.*;


class Dialog {
	
	//static final int maxChars = 25;
	private boolean show; 		// boolean variable that indicates if button is used to edit or save quest
	private boolean update; 	// boolean variable that indicates if the new quest will be created or the details of one of existing quests will be updated
	private Quest quest;
	
	private GUI gui;

	private JDialog dialogWindow; 
	
	private JPanel dialogPanel;
	
	private GroupLayout dialogLayout;
	
	private JButton saveButton;
	private JButton cancelButton;
	 
	private JLabel dialogHeader;
	private JLabel nameOfQuest;
	private JLabel priorityOfQuest;
	private JLabel commentOfQuest;
	
	private JTextField nameQuest;
	private JTextArea commentQuest;
	private JScrollPane scrollComment;
	
	private JComboBox<String> levelsOfPriority;
	
	
	public Dialog(GUI gui_) {
		gui = gui_;
		update = false;
	}
	
	// method that creates dialog window
	private void createDialogWindow(JFrame frame_,String title_, String name_, String priority_, String comment_) {
		
		dialogWindow = new JDialog(frame_, title_, true); // creating the new dialog
		dialogPanel = new JPanel();
		
		dialogWindow.setMinimumSize(new Dimension(650,50)); // setting all options of dialog
		dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		dialogWindow.setLocationRelativeTo(null);
		dialogPanel.setBackground(new Color(204,204,255));
		
		saveButton = new JButton("SAVE"); // creating save/edit button
		saveButton.addActionListener(new saveEditListener());
		saveButton.setBackground(new Color(127,0,255));
		
		cancelButton = new JButton("CANCEL"); // creating cancel button
		cancelButton.addActionListener(new cancelListener());
		cancelButton.setBackground(new Color(127,0,255));
		cancelButton.setMnemonic(KeyEvent.VK_C); 
		 
		// creating all labels
		dialogHeader = new JLabel("Let's Create a New Quest!");
		nameOfQuest = new JLabel("Name: ");
		priorityOfQuest  = new JLabel("Priority: ");
		commentOfQuest  = new JLabel("Comment: "); 
		
		// setting fonds of each label
		dialogHeader.setFont(new Font("Courier New", Font.BOLD, 20));
		nameOfQuest.setFont(new Font("Courier New", Font.BOLD, 18));
		priorityOfQuest.setFont(new Font("Courier New", Font.BOLD, 18));
		commentOfQuest.setFont(new Font("Courier New", Font.BOLD, 18));
		
		//setting the color of font 
		dialogHeader.setForeground(new Color(127,0,255));
		nameOfQuest.setForeground(new Color(127,0,255));
		priorityOfQuest.setForeground(new Color(127,0,255));
		commentOfQuest.setForeground(new Color(127,0,255));
		
		// setting the limit of the quest's name
		Document doc = new MaxLengthDocument(50);
		nameQuest = new JTextField(doc,"",8);
		
		// filing the name's textArea, comboBox, comment's textArea with data accessed from file
		nameQuest.setText(name_);
		nameQuest.setBorder(BorderFactory.createLineBorder(new Color(127,0,255)));
		nameQuest.setFont(new Font("Courier New", Font.PLAIN, 14));
		nameQuest.setEditable(true);
			
		commentQuest = new JTextArea(5,10);
		commentQuest.setText(comment_);
		commentQuest.setBorder(BorderFactory.createLineBorder(new Color(127,0,255)));
		commentQuest.setFont(new Font("Courier New", Font.PLAIN, 14));
		commentQuest.setLineWrap(true);
		commentQuest.setWrapStyleWord(true);
		commentQuest.setEditable(true);
		
       
		scrollComment = new JScrollPane(commentQuest); // adding scroll bar to comment textArea
		scrollComment.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

		
		String[] priorityLevels = {"Important","Medium","Neutral","None"}; // setting the levels of priority
		levelsOfPriority = new JComboBox<String>(priorityLevels); 
		levelsOfPriority.setFont(new Font("Courier New", Font.PLAIN, 14));
		levelsOfPriority.setSelectedItem(priority_); 
		
		/* Creating a Group Layout*/
		dialogLayout = new GroupLayout(dialogPanel);
		dialogPanel.setLayout(dialogLayout);
		dialogPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		
		dialogLayout.setAutoCreateGaps(true);
		dialogLayout.setAutoCreateContainerGaps(true);
		
		dialogLayout.setHorizontalGroup(dialogLayout
	    		.createSequentialGroup()
	    		.addGroup(dialogLayout.createParallelGroup(CENTER)
	    				.addComponent(dialogHeader)
	    				.addGroup(dialogLayout.createSequentialGroup()
	    						.addGroup(dialogLayout.createParallelGroup(LEADING)
			    						.addComponent(nameOfQuest)
										.addComponent(priorityOfQuest)	
										.addComponent(commentOfQuest))
	    						.addGroup(dialogLayout.createParallelGroup(LEADING)
	    								.addComponent(nameQuest)
	    								.addComponent(levelsOfPriority)
	    								.addComponent(scrollComment)) 
	    						.addGroup(dialogLayout.createParallelGroup(LEADING)
										.addComponent(saveButton)
										.addComponent(cancelButton)))));
	    								
																	
	    dialogLayout.linkSize(SwingConstants.HORIZONTAL, saveButton, cancelButton);
						
		dialogLayout.setVerticalGroup(dialogLayout
				.createSequentialGroup()
				.addGroup(dialogLayout.createParallelGroup(BASELINE)
						.addComponent(dialogHeader))
				.addGroup(dialogLayout.createParallelGroup(LEADING)
						.addGroup(dialogLayout.createSequentialGroup()
								.addGroup(dialogLayout.createParallelGroup(BASELINE)
										.addComponent(nameOfQuest)
										.addComponent(nameQuest)
										.addComponent(saveButton))
								.addGroup(dialogLayout.createParallelGroup(BASELINE)
										.addComponent(priorityOfQuest)
										.addComponent(levelsOfPriority)
										.addComponent(cancelButton))
								.addGroup(dialogLayout.createParallelGroup(BASELINE)
										.addComponent(commentOfQuest)
										.addComponent(scrollComment)))));
	}
	
	// method that create dialog to add new quest
	public void addQuestDialog(JFrame frame_,String title_, String name_, String priority_, String comment_) {
		createDialogWindow(frame_,title_, name_, priority_, comment_);
		show = false;
		update = false;
		saveButton.setMnemonic(KeyEvent.VK_S); 
		dialogWindow.add(dialogPanel);
		dialogWindow.pack();
		dialogWindow.setVisible(true);
	}
	
	// method that create dialog to show details of particular quest
	public void showQuestDialog(JFrame frame_,String title_, Quest quest_) {
		quest = quest_;
		createDialogWindow(frame_,title_, quest.name, quest.priority, quest.comment);
		show = true; // if show is true the saveButtons id used as editButton

		dialogHeader.setText("Details Of Your Quest:");
		saveButton.setMnemonic(KeyEvent.VK_E); 
		commentQuest.setBackground(new Color(224,224,224));
		commentQuest.setEditable(false);
		
		nameQuest.setBackground(new Color(224,224,224));
		nameQuest.setEditable(false);
		
		levelsOfPriority.setEditable(false);
		levelsOfPriority.setEnabled(false);
		UIManager.put("ComboBox.disabledForeground", Color.BLACK);
		UIManager.put("ComboBox.disabledBackground", new Color(224,224,224));
		
		saveButton.setText("EDIT");
		
		dialogWindow.add(dialogPanel);
		dialogWindow.pack();
		dialogWindow.setVisible(true);
	}
	
	// method to edit the quest
	public void editQuest(JFrame frame_,String title_, Quest quest) {
		dialogWindow.dispose(); // closing the dialog Window showing details
		show = false; 
		createDialogWindow(frame_,title_, quest.name, quest.priority, quest.comment);
		update = true;
		
		dialogHeader.setText("You Can Edit Your Quest!");
		nameQuest.setText(quest.name);
		levelsOfPriority.setSelectedItem(quest.priority);
		commentQuest.setText(quest.comment);
		
		saveButton.setMnemonic(KeyEvent.VK_S); 
		dialogWindow.add(dialogPanel);
		dialogWindow.pack();
		dialogWindow.setVisible(true);
	}
	
	
	public class saveEditListener implements ActionListener { // listener of save/edit button
		public void actionPerformed(ActionEvent a) { 
			if (show) {
				editQuest(gui.mainFrame,"Edition Of Qf Quest", quest); 
				// if show is true, the editQuest method is called (because it means that the details dialog window was opened) and button will be used as editButton
			} else { // if show is false it means that create quest window was opened, and button will be used as saveButton
				String name = nameQuest.getText();
				String priority = levelsOfPriority.getSelectedItem().toString();
				String comment = commentQuest.getText();
				if (update) { 
					// if update is true it means that quest will be updated with new name, etc.
					quest.setName(name);
					quest.setPriority(priority);
					quest.setComment(comment);
					gui.GUIEditQuest(quest);
					dialogWindow.dispose();
				} else { 
					if (name.equals("") == false) { 
						//if update is false and user correctly implements all data (name textArea is not empty), the new quest will be created 
						dialogWindow.dispose();
						gui.GUICreateQuest(name, priority, comment);
					}
				}
			}
		}
	}
	
	public class cancelListener implements ActionListener { // cancel listener
		public void actionPerformed(ActionEvent a) {
			dialogWindow.dispose();
			
		}
	}
	
}
