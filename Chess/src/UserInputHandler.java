import java.util.Scanner;

import Pieces.Move;
import Pieces.pieceTypes;

public class UserInputHandler {
    private Scanner reader = new Scanner(System.in);

    String getUsername(){
        return reader.nextLine();
    }

    Move getMove(){
        String userInput = reader.nextLine();
        return parseMove(userInput);
    }

    // Function should be done
    void printToUser(String printString){
        System.out.println(printString);
    }

    // This private method is a helper function that parses algebraic notation
    // and then returns a move object to be passed back to the main logic. For
    // example, "e4" returns dataStructures.Move(false, false, "pawn", 5, 4)
    // Currently only supports basic makes i.e. 'e4' or 'Nf6'
    // Note: This parser is VERY forgiving in how you write syntax! It permits
    // a lot of moves that are not technically part of algebraic notation!
    private Move parseMove(String move){
        // Working char describes the character in the string that is currently
        // being processed. The way this function works is by going character by
        // character through the input and assigning each part accordingly. Processed
        // characters are removed from the string
        char workingChar;
        pieceTypes type;
        int row;
        int col;
        // These next 3 are optional, so assume they're not used
        // unless otherwise stated.
        int startRow = -1;
        int startCol = -1;
        boolean isTake = false;

        // Check for kingside/queenside castling
        if(move.equals("O-O") || move.equals("0-0")){
            return new Move(true, false, pieceTypes.K, 0, 0, -1, -1, false);
        }
        else if(move.equals("O-O-O") || move.equals("0-0-0")){
            return new Move(false, true, pieceTypes.K, 0, 0, -1, -1, false);
        }

        // If the first piece is not explicitly a pawn, explicitly change the notation
        workingChar = move.charAt(0);
        if(Character.isLowerCase(workingChar) && (workingChar <= 'h' && workingChar >= 'a')){
            move = 'P' + move;
        }

        // Assign the proper piece
        workingChar = move.charAt(0);
        switch (workingChar){
            case 'R':
                type = pieceTypes.R;
                break;
            case 'N':
                type = pieceTypes.N;
                break;
            case 'B':
                type = pieceTypes.B;
                break;
            case 'Q':
                type = pieceTypes.Q;
                break;
            case 'K':
                type = pieceTypes.K;
                break;
            case 'P':
                type = pieceTypes.P;
                break;
            default:
                return null;
        }

        // Several cases for the next characters
        move = move.substring(1);
        workingChar = move.charAt(0);

        // If remaining characters are 3 e.g. Ree5, Bxc4, or
        if(move.length() == 3){
            // Three cases
            // Case one: the first character is an x
            if(workingChar == 'x'){
                isTake = true;
            }
            // Case two: a disambiguation of columns
            else if(workingChar <= 'h' && workingChar >= 'a'){
                startCol = workingChar - 96;
            }
            // Case three: a disambiguation of rows
            else if(workingChar <= '8' && workingChar >= '1'){
                startRow = workingChar - 48;
            }
            // Not a valid input
            else{
                return null;
            }
            move = move.substring(1);
            workingChar = move.charAt(0);
        }
        else if(move.length() == 4){
            // Must be that first character is a disambiguation.
            // Then next character is an 'x'
            // Case one: a disambiguation of columns
            if(workingChar >= 'a' && workingChar <= 'h'){
                startCol = workingChar - 96;
            }
            // Case two: a disambiguation of rows
            else if(workingChar >= '1' && workingChar <= '8'){
                startRow = workingChar - 48;
            }
            // Not a valid input
            else{
                return null;
            }

            move = move.substring(1);
            workingChar = move.charAt(0);

            // Is either a take or is invalid
            if(workingChar == 'x') {
                isTake = true;
            }
            else{
                return null;
            }
            move = move.substring(1);
            workingChar = move.charAt(0);
        }
        // Incorrect number of characters
        else if(move.length() != 2){
            return null;
        }


        // Get the column
        col = workingChar - 96;

        // Remove first char again and get row
        move = move.substring(1);
        workingChar = move.charAt(0);
        row = workingChar - 48;

        return new Move(false, false, type, col, row, startRow, startCol, isTake);
    }

    int getInteger(){
        try {
            return Integer.parseInt(reader.nextLine());
        }catch (NumberFormatException e){
            return -1;
        }
    }
}
