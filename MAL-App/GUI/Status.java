package GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Status extends JPanel {
    
    private static final long serialVersionUID = 1L;
	private JLabel textStatus ;
    
    public Status() {
        super();
        textStatus = new JLabel("Status Panel") ;
        Border lineborder = BorderFactory.createLineBorder(Color.BLACK, 1);     
        this.setBorder(lineborder);
        this.setPreferredSize(new Dimension(150,30));
        this.add(textStatus);                
    }   

    public void setText(String text) {
        textStatus.setText(text); 
    }
}
