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
 * @author duboisem
 */
public class JetonClickListener implements ActionListener {
    
    private final int jetonNumber;
    private final EventListenerList eventListeners;
    private final GridModel model;
    
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
    
    private void firePieceClicked(int jetonNumber) throws Exception {
        System.out.println("number : " + jetonNumber);
        model.setJetonSelected(jetonNumber);
        System.out.print("Joueur 1 :");
        for(int i = 0 ; i < model.getJetonsPlayer1().size() ; i++){
            System.out.print(model.getJetonsPlayer1().get(i).getValue() + " ,");
        }
        System.out.print("Joueur 2 :");
        for(int i = 0 ; i < model.getJetonsPlayer2().size() ; i++){
            System.out.print(model.getJetonsPlayer2().get(i).getValue() + " ,");
        }
        GridEvent event = new GridEvent(this, jetonNumber);
        for (GridListener listener : eventListeners.getListeners(GridListener.class)) {
            listener.gridClicked(event);
        }

    }
    
    public void addGridListener(GridListener listener) {
        eventListeners.add(GridListener.class, listener);
    }
    
    public void removeGridListener(GridListener listener) {
        eventListeners.remove(GridListener.class, listener);
    }
}
