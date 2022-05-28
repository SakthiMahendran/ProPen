import java.awt.Color;
import java.awt.Graphics;
import java.awt.MouseInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

class ProPen {
    public static void main(String[] args) {
        var pen = new Pen();
        pen.setRadius(10);
        pen.setColor(Color.YELLOW);
 
        var canvas = new Canvas(pen);
  
        new MainFrame(canvas);
    }    
}

class MainFrame extends JFrame {
    MainFrame(Canvas canvas) {
        this.setUndecorated(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setBackground(new Color(0,0,0,1));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setAlwaysOnTop(true);
        this.add(canvas);
        this.setVisible(true);
    }
}

class Canvas extends JPanel implements MouseListener {
    private Pen pen;

    private boolean mousePressed = false;

    Canvas(Pen pen) {
        this.addMouseListener(this);
        this.pen = pen;
        this.setBackground(new Color(0,0,0,0));
        this.startDrawing();
    }

    public void startDrawing() {
        var drawingThread = new Thread(new Runnable(){
            @Override
            public void run() {
                while(true) {
                    repaint();                    
                }
            }
        }, "drawingThread");

        drawingThread.start();
        this.repaint();
    }

    public Pen getPen() {
        return this.pen;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (mousePressed) {
            g.setColor(pen.getColor());
            var radius = pen.getRadius();    
            var point = MouseInfo.getPointerInfo().getLocation();
            g.fillOval(point.x, point.y, radius, radius);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        mousePressed = true;
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mousePressed = false;     
    }

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}         
}

class Pen {
    private Color defaultColor = Color.BLUE;
    private Color penColor = defaultColor;   
    
    private int radius = 0;

    public void setColor(Color color) {
        penColor = color;
    }

    public Color getColor() {
        return penColor;
    }   

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }
}
