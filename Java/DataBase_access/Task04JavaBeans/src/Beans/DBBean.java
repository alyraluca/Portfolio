package Beans;
import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public class DBBean {
	
	/*
	 * ------------------------------------------
	 * CONSTANTS AND VARIABLES
	 * ------------------------------------------
	 */
	/*
	 * Database name, user and password
	 */
	
	private static final String DBNAME = "DBChessGames";
	private static final String DBUSER = "mavenuser";
	private static final String DBPASSWORD = "ada0486";
	private static final String CONN_URL = "jdbc:mysql://localhost:3306/" + DBNAME
			+ "?useSSL=false&useTimezone=true&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private Connection cnDB = null;
    
    
    
   
    /*
	 * ------------------------------------------
	 * METHODS
	 * ------------------------------------------
	 */
	/*
	 * Empty constructor. Establish the connection to the DB
	 */
   
   public  DBBean() {
        try {
            // This will load the MySQL driver, each DB has its own driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Setup the connection with the DB
             cnDB = DriverManager.getConnection(CONN_URL,DBUSER,DBPASSWORD);
            
        } catch (ClassNotFoundException | SQLException sqle) {
            System.out.println("Got an exception (connecting)!");
            System.out.println(sqle.getMessage());
        }
        
    }
   /*
    * Close the connection to the DB
    */
   private void closeDBConnection() {
       try {
           if (cnDB != null) {
           	cnDB.close();
           	
           }
           
       } catch (Exception exe) {
       	System.out.println("Exception while closing" + exe.getMessage());
       }
   }
      
   /*
    * Get a random Player from the player's table 
    */
    public PlayerBean getRandomPlayer() {
    	System.out.println("Reading a random player... ");    	
    	PlayerBean randomPlayerBean = new PlayerBean();   	
    	try {    		
    		String stSQLquery = "SELECT playerID, fullname FROM Player ORDER BY RAND() LIMIT 1 ";
    		
    		// Create the MySQL prepared statement
    		Statement staSQLSelect = cnDB.createStatement();
    		ResultSet rsPlayerBean = staSQLSelect.executeQuery(stSQLquery);
    		
    		while(rsPlayerBean.next()) {
    			randomPlayerBean.setiPlayerID(rsPlayerBean.getInt("playerID"));
    			randomPlayerBean.setStFullname(rsPlayerBean.getString("fullname"));   		                
    		}    		
    	}catch (SQLException sqle) {
    		System.out.println("Got an exception (reading random player)!");
    		System.out.println(sqle.getMessage());
    	}    	
    	System.out.println("=> Player: '" + randomPlayerBean.getStFullname() + "' ...");
    	//Get a random tournament 
    	Tournament randomTournament = getRandomTournament();
        System.out.println("Reading a random tournament... ");
        System.out.println("=> Tournament: '" + randomTournament.getStName() + "' ...");
        //Add the tournament to the player's object
        randomPlayerBean.setObjTournament(randomTournament);    	
    	return randomPlayerBean;   	
    }
    /*
     * Get a random Tournament from the tornament's table 
     */
    public  Tournament getRandomTournament() {
    	
    	Tournament randomTournament = new Tournament();
    	try {
    		
    		String stSQLquery = "SELECT code, name FROM Tournament ORDER BY RAND() LIMIT 1 ";
    	
    		// Create the MySQL prepared statement
    		Statement staSQLSelect = cnDB.createStatement();
    		ResultSet rsTournament = staSQLSelect.executeQuery(stSQLquery);
    		
    		while(rsTournament.next()) {
    			randomTournament.setStCode(rsTournament.getString("code"));
    			randomTournament.setStName(rsTournament.getString("name"));
    		}
    		
    	}catch (SQLException sqle) {
    		System.out.println("Got an exception (reading random tournament)!");
    		System.out.println(sqle.getMessage());
    	}
    	return randomTournament;
    }
    /*
     * Update the player's deferral status 
     */
    public static void updatePlayerDeferralStatus(PlayerBean playerBean, boolean hasDeferral) {
        try {
        	DBBean objDBBean = new DBBean();
            String stSQLPlayerQuery = "UPDATE Player SET has_deferral = ? WHERE playerID = ?";
            PreparedStatement pstaSQLPlayerStatement = objDBBean.cnDB.prepareStatement(stSQLPlayerQuery);
            pstaSQLPlayerStatement.setBoolean(1, hasDeferral);
            pstaSQLPlayerStatement.setInt(2, playerBean.getiPlayerID());
            pstaSQLPlayerStatement.executeUpdate();
            objDBBean.closeDBConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }    
    /*
     * Update the player's game status 
     */
    public static void updatePlayerGameStatus(PlayerBean playerBean, boolean hasMatch) {
        try {
        	DBBean objDBBean = new DBBean();
            String stSQLPlayerQuery = "UPDATE Player SET has_match = ? WHERE playerID = ?";
            PreparedStatement pstaSQLPlayerStatement = objDBBean.cnDB.prepareStatement(stSQLPlayerQuery);
            pstaSQLPlayerStatement.setBoolean(1, hasMatch);
            pstaSQLPlayerStatement.setInt(2, playerBean.getiPlayerID());
            pstaSQLPlayerStatement.executeUpdate();
            objDBBean.closeDBConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     * Insert a deferral into the Deferral table ONLY if it has a deferral 'PENDING'
     */
    public static void insertDeferral(DeferralBean deferralbean) {
        try {
        	DBBean objDBBean = new DBBean();
        	// Check if there is a game in status PENDING related to the player
            String stSQLPendingGameQuery = "SELECT COUNT(*) FROM Game WHERE playerID = ? AND result = 'PENDING'";
            // create the MySQL insert preparedstatement
            PreparedStatement pstaSQLGameStatement = objDBBean.cnDB.prepareStatement(stSQLPendingGameQuery);
            pstaSQLGameStatement.setInt(1, deferralbean.getObjPlayerBean().getiPlayerID());
            // execute the preparedstatement
            ResultSet pendingGameResult = pstaSQLGameStatement.executeQuery();
            pendingGameResult.next();
            int pendingGamesCount = pendingGameResult.getInt(1);
        	
            if (pendingGamesCount > 0) {
             // If there's a PENDING game, insert the deferral
            String stSQLDeferralQuery = "INSERT INTO Deferral (deferralID, playerID, code, defdate, result) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstaSQLDeferralStatement = objDBBean.cnDB.prepareStatement(stSQLDeferralQuery);
            pstaSQLDeferralStatement.setInt(1, generateRandomDeferralID());
            pstaSQLDeferralStatement.setInt(2, deferralbean.getObjPlayerBean().getiPlayerID());
            pstaSQLDeferralStatement.setString(3, deferralbean.getObjTournament().getStCode());
            pstaSQLDeferralStatement.setTimestamp(4, Timestamp.valueOf(deferralbean.getlDefDate()));
            pstaSQLDeferralStatement.setString(5, deferralbean.getStResult());
            pstaSQLDeferralStatement.executeUpdate();
            objDBBean.closeDBConnection();
            } else {
                // If there's no PENDING game, inform the user
                System.out.println("Cannot request deferral as there is no pending game.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /*
     * Generate a random deferral ID  
     */
    public static int generateRandomDeferralID() {
    	int randomDeferralID;
    	randomDeferralID = (int) (Math.random() * Integer.MAX_VALUE);
    	return randomDeferralID;
    }
    /*
     * Generate a random Game ID  
     */
    public static int generateRandomGameID() {
    	int randomGameID;
    	randomGameID = (int) (Math.random() * Integer.MAX_VALUE);
    	return randomGameID;
    }
    
    
    /*
     * Insert a new game in the table
     */
    public static void insertGame(GameBean gameBean) {
        try {
            DBBean objDBBean = new DBBean();
            String stSQLquery = "INSERT INTO Game (gameID, code, playerID, matchdate, result) VALUES (?, ?, ?, ?, ?)";
            // create the MySQL insert preparedstatement
            PreparedStatement pstaSQLInsert = objDBBean.cnDB.prepareStatement(stSQLquery);
            pstaSQLInsert.setInt(1, generateRandomGameID());
            pstaSQLInsert.setString(2, gameBean.getObjTournament().getStCode());
            pstaSQLInsert.setInt(3, gameBean.getObjPlayerBean().getiPlayerID());
            pstaSQLInsert.setTimestamp(4, Timestamp.valueOf(gameBean.getLmatchDate()));
            pstaSQLInsert.setString(5, gameBean.getStResult());
            // execute the preparedstatement
            pstaSQLInsert.execute();
            objDBBean.closeDBConnection();
        } catch (SQLException sqle) {
            System.out.println("Got an exception (inserting game)!");
            System.out.println(sqle.getMessage());
        }
    }
    /*
     * Insert message in the message table
     * 
     */
    public static void insertMessage(MessageBean messageBean) {
        try {
            DBBean objDBBean = new DBBean();
            String stSQLMessageQuery = "INSERT INTO Message (playerID, description) VALUES (?, ?)";
            
            // Use PreparedStatement.RETURN_GENERATED_KEYS to retrieve the auto-generated key (messageID)
            PreparedStatement pstaSQLMessageStatement = objDBBean.cnDB.prepareStatement(stSQLMessageQuery, Statement.RETURN_GENERATED_KEYS);
            pstaSQLMessageStatement.setInt(1, messageBean.getObjPlayerBean().getiPlayerID());
            pstaSQLMessageStatement.setString(2, messageBean.getStDescription());
            // execute the preparedstatement
            pstaSQLMessageStatement.executeUpdate();
            
            // Retrieve the auto-generated key (messageID)
            ResultSet generatedKeys = pstaSQLMessageStatement.getGeneratedKeys();
            if (generatedKeys.next()) {               
                System.out.println("Inserted message ");
                objDBBean.closeDBConnection();
            } else {
                System.out.println("Failed to retrieve the generated messageID.");
            }          
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /*
     * Update deferral table
     * 
     */
    public static void updateDeferralVerdict(PlayerBean playerBean, String result) {
        try {
            
            DBBean objDBBean = new DBBean();
            String stSQLDeferralQuery = "UPDATE Deferral SET result = ? WHERE playerID = ?";
            PreparedStatement pstaSQLDeferralStatement = objDBBean.cnDB.prepareStatement(stSQLDeferralQuery);  
            // create the MySQL insert preparedstatement
            pstaSQLDeferralStatement.setString(1, result);
            pstaSQLDeferralStatement.setInt(2, playerBean.getiPlayerID());
            // execute the preparedstatement
            pstaSQLDeferralStatement.executeUpdate();
            objDBBean.closeDBConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    /*
     * Update Game table with the result
     * 
     */
    public static void updateGameResult(GameBean gameBean, String result) {
        try {
        	DBBean objDBBean = new DBBean();
            String stSQLquery = "UPDATE Game SET result = ? WHERE gameID = ?";
            // create the MySQL insert preparedstatement
            PreparedStatement pstaSQLStatement = objDBBean.cnDB.prepareStatement(stSQLquery);
            pstaSQLStatement.setString(1, result);
            pstaSQLStatement.setInt(2, gameBean.getiGameID());
            // execute the preparedstatement
            pstaSQLStatement.executeUpdate();
            objDBBean.closeDBConnection();
        } catch (SQLException sqle) {
            System.out.println("Got an exception (updating game result)!");
            System.out.println(sqle.getMessage());
        } 
    }      
    
    

}

