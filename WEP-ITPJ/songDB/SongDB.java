package songDB;

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
	private JTextField tfCode = new JTextField(20);
	private JTextField tfDesc = new JTextField(20);
	private JTextField tfArtist = new JTextField(20);
	private JTextField tfAlbum = new JTextField(20);
	private JTextField tfPrice = new JTextField(20);

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
	
	public static void main(String[] args) {
		ArrayList<Song> list = new ArrayList<Song>();
	  
		Scanner input = new Scanner(System.in);
	  
		System.out.print("Enter the database file name: ");
		String filename = input.nextLine();
		String choice = "";
	  
		try {
		  	Scanner i = new Scanner(new File(filename));
	   
		  	while(i.hasNextLine()) {
		  		String line = i.nextLine();
		  		String[] tokens = line.split(","); 		
		  		Song newSong = new Song(tokens, Double.parseDouble(tokens[4]));
		  		list.add(newSong);
		  	}
		} catch(FileNotFoundException e) {
			System.out.println("The database file does not exist.");
			System.out.print("Do you want to create a new one? (Y/N): ");
			choice = input.nextLine();
		}  
	  
		if(choice.charAt(0) == 'Y' || choice.charAt(0) == 'y') {
			SongDB sdb = new SongDB(list);
			sdb.setTitle("Team Roster");
			sdb.setSize(400, 350);
			sdb.setVisible(true);
		  	sdb.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		} else {
			System.out.println("Thank you");
		}
	}
	
	public SongDB(ArrayList<Song> list) {
		songs = new ArrayList<Song>();
    
		if(list.size() > 0) {
			for(Song song : list) {
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

	public void actionPerformed(ActionEvent ae) {
		Object source = ae.getSource();
		Song currentSong;
		boolean isNewSong = false;
  
		if(source == comboBox) {
			currentSong = (Song) comboBox.getSelectedItem();
  
			showSong(currentSong);
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
		} else if(source == bDelete) {
   
		} else if(source == bAccept)
  {
   if(isNewSong)
   {
	   String[] newDat = {tfCode.getText(), tfDesc.getText(), tfArtist.getText(), tfAlbum.getText()};
    currentSong = new Song(newDat, Double.parseDouble(tfPrice.getText()));
    
    songs.add(currentSong);   
    comboBox.addItem(currentSong);
    
    comboBox.setSelectedIndex(songs.size() - 1);
    currentSong = (Song) comboBox.getSelectedItem();
    showSong(currentSong);
   }
   else
   {    
   }
   
   bAccept.setEnabled(false);
   bCancel.setEnabled(false);
   
   bAdd.setEnabled(true);
   bEdit.setEnabled(true);
   bDelete.setEnabled(true);
  }
  else if(source == bCancel)
  {
   bAccept.setEnabled(false);
   bCancel.setEnabled(false);
   
   bAdd.setEnabled(true);
   bEdit.setEnabled(true);
   bDelete.setEnabled(true);
  }
  else // source == bExit
  {
   System.exit(0);
  }
}	
	private void addToPanel(JPanel panel, JComponent[] components) {
		for (int i=0; i<components.length; i++) {
			panel.add(components[i]);
		}
	}

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
