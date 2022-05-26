package Business;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class EntryABM {
	
	private Double x=0.0,y=0.0,q=0.0;
	private double TextDouble=0;
	private boolean valid;
	private JPanel XYQ,XY,TextPanel;
	private JTextField xtext,ytext,qtext,textField;
	private Color c = new Color(238, 238, 238);
	private Potential p;
	public EntryABM(String name, boolean q, Potential p) {
		this.p = p;
		if (q) {
			xtext = new JFormattedTextField() ;
	    	xtext.setBackground(c);
	    	xtext.setColumns(3);
	    	xtext.setBorder(BorderFactory.createLineBorder(c, 2));
	    	xtext.setHorizontalAlignment(JTextField.CENTER);
	        ytext = new JFormattedTextField() ;
	        ytext.setBackground(c);
	        ytext.setColumns(3);
	        ytext.setBorder(BorderFactory.createLineBorder(c, 2));
	        ytext.setHorizontalAlignment(JTextField.CENTER);
	        qtext = new JFormattedTextField() ;
	        qtext.setBackground(c);
	        qtext.setColumns(2);
	        qtext.setBorder(BorderFactory.createLineBorder(c, 2));
	        qtext.setHorizontalAlignment(JTextField.CENTER);

	        XYQ = new JPanel();
	        XYQ.setLayout(new BoxLayout(XYQ, BoxLayout.X_AXIS));
	        
	        XYQ.add(new JLabel(" "+name+"=("));
	        XYQ.add(xtext);
	        XYQ.add(new JLabel(";"));
	        XYQ.add(ytext);
	        XYQ.add(new JLabel(")"));
	        XYQ.add(new JLabel("   q"+name+"="));
	        XYQ.add(qtext);
	        XYQ.add(new JLabel("nC "));
		}
		else {
			xtext = new JFormattedTextField() ;
	    	xtext.setBackground(c);
	    	xtext.setColumns(3);
	    	xtext.setBorder(BorderFactory.createLineBorder(c, 2));
	    	xtext.setHorizontalAlignment(JTextField.CENTER);
	        ytext = new JFormattedTextField() ;
	        ytext.setBackground(c);
	        ytext.setColumns(3);
	        ytext.setBorder(BorderFactory.createLineBorder(c, 2));
	        ytext.setHorizontalAlignment(JTextField.CENTER);
	        
	        XY = new JPanel();
	        XY.setLayout(new BoxLayout(XY, BoxLayout.X_AXIS));
	        
	        XY.add(new JLabel(" "+name+"=("));
	        XY.add(xtext);
	        XY.add(new JLabel(";"));
	        XY.add(ytext);
	        XY.add(new JLabel(")"));
		}
	}
	
	public EntryABM(String name, String textField, boolean Header) {
		TextPanel = new JPanel();
		if (Header) {
			JLabel label = new JLabel(name, SwingConstants.CENTER);
			label.setBackground(Color.BLACK);
			label.setOpaque(true);
			label.setForeground(Color.WHITE);
			TextPanel.add(label);
		}
		else {
			TextPanel.add(new JLabel(name));
	    	this.textField = new JFormattedTextField() ;
	    	this.textField.setBackground(c);
	    	this.textField.setBorder(BorderFactory.createLineBorder(c, 2));
	    	this.textField.setHorizontalAlignment(JTextField.CENTER);
	    	this.textField.setText(textField);
	    	this.textField.setColumns(3);
	    	TextPanel.add(this.textField);
		}
	}
			
	public boolean check(String ABMM0) {
		valid=true;
		switch(ABMM0) {
		case "A":
			try{x=Double.parseDouble(xtext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		xtext.setText(""+p.getA().getPoint().getX());
        		System.out.println("le X de A est non conforme");}
			try{y=Double.parseDouble(ytext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		ytext.setText(""+p.getA().getPoint().getY());
        		System.out.println("le Y de A est non conforme");}
			try{q=Double.parseDouble(qtext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		qtext.setText(""+p.getA().getQ());
        		System.out.println("le Q de A est non conforme");}
		   p.getA().setQ(q);
      	   p.getA().getPoint().setX(x);
      	   p.getA().getPoint().setY(y);
			break;
			
		case "B":
			try{x=Double.parseDouble(xtext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		xtext.setText(""+p.getB().getPoint().getX());
        		System.out.println("le X de B est non conforme");}
			try{y=Double.parseDouble(ytext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		ytext.setText(""+p.getB().getPoint().getY());
        		System.out.println("le Y de B est non conforme");}
			try{q=Double.parseDouble(qtext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		qtext.setText(""+p.getB().getQ());
        		System.out.println("le Q de B est non conforme");}
			p.getB().setQ(q);
	      	p.getB().getPoint().setX(x);
	        p.getB().getPoint().setY(y);
			break;
			
		case "M":
			try{x=Double.parseDouble(xtext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		xtext.setText(""+p.getM().getX());
        		System.out.println("le X de M est non conforme");}
			try{y=Double.parseDouble(ytext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		ytext.setText(""+p.getM().getY());
        		System.out.println("le Y de M est non conforme");}
			break;
		case "M0":
			try{x=Double.parseDouble(xtext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		xtext.setText("");
        		System.out.println("le X de M0 est non conforme");}
			try{y=Double.parseDouble(ytext.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		ytext.setText("");
        		System.out.println("le Y de M0 est non conforme");}
			break;
		case "Text":
			try{TextDouble=Double.parseDouble(textField.getText());}
        	catch(NumberFormatException a){
        		valid=false;
        		textField.setText("");
        		System.out.println("la saisie est non conforme");}
			break;
		default:
			System.out.println("Le point n'est pas reconnu :)");
		}
		System.out.println("check "+valid);
		return valid;
	}
	public Double getX() {
		return x;
	}
	public Double getY() {
		return y;
	}
	public Double getQ() {
		return q;
	}
	public double getTextDouble() {
		return TextDouble;
	}
	public JPanel getXYQ() {
		return XYQ;
	}
	public JPanel getXY() {
		return XY;
	}
	public JPanel getTextPanel() {
		return TextPanel;
	}
	public void setX(Double x) {
		if (x==null) xtext.setText("");
		else xtext.setText(Double.toString(x));
	}
	public void setY(Double y) {
		if (y==null) ytext.setText("");
		else ytext.setText(Double.toString(y));
	}
	public void setQ(Double q) {
		if (q==null) qtext.setText("");
		else qtext.setText(Double.toString(q));
	}
	public void setText(String Text) {
		textField.setText(Text);
	}
}
