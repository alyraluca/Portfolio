package Beans;
import java.beans.*;

import java.io.Serializable;
import java.time.LocalDateTime;

public class PlayerBean  implements Serializable {

	/*
	 * ATTRIBUTES
	 */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String stFullname;
	private int iPlayerID;
	private Tournament objTournament;
	private boolean bHasAMatch = false;
	private boolean bHasADeferral = false;
	private LocalDateTime ldtNextMatchDate;
	private LocalDateTime ldtNextDeferralDate;	
	private GameBean gameBean; 
	private PropertyChangeSupport propertySupport;
	
	/*
	 * METHODS
	 */
	
	//Empty Constructor
	public PlayerBean() {
		propertySupport = new PropertyChangeSupport(this);
	}
	
	//Constructor with all the fields
	
	public PlayerBean(String stFullname, Tournament objTournament, boolean bHasAMatch,
						boolean bHasADeferral, int iPlayerID, LocalDateTime ldtNextMatchDate, LocalDateTime ldtNextDeferralDate) {
		propertySupport = new PropertyChangeSupport(this);
		this.stFullname = stFullname;
		this.objTournament = objTournament;
		this.bHasAMatch = bHasAMatch;
		this.bHasADeferral = bHasADeferral;
		this.iPlayerID = iPlayerID;
		this.ldtNextMatchDate = ldtNextMatchDate;
		this.ldtNextDeferralDate = ldtNextDeferralDate;		
	}
	/*
	 * GETTERS
	 */

	public int getiPlayerID() {
		return iPlayerID;
	}


	public String getStFullname() {
		return stFullname;
	}

	public Tournament getObjTournament() {
		return objTournament;
	}

	public boolean isbHasAMatch() {
		return bHasAMatch;
	}

	public boolean isbHasADeferral() {
		return bHasADeferral;
	}

	public LocalDateTime getLdtNextMatchDate() {
		return ldtNextMatchDate;
	}

	public LocalDateTime getLdtNextDeferralDate() {
		return ldtNextDeferralDate;
	}

	public PropertyChangeSupport getPropertySupport() {
		return propertySupport;
	}
	
	public GameBean getGamebean() {
		return gameBean;
	}
	
	/*
	 * SETTERS
	 */

	public void setiPlayerID(int iPlayerID) {
		this.iPlayerID = iPlayerID;
	}
	
	public void setStFullname(String stFullname) {
		this.stFullname = stFullname;
	}

	public void setObjTournament(Tournament objTournament) {
		this.objTournament = objTournament;
	}

	public void setbHasAMatch(boolean bNewHasAMatch) {
		boolean bOldHasAMatch = this.bHasAMatch;
		this.bHasAMatch = bNewHasAMatch;
		propertySupport.firePropertyChange("Has a Match", bOldHasAMatch, bNewHasAMatch);
		
	}

	public void setbHasADeferral(boolean bNewHasADeferral) {
		boolean bOldHasDeferral = this.bHasADeferral;
		this.bHasADeferral = bNewHasADeferral;
		propertySupport.firePropertyChange("Has a deferral", bOldHasDeferral,bNewHasADeferral);
	}

	public void setLdtNextMatchDate(LocalDateTime ldtNextMatchDate) {
		
	    LocalDateTime oldDate = this.ldtNextMatchDate;
	    this.ldtNextMatchDate = ldtNextMatchDate;
	    propertySupport.firePropertyChange("ldtNextMatchDate", oldDate, ldtNextMatchDate);
	}

	public void setLdtNextDeferralDate(LocalDateTime ldtNextDeferralDate) {
		
	    LocalDateTime oldDate = this.ldtNextDeferralDate;
	    this.ldtNextDeferralDate = ldtNextDeferralDate;
	    propertySupport.firePropertyChange("ldtNextDeferralDate", oldDate, ldtNextDeferralDate);
	}

	public void setPropertySupport(PropertyChangeSupport propertySupport) {
		this.propertySupport = propertySupport;
	}
	public void setGameBean(GameBean gameBean) {
        this.gameBean = gameBean;
    }	
	/*
	 * ---------------------------
	 * LISTENERS. PROPERTYCHANGE
	 * ---------------------------
	 */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
        
}














