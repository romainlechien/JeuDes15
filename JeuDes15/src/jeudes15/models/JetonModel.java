/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

import java.awt.Color;

/**
 *
 * @author duboisem
 */
public class JetonModel {
    
    public static final Color DEFAULT_COLOR = Color.green;
    public static final TokenState DEFAULT_STATE = TokenState.NOT_SELECTED;
    
    
    private Color color;
    private TokenState state;
    private int value;

    public JetonModel(int value){
        this(value, DEFAULT_COLOR, DEFAULT_STATE);
    }
   
    public JetonModel(int value, Color c){
        this(value, c, DEFAULT_STATE);
    }
    
    public JetonModel(int value, Color color, TokenState state) {
        this.value = value;
        this.color = color;
        this.state = state;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public TokenState getState() {
        return state;
    }

    public void setState(TokenState state) {
        this.state = state;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    
    
    
    
}
