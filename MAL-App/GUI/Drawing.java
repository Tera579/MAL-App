package GUI;

import java.awt.Color;
import java.awt.Desktop;
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
    private Potential p;
    
    // Graphique
    private static int width;
    private static int height;
    private Graphics2D g2;
    private BufferedImage image;
    
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
	private int nbrColor=25;
	
	// Champ electirque
	int densite = 30;
	int longueur = 2;
	
	// Cursor
	int x,y;
	boolean firstclick=false;
	
	// Affichage des Coordonnees
	private boolean showCoord;
	String text;
	int xpixel, ypixel;
	
	// Equipotentiell
	private double[][] MCoord;
	
	//private boolean
	private boolean showEquipote;
	private boolean showField;
	private boolean showQ;
	
	// Constructeur de Drawing
    public Drawing(Potential p) {
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
        g2.setBackground(Color.white);
        width = this.getWidth();
        height = this.getHeight();
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        
        update();
        
        // Affichage du potentiel au cursor
        g2.setColor(Color.BLACK);
        if (mode=="Grad" && firstclick) {
        	g2.drawString(Double.toString(pot[y][x])+"V", x+5, y+3);
        	g2.drawLine(x-3, y-3, x+3, y+3);
        	g2.drawLine(x+3, y-3, x-3, y+3);
        }
        
    }
    // Trace le champ electrique
    public void electricField() {
    	Electric elc = new Electric();
    	Point ElecPoint;
    	double norm, ei, ej;
    	int x0, y0, x1, y1, x2, y2;
    	double t;
    	int pas = (int)(width/densite);
    	for (int y = -1; y < height; y=y+pas) {
    		for (int x = -1; x < width; x=x+pas) {
    			ElecPoint = new Point(Conversion.pixeldoubleX(x), Conversion.pixeldoubleY(y), "Field"); 
    			elc.calculElectric(p.getA(), p.getB(), ElecPoint);
    			ei = elc.geti();
    			ej = elc.getj();
    			norm = Math.sqrt(Math.pow(ei, 2)+Math.pow(ej, 2));
    			x0 = x+(int)(ei/norm*ech/5*longueur);
    			y0 = y-(int)(ej/norm*ech/5*longueur);
    			t = Math.atan2(y-y0, x-x0)-Math.PI;
    			g2.drawLine(x, y, x0, y0); // ligne de la fleche
    			x1 = x0 - (int)(Math.cos(t+Math.PI/4)*5);
    			y1 = y0 - (int)(Math.sin(t+Math.PI/4)*5);
    			g2.drawLine(x0, y0, x1, y1);
    			x2 = x0 - (int)(Math.cos(t-Math.PI/4)*5);
    			y2 = y0 - (int)(Math.sin(t-Math.PI/4)*5);
    			g2.drawLine(x0, y0, x2, y2);
    		}
    	}
    }
    
    public void equipote() {
    	try {
    		for (int i=0; i<100; i++) {
    		if (MCoord[i][0]!=0) g2.drawString("M"+i+"("+MCoord[i][0]+";"+MCoord[i][1]+")", 100, 30*i+30);
    	};
    	}
    	catch(NullPointerException a){
    		
    	}
    	
    	
    }
    // Trace les points
    public void traceA() {
    	g2.setColor(Color.BLACK);
    	xpixel = Conversion.doublepixelX(p.getA().getPoint().getX());
    	ypixel = Conversion.doublepixelY(p.getA().getPoint().getY());
    	g2.drawOval(xpixel-3, ypixel-3, 6, 6);
    	if (showCoord) text = "A ("+p.getA().getPoint().getX()+";"+p.getA().getPoint().getY()+")";
    	else text = "A";
    	g2.drawString(text, xpixel+10, ypixel+10);
    	if (showQ) g2.drawString(Double.toString(p.getA().getQ())+"nC", xpixel+10, ypixel+25);
    }
	public void traceB() {
		g2.setColor(Color.BLACK);
		xpixel = Conversion.doublepixelX(p.getB().getPoint().getX());
    	ypixel = Conversion.doublepixelY(p.getB().getPoint().getY());
    	g2.drawOval(xpixel-3, ypixel-3, 6, 6);
    	if (showCoord) text = "B ("+p.getB().getPoint().getX()+";"+p.getB().getPoint().getY()+")";
    	else text = "B";
    	g2.drawString(text, xpixel+10, ypixel+10);
    	if (showQ) g2.drawString(Double.toString(p.getB().getQ())+"nC", xpixel+10, ypixel+25);
	}      
	
    // Color le graphique en fonction du potentiel
    public void gradientColor() {
    	int i,j;
    	Color c=null;
    	for (i = 0; i < height; i++) {
    		for (j = 0; j < width; j++) {
    			c = ColorGradient.getColorGradient(pot[i][j], plus, minus, nbrColor);
    			image.setRGB(j, i, c.getRGB());
    		}
        }
    	g2.drawImage(image, null, 0, 0);
    }
    
    public void gradientScale() {
    	for (int x=1; x<=nbrColor; x++) {
    		double h = ((double)x)*0.65/((double)nbrColor);
    		g2.setColor(Color.getHSBColor((float) h, 1, 1));
    		g2.fillRect(width-230-(int)(200/(double)nbrColor)+(int)((double)(x*200)/(double)nbrColor), height-40, (int)(200/(double)nbrColor)+1, 25);
    	}
    	g2.setColor(Color.BLACK);
    	g2.drawRect(width-230, height-40, 200, 25);
    	FontMetrics metrics = g2.getFontMetrics();
    	g2.setColor(Color.BLACK);
    	if (minus<9999) minus = Double.parseDouble(String.format("%6.3e",Double.parseDouble(Double.toString(minus))));
    	if (plus>9999) plus = Double.parseDouble(String.format("%6.3e",Double.parseDouble(Double.toString(plus))));
    	g2.drawString(Double.toString(minus), width-230-metrics.stringWidth(Double.toString((int)minus))/2, height-2);
    	g2.drawString("0", width-130-metrics.stringWidth("0")/2, height-2);
    	g2.drawLine(width-130, height-40, width-130, height-15);
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
    
    // Selection du mode (definit par Control)
    public void update() {
        switch (mode){
        	case "Zero":
        		break;
        	case "Axes":
        		traceAxes();
        		break;
        	case "Clas":
        		traceA();
                traceB();
        	    //if (showM) traceM();
                traceAxes();
        		break;
        	case "Grad":
        		gradientColor();
        		traceA();
                traceB();
        	    //if (showM) traceM();
                traceAxes();
                gradientScale();
        		break;
        	default:
        }
        if (showField) {
        	electricField();
        	if (mode=="Grad") gradientScale(); // repaint l'echelle de gradient sur le field
        }
        if (showEquipote) {
        	equipote();
        	if (mode=="Grad") gradientScale(); // repaint l'echelle de gradient sur le field
        }
    }
    
    // Enregistre l'image 
     public void saveImage() {
    	g2 = image.createGraphics();
    	g2.setColor(Color.white);
    	g2.fillRect(0, 0, width, height);
        try {
            File output3 = new File("output.png");
            update();
            ImageIO.write(image, "png", output3);
            Desktop.getDesktop().open(new File("output.png"));
         } catch(IOException log) {
            System.out.println(log);
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
    	if (nbrColor<10) nbrColorMax = 10;
    	if (this.nbrColor != Math.abs(nbrColorMax)) {
    		this.nbrColor = Math.abs(nbrColorMax);
    	}
    	
    }
    public void setPot(double[][] pot, double plus, double minus) {
    	this.pot = pot;
    	this.plus = plus;
    	this.minus = minus;
    }
    
    public void setShowCoord(boolean showCoord) {
    	this.showCoord = showCoord;
    }
    
    public void setShowQ(boolean showQ) {
    	this.showQ = showQ;
    	System.out.println(showQ);
    }
    
    public void setShowField(boolean showField) {
    	this.showField = showField;
    }
    
    public void setShowEquipote(boolean showEquipote) {
    	this.showEquipote = showEquipote;
    }
    
    public void setEquipote(double[][] MCoord) {
    	this.MCoord = MCoord;
    }
    
    public void setField(int longueur, int densite) {
    	this.longueur = longueur;
    	this.densite = densite;
    }
	
    // Methodes de MouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		if (mode=="Grad") {
			x = e.getX();
			y = e.getY();
			this.repaint();
			firstclick = true;
		}
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