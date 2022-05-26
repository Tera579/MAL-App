package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.FontMetrics;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import Business.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Drawing extends JPanel implements MouseListener{
	
	private static final long serialVersionUID = 1L;
	
	// Potential
	double[][] pot;
	double plus;
	double minus;

	// Constucteur
    private Potential p;
    private Control panelControl;
    
    // Graphique
    private static int width;
    private static int height;
    private Graphics2D g2;
    private BufferedImage image;
    
	// Cursor
	private int x,y;
	
	// Affichage des Coordonnees
	private String text;
	
	
	// Constructeur de Drawing
    public Drawing(Potential p, Control panelControl) {
        this.p = p;
        this.panelControl = panelControl;
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
    }
    // Trace le champ electrique
    public void electricField() {
    	Electric elc = new Electric();
    	Point ElecPoint;
    	double norm, ei, ej;
    	int x0, y0, x1, y1, x2, y2;
    	double t;
    	int pas = (int)(width/panelControl.densite);
    	for (int y = -1; y < height; y=y+pas) {
    		for (int x = -1; x < width; x=x+pas) {
    			ElecPoint = new Point(Conversion.pixeldoubleX(x), Conversion.pixeldoubleY(y), "Field"); 
    			elc.calculElectric(p.getA(), p.getB(), ElecPoint);
    			ei = elc.geti();
    			ej = elc.getj();
    			norm = Math.sqrt(Math.pow(ei, 2)+Math.pow(ej, 2));
    			x0 = x+(int)(ei/norm*panelControl.longueur);
    			y0 = y-(int)(ej/norm*panelControl.longueur);
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
    public void drawEquipote(double xStart, double yStart) {
    	Electric elc = new Electric();
    	Point ElecPoint;
    	double norm, ei, ej;
    	double x0=0, y0=0;
    	int xpixel = Conversion.doublepixelX(xStart);
    	int ypixel = Conversion.doublepixelY(yStart);
		boolean breaked = false;
		g2.setColor(Color.BLACK);
    	int espace = 3;
    	Polygon a = new Polygon();
		a = new Polygon(new int[]{xpixel-espace, xpixel-espace, xpixel+espace, xpixel+espace}, new int[]{ypixel-espace, ypixel+espace, ypixel+espace, ypixel-espace}, 4);
    	for (int j = 0; j < 10000; j++) {
			ElecPoint = new Point(xStart, yStart, "Field"); 
			elc.calculElectric(p.getA(), p.getB(), ElecPoint);
			ei = elc.geti();
			ej = elc.getj();
			norm = Math.sqrt(Math.pow(ei, 2)+Math.pow(ej, 2));
			x0 = xStart+ej/norm/panelControl.ech*3;
			y0 = yStart-ei/norm/panelControl.ech*3;
			boolean run=true;
			double x0clone=x0, y0clone=y0;
			int limit = 0;
			do {
				limit++;
				p.calculPotential(p.getA(), p.getB(), new Point (x0, y0, "Field"));
				double px0 = p.getV();
				p.calculPotential(p.getA(), p.getB(), new Point (x0clone, y0clone, "Field"));
    			double px0clone = p.getV();
    			x0clone = x0clone + ei/norm/panelControl.ech;
				y0clone = y0clone + ej/norm/panelControl.ech;
				x0 = x0 - ei/norm/panelControl.ech;
				y0 = y0 - ej/norm/panelControl.ech;
				p.calculPotential(p.getA(), p.getB(), new Point (x0, y0, "Field"));
				double px0next = p.getV();
				p.calculPotential(p.getA(), p.getB(), new Point (x0clone, y0clone, "Field"));
    			double px0clonenext = p.getV();
    			if ((px0/pot[ypixel][xpixel]<1 && px0next/pot[ypixel][xpixel]>1) || (px0/pot[ypixel][xpixel]>1 && px0next/pot[ypixel][xpixel]<1)) {
    				run = false;
    			}
    			if ((px0clone/pot[ypixel][xpixel]<1 && px0clonenext/pot[ypixel][xpixel]>1) || (px0clone/pot[ypixel][xpixel]>1 && px0clonenext/pot[ypixel][xpixel]<1)) {
    				x0 = x0clone;
    				y0 = y0clone;
    				run = false;
    			}
    			if (a.contains(Conversion.doublepixelX(x0), Conversion.doublepixelY(y0)) && j>30) {
    				breaked = true;
    				break;
    			}
    			if (limit>10000) break;
			} while (run);
			g2.drawLine(Conversion.doublepixelX(xStart), Conversion.doublepixelY(yStart), Conversion.doublepixelX(x0), Conversion.doublepixelY(y0));
			xStart = x0;
			yStart = y0;
			if (breaked) break;
		}
    	g2.drawLine(Conversion.doublepixelX(x0), Conversion.doublepixelY(y0),  Conversion.doublepixelX(x0), Conversion.doublepixelY(y0));
    }
    
    public void equipote() {
    	g2.setStroke(new BasicStroke(2));
    	int numLines = 18;
    	int xA = Conversion.doublepixelX(p.getA().getPoint().getX());
    	int yA = Conversion.doublepixelY(p.getA().getPoint().getY());
    	int xB = Conversion.doublepixelX(p.getB().getPoint().getX());
    	int yB = Conversion.doublepixelY(p.getB().getPoint().getY());
    	int dx=0, dy=0, xFinder=1, yFinder=1;
    	int norm = (int) Math.sqrt(Math.pow(xA-xB, 2)+Math.pow(yA-yB, 2));
    	if (4*numLines < norm) {
    		if (xA<xB) {
        		xFinder=1;
        		dx = (xB-xA)/(numLines);
        	}
        	if (xB<xA) {
        		xFinder=-1;
        		dx = (xA-xB)/(numLines);
        	}
        	if (yA<yB) {
        		yFinder=1;
        		dy = (yB-yA)/(numLines);
        	}
        	if (yB<yA) {
        		yFinder=-1;
        		dy = (yA-yB)/(numLines);
        	}
        	try {
        		int j=0;
        		for (int i=0; i<numLines; i++) {
        			j=j+1;
        			drawEquipote(Conversion.pixeldoubleX(xA + dx*j*xFinder), Conversion.pixeldoubleY(yA + dy*j*yFinder));
        		}
        	}
        	catch(NullPointerException b){}
        	
        	try {
        		for (int i=0; i<panelControl.nbrMintEqui; i++) {
        			int xpixel = Conversion.doublepixelX(panelControl.MCoordEqui[i][0]);
        	    	int ypixel = Conversion.doublepixelY(panelControl.MCoordEqui[i][1]);
        	    	g2.drawOval(xpixel-3, ypixel-3, 6, 6);
        	    	if (panelControl.affCoordSelected) text = "M"+i+" ("+panelControl.MCoordEqui[i][0]+";"+panelControl.MCoordEqui[i][1]+")";
        	    	else text = "M"+i;
        	    	g2.drawString(text, xpixel+10, ypixel+10);
        	    	g2.drawString(((double)((int)((pot[ypixel][xpixel])*100)))/100+"V", xpixel+10, ypixel+25);
        	    	drawEquipote(panelControl.MCoordEqui[i][0], panelControl.MCoordEqui[i][1]);
        		}
        	}
        	catch(NullPointerException b){}
    	}
    	else g2.drawString("Zoomez !", xA+10, yA-10);
    	g2.setStroke(new BasicStroke(1));
    }
    
    private void drawFieldLines (double xStart, double yStart, int directORindircet, Polygon a, Polygon b) {
    	Electric elc = new Electric();
    	Point ElecPoint;
    	double norm, ei, ej, x0, y0;
    	int x0Pixel, y0Pixel, xPixel, yPixel;
    	for (int i=0; i<10000; i++) {
    		ElecPoint = new Point(xStart, yStart, "Field"); 
			elc.calculElectric(p.getA(), p.getB(), ElecPoint);
			ei = elc.geti()*directORindircet;
			ej = elc.getj()*directORindircet;
			norm = Math.sqrt(Math.pow(ei, 2)+Math.pow(ej, 2));
			x0 = xStart+ei/norm/panelControl.ech*3;
			y0 = yStart+ej/norm/panelControl.ech*3;
			xPixel = Conversion.doublepixelX(xStart);
	    	yPixel = Conversion.doublepixelY(yStart);
	    	x0Pixel = Conversion.doublepixelX(x0);
			y0Pixel = Conversion.doublepixelY(y0);
			if ((a.contains(x0Pixel, y0Pixel) || b.contains(x0Pixel, y0Pixel)) && i>2) {
				if (a.contains(x0Pixel, y0Pixel)) g2.drawLine(xPixel, yPixel, Conversion.doublepixelX(p.getA().getPoint().getX()), Conversion.doublepixelY(p.getA().getPoint().getY()));
				if (b.contains(x0Pixel, y0Pixel)) g2.drawLine(xPixel, yPixel, Conversion.doublepixelX(p.getB().getPoint().getX()), Conversion.doublepixelY(p.getB().getPoint().getY()));
				break;
			}
			g2.drawLine(xPixel, yPixel, x0Pixel, y0Pixel);
			if (i%30==0 && panelControl.affChampLigneDirection && i!=0) {
				double t = Math.atan2(yStart-y0, xStart-x0)-Math.PI;
    			int x1 = Conversion.doublepixelX(x0 + Math.cos(t+Math.PI/4)/panelControl.ech*10*directORindircet);
    			int y1 = Conversion.doublepixelY(y0 + Math.sin(t+Math.PI/4)/panelControl.ech*10*directORindircet);
    			g2.drawLine(x0Pixel, y0Pixel, x1, y1);
    			int x2 = Conversion.doublepixelX(x0 + Math.cos(t-Math.PI/4)/panelControl.ech*10*directORindircet);
    			int y2 = Conversion.doublepixelY(y0 + Math.sin(t-Math.PI/4)/panelControl.ech*10*directORindircet);
    			g2.drawLine(x0Pixel, y0Pixel, x2, y2);
			}
			xStart = x0;
			yStart = y0;
		}
    }
    public void fieldLines() {
    	
    	int numLines = 18;
    	
    	g2.setStroke(new BasicStroke(2));
    	double xA = p.getA().getPoint().getX();
    	double yA = p.getA().getPoint().getY();
    	double xB = p.getB().getPoint().getX();
    	double yB = p.getB().getPoint().getY();
    	int xAPixel = Conversion.doublepixelX(xA);
    	int yAPixel = Conversion.doublepixelY(yA);
    	int xBPixel = Conversion.doublepixelX(xB);
    	int yBPixel = Conversion.doublepixelY(yB);
    	int espace = 5;
    	Polygon a = new Polygon();
		a = new Polygon(new int[]{xAPixel-espace, xAPixel-espace, xAPixel+espace, xAPixel+espace}, new int[]{yAPixel-espace, yAPixel+espace, yAPixel+espace, yAPixel-espace}, 4);
		Polygon b = new Polygon();
		b = new Polygon(new int[]{xBPixel-espace, xBPixel-espace, xBPixel+espace, xBPixel+espace}, new int[]{yBPixel-espace, yBPixel+espace, yBPixel+espace, yBPixel-espace}, 4);
		double d=1;
		try {
        	g2.setColor(Color.DARK_GRAY);
    	    for (int i=0; i<numLines; i++) {
    	    	if (p.getA().getQ()==0 && p.getB().getQ()!=0) {
    	    		drawFieldLines (xB + d*Math.cos(Math.PI/numLines*2*i), yB - d*Math.sin(Math.PI/numLines*2*i), 1, a ,b);
            	   	drawFieldLines (xB + d*Math.cos(Math.PI/numLines*2*i), yB - d*Math.sin(Math.PI/numLines*2*i), -1, a ,b);
    	    	}
    	    	if (p.getA().getQ()!=0 && p.getB().getQ()==0) {
    	    		drawFieldLines (xA + d*Math.cos(Math.PI/numLines*2*i), yA - d*Math.sin(Math.PI/numLines*2*i), 1, a ,b);
            	   	drawFieldLines (xA + d*Math.cos(Math.PI/numLines*2*i), yA - d*Math.sin(Math.PI/numLines*2*i), -1, a ,b);
    	    	}
    	    	if (p.getA().getQ()!=0 && p.getB().getQ()!=0) {
    	    		if (p.getA().getQ()/p.getB().getQ()<0) {
    	    			if (Math.abs(p.getA().getQ())<Math.abs(p.getB().getQ())) {
            	    		drawFieldLines (xA + d*Math.cos(Math.PI/numLines*2*i), yA - d*Math.sin(Math.PI/numLines*2*i), 1, a ,b);
                    	   	drawFieldLines (xA + d*Math.cos(Math.PI/numLines*2*i), yA - d*Math.sin(Math.PI/numLines*2*i), -1, a ,b);
            	    	}
        	    		else {
                    	   	drawFieldLines (xB + d*Math.cos(Math.PI/numLines*2*i), yB - d*Math.sin(Math.PI/numLines*2*i), 1, a ,b);
                    	   	drawFieldLines (xB + d*Math.cos(Math.PI/numLines*2*i), yB - d*Math.sin(Math.PI/numLines*2*i), -1, a ,b);
            	    	}
    	    		}
    	    		if (p.getA().getQ()/p.getB().getQ()>0) {
    	    			drawFieldLines (xA + d*Math.cos(Math.PI/numLines*2*i), yA - d*Math.sin(Math.PI/numLines*2*i), 1, a ,b);
                	   	drawFieldLines (xA + d*Math.cos(Math.PI/numLines*2*i), yA - d*Math.sin(Math.PI/numLines*2*i), -1, a ,b);
                	   	drawFieldLines (xB + d*Math.cos(Math.PI/numLines*2*i), yB - d*Math.sin(Math.PI/numLines*2*i), 1, a ,b);
                	   	drawFieldLines (xB + d*Math.cos(Math.PI/numLines*2*i), yB - d*Math.sin(Math.PI/numLines*2*i), -1, a ,b);
    	    		}
    	    	}
    		}
    	}
    	catch(NullPointerException c){}
    	g2.setColor(Color.BLACK);
    	try {
    		for (int x=0; x<panelControl.nbrMintFieldLines; x++) {
    			int xpixel = Conversion.doublepixelX(panelControl.MCoordFieldLines[x][0]);
    			int ypixel = Conversion.doublepixelY(panelControl.MCoordFieldLines[x][1]);
    			g2.drawOval(xpixel-3, ypixel-3, 6, 6);
    	    	if (panelControl.affCoordSelected) text = "M"+x+"' ("+panelControl.MCoordFieldLines[x][0]+";"+panelControl.MCoordFieldLines[x][1]+")";
    	    	else text = "M"+x+"'";
    	    	g2.drawString(text, xpixel+10, ypixel+10);
    			drawFieldLines (panelControl.MCoordFieldLines[x][0], panelControl.MCoordFieldLines[x][1], 1, a ,b);
    	    	drawFieldLines (panelControl.MCoordFieldLines[x][0], panelControl.MCoordFieldLines[x][1], -1, a ,b);
    		};
    	}
    	catch(NullPointerException c){}
    	g2.setStroke(new BasicStroke(1));
    }
    // Trace les points
    public void traceA() {
    	g2.setColor(Color.BLACK);
    	int xpixel = Conversion.doublepixelX(p.getA().getPoint().getX());
    	int ypixel = Conversion.doublepixelY(p.getA().getPoint().getY());
    	g2.fillOval(xpixel-3, ypixel-3, 6, 6);
    	if (panelControl.affCoordSelected) text = "A ("+p.getA().getPoint().getX()+";"+p.getA().getPoint().getY()+")";
    	else text = "A";
    	g2.drawString(text, xpixel+10, ypixel+10);
    	if (panelControl.affQSelected) g2.drawString(Double.toString(p.getA().getQ())+"nC", xpixel+10, ypixel+25);
    }
	public void traceB() {
		g2.setColor(Color.BLACK);
		int xpixel = Conversion.doublepixelX(p.getB().getPoint().getX());
    	int ypixel = Conversion.doublepixelY(p.getB().getPoint().getY());
    	g2.fillOval(xpixel-3, ypixel-3, 6, 6);
    	if (panelControl.affCoordSelected) text = "B ("+p.getB().getPoint().getX()+";"+p.getB().getPoint().getY()+")";
    	else text = "B";
    	g2.drawString(text, xpixel+10, ypixel+10);
    	if (panelControl.affQSelected) g2.drawString(Double.toString(p.getB().getQ())+"nC", xpixel+10, ypixel+25);
	}      
	
    // Color le graphique en fonction du potentiel
    public void gradientColor() {
    	int i,j;
    	Color c=null;
    	for (i = 0; i < height; i++) {
    		for (j = 0; j < width; j++) {
    			c = ColorGradient.getColorGradient(pot[i][j], plus, minus, panelControl.nbrColor);
    			image.setRGB(j, i, c.getRGB());
    		}
        }
    	g2.drawImage(image, null, 0, 0);
    }
    
    public void gradientCursor() {
    	g2.drawString(((double)((int)((pot[y][x])*100)))/100+"V", x+5, y+3);
    	g2.drawLine(x-3, y-3, x+3, y+3);
    	g2.drawLine(x+3, y-3, x-3, y+3);
    }
    
    public void gradientScale() {
    	for (int x=1; x<=panelControl.nbrColor; x++) {
    		double h = ((double)x)*0.65/((double)panelControl.nbrColor);
    		g2.setColor(Color.getHSBColor((float) h, 1, 1));
    		g2.fillRect(width-230-(int)(200/(double)panelControl.nbrColor)+(int)((double)(x*200)/(double)panelControl.nbrColor), height-40, (int)(200/(double)panelControl.nbrColor)+1, 25);
    	}
    	g2.setColor(Color.BLACK);
    	g2.drawRect(width-230, height-40, 200, 25);
    	FontMetrics metrics = g2.getFontMetrics();
    	g2.setColor(Color.BLACK);
    	double minusPower = minus;
    	double plusPower = plus;  
        String minusPowerString=String.valueOf(((double)((int)((minus)*100)))/100);
        String plusPowerString=String.valueOf(((double)((int)((plus)*100)))/100);
        int count = 0;
        if (minus < -10000){
            while (minusPower < -1) {
                minusPower = minusPower /10;
                count = count + 1;
                minusPowerString = "-10^"+String.valueOf(count);
            }
        }
        if (minus > 10000){
            while (minusPower > 1) {
                minusPower = minusPower /10;
                count = count + 1;
                minusPowerString = "10^"+String.valueOf(count);
            }
        }
        count = 0;
        if (plus < -10000){
            while (plusPower < -1) {
                plusPower = plusPower /10;
                count = count + 1;
                plusPowerString = "-10^"+String.valueOf(count);
            }
        }
        if (plus > 10000){
            while (plusPower > 1) {
                plusPower = plusPower /10;
                count = count + 1;
                plusPowerString = "10^"+String.valueOf(count);
            }
        }
         
    g2.drawString(minusPowerString, width-230-metrics.stringWidth(minusPowerString)/2, height-2);
    g2.drawString("0", width-130-metrics.stringWidth("0")/2, height-2);
    g2.drawLine(width-130, height-40, width-130, height-15);
    g2.drawString(plusPowerString, width-30-metrics.stringWidth(plusPowerString)/2, height-2);
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
        for (double x = 0; x <= panelControl.xmax; x=x+panelControl.grad) {
    			int xe = (int) (x * panelControl.ech + width/2);
    			g2.drawLine(width - xe, height/2, width - xe, height/2 + 4);
    			g2.drawLine(xe, height/2, xe, height/2 + 4);
    			
        		FontMetrics fontMetrics = g2.getFontMetrics();
        		String textxp = String.valueOf(x);
        		String textxn = String.valueOf(-x);
        		
        		// Dessine 0 pour qu'il soit centre avec l'axe verticale
        		if (x==0) g2.drawString("0", width/2 - 10 - fontMetrics.stringWidth("0"), height/2 + 20);
        		else {
        			// Centre le text sur la graduation
        			g2.drawString(textxp, xe - fontMetrics.stringWidth(textxp)/2, height/2 + 20);
        			g2.drawString(textxn, width - xe - fontMetrics.stringWidth(textxn)/2, height/2 + 20);
        		}
        }

        // Trace les graduations verticales
        for (double y = 0; y <= panelControl.xmax; y=y+panelControl.grad) {
        	// Ne dessine pas 0 (dessine precedement)
        	if (y!=0) {
        		int ye = (int) (y * panelControl.ech + height/2);
        		g2.drawLine(width/2, height - ye, width/2 - 4, height - ye);
        		g2.drawLine(width/2, ye, width/2 - 4, ye);
        		
        		FontMetrics fontMetrics = g2.getFontMetrics();
        		String textyp = String.valueOf(y);
        		String textyn = String.valueOf(-y);
        				;
        		// Centre le text à gauche de l'axe
        		g2.drawString(textyn, width/2 - 10 - fontMetrics.stringWidth(textyn), ye+5);
        		g2.drawString(textyp, width/2 - 10 - fontMetrics.stringWidth(textyp), height - ye+5);
        	}
        }
    }
    
    // Selection du mode (definit par Control)
    public void update() {
    	PotentialField Field = new PotentialField(p);
 	   	pot = Field.getPotentialField();
 	   	plus = Field.getPotentialFieldPlus();
 	    minus = Field.getPotentialFieldMinus();
    	try {
    		if (panelControl.gradientSelected) {
    			try {gradientColor();}
    			catch (ArrayIndexOutOfBoundsException e) {this.repaint();}
    		}
    		if (panelControl.champSelected) {
    			try {electricField();}
    			catch (ArrayIndexOutOfBoundsException e) {this.repaint();}
    		}
        	if (panelControl.equipoteSelected) {
    			try {equipote();}
    			catch (ArrayIndexOutOfBoundsException e) {this.repaint();}
    		} 
        	if (panelControl.champLigneSelected) fieldLines();
        	if (panelControl.gradientSelected) gradientScale(); // repaint l'echelle de gradient sur le field
        	if (panelControl.Point.isEnabled()) {
        		traceAxes();
        		traceA();
        		traceB();
        	}
        	if (panelControl.gradientCurseurSelected)gradientCursor();
    	}
    	catch(NullPointerException a) {}
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
         }
     }
    
    // Renvoi les Dimensions du panneau Drawing
    public static int getWidthDrawing(){
        return width;
    }
    public static int getHeightDrawing(){
        return height;
    }
    
	
    // Methodes de MouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		x = e.getX();
		y = e.getY();
		if (panelControl.equipoteSelected) {
			panelControl.Para8.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
			panelControl.Para8.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
		}
		if (panelControl.champLigneSelected) {
		panelControl.Para10.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
		panelControl.Para10.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
		}
		if (panelControl.panPoint.isVisible()) {
			if (e.getButton()==1) {
				panelControl.AXYQ.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
				panelControl.AXYQ.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
				p.getA().getPoint().setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
				p.getA().getPoint().setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
			}
			if (e.getButton()==3) {
				panelControl.BXYQ.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
				panelControl.BXYQ.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
				p.getB().getPoint().setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
				p.getB().getPoint().setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
			}
		}
		this.repaint();
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