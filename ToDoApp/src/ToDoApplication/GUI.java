package ToDoApplication;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.awt.*;
import java.util.*;
import java.awt.event.*;

public class GUI {
	
	JFrame mainFrame;
	private JPanel mainPanel;
	
	private JLabel header;
	
	private JButton createQuestButton;
	
	private Dialog dialogWindow; 
	
	private BorderLayout frameLayout;
	private FlowLayout mainButtonsArea;
	private GridLayout checkBoxArea;
	private GridLayout questsButtonsArea;
	private GridLayout headerLabel;
	
	private JPanel southPanel;
	private JPanel westPanel;
	private JPanel centerPanel;
	private JPanel headerPanel;
	
	private ArrayList<Quest> quests; // contains all created quests
	private ArrayList<MyCheckBox> checkBoxes; // contains all created check boxes
	private ArrayList<MyButton> buttons; // contains all creates buttons

	private File questFile; // file where all quests and their details are saved
	
	public GUI() { 
		quests = new ArrayList<Quest>();
		checkBoxes = new ArrayList<MyCheckBox>();
		buttons = new ArrayList<MyButton>();
		questFile = new File("quest.txt");	
	}
	
	public void createGUI(){ /* method which creates whole GUI*/
		
		mainFrame = new JFrame("To Do App"); // creating main frame
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // setting options of main frame
		mainFrame.setBounds(600,600,600,600);
		mainFrame.setMinimumSize(new Dimension(350,10));
		mainFrame.setLocationRelativeTo(null);
		mainFrame.setResizable(false);
		
		
		mainFrame.addWindowListener(new WindowAdapter() { 
			public void windowClosing(WindowEvent e) {
		        GUIWrite2File();
		    }
		} ); // actionListener which before closing window save whole contents of quests, buttons and checkBoxes array to file 
		
		
		frameLayout = new BorderLayout(); // setting the layout of main frame
		mainPanel = new JPanel(frameLayout);
		
		mainPanel.setBackground(new Color(204,204,255)); // set colors of main panel
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); //setting the bounds of main panel
		
		frameLayout.setHgap(20); // defining the gaps between elements added to the panel
		frameLayout.setVgap(20);
		
		/* Creating all subaltern layouts and panels*/
		headerLabel = new GridLayout(); // panel with header
		headerPanel = new JPanel(headerLabel);
		
		mainButtonsArea = new FlowLayout(); // panel with functional buttons
		southPanel = new JPanel(mainButtonsArea);
		
		checkBoxArea = new GridLayout(checkBoxes.size(),1); // panel with checkBoxes
		westPanel = new JPanel(checkBoxArea);
		
		questsButtonsArea = new GridLayout(buttons.size(),1); // panel with questsButtons
		centerPanel = new JPanel(questsButtonsArea);
		
		/* Setting details of each panel*/
		header = new JLabel("Let's get to work!", JLabel.CENTER);
		header.setFont(new Font("Courier New", Font.BOLD, 20));
		headerPanel.setBackground(new Color(204,204,255));
		header.setForeground(new Color(127,0,255));
		headerPanel.add(header);
		
		mainButtonsArea.setVgap(2);
		
		westPanel.setBackground(new Color(204,204,255));
		checkBoxArea.setVgap(2);
		
		questsButtonsArea.setVgap(2);
		
		
		createQuestButton = new JButton("ADD QUEST"); // creating the "Add Quest" button
		createQuestButton.setMnemonic(KeyEvent.VK_A); 
		createQuestButton.setMaximumSize(new Dimension(120,20));
		createQuestButton.setBackground(new Color(127,0,255));
		createQuestButton.setVerticalAlignment(SwingConstants.CENTER);
		createQuestButton.addActionListener(new AddListener());
		
		southPanel.setBackground(new Color(204,204,255));
		southPanel.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		southPanel.add(createQuestButton);
		
		
		mainPanel.add(BorderLayout.NORTH, headerPanel); // adding all subaltern panels to the main panel
		mainPanel.add(BorderLayout.SOUTH, southPanel);
		mainPanel.add(BorderLayout.WEST, westPanel);
		mainPanel.add(BorderLayout.CENTER, centerPanel);
	
		
		mainFrame.getContentPane().add(mainPanel); // adding mainPanel to the container
		mainFrame.pack(); //  packing the components within the window based on the component’s preferred sizes
		mainFrame.setVisible(true);
		
		dialogWindow = new Dialog(GUI.this); // creating dialog window for adding new quests, editing old ones and showing details of the present ones
		
		GUIReadFromFile(questFile); // after creating GUI, read and add to user interface all created quests in the previous running of application
		
	}
	

	// method creating new quests
	public void GUICreateQuest(String name,  String priority, String comment) {
		
		Quest quest = new Quest(name, priority, comment);
		
		MyCheckBox checkBox = new MyCheckBox(quest.id);
		checkBox.setSelected(false);
		checkBox.addActionListener(new CheckBoxListener());
		checkBox.setActionCommand(Long.toString(checkBox.id)); // setting as the action command the number of id of particular checkBox
		
		MyButton myButton = new MyButton(quest.id,quest.name);  // for each quest is created a button with name of the quest
		myButton.setName(name);
		
		myButton.setHorizontalAlignment(SwingConstants.LEFT);
		myButton.setBorder(BorderFactory.createEmptyBorder());
		myButton.setContentAreaFilled(false);
		myButton.addActionListener(new QuestButtonListener()); // adding action listener
		myButton.setActionCommand(Long.toString(myButton.id)); // setting as the action command the number of id of particular button
		
		quests.add(quest); // adding components to particular list 
		checkBoxes.add(checkBox);
		buttons.add(myButton);
		
		westPanel.add(checkBox); // adding components to particular panel 
		checkBox.setBackground(new Color(204,204,255)); // setting color of checkBoxes
		checkBox.setForeground(new Color(204,204,255));
	
		centerPanel.add(myButton);
		centerPanel.setBackground(new Color(204,204,255));
		
		mainFrame.pack();
		mainFrame.revalidate(); // repainting the frame
		mainFrame.repaint();
	}
	
	// method editing the quest on the user's panel
	public void GUIEditQuest(Quest quest_) {
		for (int i=0; i<quests.size(); i++) { // searching for the buttons which id equals to the editing quest id
			if (quest_.id == buttons.get(i).id) {
				buttons.get(i).setName(quest_.name); //setting new name of buttons
				break;
			}
		}
		
		mainFrame.pack();
		mainFrame.revalidate(); // repainting the main frame
		mainFrame.repaint();
	}
	
	// methods saving content of each array: quests, checkBoxes, buttons to file 
	public void GUIWrite2File() {
		
		try { 
			PrintWriter outFile = new PrintWriter("quest.txt");
			for (int i=0; i< quests.size(); i++) {
					outFile.println(quests.get(i).id+","
							+ quests.get(i).name+"," 
							+ quests.get(i).priority 
							+ ","+quests.get(i).comment); // saving to file all quests and their properties
					
							
			}
			outFile.close();
		} catch (IOException ex){ // saving to the file failed
			
		} catch (IndexOutOfBoundsException ind) { // if arrays were empty => user didn't create any quests
			
		}	
	}
	
	//method to read from file
	public void GUIReadFromFile(File file2Read) {
		try {
			
			FileReader fileReader = new FileReader(file2Read);
			BufferedReader bufferedReader = new BufferedReader(fileReader); 
			String textLine = bufferedReader.readLine(); // reading by line
			String comment = "";
			String[] splits = null;
			while (textLine != null) {
				splits = textLine.split(",");// Splitting each line by ","
					try {
						comment = splits[3]; // finding out if user add any comment 
					} catch (ArrayIndexOutOfBoundsException ind) {
						// if user did not add comment, there will be add empty one
						comment = "";
					}
					GUICreateQuest(splits[1],splits[2],comment); // creating quest (one per line)
				textLine = bufferedReader.readLine(); // reading the next line 
			}	
			bufferedReader.close();
			file2Read.delete(); // deleting the old file 
			
		} catch (FileNotFoundException ex) { // file has not been found
			
		} catch (IOException ex) {  // saving to the file failed
		}
	}
		
	public class AddListener implements ActionListener { // creating listener for "add quest" button
		public void actionPerformed(ActionEvent a) {
			dialogWindow.addQuestDialog(mainFrame,"Create Quest","","","");
			
		}
	}
	
	public void GUIDeleteQuest(Integer j) { // method which deletes corresponding quest; j-index of the checkBox on the list checkBoxes
				
				westPanel.remove(checkBoxes.get(j)); // removing the checkBox from panel which contains all checkBoxes
				centerPanel.remove(buttons.get(j)); // removing the responsive button to corresponding checkBox
				
				quests.remove(quests.get(j)); // removing quest from the quests list
				buttons.remove(buttons.get(j)); // removing button from the buttons list 
				checkBoxes.remove(checkBoxes.get(j)); // removing checkBox from the list checkBoxes
				
				mainFrame.pack();
				mainFrame.revalidate(); // repainting the frame
				mainFrame.repaint();
	}
	
	public class CheckBoxListener implements ActionListener { // creating checkBox listener 
		public void actionPerformed(ActionEvent a) {
			for (int j=0; j<checkBoxes.size(); j++) {
				if (checkBoxes.get(j).isSelected()) {
					GUIDeleteQuest(j); // removing this quest from frame
					break;
				}
			}
		}
	}
	 
	public class QuestButtonListener implements ActionListener { // creating questsButton listener
		public void actionPerformed(ActionEvent a) {
			
			String ac = a.getActionCommand(); // getting the action commander
			
			for (int i=0; i<buttons.size(); i++) { // looking for the buttons with id which command the action
				if (ac.equals(Long.toString(buttons.get(i).id))) {
					dialogWindow.showQuestDialog(mainFrame,"Details Of Quest", quests.get(i)); // showing details of the quest
					break;
				}
			}
		}
	}
		
}
