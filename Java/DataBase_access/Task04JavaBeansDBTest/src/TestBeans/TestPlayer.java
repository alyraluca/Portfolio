package TestBeans;

import java.time.LocalDateTime;

import Beans.*;
/**
 * =====================================================================
 * Main programme to interact among JavaBeans
 * =====================================================================
 */
public class TestPlayer {

	public static void main(String[] args) {
				/*
		    	 * ---------------------
				 * Creating the objects
				 * ---------------------
				 */
				DBBean dbBean = new DBBean();
	            //Object source            				
				PlayerBean objPlayer = dbBean.getRandomPlayer();
				 //Objects listeners
	            Tournament objTournament = dbBean.getRandomTournament();           
	            GameBean objGame = new GameBean();
	            MessageBean objMessage = new MessageBean();
	            DeferralBean objDeferral = new DeferralBean();
	            
	            /*
	             * ----------------------------------------------
	             * Assign the object source to the listeners
	             * Start the listeners objects
	             * ----------------------------------------------
	             */
	            objMessage.setObjPlayerBean(objPlayer);	           
	            objDeferral.setObjPlayerBean(objPlayer);
	            objDeferral.setObjTournament(objTournament);
	            
	            objGame.setObjPlayerBean(objPlayer);
	            objGame.setObjTournament(objTournament);
	            
	            objPlayer.addPropertyChangeListener(objMessage);
	            objPlayer.addPropertyChangeListener(objGame);	            
	            objPlayer.addPropertyChangeListener(objDeferral);
	           
	            /*
	             * --------------
	             * Firing events
	             * --------------
	             */

	            System.out.println("--------------------------");
	            
	            //Chess player plays a match in a tournament on a date	            
	            objPlayer.setLdtNextMatchDate(LocalDateTime.of(2024, 3, 8, 18, 0));	
	            
	            //Player requests a DEFERRAL of the chess game          
	            objPlayer.setLdtNextDeferralDate(LocalDateTime.of(2024, 3, 8, 18, 0));            
	            
	            //Chess player finishes a match
	            objPlayer.setLdtNextMatchDate(null);
	            	            
	            //Postponement of the deferral
	            objPlayer.setLdtNextDeferralDate(null);//Set the deferral date to null	            
	}

}
