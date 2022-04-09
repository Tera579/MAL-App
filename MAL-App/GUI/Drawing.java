package GUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.JLabel;
import javax.swing.JPanel;

import Business.Conversion;
import Business.Particule;
import Business.Point;
import Business.Potentiel;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class Drawing extends JPanel{
	
	private Potentiel p;
	
    public Drawing(Potentiel p) {
        this.p = p;
        JLabel board = new JLabel();
        board.setForeground(Color.BLACK);

        this.setPreferredSize(new Dimension(400, 180));
        this.setBackground(Color.WHITE);
        this.add(board);

    }
    

    private int oX;
    private int oY;
    private static int width;
    private static int height;
    
    private final int xmin = -150;
    private final int xmax = 150;
    private final int ymin = -150;
    private final int ymax = 150;

    private final int aX = 10;
    private final int bX = 10;
    private final int aY = 5;
    private final int bY = 5;
    private double echX = 40;
    private double echY = 40;
    
    private Graphics2D g2;
    private boolean start = false;
	private double[][] pot;
	private String mode;

    /**
     * La méthode paintComponent() est appelée automatiquement par le système
     * graphique de Java pour mettre à jour le panneau Graphics est la classe de
     * l'objet tr qui hérite de Graphics et Graphics2D est une classe qui hérite
     * de Graphics et qui intègre des méthodes de dessin supplémentaire
     *
     * @param g objet
     */
    @Override
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g2 = (Graphics2D) g;

        /**
         * met à jour la position de l'origine dans l'hypothèse où la géométrie
         * de la fenêtre aurait changé
         */
        oX = this.getWidth() / 2;
        oY = this.getHeight() / 2;
        width = this.getWidth();
        height = this.getHeight();
        
        // les deux lignes suivantes passent le fond du panenau en noir
        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);

        g2.setBackground(Color.WHITE);
        g2.clearRect(0, 0, width, height);
        
        if (start) {
        	gradient();
        	traceA();
        	traceB();
        	traceM();
        	traceAxes();
        }
        else traceAxes();
        
        }
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
    public void gradient() {
    	int i,j;
    	Color c=null;
    	if (mode=="grad") {
    		
    		for (i = 0; i < height; i++) {
    			for (j = 0; j < width; j++) {
    				if (pot[i][j]<0) {
    					c = new Color(255,0,0);
    					g2.setColor(c);
    				}
    				else {
    					c = new Color(0,0,255);
    					g2.setColor(c);
    				}
    				g2.drawLine(j, i, j, i);
    			}
    		}
        }
    	
    }
    /**
     * Trace les Axes et les graduations
     */
    public void traceAxes() {

        g2.setColor(Color.BLACK);

        // tracer axe Horizontal
        g2.drawLine(aX, oY, width - bX, oY);
        g2.drawLine(width - aX, oY, width - bX - 8, oY - 4);
        g2.drawLine(width - aX, oY, width - bX - 8, oY + 4);
        // tracer axe Vertical
        g2.drawLine(oX, width - aY, oX, bY);
        g2.drawLine(oX, aY, oX - 4, bY + 8);
        g2.drawLine(oX, aY, oX + 4, bY + 8);

        /**
         * tracer des graduations horizontales
         */
        for (double x = xmin; x <= xmax; x = x + 1) {
    			int xe = (int) (x * echX + oX);
    			g2.drawLine(xe, oY, xe, oY + 4);
        		FontMetrics fontMetrics = g2.getFontMetrics();
        		String textx = String.format("%.0f", x);
        		if (x!=0) g2.drawString(textx, xe +5 - fontMetrics.stringWidth(textx), oY + 20);
        		else g2.drawString(textx, oX - 10 - fontMetrics.stringWidth(textx), oY + 20);
        }

        /**
         * tracer des graduations verticales
         */
        for (double y = ymin; y <= ymax; y = y + 1) {
        	if (y!=0) {
        		int ye = (int) (-y * echY + oY);
        		g2.drawLine(oX, ye, oX - 4, ye);
        		FontMetrics fontMetrics = g2.getFontMetrics();
        		String texty = String.format("%.0f", y);
        		g2.drawString(texty, oX - 10 - fontMetrics.stringWidth(texty), +ye+5);
        	}
        }
    }
    public static int getWidthDrawing(){
        return width;
    }
    public static int getHeightDrawing(){
        return height;
    }
    public void start(double[][] pot, String mode) {
    	this.mode = mode;
    	start = true;
    	this.pot = pot;
    }
}