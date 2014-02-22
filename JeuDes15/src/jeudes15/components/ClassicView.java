/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import java.beans.IndexedPropertyChangeEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import jeudes15.models.GridModel;
import jeudes15.models.JetonModel;
import jeudes15.models.TokenState;

/**
 *
 * @author lechiero
 */
public class ClassicView extends JComponent {

   private Token temp;
    private JPanel panelJetons;
    private JPanel panelJoueurs;
    private JPanel panelJoueur1;
    private JPanel panelJoueur2;
    private GridModel model;
    private PropertyChangeListener gridPropertyChangeListener;
    private PropertyChangeListener enabledPropertyChangeListener;

    public ClassicView() {
        this(new GridModel());
    }

    public ClassicView(GridModel model) {

        panelJetons = new JPanel();
        panelJoueurs = new JPanel();
        panelJoueur1 = new JPanel();
        panelJoueur2 = new JPanel();

        gridPropertyChangeListener = new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent pce) {
                GridModel newGrid = (GridModel) pce.getNewValue();
                updateClassicView(newGrid);
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
        initPanels();
    }

    private void registerModel(GridModel aModel) {
        model = aModel;
        this.model.addPropertyChangeListener(GridModel.JETON_PROPERTY, gridPropertyChangeListener);
        this.model.addPropertyChangeListener(GridModel.ENABLED_PROPERTY, enabledPropertyChangeListener);
        updateClassicView(model);
    }

    public Dimension getPreferredSize() {
        return new Dimension(500, 200);
    }

    private void updateClassicView(GridModel grid) {
        List<JetonModel> newJetons = grid.getJetons();
        List<JetonModel> player1 = grid.getJetonsPlayer1();
        List<JetonModel> player2 = grid.getJetonsPlayer2();
        List<Integer> player1SelectedJetons = grid.getPlayer1SelectedJetons();
        List<Integer> player2SelectedJetons = grid.getPlayer2SelectedJetons();
        boolean equality = grid.isThereAnEquality();
        boolean isAWinner = grid.isThereAWinner();
        removeAll();
        panelJoueurs.removeAll();
        panelJetons.removeAll();
        panelJoueur1.removeAll();
        panelJoueur2.removeAll();

            if (newJetons == null || newJetons.isEmpty()) {
                throw new IllegalArgumentException("Trying to set the classicView with an empty model");
            }

            for (int i = 0; i < newJetons.size(); i++) {
                JetonModel jeton = newJetons.get(i);
                final Token jetonWidget = new Token(jeton, Shape.OVALE);

                if(!isAWinner){
                    jetonWidget.addActionListener(new JetonClickListener(model, jeton.getValue()));
                }
                

                Integer temporaire = jeton.getValue();
                if (player1SelectedJetons.contains(temporaire) || player2SelectedJetons.contains(temporaire)) {
                    jetonWidget.setVisible(false);
                    jetonWidget.setEnabled(false);
                }
                panelJetons.add(jetonWidget);
            }

            if (!player1.isEmpty()) {
                for (int i = 0; i < player1.size(); i++) {
                    temp = new Token(player1.get(i), Shape.OVALE);
                    panelJoueur1.add(temp);
                }
                for(int i=player1.size() ; i<5 ; i++){
                    panelJoueur1.add(new JLabel(""));
                }
            }

            if (!player2.isEmpty()) {
                for (int i = 0; i < player2.size(); i++) {
                    temp = new Token(player2.get(i), Shape.OVALE);
                    panelJoueur2.add(temp);
                }
                for(int i=player2.size() ; i<5 ; i++){
                    panelJoueur2.add(new JLabel(""));
                }
            }

            panelJoueurs.add(panelJoueur1);
            panelJoueurs.add(panelJoueur2);
            //Ajout au composant
            this.add(panelJetons);
            this.add(panelJoueurs);

             Label l = null;
            if (isAWinner){
                //TODO pop up
                 l = new Label("Le vainqueur est le joueur " + grid.whoIsTheWinner());
            }
            else if(equality){
                //TODO pop up
                l = new Label("Pas de vainqueur.");
            }

        validate();
        repaint();

    }

    public GridModel getModel() {
        return model;
    }

    public void setModel(GridModel model) {
        registerModel(model);
    }

    private void initPanels() {
        this.setLayout(new GridLayout(2, 1, 3, 3));

        // Panel Jetons (Haut)

        panelJetons.setLayout(new GridLayout(1, 9, 2, 2));
        /* for(int i=0;i < 9;i++){
         temp = new Token(listModels.get(i), Color.green, TokenState.NOT_SELECTED, Shape.OVALE);
         panelJetons.add(temp);
         }*/


        // Panel choix joueurs (bas)

        panelJoueur1.setLayout(new GridLayout(1, 5, 2, 2));
        panelJoueur2.setLayout(new GridLayout(1, 5, 2, 2));

        // Bordure sur les panels Joueur1 et Joueur2

        panelJoueur1.setBorder(new TitledBorder("Player 1 Tokens"));
        panelJoueur2.setBorder(new TitledBorder("Player 2 Tokens"));

        // Panel principal des jetons sélectionnés
        panelJoueurs.setLayout(new GridLayout(1, 2, 2, 2));

        panelJoueurs.add(panelJoueur1);
        panelJoueurs.add(panelJoueur2);

        //Ajout au composant
        this.add(panelJetons);
        this.add(panelJoueurs);
    }
}
