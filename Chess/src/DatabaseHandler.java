import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

class DatabaseHandler {
    private boolean serverRunning;
    private boolean tableInitialized;
    Connection conn;

    DatabaseHandler(){

    }

    // connect to MySQL database using JDBC driver
    public String connectToDatabase(){
        if (serverRunning) return "Already connected to database!";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch(ClassNotFoundException e) {
            return "Unable to load MySQL driver";
        }

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/chess?user=chessuser" +
                                 "&password=ch355&serverTimezone=UTC");
        }
        catch (SQLException e) {
            return "Unable to connect to database";
        }

        serverRunning = true;
        return "Connected to database!";
    }

    // check if scores table exists in database
    public String loadTable(){
        if(!serverRunning) return "Not connected to any SQL database!";
        if(tableInitialized) return "Table already initialized!";

        try{
            DatabaseMetaData dbm = conn.getMetaData();
            ResultSet rs = dbm.getTables("chess", null, "scores", null);

            if (rs.next()) tableInitialized = true;
            else return "Table does not exist yet";

        } catch(Exception e){
            return "An error occured while trying to load the table";
        }

        return "Table initialized. Ready to show/update scores!";
    }

    // show specified number of top scores, ordered by win/loss ratio
    public String getScores(int numRetrieve){

        if(!tableInitialized) return "Hey! The table is not initialized!";
        if(numRetrieve <= 0) return "Too few values to return";

        String output_format = "%s:\t\tWins:%d\tLosses:%d\tDraws:%d\n";
        String output = "";

        try {
            Statement statement = conn.createStatement();
            String sqlStatement = String.format("SELECT * FROM scores ORDER BY wins/losses DESC limit %d", numRetrieve);
            ResultSet rs = statement.executeQuery(sqlStatement);

            while(rs.next()){
                String username = rs.getString("username");
                int wins = rs.getInt("wins");
                int losses = rs.getInt("losses");
                int draws = rs.getInt("draws");

                output = output + String.format(output_format, username, wins, losses, draws);
        }
      } catch(Exception e){
              return "An error occured while trying to retrieve top scores";
      }

        return output;
    }

    // create scores table if it already doesn't exist
    public String createTable(){
        if(!serverRunning) return "Not connected to any SQL database!";

        try {
            Statement statement = conn.createStatement();
            String sqlStatement = "CREATE TABLE IF NOT EXISTS sample_scores" +
                   "(username VARCHAR(20)," +
                   " wins INT, " +
                   " losses INT, " +
                   " draws INT, " +
                   " PRIMARY KEY (username))";

            statement.executeUpdate(sqlStatement);

        }catch(Exception e){
            return "Error! Could not create table!";
        }
        return "Table successfully created. No data currently";
    }

    // update the score for a particular username
    public boolean updateScores(String username, int result){
        // Update the MySQL database for a username and what they
        // got. Should be as simple as incrementing a number in the database

        if(!tableInitialized) return false;

        Boolean usernameExists = false;

        try {

            Statement statement = conn.createStatement();
            String sqlStatement = String.format("SELECT * FROM scores WHERE username='%s'", username);
            ResultSet rs = statement.executeQuery(sqlStatement);

            if (rs.next()) usernameExists = true;

            if(result == 0){
                if (usernameExists) sqlStatement = String.format("UPDATE scores SET losses=losses+1 WHERE username='%s'", username);
                else  sqlStatement = String.format("INSERT INTO scores VALUES ('%s', 0, 1, 0) ", username);
            }
            else if(result == 1){
                if (usernameExists) sqlStatement = String.format("UPDATE scores SET wins=wins+1 WHERE username='%s'", username);
                else sqlStatement = String.format("INSERT INTO scores VALUES ('%s', 1, 0, 0) ", username);
            }
            else if(result == 2){
                if (usernameExists) sqlStatement = String.format("UPDATE scores SET draws=draws+1 WHERE username='%s'", username);
                else sqlStatement = String.format("INSERT INTO scores VALUES (''%s', 0, 0, 1) ", username);
            }
            statement = conn.createStatement();
            statement.executeUpdate(sqlStatement);
            return true;

        } catch(Exception e){
              return false;
        }
    }

    Boolean getServerStatus() {
        return serverRunning;
    }

    Boolean getTableStatus() {
        return tableInitialized;
    }

    String getStatus(){

        if (tableInitialized) return "Database fully initialized";
        else if (serverRunning) return "Connected to database. No table created";
        else return "Not connected to database";
    }
}
