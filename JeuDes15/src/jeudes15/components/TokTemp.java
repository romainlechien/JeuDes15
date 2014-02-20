/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import jeudes15.models.TokenState;

/**
 *
 * @author duboisem
 */
public class TokTemp extends JComponent{
    
    private static final long serialVersionUID = 1L;
    
    private static final double PIN_RATE = 0.3;
    private static final int DEFAULT_PIECE_VALUE = 0;
    private static final int DEFAULT_SHAPE = Shape.OVALE;
    private static final TokenState DEFAULT_PIECE_STATE = TokenState.NOT_SELECTED;
    private static final Color DEFAULT_PIECE_COLOR = Color.GREEN;
    private static final Color PLAYER1_SELECTED_COLOR = Color.RED;
    private static final Color PLAYER2_SELECTED_COLOR = Color.YELLOW;
    private static final Logger LOG = Logger.getLogger(TokTemp.class.getName());
    
    public static final String COLOR_PROPERTY = "color";
    public static final String STATE_PROPERTY = "state";
    
    public static final Dimension PREFERRED_SIZE = new Dimension(70, 70);

    private int value;
    private JLabel jlValue;
    private Color color;
    private TokenState state;
    
    public TokTemp(int value, Color color, TokenState state) {
        this.value = value;
        this.color = color;
        this.state = state;
        this.jlValue = new JLabel(new Integer(value).toString());
        this.jlValue.setBackground(Color.BLACK);
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (isEnabled()) {
                    fireActionPerformed();
                }
            }

        });
        
        this.add(this.jlValue, JLayeredPane.DRAG_LAYER);
    }

    public TokTemp() {
        this(DEFAULT_PIECE_VALUE, DEFAULT_PIECE_COLOR, DEFAULT_PIECE_STATE);
    }

    @Override
    public Dimension getPreferredSize() {
        return PREFERRED_SIZE;
    }
    
    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        Color oldColor = getColor();
        this.color = color;
        firePropertyChange(COLOR_PROPERTY, oldColor, this.color);
        repaint();
    }

    public TokenState getState() {
        return state;
    }

    public void setState(TokenState state) {
        TokenState oldState = getState();
        this.state = state;
        firePropertyChange(STATE_PROPERTY, oldState, this.state);
        repaint();
    }

    @Override
    public void paint(Graphics grphcs) {
        super.paint(grphcs);
        if (getState() != null) {
            Color oldColor = grphcs.getColor();

            double diameter = Math.min(getWidth(), getHeight());
            double pinDiameter = diameter * PIN_RATE;
            double x0 = (getWidth() - diameter) / 2;
            double y0 = (getHeight() - diameter) / 2;
            double x0Pin = x0 + (diameter - pinDiameter) / 2;
            double y0Pin = y0 + (diameter - pinDiameter) / 2;

            grphcs.setColor(getColor());
            grphcs.fillOval((int) x0, (int) y0, (int) diameter, (int) diameter);
            drawPin(grphcs, x0Pin, y0Pin, pinDiameter);

            if (!isEnabled()) {
                grphcs.setColor(DEFAULT_PIECE_COLOR);
                Graphics2D g2d = (Graphics2D) grphcs;
                g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3F));
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
            grphcs.setColor(oldColor);
        }
    }

    private void drawPin(Graphics grphcs, double x0Pin, double y0Pin, double pinDiameter) {
        switch (getState()) {
            case NOT_SELECTED:
                grphcs.setColor(DEFAULT_PIECE_COLOR);
                grphcs.fillOval((int) x0Pin, (int) y0Pin, (int) pinDiameter, (int) pinDiameter);
                break;
            case PLAYER1_SELECTED:
                grphcs.setColor(PLAYER1_SELECTED_COLOR);
                grphcs.fillOval((int) x0Pin, (int) y0Pin, (int) pinDiameter, (int) pinDiameter);
                break;
            case PLAYER2_SELECTED:
                grphcs.setColor(PLAYER2_SELECTED_COLOR);
                grphcs.drawOval((int) x0Pin, (int) y0Pin, (int) pinDiameter, (int) pinDiameter);
        }
    }

    private void fireActionPerformed() {
        ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
        for (ActionListener listener : getListeners(ActionListener.class)) {
            listener.actionPerformed(event);
        }
    }

    public void addActionListener(ActionListener listener) {
        this.listenerList.add(ActionListener.class, listener);
    }

    public void removeActionListener(ActionListener listener) {
        this.listenerList.remove(ActionListener.class, listener);
    }
    
    
}
