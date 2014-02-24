/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.EventListenerList;
import jeudes15.models.GridModel;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class JetonClickListener implements ActionListener {
    
    private final int jetonNumber;
    private final EventListenerList eventListeners;
    private final GridModel model;
    
    /**
     * Constructeur des evenements sur les jetons.
     * @param model : Le modele général de l'application
     * @param jetonNumber : la valeur du jeton
     */
    JetonClickListener(GridModel model, int jetonNumber) {
        this.model = model;
        this.jetonNumber = jetonNumber;
        eventListeners = new EventListenerList();
    }    
    
    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            firePieceClicked(jetonNumber);
        } catch (Exception ex) {
            Logger.getLogger(JetonClickListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /**
     * Traitement associé à un clic
     * @param jetonNumber : la valeur du jeton
     * @throws Exception 
     */
    private void firePieceClicked(int jetonNumber) throws Exception {
        model.setJetonSelected(jetonNumber);
        GridEvent event = new GridEvent(this, jetonNumber);
        for (GridListener listener : eventListeners.getListeners(GridListener.class)) {
            listener.gridClicked(event);
        }

    }
    
    /**
     *
     * @param listener
     */
    public void addGridListener(GridListener listener) {
        eventListeners.add(GridListener.class, listener);
    }
    
    /**
     *
     * @param listener
     */
    public void removeGridListener(GridListener listener) {
        eventListeners.remove(GridListener.class, listener);
    }
}
