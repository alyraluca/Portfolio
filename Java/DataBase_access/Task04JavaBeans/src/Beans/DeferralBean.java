package Beans;

import java.io.Serializable;

import java.time.LocalDateTime;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DeferralBean implements Serializable, PropertyChangeListener{
	/*
	 * ------------------------------------------
	 * ATTRIBUTES
	 * ------------------------------------------
	 */
	private static final long serialVersionUID = 1L;
	private PlayerBean objPlayerBean;
	private Tournament objTournament;
	private int iDeferralID;
	private LocalDateTime lDefDate;
	private String stResult;
	private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
	
	
	public PropertyChangeSupport getPropertySupport() {
        return propertySupport;
    }
	//Empty Constructor
	public DeferralBean() {		
	}
	/*
	 * Constructor with all fields
	 */
	public DeferralBean(PlayerBean objPlayerBean, Tournament objTournament,int iDeferralID,
			LocalDateTime lDefDate, String stResult) {
		
		this.iDeferralID = iDeferralID;
		this.lDefDate = lDefDate;
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


	public int getiDeferralID() {
		return iDeferralID;
	}


	public LocalDateTime getlDefDate() {
		return lDefDate;
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


	public void setiDeferralID(int iDeferralID) {
		this.iDeferralID = iDeferralID;
	}


	public void setlDefDate(LocalDateTime lDefDate) {
		this.lDefDate = lDefDate;
	}

	
	public void setStResult(String stNewResult) {		
		String oldResult = this.stResult;
		this.stResult = stNewResult;
		propertySupport.firePropertyChange("stResult", oldResult, stNewResult);		
	}
	/*
   	 * -------------------------
   	 * LISTENERS. EVENTS RAISED
   	 * -------------------------
   	 */
	public void propertyChange (PropertyChangeEvent pceEvent) {
		if ("ldtNextDeferralDate".equals(pceEvent.getPropertyName())) {
			//Check if there is a deferral date
	        if (pceEvent.getNewValue() != null) {
	            // If there is: Insert deferral into Deferral table and update player's deferral status
	            System.out.println("HI, DEFERRALBEAN SPEAKING!");
	            System.out.println("Inserted Deferral.");
	            System.out.println("Updated player. Has a deferral requested.");
	            System.out.println("--------------------------");
	           
	            LocalDateTime ldtDeferralDate = objPlayerBean.getLdtNextDeferralDate();
	            DeferralBean deferralBean = new DeferralBean(objPlayerBean, objTournament, iDeferralID, ldtDeferralDate, "REQUESTED");
	            //INsert the deferral in the deferral table
	            DBBean.insertDeferral(deferralBean);
	            //Update the player's table
	            DBBean.updatePlayerDeferralStatus(objPlayerBean, true);
	        } else {
	            // Update the postponement at Deferral table and update player's deferral status
	            System.out.println("HI, DEFERRALBEAN SPEAKING!");
	            System.out.println("Updated Deferral (REJECTED).");
	            System.out.println("Updated player. No longer has a deferral request.");
	            //Update the player's table
	            DBBean.updatePlayerDeferralStatus(objPlayerBean, false);
	            //Update the deferral table
	            DBBean.updateDeferralVerdict(objPlayerBean, "REJECTED");
	          
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
