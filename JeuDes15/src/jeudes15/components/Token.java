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
import jeudes15.models.TokenModel;
import jeudes15.models.TokenState;
import static jeudes15.models.TokenState.NOT_SELECTED;
import static jeudes15.models.TokenState.PLAYER2_SELECTED;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class Token extends JLayeredPane {

    // Taille du texte utilisé dans le composant
    private static final int DEFAULT_SIZE_TEXT = 36;
    
    private static final int DEFAULT_SHAPE = Shape.OVALE;
    private static final int DEFAULT_PIECE_VALUE = 1;
    private static final Color DEFAULT_PIECE_COLOR = Color.GREEN;
    private static final Color PLAYER1_SELECTED_COLOR = Color.RED;
    private static final Color PLAYER2_SELECTED_COLOR = Color.BLUE;
    private static final Color PIECE_WIN_COLOR = Color.YELLOW;
    
    private final JLabel label;
    private int sizeText;
    private final Shape shape;
    private final Shape armedShape;
    private int value;
    private Color color;
    private TokenState state;
    private TokenModel model;

    /**
     * Constructeur par défaut
     */
    public Token() {
        this(new TokenModel(DEFAULT_PIECE_VALUE), DEFAULT_SHAPE);
    }
    
     /**
     * Constructeur prenant en parametre le modele utilisé dans le jeton
     * @param model : le modele du jeton
     */
    public Token(TokenModel model) {
        this(model,DEFAULT_SHAPE);
    }
    
    /**
     * Constructeur parametré avec le modele et la forme à utiliser
     * @param model : le modele du jeton
     * @param shapePick : la forme
     */
    public Token(TokenModel model, int shapePick) {
        super();
        this.model = model;
        this.value = this.model.getValue();
        this.state = this.model.getState();
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

        updateColor();
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

    /**
     * 
     * @param text
     */
    public void setText(String text) {
        label.setText(text);
    }

    /**
     *
     * @return le texte du jeton
     */
    public String getText() {
        return label.getText();
    }

    /**
     *
     * @param backgroundColor
     */
    public void setBackgroundColor(Color backgroundColor) {
        shape.setColor(backgroundColor);
    }

    /**
     *
     * @return la couleur de fond du jeton
     */
    public Color getBackgroundColor() {
        return shape.getColor();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(
                Math.max(shape.getPreferredSize().width, label.getPreferredSize().width),
                Math.max(shape.getPreferredSize().height, label.getPreferredSize().height));
    }
    
    private void fireActionPerformed() {
            ActionEvent event = new ActionEvent(this, ActionEvent.ACTION_PERFORMED, "");
            for (ActionListener listener : getListeners(ActionListener.class)) {
               listener.actionPerformed(event);
            }
        }
    
        /**
     *
     * @param listener
     */
    public void addActionListener(ActionListener listener) {
        this.listenerList.add(ActionListener.class, listener);
    }

    /**
     *
     * @param listener
     */
    public void removeActionListener(ActionListener listener) {
        this.listenerList.remove(ActionListener.class, listener);
    }
    
    @Override
    public boolean contains(int x, int y) {
        return shape.contains(x, y);
    }

    /**
     * Methode mettant à jour la couleur du jeton et de son ecriture en fonction des états.
     */
    private void updateColor() {
        switch(state){
            case NOT_SELECTED : 
                this.setBackgroundColor(DEFAULT_PIECE_COLOR);
                break;
            case PLAYER1_SELECTED : 
                this.setBackgroundColor(PLAYER1_SELECTED_COLOR);
                break;
            case PLAYER2_SELECTED : 
                this.setBackgroundColor(PLAYER2_SELECTED_COLOR);
                break;
            case PIECE_WIN_PLAYER1 : 
                this.setBackgroundColor(PLAYER1_SELECTED_COLOR);
                this.label.setForeground(PIECE_WIN_COLOR);
                break;
            case PIECE_WIN_PLAYER2 : 
                this.setBackgroundColor(PLAYER2_SELECTED_COLOR);
                this.label.setForeground(PIECE_WIN_COLOR);
                break;
        }
    }
    
    
}
