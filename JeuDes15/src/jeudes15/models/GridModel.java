/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.logging.Logger;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

/**
 *
 * @author Emilien Dubois et Romain Lechien
 */
public class GridModel {

    /*******************************<*Constantes*>***************************************************/
    /**
     *  Nombre de jetons à disposer sur la grille pour une partie par défaut
     */
    public static final int DEFAULT_GRID_NB_ITEMS = 9;
   
    /**
     *  Joueur commençant à jouer pour une partie par défaut
     */
    public static final int DEFAULT_BEGINNER_PLAYER = 1;
      
    /**
     *  Définition de la propriété JETON, utilisée à chaque modification du model d'un jeton
     */
    public static final String JETON_PROPERTY = "jeton";
   
    /**
     *  Définition de la propriété ENDGAME, utilisée lorsque la fin de partie survient
     */
    public static final String ENDGAME_PROPERTY = "findujeu";
    
    private static final Logger LOG = Logger.getLogger(GridModel.class.getName());    
   
    /*******************************</*Constantes*>***************************************************/
    
    private final PropertyChangeSupport support;
    private List<JetonModel> jetons;
    private int winner;
    private int currentPlayer;
    private List<JetonModel> jetonsPlayer1;
    private List<JetonModel> jetonsPlayer2;
    private List<Integer> combiGagnante;
    private boolean popUpEndGame;

    /**
     *  Constructeur par défaut
     */
    public GridModel() {
        this(DEFAULT_GRID_NB_ITEMS);
    }


    /**
     *  Constructeur à partir d'un nombre de jetons
     * @param nbitems
     * 
     */
    public GridModel(int nbitems) {

        jetons = new ArrayList<>(nbitems);
        jetonsPlayer1 = new ArrayList<>();
        jetonsPlayer2 = new ArrayList<>();
        combiGagnante = new ArrayList<>();

        support = new PropertyChangeSupport(this);
        winner = 0;
        currentPlayer = DEFAULT_BEGINNER_PLAYER;
        popUpEndGame = false;
        // init();
        newGame();
    }

    /**
     *  Constructeur par copie
     * @param m
     */
    public GridModel(GridModel m) {
        this.support = m.support;
        this.jetons = m.jetons;
        this.winner = m.winner;
        this.currentPlayer = m.currentPlayer;
        this.jetonsPlayer1 = m.jetonsPlayer1;
        this.jetonsPlayer2 = m.jetonsPlayer2;
        combiGagnante = new ArrayList<>();
    }

    /**
     *  Retourne la liste des jetons sélectionnés par le joueur 1
     * @return selectedJetons
     */
    public List<Integer> getPlayer1SelectedJetons() {
        List<Integer> selectedJetons = new ArrayList<>();

        for (int i = 0; i < jetonsPlayer1.size(); i++) {
            selectedJetons.add(jetonsPlayer1.get(i).getValue());
        }
        return selectedJetons;
    }

    /**
     *  Retourne la liste des jetons sélectionnés par le joueur 2
     * @return selectedJetons
     */
    public List<Integer> getPlayer2SelectedJetons() {
        List<Integer> selectedJetons = new ArrayList<>();

        for (int i = 0; i < jetonsPlayer2.size(); i++) {
            selectedJetons.add(jetonsPlayer2.get(i).getValue());
        }
        return selectedJetons;
    }

    /**
     *  Retourne si un des deux joueurs a gagné la partie
     *  Met à jour la variable winner qui contient le numéro du joueur ayant gagné. winner vaut 0 si personne n'a gagné
     * @return true || false
     */
    public boolean isThereAWinner() {
        setWinner();
        return (winner != 0);
    }

    /**
     *  Retourne si oui ou non, il y a une égalité et que la partie est finie
     * @return true || false
     */
    public boolean isThereAnEquality() {

        List<Integer> selectedJetons = new ArrayList<>();
        for (int i = 0; i < jetonsPlayer2.size(); i++) {
            selectedJetons.add(jetonsPlayer2.get(i).getValue());
        }
        for (int i = 0; i < jetonsPlayer1.size(); i++) {
            selectedJetons.add(jetonsPlayer1.get(i).getValue());
        }

        return (winner == 0 && selectedJetons.size() == 9);
    }

    /**
     *  Retourne le numéro du gagnant. S'il y en a pas, renvoit 0;
     * @return winner
     */
    public int whoIsTheWinner() {
        return winner;
    }

    /**
     *  Met à jour la variable winner.
     *  Si le joueur 1 a gagné, winner vaudra 1, 
     *  sinon si le joueur 2 a gagné winner vaudra 2
     *  sinon winner vaudra 0 car personne n'a gagné.
     */
    public void setWinner() {

        boolean gagne = false;

        for (int i = 0; i <= jetonsPlayer1.size() - 3 && !gagne; i++) {
            for (int j = i + 1; j <= jetonsPlayer1.size() - 2 && !gagne; j++) {
                for (int k = j + 1; k <= jetonsPlayer1.size() - 1 && !gagne; k++) {
                    if (resultCombi(jetonsPlayer1.get(i), jetonsPlayer1.get(j), jetonsPlayer1.get(k)) == 15) {
                        gagne = true;
                        jetonsPlayer1.get(i).setState(TokenState.PIECE_WIN_PLAYER1);
                        jetonsPlayer1.get(j).setState(TokenState.PIECE_WIN_PLAYER1);
                        jetonsPlayer1.get(k).setState(TokenState.PIECE_WIN_PLAYER1);
                    }
                }
            }
        }
        if (gagne) {
            winner = 1;
        } else {

            for (int i = 0; i <= jetonsPlayer2.size() - 3 && !gagne; i++) {
                for (int j = i + 1; j <= jetonsPlayer2.size() - 2 && !gagne; j++) {
                    for (int k = j + 1; k <= jetonsPlayer2.size() - 1 && !gagne; k++) {
                        if (resultCombi(jetonsPlayer2.get(i), jetonsPlayer2.get(j), jetonsPlayer2.get(k)) == 15) {
                            gagne = true;
                            jetonsPlayer2.get(i).setState(TokenState.PIECE_WIN_PLAYER2);
                            jetonsPlayer2.get(j).setState(TokenState.PIECE_WIN_PLAYER2);
                            jetonsPlayer2.get(k).setState(TokenState.PIECE_WIN_PLAYER2);
                        }
                    }
                }
            }
            if (gagne) {
                winner = 2;
            }
        }
    }

    /*
     * Calcule la somme d'une combinaison de 3 jetons
     */
   private int resultCombi(JetonModel get, JetonModel get0, JetonModel get1) {
        return (get.getValue() + get0.getValue() + get1.getValue());
    }

    /**
     *
     * @param value
     * @throws Exception
     */
    public void setJetonSelected(int value) throws Exception {
        GridModel oldModel = null;
        GridModel newModel = null;

        for (JetonModel jm : jetons) {
            if (jm.getValue() == value) {
                oldModel = new GridModel(this);
                if (currentPlayer == 1) {
                    jm.setState(TokenState.PLAYER1_SELECTED);
                    jetonsPlayer1.add(jm);
                    nextPlayer();
                    newModel = new GridModel(this);
                    support.firePropertyChange(JETON_PROPERTY, oldModel, newModel);
                } else if (currentPlayer == 2) {
                    jm.setState(TokenState.PLAYER2_SELECTED);
                    jetonsPlayer2.add(jm);
                    nextPlayer();
                    newModel = new GridModel(this);
                    support.firePropertyChange(JETON_PROPERTY, oldModel, newModel);
                } else {
                    throw new Exception("GridModel : CurrentPlayer non valide");
                }
            }
        }
        
        if(isThereAWinner()||isThereAnEquality()){
            support.firePropertyChange(ENDGAME_PROPERTY, oldModel, newModel);
        }
    }

    private void nextPlayer() throws Exception {
        if (currentPlayer == 1) {
            currentPlayer = 2;
        } else if (currentPlayer == 2) {
            currentPlayer = 1;
        } else {
            throw new Exception("GridModel : CurrentPlayer non valide");
        }
    }

    /**
     *
     * @return
     */
    public List<JetonModel> getJetons() {
        return jetons;
    }

    /**
     *
     * @return
     */
    public List<JetonModel> getJetonsPlayer1() {
        return jetonsPlayer1;
    }

    /**
     *
     * @return
     */
    public List<JetonModel> getJetonsPlayer2() {
        return jetonsPlayer2;
    }

    /**
     *
     * @param pl
     */
    public void addPropertyChangeListener(PropertyChangeListener pl) {
        support.addPropertyChangeListener(pl);
    }

    /**
     *
     * @param pl
     */
    public void removePropertyChangeListener(PropertyChangeListener pl) {
        support.removePropertyChangeListener(pl);
    }

    /**
     *
     * @param string
     * @param pl
     */
    public void addPropertyChangeListener(String string, PropertyChangeListener pl) {
        support.addPropertyChangeListener(string, pl);
    }

    /**
     *
     * @param string
     * @param pl
     */
    public void removePropertyChangeListener(String string, PropertyChangeListener pl) {
        support.removePropertyChangeListener(string, pl);
    }

    /**
     *
     */
    public void newGame() {

        GridModel oldModel = null;
        GridModel newModel = null;
        

        oldModel = new GridModel(this);
        currentPlayer = 1;
        this.winner = 0;
        jetons = new ArrayList<>();
        for (int i = 1; i <= DEFAULT_GRID_NB_ITEMS; i++) {
            jetons.add(new JetonModel(i));
        }
        jetonsPlayer1 = new ArrayList<>();
        jetonsPlayer2 = new ArrayList<>();
        
        newModel = new GridModel(this);
        support.firePropertyChange(JETON_PROPERTY, oldModel, newModel);
    }
  
    /**
     *
     * @return
     */
    public boolean isPopUpEndGameDisplayed(){
        return popUpEndGame;
    }
    
    /**
     *
     * @param val
     */
    public void setPopUpEndGameDisplayed(boolean val){
        popUpEndGame = val;
    }
}
