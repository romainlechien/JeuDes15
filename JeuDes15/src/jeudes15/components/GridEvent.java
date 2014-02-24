/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.util.EventObject;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class GridEvent extends EventObject {
    private final int jetonValue;
    
    /**
     * Constructeur 
     * @param o 
     * @param pieceNumber : la valeur du jeton
     */
    public GridEvent(Object o, int pieceNumber) {
        super(o);
        this.jetonValue = pieceNumber;
    }

    /**
     *
     * @return la valeur du jeton enregistré par l'évenement.
     */
    public int getPieceNumber() {
        return jetonValue;
    }
}
