/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public enum TokenState {

    /**
     * Etat du jeton par défaut
     */
    NOT_SELECTED, 
    
    /**
     * Etat du jeton lorsqu'il est sélectionné par le joueur 1
     */
    PLAYER1_SELECTED,
   
    /**
     * Etat du jeton lorsqu'il est sélectionné par le joueur 2
     */
    PLAYER2_SELECTED,
    
    /**
     * Etat du jeton lorsque celui-ci fait parti de la combinaison gagnante du joueur 1
     */
    PIECE_WIN_PLAYER1,
    
    /**
     * Etat du jeton lorsque celui-ci fait parti de la combinaison gagnante du joueur 2
     */
    PIECE_WIN_PLAYER2
}
