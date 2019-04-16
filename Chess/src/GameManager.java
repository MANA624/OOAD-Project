import Pieces.Move;

class GameManager {
    private DatabaseHandler database;
    private Game game;
    private UserInputHandler uinput;

    private final String loopInstructions = "Hello! Welcome to the Chess Game!\n" +
            "Enter the number corresponding with the option you'd like\n" +
            "1) Play game of chess\n" +
            "2) Set up database and table\n" +
            "3) View leaderboard and top scores\n" +
            "4) Exit the program";

    GameManager(){
        database = new DatabaseHandler();
        uinput = new UserInputHandler();
    }

    void startProgram(){
        int response;

        uinput.printToUser(database.getStatus());
        uinput.printToUser("Welcome to the chess game!");

        while(true){
            uinput.printToUser(loopInstructions);
            response = uinput.getInteger();
            if(response == 1){
                startGame();
            }
            else if(response == 2){
                uinput.printToUser(database.createTable());
            }
            else if(response == 3){
                uinput.printToUser("How many scores do you want to see?");
                response = uinput.getInteger();
                uinput.printToUser(database.getScores(response));
            }
            else if(response == 4){
                uinput.printToUser("Exiting program");
                break;
            }
            else{
                uinput.printToUser("Not a valid response");
            }
        }
    }

    private void startGame(){
        uinput.printToUser("Game starting!");
        game = new Game();
        String color;
        Move userMove;
        boolean isLegal;

        // Begin the loop for the whole chess game that gets user input
        while(!game.isGameEnd()){
            color = game.getPlayerTurn();
            isLegal = false;
            while(!isLegal) {
                uinput.printToUser("It is " + color + "'s turn. Enter your move");
                userMove = uinput.getMove();
                if(userMove == null){
                    uinput.printToUser("Not legal syntax. Try again");
                    continue;
                }
                isLegal = game.makeMove(userMove);
                if(!isLegal){
                    uinput.printToUser("Invalid move. Still " + color + "'s turn");
                }
            }
            if(game.isCheck()){
                uinput.printToUser("Check!");
            }
        }
    }
}
