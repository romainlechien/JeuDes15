/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package jeudes15.components;

import java.util.EventObject;

/**
 *
 * @author David
 */
public class TokenEvent extends EventObject {

    public TokenEvent(Token source) {
        super(source);
    }
    
}
