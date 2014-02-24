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
import jeudes15.models.TokenModel;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class Morpion extends JComponent {

    private PropertyChangeListener gridPropertyChangeListener;
    private static final int DEFAULT_SIZE_COL = 3;
    private GridModel model;
    private List<Integer> placementJetons;

    /**
     * Constructeur par défaut
     */
    public Morpion() {
        this(new GridModel());
    }


    /**
     * Construcuteur permettant d'initialiser le composant avec le modele passé en parametre.
     * @param model
     */
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

    /**
     * Méthode d'enregistrement du model dans le composant et d'abonnement à la proprieté JETON_PROPERTY
     * Elle permet d'intiialiser les éléements graphiques du composant avec le modele.
     * @param aModel 
     */
    private void registerModel(GridModel aModel) {
        model = aModel;
        this.model.addPropertyChangeListener(GridModel.JETON_PROPERTY, gridPropertyChangeListener);
        updateMorpion(model);
    }

    /**
     * Méthode de initialisation des layouts afin d'organiser le composant
     */
    private void initPanel() {
        this.setLayout(new GridLayout(DEFAULT_SIZE_COL, DEFAULT_SIZE_COL, 3, 3));
        this.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
    }
    
    /**
     * 
     * @return la dimension preferée du composant
     */
    public Dimension getPreferredSize() {
        return new Dimension(300, 300);
    }
    
    /**
     * Méthode permettant d'ordonner la liste afin d'assurer le bon placement 
     * de chacun des éléments dans la grille du morption
     * @param l : la liste à ordonner
     * @return la liste ordonnée
     */
    private List<TokenModel> ordonnerListe(List<TokenModel> l){
        List<TokenModel> listResult = new ArrayList<>();
        
        for (int i = 0; i < this.placementJetons.size() ; i++){
            for (int j = 0; j < l.size(); j++){
                if (l.get(j).getValue() == this.placementJetons.get(i)){
                    listResult.add(l.get(j));
                }
            }
        }
        
        
        return listResult;
    }
    
    /**
     * Methode de mise à jour du composant en fonction du modele passé en parametre.
     * @param grid : Le modele à utiliser pour mettre à jour la fenetre
     */
    private void updateMorpion(GridModel newGrid) {

        List<TokenModel> newJetons = newGrid.getJetons();
        newJetons = ordonnerListe(newJetons);
        List<Integer> player1SelectedJetons = newGrid.getPlayer1SelectedJetons();
        List<Integer> player2SelectedJetons = newGrid.getPlayer2SelectedJetons();
        List<TokenModel> player1 = newGrid.getJetonsPlayer1();
        List<TokenModel> player2 = newGrid.getJetonsPlayer2();
        boolean equality = newGrid.isThereAnEquality();
        boolean isAWinner = newGrid.isThereAWinner();
        removeAll();
        Token jetonWidget = null;
        TokenModel jeton = null;
        Integer temp = 0;
 

            for (int i = 0; i < newJetons.size(); i++) {
                jeton = newJetons.get(i);
                temp = jeton.getValue();
                jetonWidget = new Token(jeton, Shape.RECTANGLE);
                jetonWidget.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                if (!isAWinner && !player1SelectedJetons.contains(temp) && !player2SelectedJetons.contains(temp)) {
                    jetonWidget.addActionListener(new TokenClickListener(model, temp));
                }
                this.add(jetonWidget);
            }
        validate();
        repaint();
    }

    /**
     *
     * @return le modele de l'application
     */
    public GridModel getModel() {
        return model;
    }

    /**
     * Affecte le modele passé en parametre 
     * @param model : le modele à enregistrer dans l'application
     */
    public void setModel(GridModel model) {
        registerModel(model);
    }
}
