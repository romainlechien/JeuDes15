/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class TokenModel {
    
    /**
     *  Etat par défaut d'un jeton
     */
    public static final TokenState DEFAULT_STATE = TokenState.NOT_SELECTED;
    
    
    private TokenState state;
    private int value;

    /**
     *  Constructeur partiel
     * @param value : valeur du jeton
     */
    public TokenModel(int value){
        this(value, DEFAULT_STATE);
    }
       
    /**
     *  Constructeur complet
     * @param value : valeur du jeton
     * @param state : Etat du jeton
     */
    public TokenModel(int value, TokenState state) {
        this.value = value;
        this.state = state;
    }

    /**
     * @return l'etat du jeton
     */
    public TokenState getState() {
        return state;
    }

    /**
     * Change l'etat du jeton
     * @param state : nouvel etat du jeton
     */
    public void setState(TokenState state) {
        this.state = state;
    }

    /**
     * @return la valeur du jeton
     */
    public int getValue() {
        return value;
    }

    /**
     *  Change la valeur du jeton
     * @param value : nouvelle valeur du jeton
     */
    public void setValue(int value) {
        this.value = value;
    }
    
    
    
    
}
