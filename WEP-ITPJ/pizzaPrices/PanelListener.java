package pizzaPrices;

import java.awt.event.ActionListener;

public class PanelListener implements ActionListener {
	public void actionPerformed( ActionEvent e )
	{
	Object source = e.getSource();
	if ( source == calculateButton )
	{
	// Convert string values to numbers
	double firstValue = Double.parseDouble( firstValueField.getText() );
	double secondValue = Double.parseDouble( secondValueField.getText() );
	double sum = firstValue + secondValue;
	// Convert number to string
	String s = "" + sum;
	sumField.setText( s );
	}
	if ( source == exitButton )
	System.exit( 0 );
	}
}
