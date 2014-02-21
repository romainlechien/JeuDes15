/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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

    private PropertyChangeListener gridPropertyChangeListener;
    private PropertyChangeListener enabledPropertyChangeListener;
    private static final int DEFAULT_SIZE_COL = 3;
    private Token temp;
    private GridModel model;

    public Morpion() {
        this(new GridModel());
    }

    public Morpion(GridModel model) {


        gridPropertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                GridModel newGrid = (GridModel) pce.getNewValue();
                updateMorpion(newGrid);
            }
        };

        enabledPropertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                boolean enabled = (boolean) pce.getNewValue();
                for (Component widget : getComponents()) {
                    widget.setEnabled(enabled);
                }
                repaint();
            }
        };

        registerModel(model);
        initPanel();  
    }

    private void updateMorpion(GridModel newGrid) {
        // TODO
    }

    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
    
    public GridModel getModel() {
        return model;
    }

    public void setModel(GridModel model) {
        registerModel(model);
    }
    
     private void registerModel(GridModel aModel) {
        model = aModel;
        this.model.addPropertyChangeListener(GridModel.JETON_PROPERTY, gridPropertyChangeListener);
        this.model.addPropertyChangeListener(GridModel.ENABLED_PROPERTY, enabledPropertyChangeListener);
        updateMorpion(model);
    }

    private void initPanel() {
        this.setLayout(new GridLayout(DEFAULT_SIZE_COL, DEFAULT_SIZE_COL, 3, 3));
        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));

        List<JetonModel> listModels = model.getJetons();

        for (int i = 0; i < 9; i++) {
            temp = new Token(listModels.get(i), Color.green, TokenState.NOT_SELECTED, Shape.RECTANGLE);
            temp.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            this.add(temp);
        }
    }
}
