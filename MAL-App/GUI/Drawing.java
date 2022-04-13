package GUI;

import java.awt.Color;
import java.awt.FontMetrics;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Business.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drawing extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;

	// Constucteur
    private Potentiel p;
    
    // Graphique
    private static int width;
    private static int height;
    private Graphics2D g2;
    
    // Selection du mode
    private String mode="Zero";
    
    // Selection de l'echelle
    private double xmax;
    private int grad;
	private int ech;
	
	// Gradient
	private double[][] pot;
	private double minus;
	private double plus;
	private int nbrColor;
	
	// Constructeur de Drawing
    public Drawing(Potentiel p) {
        this.p = p;
        this.setBackground(Color.WHITE);
        addMouseListener(this);
    }   
    
    // Lance les differentes methodes graphique
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Definition des variables Graphique
        g2 = (Graphics2D) g;
        width = this.getWidth();
        height = this.getHeight();
        
        // Selection du mode (definit par Control)
        switch (mode){
        	case "Zero":
        		// Image de base sur le panneau Drawing
        		BufferedImage image=null;
        		try {
        			image = ImageIO.read(new File("photo.png"));
    			} catch (IOException e) {
    				e.printStackTrace();
    			}
            	g2.drawImage(image, 0, 0, width, height, null);
        		break;
        	case "Axes":
            	traceAxes();
            	break;
        	case "Clas":
        		traceA();
            	traceB();
            	traceM();
            	traceAxes();
            	break;
        	case "Grad":
        		gradient();
        		traceA();
            	traceB();
            	traceM();
            	traceAxes();
        		break;
        	default:
        }
    }
    
    // Trace les points
    public void traceA() {
    	g2.setColor(Color.BLACK);
    	g2.drawOval(Conversion.doublepixelX(p.getA().getPoint().getX())-3, Conversion.doublepixelY(p.getA().getPoint().getY())-3, 6, 6);
        g2.drawString(p.getA().getPoint().getName(), Conversion.doublepixelX(p.getA().getPoint().getX())+10, Conversion.doublepixelY(p.getA().getPoint().getY())+10);
    }
	public void traceB() {
		g2.setColor(Color.BLACK);
		g2.drawOval(Conversion.doublepixelX(p.getB().getPoint().getX())-3, Conversion.doublepixelY(p.getB().getPoint().getY())-3, 6, 6);
    	g2.drawString(p.getB().getPoint().getName(), Conversion.doublepixelX(p.getB().getPoint().getX())+10, Conversion.doublepixelY(p.getB().getPoint().getY())+10);
	}        
	public void traceM() {
    	g2.setColor(Color.BLACK);
    	g2.drawOval(Conversion.doublepixelX(p.getM().getX())-3, Conversion.doublepixelY(p.getM().getY())-3, 6, 6);
    	g2.drawString(p.getM().getName(), Conversion.doublepixelX(p.getM().getX())+10, Conversion.doublepixelY(p.getM().getY())+10);
    }
    
    // Color le graphique en fonction du potentiel
    public void gradient() {
    	int i,j;
    	Color c=null;
    	for (i = height-1; i >=0; i--) {
    		for (j = width-1; j >= 0; j--) {
    			c = ColorGradient.getColorGradient(pot[i][j], plus, minus, nbrColor);
    			g2.setColor(c);
    			g2.drawLine(j, i, 1, 1);
    		}
        }
    	int nbrColorMax = 50; // Au dessu de 50, des erreurs d'aproximation forme des lignes transparentes sur l'echelle
    	if (nbrColor<=50) nbrColorMax = nbrColor;
    	for (int x=1; x<=nbrColorMax; x++) {
    		double h = ((double)x)*0.80/((double)nbrColorMax);
    		g2.setColor(Color.getHSBColor((float) h, 1, 1));
    		g2.fillRect(width-230-(int)(200/(double)nbrColorMax)+(int)((double)(x*200)/(double)nbrColorMax), height-40, (int)(200/(double)nbrColorMax), 25);
    	}
    	g2.setColor(Color.BLACK);
    	g2.drawRect(width-230, height-40, 200, 25);
    	FontMetrics metrics = g2.getFontMetrics();
    	g2.setColor(Color.BLACK);
    	if (minus<9999) minus = Double.parseDouble(String.format("%6.3e",Double.parseDouble(Double.toString(minus))));
    	if (plus>9999) plus = Double.parseDouble(String.format("%6.3e",Double.parseDouble(Double.toString(plus))));
    	g2.drawString(Double.toString(minus), width-230-metrics.stringWidth(Double.toString((int)minus))/2, height-2);
    	g2.drawString(Double.toString(plus), width-30-metrics.stringWidth(Double.toString((int)plus))/2, height-2);
    	
    }
    
    // Trace les Axes et les graduations
    public void traceAxes() {
        g2.setColor(Color.BLACK);

        // Trace l'axe Horizontal
        g2.drawLine(10, height/2, width - 10, height/2);
        g2.drawLine(width - 10, height/2, width - 10 - 8, height/2 - 4);
        g2.drawLine(width - 10, height/2, width - 10 - 8, height/2 + 4);
        
        // Trace l'axe Vertical
        g2.drawLine(width/2, height - 5, width/2, 5);
        g2.drawLine(width/2, 5, width/2 - 4, 5 + 8);
        g2.drawLine(width/2, 5, width/2 + 4, 5 + 8);

        // Trace les graduations horizontales
        for (double x = 0; x <= xmax; x=x+grad) {
    			int xe = (int) (x * ech + width/2);
    			g2.drawLine(width - xe, height/2, width - xe, height/2 + 4);
    			g2.drawLine(xe, height/2, xe, height/2 + 4);
    			
        		FontMetrics fontMetrics = g2.getFontMetrics();
        		String textxp = String.format("%.0f", x);
        		String textxn = String.format("%.0f", -x);
        		
        		// Dessine 0 pour qu'il soit centre avec l'axe verticale
        		if (x==0) g2.drawString(textxp, width/2 - 10 - fontMetrics.stringWidth(textxp), height/2 + 20);
        		else {
        			// Centre le text sur la graduation
        			g2.drawString(textxp, xe - fontMetrics.stringWidth(textxp)/2, height/2 + 20);
        			g2.drawString(textxn, width - xe - fontMetrics.stringWidth(textxn)/2, height/2 + 20);
        		}
        }

        // Trace les graduations verticales
        for (double y = 0; y <= xmax; y=y+grad) {
        	// Ne dessine pas 0 (dessine precedement)
        	if (y!=0) {
        		int ye = (int) (y * ech + height/2);
        		g2.drawLine(width/2, height - ye, width/2 - 4, height - ye);
        		g2.drawLine(width/2, ye, width/2 - 4, ye);
        		
        		FontMetrics fontMetrics = g2.getFontMetrics();
        		String textyp = String.format("%.0f", y);
        		String textyn = String.format("%.0f", -y)
        				;
        		// Centre le text à gauche de l'axe
        		g2.drawString(textyn, width/2 - 10 - fontMetrics.stringWidth(textyn), ye+5);
        		g2.drawString(textyp, width/2 - 10 - fontMetrics.stringWidth(textyp), height - ye+5);
        	}
        }
    }
    
    // Renvoi les Dimensions du panneau Drawing
    public static int getWidthDrawing(){
        return width;
    }
    public static int getHeightDrawing(){
        return height;
    }
    
    // Initialisation des variables par Control
    public void setEch(int ech, int xmax, int grad) {
    	this.ech = ech;
    	this.xmax = xmax;
    	this.grad = grad;
    }
    public void setMode(String mode) {
    	this.mode = mode;
    }
    public void setNbrColor(int nbrColor) {
    	int nbrColorMax = nbrColor;
    	if (nbrColor<5) nbrColorMax = 5;
    	this.nbrColor = Math.abs(nbrColorMax);
    }
    public void setPot(double[][] pot, double plus, double minus) {
    	this.pot = pot;
    	this.plus = plus;
    	this.minus = minus;
    }
	
    // Methodes de MouseListener
    @Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		System.out.println("x="+x+" y="+y);
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}