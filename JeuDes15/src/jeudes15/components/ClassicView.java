/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
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
    
    
    public ClassicView(){
        
        panelJetons = new JPanel();
        panelJoueurs = new JPanel();
        panelJoueur1 = new JPanel();
        panelJoueur2 = new JPanel();
        
        this.setLayout(new GridLayout(2, 1, 3, 3));
        
        // Panel Jetons (Haut)
        
        panelJetons.setLayout(new GridLayout(1, 9, 2, 2));
        for(int i=1;i<10;i++){
            temp = new Token(i, Color.green, TokenState.NOT_SELECTED, Shape.OVALE);
            panelJetons.add(temp);
        }
        this.add(panelJetons);
        
        // Panel choix joueurs (bas)
        
        panelJoueurs.setLayout(new GridLayout(1, 2, 2, 2));
        
        panelJoueurs.add(panelJoueur1);
        panelJoueurs.add(panelJoueur2);
        
        
        panelJoueur1.setLayout(new GridLayout(1, 5, 2, 2));
        for(int i=1;i<6;i++){
            temp = new Token(i, Color.green, TokenState.NOT_SELECTED, Shape.OVALE);
            panelJoueur1.add(temp);
        }
        
        panelJoueur2.setLayout(new GridLayout(1, 5, 2, 2));
        for(int i=1;i<6;i++){
            temp = new Token(i, Color.green, TokenState.NOT_SELECTED, Shape.OVALE);
            panelJoueur2.add(temp);
        }
        // Bordure sur les panels Joueur1 et Joueur2
        
        panelJoueur1.setBorder(new TitledBorder("Player 1 Tokens"));
        panelJoueur2.setBorder(new TitledBorder("Player 2 Tokens"));
        
        this.add(panelJoueurs);
        
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(500, 200);
    }
}
