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
    private Control panelControl;
    
    // Graphique
    private static int width;
    private static int height;
    private Graphics2D g2;
    private BufferedImage image;
    
	// Cursor
	private int x,y;
	private boolean firstclick=false;
	
	// Affichage des Coordonnees
	private String text;
	private int xpixel, ypixel;
	
	// Tracer A et B click
	boolean traceA, traceB;
	
	
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
        if (traceA) {
        	traceA();
        	if (traceB) {
        		traceB();
        	}
        } 
        else {
        	traceA=false;
        	traceB=false;
        }
        update();
        
        // Affichage du potentiel au cursor
        g2.setColor(Color.BLACK);
        if (panelControl.mode=="Grad" && firstclick) {
        	g2.drawString(Double.toString(panelControl.pot[y][x])+"V", x+5, y+3);
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
    
    public void equipote() {
    	try {
    		for (int i=0; i<panelControl.nbrMintEqui; i++) {
    			g2.setColor(Color.BLACK);
    	    	xpixel = Conversion.doublepixelX(panelControl.MCoordEqui[i][0]);
    	    	ypixel = Conversion.doublepixelY(panelControl.MCoordEqui[i][1]);
    	    	g2.drawOval(xpixel-3, ypixel-3, 6, 6);
    	    	if (panelControl.ShowCoordselected) text = "M"+i+" ("+panelControl.MCoordEqui[i][0]+";"+panelControl.MCoordEqui[i][1]+")";
    	    	else text = "M"+i;
    	    	g2.drawString(text, xpixel+10, ypixel+10);
    	    	g2.drawString(panelControl.pot[ypixel][xpixel]+"V", xpixel+10, ypixel+25);
    	};
    	}
    	catch(NullPointerException a){
    		
    	}
    }
    
    public void fieldLines() {
    	Electric elc = new Electric();
    	Point ElecPoint;
    	double norm, ei, ej;
    	int x0, y0;
    	g2.setColor(Color.BLACK);
    	try {
    		for (int x=0; x<panelControl.nbrMintFieldLines; x++) {
    			g2.setColor(Color.BLACK);
    	    	xpixel = Conversion.doublepixelX(panelControl.MCoordFieldLines[x][0]);
    	    	ypixel = Conversion.doublepixelY(panelControl.MCoordFieldLines[x][1]);
    	    	g2.drawOval(xpixel-3, ypixel-3, 6, 6);
    	    	if (panelControl.ShowCoordselected) text = "M"+x+" ("+panelControl.MCoordFieldLines[x][0]+";"+panelControl.MCoordFieldLines[x][1]+")";
    	    	else text = "M"+x;
    	    	g2.drawString(text, xpixel+10, ypixel+10);
    	    	for (int i=0; i<10000; i++) {
    	    		ElecPoint = new Point(Conversion.pixeldoubleX(xpixel), Conversion.pixeldoubleY(ypixel), "Field"); 
    				elc.calculElectric(p.getA(), p.getB(), ElecPoint);
    				ei = elc.geti();
    				ej = elc.getj();
    				norm = Math.sqrt(Math.pow(ei, 2)+Math.pow(ej, 2));
    				x0 = xpixel+(int)(ei/norm*10);
    				y0 = ypixel-(int)(ej/norm*10);
    				g2.drawLine(xpixel, ypixel, x0, y0);
    				if (i%5==0 && panelControl.showFieldLinesDirection) {
    					double t = Math.atan2(ypixel-y0, xpixel-x0)-Math.PI;
    	    			int x1 = x0 - (int)(Math.cos(t+Math.PI/4)*5);
    	    			int y1 = y0 - (int)(Math.sin(t+Math.PI/4)*5);
    	    			g2.drawLine(x0, y0, x1, y1);
    	    			int x2 = x0 - (int)(Math.cos(t-Math.PI/4)*5);
    	    			int y2 = y0 - (int)(Math.sin(t-Math.PI/4)*5);
    	    			g2.drawLine(x0, y0, x2, y2);
    				}
    				xpixel = x0;
    				ypixel = y0;
    			}
    	    	xpixel = Conversion.doublepixelX(panelControl.MCoordFieldLines[x][0]);
    	    	ypixel = Conversion.doublepixelY(panelControl.MCoordFieldLines[x][1]);
    	    	for (int j=0; j<10000; j++) {
    	    		ElecPoint = new Point(Conversion.pixeldoubleX(xpixel), Conversion.pixeldoubleY(ypixel), "Field"); 
    				elc.calculElectric(p.getA(), p.getB(), ElecPoint);
    				ei = -elc.geti();
    				ej = -elc.getj();
    				norm = Math.sqrt(Math.pow(ei, 2)+Math.pow(ej, 2));
    				x0 = xpixel+(int)(ei/norm*10);
    				y0 = ypixel-(int)(ej/norm*10);
    				g2.drawLine(xpixel, ypixel, x0, y0);
    				if (j%5==0 && panelControl.showFieldLinesDirection) {
    					double t = Math.atan2(ypixel-y0, xpixel-x0)-Math.PI;
    	    			int x1 = x0 + (int)(Math.cos(t+Math.PI/4)*5);
    	    			int y1 = y0 + (int)(Math.sin(t+Math.PI/4)*5);
    	    			g2.drawLine(x0, y0, x1, y1);
    	    			int x2 = x0 + (int)(Math.cos(t-Math.PI/4)*5);
    	    			int y2 = y0 + (int)(Math.sin(t-Math.PI/4)*5);
    	    			g2.drawLine(x0, y0, x2, y2);
    				}
    				xpixel = x0;
    				ypixel = y0;
    	    	}
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
    	if (panelControl.ShowCoordselected) text = "A ("+p.getA().getPoint().getX()+";"+p.getA().getPoint().getY()+")";
    	else text = "A";
    	g2.drawString(text, xpixel+10, ypixel+10);
    	if (panelControl.ShowQselected) g2.drawString(Double.toString(p.getA().getQ())+"nC", xpixel+10, ypixel+25);
    }
	public void traceB() {
		g2.setColor(Color.BLACK);
		xpixel = Conversion.doublepixelX(p.getB().getPoint().getX());
    	ypixel = Conversion.doublepixelY(p.getB().getPoint().getY());
    	g2.drawOval(xpixel-3, ypixel-3, 6, 6);
    	if (panelControl.ShowCoordselected) text = "B ("+p.getB().getPoint().getX()+";"+p.getB().getPoint().getY()+")";
    	else text = "B";
    	g2.drawString(text, xpixel+10, ypixel+10);
    	if (panelControl.ShowQselected) g2.drawString(Double.toString(p.getB().getQ())+"nC", xpixel+10, ypixel+25);
	}      
	
    // Color le graphique en fonction du potentiel
    public void gradientColor() {
    	int i,j;
    	Color c=null;
    	for (i = 0; i < height; i++) {
    		for (j = 0; j < width; j++) {
    			c = ColorGradient.getColorGradient(panelControl.pot[i][j], panelControl.plus, panelControl.minus, panelControl.nbrColor);
    			image.setRGB(j, i, c.getRGB());
    		}
        }
    	g2.drawImage(image, null, 0, 0);
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
    	if (panelControl.minus<9999) panelControl.minus = Double.parseDouble(String.format("%6.3e",Double.parseDouble(Double.toString(panelControl.minus))));
    	if (panelControl.plus>9999) panelControl.plus = Double.parseDouble(String.format("%6.3e",Double.parseDouble(Double.toString(panelControl.plus))));
    	g2.drawString(Double.toString(panelControl.minus), width-230-metrics.stringWidth(Double.toString((int)panelControl.minus))/2, height-2);
    	g2.drawString("0", width-130-metrics.stringWidth("0")/2, height-2);
    	g2.drawLine(width-130, height-40, width-130, height-15);
    	g2.drawString(Double.toString(panelControl.plus), width-30-metrics.stringWidth(Double.toString((int)panelControl.plus))/2, height-2);
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
        for (double y = 0; y <= panelControl.xmax; y=y+panelControl.grad) {
        	// Ne dessine pas 0 (dessine precedement)
        	if (y!=0) {
        		int ye = (int) (y * panelControl.ech + height/2);
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
        switch (panelControl.mode){
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
        if (panelControl.Fieldselected) electricField();
        if (panelControl.Equipoteselected) equipote();
        if (panelControl.FieldLinesselected) fieldLines();
        if (panelControl.mode=="Grad") gradientScale(); // repaint l'echelle de gradient sur le field
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
    
	
    // Methodes de MouseListener
	@Override
	public void mouseClicked(MouseEvent e) {
		if (panelControl.mode=="Grad") {
			x = e.getX();
			y = e.getY();
			firstclick = true;
		}
		if (panelControl.Equipoteselected) {
			panelControl.Para8.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
			panelControl.Para8.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
		}
		if (panelControl.FieldLinesselected) {
		panelControl.Para10.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
		panelControl.Para10.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
		}
		switch (panelControl.saisie) {
		case 0: 
			panelControl.AXYQ.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
			panelControl.AXYQ.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
			p.getA().getPoint().setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
			p.getA().getPoint().setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
			traceA = true;
			break;
		case 1:
			panelControl.BXYQ.setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
			panelControl.BXYQ.setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
			p.getB().getPoint().setX(((double)((int)((Conversion.pixeldoubleX(e.getX()))*10)))/10);
			p.getB().getPoint().setY(((double)((int)((Conversion.pixeldoubleY(e.getY()))*10)))/10);
			traceB = true;
			break;
		default:
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