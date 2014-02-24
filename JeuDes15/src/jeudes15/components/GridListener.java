/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.util.EventListener;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public interface GridListener extends EventListener {
    public void gridClicked(GridEvent event);
}
