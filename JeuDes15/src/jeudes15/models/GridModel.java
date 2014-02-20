/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package jeudes15.models;

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
        for (int i = 0; i < nbitems; i++) {
            jetons.add(null);
        }
        support = new PropertyChangeSupport(this);
        winner = 0;
        currentPlayer = DEFAULT_BEGINNER_PLAYER;
        init();
    }

    public boolean isThereAWinner() {
        return (winner != 0);
    }

    public int whoIsTheWinner(){
        return winner;
    }
    
    public void setWinner() {
      
       boolean gagne = false;
        
        for (int i = 0; i <= jetonsPlayer1.size()-3 && !gagne; i++){
            for (int j = i+1; j <= jetonsPlayer1.size()-2 && !gagne; j++){
                for(int k = j+1; k <= jetonsPlayer1.size()-1 && !gagne ; k++){
                    if (resultCombi(jetonsPlayer1.get(i), jetonsPlayer1.get(j), jetonsPlayer1.get(k))== 15)
                        gagne = true;
                }
            }   
        }
        if (gagne){
            winner = 1;
        } else {
            
             for (int i = 0; i <= jetonsPlayer2.size()-3 && !gagne; i++){
               for (int j = i+1; j <= jetonsPlayer2.size()-2 && !gagne; j++){
                for(int k = j+1; k <= jetonsPlayer2.size()-1 && !gagne ; k++){
                    if (resultCombi(jetonsPlayer2.get(i), jetonsPlayer2.get(j), jetonsPlayer2.get(k))== 15)
                        gagne = true;
                 }
                }   
             }
             if (gagne){
                 winner = 2;
             }
        }
    }
    
     private int resultCombi(JetonModel get, JetonModel get0, JetonModel get1) {
        return (get.getValue()+get0.getValue()+get1.getValue());
    }

    public void setJetonSelected(int value) throws Exception {

        JetonModel oldJeton = null;
        JetonModel newJeton = null; 
        for (JetonModel jm : jetons) {
            if (jm.getValue() == value) {
                oldJeton = jm;
                if (currentPlayer == 1) {
                    jm.setState(TokenState.PLAYER1_SELECTED);
                    jetonsPlayer1.add(jm);
                    newJeton = jm;
                } else if (currentPlayer == 2) {
                    jm.setState(TokenState.PLAYER2_SELECTED);
                    jetonsPlayer2.add(jm);
                    newJeton = jm;
                } else {
                    throw new Exception("GridModel : CurrentPlayer non valide");
                }
            }
        }
        nextPlayer();
        support.firePropertyChange(JETON_PROPERTY, oldJeton, newJeton);

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
     
        jetons = new ArrayList<>(); 
        for (int i = 0 ; i < DEFAULT_GRID_NB_ITEMS ; i++){
            jetons.add(new JetonModel(i+1));
        }   
        jetonsPlayer1 = new ArrayList<>();
        jetonsPlayer2 = new ArrayList<>();
    }
    
    
    
    
   private void init() {
        jetonsPlayer1.add(new JetonModel(8));

        jetonsPlayer1.add(new JetonModel(9));
        jetonsPlayer1.add(new JetonModel(2));
        
     
        jetonsPlayer2.add(new JetonModel(8));
        jetonsPlayer2.add(new JetonModel(7));
        jetonsPlayer2.add(new JetonModel(4));
        jetonsPlayer2.add(new JetonModel(9));
        System.out.println("le winner est le joueur numéro : "+whoIsTheWinner());   
        setWinner();
        System.out.println("le winner est le joueur numéro : "+whoIsTheWinner());   
        
        
        jetonsPlayer1.add(new JetonModel(3));
        jetonsPlayer1.add(new JetonModel(7));
        
        setWinner();
         System.out.println("le winner est le joueur numéro : "+whoIsTheWinner());   
    }
}
