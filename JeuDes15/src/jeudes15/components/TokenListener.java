/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.util.EventListener;

/**
 *
 * @author David
 */
public interface TokenListener extends EventListener {

    void shapeLabelClicked(TokenEvent shapeLabelEvent);
}
