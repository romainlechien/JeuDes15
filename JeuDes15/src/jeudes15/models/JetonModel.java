/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class JetonModel {
    
    /**
     *
     */
    public static final TokenState DEFAULT_STATE = TokenState.NOT_SELECTED;
    
    
    private TokenState state;
    private int value;

    /**
     *
     * @param value
     */
    public JetonModel(int value){
        this(value, DEFAULT_STATE);
    }
       
    /**
     *
     * @param value
     * @param state
     */
    public JetonModel(int value, TokenState state) {
        this.value = value;
        this.state = state;
    }

    /**
     *
     * @return
     */
    public TokenState getState() {
        return state;
    }

    /**
     *
     * @param state
     */
    public void setState(TokenState state) {
        this.state = state;
    }

    /**
     *
     * @return
     */
    public int getValue() {
        return value;
    }

    /**
     *
     * @param value
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    
    
    
}
