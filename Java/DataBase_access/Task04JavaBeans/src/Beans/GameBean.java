package Beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDateTime;

public class GameBean implements Serializable, PropertyChangeListener{
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;
	private PlayerBean objPlayerBean;
	private Tournament objTournament;
	private int iGameID;
	private LocalDateTime lmatchDate;
	private String stResult;
	private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
	
	
	public PropertyChangeSupport getPropertySupport() {
        return propertySupport;
    }
	/*
	 * Empty constructor
	 */
	public GameBean() {
		
	}
	/*
	 * Constructor with all fields
	 */
	public GameBean(PlayerBean objPlayerBean, Tournament objTournament, int iGameID,
			LocalDateTime lmatchDate, String stResult) {
		this.iGameID = iGameID;
        this.lmatchDate = lmatchDate;
		this.objPlayerBean = objPlayerBean;
		this.objTournament = objTournament;
		this.stResult = stResult;
	}

	/*
	 * GETTERS
	 */
	public PlayerBean getObjPlayerBean() {
		return objPlayerBean;
	}

	public Tournament getObjTournament() {
		return objTournament;
	}

	public int getiGameID() {
		return iGameID;
	}

	public LocalDateTime getLmatchDate() {
		return lmatchDate;
	}

	public String getStResult() {
		return stResult;
	}
	/*
	 * SETTERS
	 */

	public void setObjPlayerBean(PlayerBean objPlayerBean) {
		this.objPlayerBean = objPlayerBean;
	}

	public void setObjTournament(Tournament objTournament) {
		this.objTournament = objTournament;
	}

	public void setiGameID(int iGameID) {
		this.iGameID = iGameID;
	}

	public void setLmatchDate(LocalDateTime lmatchDate) {
		this.lmatchDate = lmatchDate;
	}
	
	public void setStResult(String stNewResult) {
		String oldResult = this.stResult;
		this.stResult = stNewResult;
		propertySupport.firePropertyChange("stResult", oldResult, stNewResult);
	}
	
	public void propertyChange (PropertyChangeEvent pceEvent) {
		
		if ("ldtNextMatchDate".equals(pceEvent.getPropertyName())) {
			//Check if there is a match date
	        if (pceEvent.getNewValue() != null) {
	            // Insert match into Game table and update player's match status
	            System.out.println("HI, GAMEBEAN SPEAKING!");
	            System.out.println("Inserted Game.");
	            System.out.println("Updated player. Has a match appointment.");
	            System.out.println("--------------------------");
	          //Insert a match into the Game table
	           GameBean gameBean = new GameBean(objPlayerBean, objTournament, iGameID, objPlayerBean.getLdtNextMatchDate(), "PENDING");
	           DBBean.insertGame(gameBean);
	         //Update the players table indicating that he has a match
	           DBBean.updatePlayerGameStatus(objPlayerBean, true);
	           
	        } else {
	            // Update the match at Game table and update player's match status
	            System.out.println("HI, GAMEBEAN SPEAKING!");
	            System.out.println("Updated Game (DRAWS).");
	            System.out.println("Updated player. No longer has a match appointment.");
	            System.out.println("--------------------------");
	          //Update the player's 'has_match' to false 
	            DBBean.updatePlayerGameStatus(objPlayerBean, false);
	          //Update the game table to 'DRAW'
	            GameBean gameBean = new GameBean(objPlayerBean, objTournament, iGameID, objPlayerBean.getLdtNextMatchDate(), "PENDING");
		        DBBean.updateGameResult(gameBean, "DRAWS");
	        }
	    }
	}
		
		
	public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
}








