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
 * @author duboisem
 */
public class GridModel {

    public static final int DEFAULT_GRID_NB_ITEMS = 9;
    public static final int DEFAULT_BEGINNER_PLAYER = 1;
    public static final boolean DEFAULT_ENABLED = true;
    private static final Logger LOG = Logger.getLogger(GridModel.class.getName());
    public static final String ENABLED_PROPERTY = "enabled";
    public static final String JETON_PROPERTY = "jeton";
    private final PropertyChangeSupport support;
    private List<JetonModel> jetons;
    private int winner;
    private int currentPlayer;
    private List<JetonModel> jetonsPlayer1;
    private List<JetonModel> jetonsPlayer2;
    private List<Integer> combiGagnante;

    public GridModel() {
        this(DEFAULT_GRID_NB_ITEMS);
    }

    public GridModel(int nbitems) {
        this(nbitems, DEFAULT_ENABLED);
    }

    public GridModel(int nbitems, boolean theEnabled) {

        jetons = new ArrayList<>(nbitems);
        jetonsPlayer1 = new ArrayList<>();
        jetonsPlayer2 = new ArrayList<>();
        combiGagnante = new ArrayList<>();

        support = new PropertyChangeSupport(this);
        winner = 0;
        currentPlayer = DEFAULT_BEGINNER_PLAYER;
        // init();
        newGame();
    }

    public GridModel(GridModel m) {
        this.support = m.support;
        this.jetons = m.jetons;
        this.winner = m.winner;
        this.currentPlayer = m.currentPlayer;
        this.jetonsPlayer1 = m.jetonsPlayer1;
        this.jetonsPlayer2 = m.jetonsPlayer2;
        combiGagnante = new ArrayList<>();
    }

    public List<Integer> getPlayer1SelectedJetons() {
        List<Integer> selectedJetons = new ArrayList<>();

        for (int i = 0; i < jetonsPlayer1.size(); i++) {
            selectedJetons.add(jetonsPlayer1.get(i).getValue());
        }
        return selectedJetons;
    }

    public List<Integer> getPlayer2SelectedJetons() {
        List<Integer> selectedJetons = new ArrayList<>();

        for (int i = 0; i < jetonsPlayer2.size(); i++) {
            selectedJetons.add(jetonsPlayer2.get(i).getValue());
        }
        return selectedJetons;
    }

    public boolean isThereAWinner() {
        setWinner();
        return (winner != 0);
    }

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

    public int whoIsTheWinner() {
        return winner;
    }

    public void setWinner() {

        boolean gagne = false;

        for (int i = 0; i <= jetonsPlayer1.size() - 3 && !gagne; i++) {
            for (int j = i + 1; j <= jetonsPlayer1.size() - 2 && !gagne; j++) {
                for (int k = j + 1; k <= jetonsPlayer1.size() - 1 && !gagne; k++) {
                    if (resultCombi(jetonsPlayer1.get(i), jetonsPlayer1.get(j), jetonsPlayer1.get(k)) == 15) {
                        gagne = true;
                        combiGagnante.add(jetonsPlayer1.get(i).getValue());
                        combiGagnante.add(jetonsPlayer1.get(j).getValue());
                        combiGagnante.add(jetonsPlayer1.get(k).getValue());
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
                            combiGagnante.add(jetonsPlayer2.get(i).getValue());
                            combiGagnante.add(jetonsPlayer2.get(j).getValue());
                            combiGagnante.add(jetonsPlayer2.get(k).getValue());
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

    private int resultCombi(JetonModel get, JetonModel get0, JetonModel get1) {
        return (get.getValue() + get0.getValue() + get1.getValue());
    }

    public void setJetonSelected(int value) throws Exception {

        GridModel oldModel = null;
        GridModel newModel = null;

        for (JetonModel jm : jetons) {
            if (jm.getValue() == value) {
                oldModel = new GridModel(this);
                if (currentPlayer == 1) {
                    jm.setState(TokenState.PLAYER1_SELECTED);
                    jetonsPlayer1.add(jm);
                    newModel = new GridModel(this);
                    nextPlayer();
                    support.firePropertyChange(JETON_PROPERTY, oldModel, newModel);
                } else if (currentPlayer == 2) {
                    jm.setState(TokenState.PLAYER2_SELECTED);
                    jetonsPlayer2.add(jm);
                    newModel = new GridModel(this);
                    nextPlayer();
                    support.firePropertyChange(JETON_PROPERTY, oldModel, newModel);
                } else {
                    throw new Exception("GridModel : CurrentPlayer non valide");
                }
            }
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

    public List<JetonModel> getJetons() {
        return jetons;
    }

    public List<JetonModel> getJetonsPlayer1() {
        return jetonsPlayer1;
    }

    public List<JetonModel> getJetonsPlayer2() {
        return jetonsPlayer2;
    }

    public void addPropertyChangeListener(PropertyChangeListener pl) {
        support.addPropertyChangeListener(pl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pl) {
        support.removePropertyChangeListener(pl);
    }

    public void addPropertyChangeListener(String string, PropertyChangeListener pl) {
        support.addPropertyChangeListener(string, pl);
    }

    public void removePropertyChangeListener(String string, PropertyChangeListener pl) {
        support.removePropertyChangeListener(string, pl);
    }

    public void newGame() {

        GridModel oldModel = null;
        GridModel newModel = null;

        oldModel = new GridModel(this);
        currentPlayer = 1;
        jetons = new ArrayList<>();
        for (int i = 1; i <= DEFAULT_GRID_NB_ITEMS; i++) {
            jetons.add(new JetonModel(i));
        }
        jetonsPlayer1 = new ArrayList<>();
        jetonsPlayer2 = new ArrayList<>();

        newModel = new GridModel(this);
        System.out.println("C'est au joueur " + currentPlayer);
        support.firePropertyChange(JETON_PROPERTY, oldModel, newModel);
    }

    public List<Integer> getCombiGagnante() {
        return combiGagnante;
    }
}
