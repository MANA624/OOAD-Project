import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class DatabaseHandler {
    private boolean serverRunning;
    private boolean tableInitialized;
    Connection conn;

    DatabaseHandler(){

    }

    public String connectToDatabase(){
        if(serverRunning){
            return "Already connected to database!";
        }

        try{
            // connect to database
        }catch (Exception e){
            return "Couldn't connect to database";
        }
        return "Connected to database!";
    }

    public String loadTable(){
        if(!serverRunning){
            return "Not connected to any SQL databse!";
        }
        if(tableInitialized){
            return "Table already initialized!";
        }

        try{
            // Check if table is initialized
        }catch(Exception e){
            return "Failed to load table";
        }

        return "Table initialized. Ready to show/update scores!";
    }

    public String getScores(int numRetrieve){
        if(!tableInitialized){
            return "Hey! The table is not initialized!";
        }
        if(numRetrieve <= 0){
            return "Too few values to return";
        }
        return "Matt\t\tWins:5\tLoss:7\tDraw:1\nMehmet:Wins:6\tLoss:8\tDraw:0";
    }

    public String createTable(){
        if(!serverRunning){
            return "Not connected to any SQL database!";
        }

        try{
            // Create a database and install the correct MySQL databse inside of it
        }catch(Exception e){
            return "Error! Could not create table!";
        }
        return "Table successfully created. No data currently";
    }

    public boolean updateScores(String username, int result){
        // Update the MySQL database for a username and what they
        // got. Should be as simple as incrementing a number in the database
        if(result == 0){
            // They lost
        }
        else if(result == 1){
            // They won
        }
        else if(result == 2){
            // A draw
        }
        return true; // For success
    }

    String getStatus(){
        if(tableInitialized){
            return "Database fully initialized";
        }
        else if(serverRunning){
            return "Connected to database. No table created";
        }
        else{
            return "Not connected to database";
        }
    }
}
