package pizzaPrices;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

public class SizePanel extends JPanel {
	private boolean selected;
	private double price;
	private String[] sizeNames = {"Small", "Medium", "Large"};
	private boolean[] sizeSelected = {false, false, false};
	private JRadioButton[] sizes = new JRadioButton[3];
	
	
	public SizePanel() {
		ButtonGroup group = new ButtonGroup();
		for (int i=0; i<sizes.length; i++) {
			sizes[i] = new JRadioButton(sizeNames[i]);
			group.add(sizes[i]);
		}
		
		
		ActionListener sliceActionListener = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        AbstractButton aButton = (AbstractButton) actionEvent.getSource();
		        
		      }
		    };
		this.addActionListener(sliceActionListener);
	}
	
	private void setPrice(String size) {
		if (sizeSelected[0]) {
			this.price = 10.95;
		} else if (sizeSelected[1]) {
			this.price = 13.95;
		} else if (sizeSelected[2]) {
			this.price = 16.95;
		}
	}
	
	public double getPrice() {
		return price;
	}
	
	protected void setSelected() {
		
	}
	
	public boolean getSelected() {
		return selected;
	}
}
