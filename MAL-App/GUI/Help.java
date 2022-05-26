package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class Help extends JPanel {
    
    private static final long serialVersionUID = 1L;
	private JLabel textHelp ;
    
    public Help() {
        super();
        this.setLayout(new BorderLayout());
        textHelp = new JLabel("Help Panel") ;
        Border lineborder = BorderFactory.createLineBorder(Color.BLACK, 1);     
        this.setBorder(lineborder);
        this.setPreferredSize(new Dimension(150,30));
        JPanel labelContainer = new JPanel();
        labelContainer.add(textHelp);
        this.add(labelContainer, BorderLayout.CENTER);     
        
        ImageIcon icon = new ImageIcon("EXIT.png");
		icon = new ImageIcon(icon.getImage().getScaledInstance( 25, 25,  java.awt.Image.SCALE_AREA_AVERAGING));
	   	JButton button = new JButton(icon);
	   	button.setBackground(getBackground());
	   	button.setBorder(BorderFactory.createLineBorder(getBackground(), 1));
        button.addActionListener((ActionEvent evt) -> {System.exit(0); });
        this.add(button, BorderLayout.WEST);
    }   
    
    public void setText(String text) {
        textHelp.setText(text); 
    }
    
}