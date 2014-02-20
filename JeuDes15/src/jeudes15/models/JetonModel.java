/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

/**
 *
 * @author duboisem
 */
public class JetonModel {
    
    public static final TokenState DEFAULT_STATE = TokenState.NOT_SELECTED;
    
    
    private TokenState state;
    private int value;

    public JetonModel(int value){
        this(value, DEFAULT_STATE);
    }
       
    public JetonModel(int value, TokenState state) {
        this.value = value;
        this.state = state;
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
