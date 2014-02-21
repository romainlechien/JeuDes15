/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.EventListener;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.event.EventListenerList;
import jeudes15.models.JetonModel;
import jeudes15.models.TokenState;

/**
 *
 * @author David
 */
public class Token extends JLayeredPane {

    public static final String BACKGROUND_COLOR_PROPERTY = "backgroundColor";
    public static final String TEXT_PROPERTY = "text";
    public static final String COLOR_PROPERTY = "color";
    public static final String STATE_PROPERTY = "state";
    public static final String VALUE_PROPERTY = "value";
    
    private static final int DEFAULT_SIZE_TEXT = 36;
    
    private static final int DEFAULT_SHAPE = Shape.OVALE;
    private static final int DEFAULT_PIECE_VALUE = 1;
    private static final TokenState DEFAULT_PIECE_STATE = TokenState.NOT_SELECTED;
    private static final Color DEFAULT_PIECE_COLOR = Color.GREEN;
    private static final Color PLAYER1_SELECTED_COLOR = Color.RED;
    private static final Color PLAYER2_SELECTED_COLOR = Color.YELLOW;
    
    private final JLabel label;
    private int sizeText;
    private final Shape shape;
    private final Shape armedShape;
    private final EventListenerList listeners;
    private int value;
    private Color color;
    private TokenState state;
    private JetonModel model;

    public static final Dimension PREFERRED_SIZE = new Dimension(70, 70);

    public Token() {
        this(null, DEFAULT_PIECE_COLOR, DEFAULT_PIECE_STATE, DEFAULT_SHAPE);
    }
    
     public Token(JetonModel model) {
        this(model, DEFAULT_PIECE_COLOR, DEFAULT_PIECE_STATE, DEFAULT_SHAPE);
    }
    
    public Token(JetonModel model, Color color, TokenState state, int shapePick) {
        super();
        this.model = model;
        this.value = model.getValue();
        this.color = color;
        this.state = state;
        listeners = new EventListenerList();
        label = new JLabel(new Integer(value).toString());
        shape = new Shape(shapePick,Color.red);
        sizeText = DEFAULT_SIZE_TEXT;
        armedShape = new Shape(shapePick,Color.red);
        
        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent me) {
                if (isEnabled()) {
                    fireActionPerformed();
                }
            }

        });
        
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setFont(new Font("SansSerif",Font.BOLD,sizeText));
        
        shape.setColor(color);

        label.addPropertyChangeListener("state", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                firePropertyChange(STATE_PROPERTY, pce.getOldValue(), pce.getNewValue());
            }
        });

        shape.addPropertyChangeListener("color", new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                        armedShape.setColor(((Color)pce.getNewValue()).darker());

                firePropertyChange(BACKGROUND_COLOR_PROPERTY, pce.getOldValue(), pce.getNewValue());
            }
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseExited(MouseEvent me) {
                setArmed(false);
            }

            @Override
            public void mouseEntered(MouseEvent me) {
                setArmed(true);
            }

            @Override
            public void mousePressed(MouseEvent me) {
                setPressed(true);
            }

            @Override
            public void mouseClicked(MouseEvent me) {
//                fireShapeLabelClicked(new TokenEvent(Token.this));
            }
        });

        add(shape, JLayeredPane.DEFAULT_LAYER);
        add(label, JLayeredPane.DRAG_LAYER);
        armedShape.setVisible(false);
    }

    @Override
    public void setBounds(int x, int y, int w, int h) {
        super.setBounds(x, y, w, h);
        label.setBounds(0, 0, w, h);
        shape.setBounds(0, 0, w, h);
        armedShape.setBounds(0, 0, w, h);
    }

    public void setText(String text) {
        label.setText(text);
    }

    public String getText() {
        return label.getText();
    }

    public void setBackgroundColor(Color backgroundColor) {
        shape.setColor(backgroundColor);
    }

    public Color getBackgroundColor() {
        return shape.getColor();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                Math.max(shape.getPreferredSize().width, label.getPreferredSize().width),
                Math.max(shape.getPreferredSize().height, label.getPreferredSize().height));
    }

    private void setArmed(boolean b) {
        armedShape.setVisible(b);
    }

    private void setPressed(boolean b) {
    }

   /* private void fireShapeLabelClicked(TokenEvent shapeLabelEvent) {
        for (TokenListener listener : listeners.getListeners(TokenListener.class)) {
          listener.shapeLabelClicked(shapeLabelEvent);
        }
    }

    public void addShapeLabelListener(TokenListener listener) {
        listeners.add(TokenListener.class, listener);
    }

    public void removeShapeLabelListener(TokenListener listener) {
        listeners.remove(TokenListener.class, listener);
    }
    */
    
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
    
    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }
    
    
}
