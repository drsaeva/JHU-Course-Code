/**
 * Class SongDB
 * @author David Saeva
 * @version v1.0
 * 
 * This class contains all necessary controlling logic for the song database program. It produces a GUI with labels
 * 	and fields for each parameter of a Song object, buttons for manipulation of the database (whether to add a new song,
 * 	edit/remove the currently selected song, accept/cancel an addition/edit in progress, or exit), and a combo box for 
 * 	selecting individual songs from the database. 
 * 
 */
package SongDB;

import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;

public class SongDB extends JFrame implements ActionListener {
	private ArrayList<Song> songs;
	private JLabel labSelect = new JLabel("Select Song:");
	private JLabel labCode = new JLabel("Item Code:      ");
	private JLabel labDesc = new JLabel("Description:   ");
	private JLabel labArtist = new JLabel("Artist:              ");
	private JLabel labAlbum = new JLabel("Album:            ");
	private JLabel labPrice = new JLabel("Price:              ");

	private JComboBox<Song> comboBox = new JComboBox<Song>();
	private JTextField tfCode = new JTextField(30);
	private JTextField tfDesc = new JTextField(30);
	private JTextField tfArtist = new JTextField(30);
	private JTextField tfAlbum = new JTextField(30);
	private JTextField tfPrice = new JTextField(30);

	private JButton bAdd = new JButton("Add");
	private JButton bEdit = new JButton("Edit");
	private JButton bDelete = new JButton("Delete");
	private JButton bAccept = new JButton("Accept");
	private JButton bCancel = new JButton("Cancel");
	private JButton bExit = new JButton("Exit");
	
	private JPanel[] panels = new JPanel[8];
	private JComponent[][] components = { 
			{labSelect, comboBox}, {labCode, tfCode},
			{labDesc, tfDesc}, {labArtist, tfArtist},
			{labAlbum, tfAlbum}, {labPrice, tfPrice},
			{bAdd, bEdit, bDelete, bAccept, bCancel},
			{bExit}
	};
	
	private boolean isNewSong = false;
	private static boolean outputList = false;
	
	/**
	 * Main method for SongDB program. Takes input from user at runtime for input file - if nothing is provided, a new song DB is generated.
	 * 	For an existing file, that file is parsed for song information
	 * @param args runtime arguments
	 */
	public static void main(String[] args) {
		ArrayList<Song> songsIn = new ArrayList<Song>();
		Scanner input = new Scanner(System.in);
		String userChoice = "";
	  
		try {
		  	Scanner songReader = new Scanner(new File(args[0]));
		  	outputList = true;
		  	while(songReader.hasNextLine()) {
		  		String line = songReader.nextLine();
		  		String[] tokens = line.split(","); 		
		  		Song newSong = new Song(tokens, setPriceFromString(tokens[4]));
		  		songsIn.add(newSong);
		  	}
		  	songReader.close();
		} catch(FileNotFoundException e) {
			System.out.println("The file provided does not exist.");
			System.out.print("Do you want to create a new one? (Y/N): ");
			userChoice = input.nextLine();
			
		}  
		
		if(userChoice.charAt(0) == 'Y' || userChoice.charAt(0) == 'y') {
			SongDB s = new SongDB(songsIn);
			s.setTitle("My Songs");
			s.setSize(500, 350);
			s.setVisible(true);
		  s.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		  
		  if (outputList) {
		  	BufferedWriter w = null;
				try {
					w = new BufferedWriter(new FileWriter(new File(args[0])));
					
					ArrayList<Song> songsOut = s.getSongsFinal();
					for (int i=0; i<songsOut.size(); i++) {
						if (songsOut.get(i) != null) {
							w.write(songsOut.get(i).getItemCode() + "," + songsOut.get(i).getDesc() + "," + 
									songsOut.get(i).getAlbum() + "," + songsOut.get(i).getArtist() + "," + songsOut.get(i).getPrice());
							w.newLine();
						}
					}
					
					w.close();
					
				} catch(java.io.IOException e) {
					System.out.println("The file provided at runtime does not exist. Your data will not be saved. Thank you for using SongDB.");
				}
		  }
		  
		} else {
			System.out.println("No problem! Thank you for using SongDB.");
			
		}
		input.close();
  	
	}
	
	/**
	 * Constructor for the SongDB class
	 * @param songList
	 */
	public SongDB(ArrayList<Song> songList) {
		
		songs = new ArrayList<Song>();
    
		if(songList.size() > 0) {
			for(Song song : songList) {
				songs.add(song);
				comboBox.addItem(song);
				
			}
		} else {
			String[] yellowSub = {"BT012", "Yellow Submarine", "The Beatles", "Beatles Greatest Hits 1"};
			Song oneSong = new Song(yellowSub, 1.99);
			songs.add(oneSong);   
			comboBox.addItem(oneSong);
			
		}
		for (int i=0; i<8; i++) {
			panels[i] = new JPanel();
			addToPanel(panels[i], components[i]);
			
		}
  
		setLayout(new FlowLayout());
		for (int i=0; i<8; i++) {
			add(panels[i]);
			
		}
    
		Song currentSong = (Song) comboBox.getSelectedItem();
		showSong(currentSong);
  
		bAccept.setEnabled(false);
		bCancel.setEnabled(false);
  
		comboBox.addActionListener(this);
		bAdd.addActionListener(this);
		bEdit.addActionListener(this);
		bDelete.addActionListener(this);
		bAccept.addActionListener(this);
		bCancel.addActionListener(this);
		bExit.addActionListener(this);
		
	}

	/**
	 * Implements ActionListener.actionPerformed, generating action events for each button and the combo box
	 * 
	 * See individual comments in code for more information
	 */
	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		Song currentSong;
		
		//
		if(source == comboBox) {
			currentSong = (Song) comboBox.getSelectedItem();
			showSong(currentSong);
		
		// begins the addition process for a new song by enabling the textfields for editing
		} else if(source == bAdd) {
			isNewSong = true;
			
			tfCode.setText("");
			tfDesc.setText("");
			tfArtist.setText("");
			tfAlbum.setText("");
			tfPrice.setText("0");
   
			tfCode.setEnabled(true);
			tfDesc.setEnabled(true);
			tfArtist.setEnabled(true);
			tfAlbum.setEnabled(true);
			tfPrice.setEnabled(true);
   
			bAdd.setEnabled(false);
			bEdit.setEnabled(false);
			bDelete.setEnabled(false);
			bAccept.setEnabled(true);
			bCancel.setEnabled(true);
			
		// enables the user to edit fields for the currently-selected song
		} else if(source == bEdit) {
			isNewSong = false;
			
			tfDesc.setEnabled(true);
			tfArtist.setEnabled(true);
			tfAlbum.setEnabled(true);
			tfPrice.setEnabled(true);
   
			bAdd.setEnabled(false);
			bEdit.setEnabled(false);
			bDelete.setEnabled(false);
			bAccept.setEnabled(true);
			bCancel.setEnabled(true);  
			
		// removes song currently selected by combobox 
		} else if(source == bDelete) {
			if (songs.size() > 1) {
				comboBox.removeItemAt(comboBox.getSelectedIndex());
				songs.remove(comboBox.getSelectedItem());
				comboBox.setSelectedIndex(songs.size()-1);
				System.out.println(songs.size());
				showSong((Song)comboBox.getSelectedItem());
				
			} else {
				JOptionPane.showMessageDialog(null,"Cannot remove last song in database","Error",JOptionPane.WARNING_MESSAGE);
			}
		
		// accepts the current edit or addition for the song whose fields are currently being manipulated
		} else if(source == bAccept) {
			if (isNewSong) {
			String[] newDat = {tfCode.getText(), tfDesc.getText(), tfArtist.getText(), tfAlbum.getText()};
			currentSong = new Song(newDat, setPriceFromString(tfPrice.getText()));
			System.out.println(currentSong.toString());
     
			songs.add(currentSong);   
			comboBox.addItem(currentSong);
			comboBox.setSelectedIndex(songs.size() - 1);
			currentSong = (Song) comboBox.getSelectedItem();
			showSong(currentSong);
			
			} else {
				currentSong = (Song) comboBox.getSelectedItem();
				currentSong.setDesc(tfDesc.getText());
				currentSong.setArtist(tfArtist.getText());
				currentSong.setAlbum(tfAlbum.getText());
				currentSong.setPrice(setPriceFromString(tfPrice.getText()));
			
			}
			
			bAccept.setEnabled(false);
			bCancel.setEnabled(false);
			bAdd.setEnabled(true);
			bEdit.setEnabled(true);
			bDelete.setEnabled(true);
			
		// resets buttons and state of fields
		} else if(source == bCancel) {
			bAccept.setEnabled(false);
			bCancel.setEnabled(false);
			bAdd.setEnabled(true);
			bEdit.setEnabled(true);
			bDelete.setEnabled(true);
			
		} else {
			System.exit(0);
		}
	}
	
	/**
	 * Parses the price of a Song from a String input and returns it. In the event that the input string cannot be parsed for a double,
	 * 	this returns a price of $1.99
	 */
	private static double setPriceFromString(String input) {
		double price = 1.99;
		try {
			price = Double.parseDouble(input);
		} catch (java.lang.NumberFormatException e) {
			JOptionPane.showMessageDialog(null,"Invalid entry for song price, setting to default value of $1.99","Error",JOptionPane.WARNING_MESSAGE);
		}
		return price;
	}
	
	/**
	 * Returns final arraylist of songs after editing to output to file
	 */
	public ArrayList<Song> getSongsFinal() {
		return songs;
	}
	
	/**
	 * Method to simplify addition of JComponents to a given JPanel in a JFrame
	 * @param panel JPanel to which components will be added
	 * @param components JComponent array containing buttons, text fields, etc specific to the panel
	 */
	private void addToPanel(JPanel panel, JComponent[] components) {
		for (int i=0; i<components.length; i++) {
			panel.add(components[i]);
		}
	}

	/**
	 * Sets text fields for song data equal to those of the passed in song object,
	 * 	which is the currently-selected song in the combobox
	 * @param currentSong Song object currently selected in the combobox
	 */
	private void showSong(Song currentSong) {
		tfCode.setText(currentSong.getItemCode());
		tfDesc.setText(currentSong.getDesc());
		tfArtist.setText(currentSong.getArtist());
		tfAlbum.setText(currentSong.getAlbum());
		tfPrice.setText("" + currentSong.getPrice());
		tfCode.setEnabled(false);
		tfDesc.setEnabled(false);
		tfArtist.setEnabled(false);
  	tfAlbum.setEnabled(false);
  	tfPrice.setEnabled(false);
  	
	}

}
