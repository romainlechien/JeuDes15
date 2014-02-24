/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import jeudes15.models.GridModel;
import jeudes15.models.JetonModel;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class Morpion extends JComponent {

    private PropertyChangeListener gridPropertyChangeListener;
    private static final int DEFAULT_SIZE_COL = 3;
    private GridModel model;
    private List<Integer> placementJetons;

    public Morpion() {
        this(new GridModel());
    }

    public Morpion(GridModel model) {

        placementJetons = new ArrayList<>();
        placementJetons.add(8);
        placementJetons.add(3);
        placementJetons.add(4);
        placementJetons.add(1);
        placementJetons.add(5);
        placementJetons.add(9);
        placementJetons.add(6);
        placementJetons.add(7);
        placementJetons.add(2);
        
        gridPropertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                GridModel newGrid = (GridModel) pce.getNewValue();
                updateMorpion(newGrid);
            }
        };

        registerModel(model);
        initPanel();
    }

    private List<JetonModel> ordonnerListe(List<JetonModel> l){
        List<JetonModel> listResult = new ArrayList<>();
        
        for (int i = 0; i < this.placementJetons.size() ; i++){
            for (int j = 0; j < l.size(); j++){
                if (l.get(j).getValue() == this.placementJetons.get(i)){
                    listResult.add(l.get(j));
                }
            }
        }
        
        
        return listResult;
    }
    
    private void updateMorpion(GridModel newGrid) {
        System.out.println("UpdateMorpion");

        List<JetonModel> newJetons = newGrid.getJetons();
        newJetons = ordonnerListe(newJetons);
        List<Integer> player1SelectedJetons = newGrid.getPlayer1SelectedJetons();
        List<Integer> player2SelectedJetons = newGrid.getPlayer2SelectedJetons();
        List<JetonModel> player1 = newGrid.getJetonsPlayer1();
        List<JetonModel> player2 = newGrid.getJetonsPlayer2();
        System.out.println("NB Jetons vides : " + newJetons.size() + "  ;  NB Jetons Joueurs 1 : " + player1.size() + "  ;  NB Jetons Joueurs 2 : " + player2.size());
        boolean equality = newGrid.isThereAnEquality();
        boolean isAWinner = newGrid.isThereAWinner();
        removeAll();
        Token jetonWidget = null;
        JetonModel jeton = null;
        Integer temp = 0;
 

            for (int i = 0; i < newJetons.size(); i++) {
                jeton = newJetons.get(i);
                temp = jeton.getValue();
                jetonWidget = new Token(jeton, Shape.RECTANGLE);
                jetonWidget.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (!isAWinner && !player1SelectedJetons.contains(temp) && !player2SelectedJetons.contains(temp)) {
                    jetonWidget.addActionListener(new JetonClickListener(model, temp));
                }
                this.add(jetonWidget);
            }
        validate();
        repaint();
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
        updateMorpion(model);
    }

    private void initPanel() {
        this.setLayout(new GridLayout(DEFAULT_SIZE_COL, DEFAULT_SIZE_COL, 3, 3));
        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }
}
