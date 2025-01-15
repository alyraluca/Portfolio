package Beans;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;
import java.time.LocalDateTime;

public class MessageBean implements Serializable, PropertyChangeListener {
	
	/*
	 * ATTRIBUTES
	 */
	private static final long serialVersionUID = 1L;	
	private int iMessageID;
	private String stDescription;
	private PlayerBean objPlayerBean;
	private PropertyChangeSupport propertySupport = new PropertyChangeSupport(this);
	
	public PropertyChangeSupport getPropertySupport() {
        return propertySupport;
    }
	/*
	 * ------------------------------------------
	 * METHODS
	 * ------------------------------------------
	 */
	/*
	 * Empty constructor
	 */
	
	public MessageBean() {}

	
	/*
	 * Constructor with all fields
	 */

	public MessageBean(int iMessageID, String stDescription, PlayerBean objPlayerBean) {
		this.iMessageID = iMessageID;
		this.stDescription = stDescription;
		this.objPlayerBean = objPlayerBean;
	}

	/*
	 * ------------------------------------------
	 * GETTERS
	 * ------------------------------------------
	 */
	public int getiMessageID() {
		return iMessageID;
	}


	public String getStDescription() {
		return stDescription;
	}


	public PlayerBean getObjPlayerBean() {
		return objPlayerBean;
	}

	/*
	 * ------------------------------------------
	 * SETTERS
	 * ------------------------------------------
	 */
	public void setiMessageID(int iMessageID) {
		this.iMessageID = iMessageID;
	}


	public void setStDescription(String stDescription) {
		this.stDescription = stDescription;
	}


	public void setObjPlayerBean(PlayerBean objPlayerBean) {
		this.objPlayerBean = objPlayerBean;
	}
	/*
   	 * -------------------------
   	 * LISTENERS. EVENTS RAISED
   	 * -------------------------
   	 */
	public void propertyChange (PropertyChangeEvent pceEvent) {
				
		if ("ldtNextMatchDate".equals(pceEvent.getPropertyName())) {
			LocalDateTime matchDate = ((PlayerBean) pceEvent.getSource()).getLdtNextMatchDate();
			//Check if game has a date
			if (matchDate != null) {
			
            System.out.println("HI, MESSAGEBEAN SPEAKING!");
            System.out.println("          ********          ");
            System.out.println("Game match set as PENDING and registered to player '" +
                    ((PlayerBean) pceEvent.getSource()).getStFullname() +
                    "' at tournament '" + ((Tournament) ((PlayerBean) pceEvent.getSource()).getObjTournament()).getStName() +
                    "' on date " + ((PlayerBean) pceEvent.getSource()).getLdtNextMatchDate() +
                    " and time " + matchDate.toLocalTime());
            System.out.println("          ********          ");
            //Insert this message in the message table
    		DBBean.insertMessage(this);
            System.out.println("--------------------------");
			}
			
			//-----------------------------------------------------------
			
        } 
		if ("ldtNextDeferralDate".equals(pceEvent.getPropertyName())) {
        	LocalDateTime nextDeferralDate = ((PlayerBean) pceEvent.getSource()).getLdtNextDeferralDate();
        	//Check if there is a deferral date
        	if (nextDeferralDate != null) {
            System.out.println("HI, MESSAGEBEAN SPEAKING!");
            System.out.println("          ********          ");
            System.out.println("Deferral set as REQUESTED and registered to player '" +
                    ((PlayerBean) pceEvent.getSource()).getStFullname() +
                    "' at tournament '" + ((Tournament) ((PlayerBean) pceEvent.getSource()).getObjTournament()).getStName() +
                    "' on date " + ((PlayerBean) pceEvent.getSource()).getLdtNextDeferralDate());
            
                System.out.println(" and time " + nextDeferralDate.toLocalTime());
                System.out.println("          ********          ");
                //Insert this message in the message table
        		DBBean.insertMessage(this);
                System.out.println("--------------------------"); 
            }                      
        }		
		//-------------------------------------------
		
		if ("ldtNextMatchDate".equals(pceEvent.getPropertyName())) {
			LocalDateTime matchDate = ((PlayerBean) pceEvent.getSource()).getLdtNextMatchDate();	
			//CHeck if there is a game date
			if (matchDate==null) {	
				System.out.println("HI, MESSAGEBEAN SPEAKING!");
				System.out.println("          ********          ");	           
		        System.out.println(" Chess game completed set as DRAWS");	 
		        System.out.println("          ********          ");
		        //Insert this message in the message table
		        DBBean.insertMessage(this);
		        System.out.println("--------------------------");		        
			}
		}
		if ("ldtNextDeferralDate".equals(pceEvent.getPropertyName())) {
				LocalDateTime nextDeferralDate = ((PlayerBean) pceEvent.getSource()).getLdtNextDeferralDate();
	        	//Check if there is a deferral date
				if (nextDeferralDate == null) {
					System.out.println("HI, MESSAGEBEAN SPEAKING!");
					System.out.println("          ********          ");				
	                System.out.println("Deferral request set as REJECTED");	            
	                System.out.println("          ********          ");
	                //Insert this message in the message table
		 	        DBBean.insertMessage(this);
		 	        System.out.println("--------------------------");
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
	







