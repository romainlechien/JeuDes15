/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.logging.Logger;
import javax.swing.JComponent;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class Shape extends JComponent {

    public final static int OVALE = 1;
    public final static int RECTANGLE = 2;
    private static final long serialVersionUID = 1L;
    private static final Logger LOG = Logger.getLogger(Shape.class.getName());
    private int myShape;
    private Color myColor;
    
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    @Override
    public void addPropertyChangeListener(PropertyChangeListener pl) {
        support.addPropertyChangeListener(pl);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener pl) {
        support.removePropertyChangeListener(pl);
    }

    @Override
    public void addPropertyChangeListener(String string, PropertyChangeListener pl) {
        support.addPropertyChangeListener(string, pl);
    }

    @Override
    public void removePropertyChangeListener(String string, PropertyChangeListener pl) {
        support.removePropertyChangeListener(string, pl);
    }

    public Shape() {
        this(Shape.OVALE, Color.red);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(50, 50);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(100, 100); 
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(50, 50); 
    }

     @Override
    public boolean contains(int x, int y) {
        switch (getShape()) {
            case OVALE:
                double a = getWidth() / 2;
                double b = getHeight() / 2;
                double dx = x - a;
                double dy = y - b;
                return ((dx * dx) / (a * a) + (dy * dy) / (b * b) <= 1);
        }
        return super.contains(x, y);
    }

   
    public Shape(int theShape, Color theColor) {
        super();
        setShape(theShape);
        setColor(theColor);
    }

    public final void setColor(Color theColor) {
        Color oldColor=getColor();
        myColor = theColor;
        support.firePropertyChange(COLOR_PROPERTY, oldColor, myColor);
        repaint();
    }
    public static final String COLOR_PROPERTY = "color";

    public Color getColor() {
        return myColor;
    }

    public final void setShape(int theShape) {
        int oldShape = getShape();
        myShape = (theShape == OVALE || theShape == RECTANGLE)
                ? theShape
                : OVALE;
        support.firePropertyChange(SHAPE_PROPERTY, oldShape, myShape);
        repaint();
    }
    public static final String SHAPE_PROPERTY = "shape";

    public int getShape() {
        return myShape;
    }

    @Override
    public void paint(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(myColor);
        switch (myShape) {
            case RECTANGLE:
                g.fillRect(0, 0, getWidth(), getHeight());
                break;
            default:
                g.fillOval(0, 0, getWidth(), getHeight());
        }
        g.setColor(oldColor);
    }

}
