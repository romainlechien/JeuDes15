/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.util.EventObject;

/**
 *
 * @author duboisem
 */
public class GridEvent extends EventObject {
    private static final long serialVersionUID = 1L;
    private final int jetonValue;
    
    public GridEvent(Object o, int pieceNumber) {
        super(o);
        this.jetonValue = pieceNumber;
    }

    public int getPieceNumber() {
        return jetonValue;
    }
}
