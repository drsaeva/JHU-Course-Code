package pizzaPrices;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class PizzaPrices extends JFrame {
	private JLabel[] pizzaPrice = new JLabel[1];
	private JTextField sum;
	private JRadioButton[] sizes = new JRadioButton[3];
	private JCheckBox[] tops = new JCheckBox[3];
	private String[] sizeNames = {"Small", "Medium", "Large"};
	private boolean[] sizeSelected = {false, false, false};
	private String[] toppingNames = {"Pepperoni", "Mushroom", "Sausage"};
	private boolean[] toppingSelected = {false, false, false};
	private int[] toppingPrices = new int[3];
	
			
	public static void main(String[] args) {
		PizzaPrices f = new PizzaPrices();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setTitle("Mama's Pizza");
		f.setSize(400, 267);
		f.setVisible(true);
		
	}
	
	public PizzaPrices() {
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
		ButtonGroup group = new ButtonGroup();
		for (int i=0; i<sizes.length; i++) {
			sizes[i] = new JRadioButton(sizeNames[i]);
			group.add(sizes[i]);
			tops[i] = new JCheckBox(toppingNames[i]);
			tops[i].setSelected(toppingSelected[i]);
			toppingPrices[i] = (toppingSelected[i]) ? 1 : 0;
		}
		
		
		pizzaPrice[0] = new JLabel("Price: $");
		sum = new JTextField(10);
		sum.setEditable( false );
		JPanel size = easyPanelMaker("Size - only one size allowed per pizza", sizes);
		JPanel toppings = easyPanelMaker("Toppings - select no toppings for cheese only", tops);
		JPanel price = easyPanelMaker("Price - this is what the customer pays", pizzaPrice);
		container.add(size);
		container.add(toppings);
		container.add(price);
		this.add(container);
		
		
		
	}
	
	public JPanel easyPanelMaker(String name, Component[] comps) {
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.CENTER));
		panel.setName(name);
		
		for (int i=0; i<comps.length; i++) {
			panel.add(comps[i]);
		}
		
		TitledBorder title;
		title = BorderFactory.createTitledBorder(name);
		panel.setBorder(title);
		return panel;
	}

	
}
