package GUI;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Help extends JPanel {
    
    private static final long serialVersionUID = 1L;
	private JLabel textHelp ;
    
    public Help() {
        super();
        textHelp = new JLabel("Help Panel") ;
        Border lineborder = BorderFactory.createLineBorder(Color.BLACK, 1);     
        this.setBorder(lineborder);
        this.setPreferredSize(new Dimension(150,30));
        this.add(textHelp);                
    }   
    
    public void setText(String text) {
        textHelp.setText(text); 
    }
    
}