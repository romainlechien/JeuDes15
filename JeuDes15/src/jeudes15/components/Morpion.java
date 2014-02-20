/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import jeudes15.models.GridModel;
import jeudes15.models.JetonModel;
import jeudes15.models.TokenState;

/**
 *
 * @author lechiero
 */
public class Morpion extends JComponent {
    
    private static final int DEFAULT_SIZE_COL = 3;
    private Token temp;
    private GridModel model;
    
    public Morpion(GridModel model){
        
        this.model = model;
        
        this.setLayout(new GridLayout(DEFAULT_SIZE_COL, DEFAULT_SIZE_COL, 3, 3));
        this.setBorder(BorderFactory.createEmptyBorder(2,2,2,2));
        
        List<JetonModel> listModels = model.getJetons();
        
        for(int i=1;i<10;i++){
            temp = new Token(listModels.get(i), i, Color.green, TokenState.NOT_SELECTED, Shape.RECTANGLE);
            temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.add(temp);
        }
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(300,300);
    }
}
