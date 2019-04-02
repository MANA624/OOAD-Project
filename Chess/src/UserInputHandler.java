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
    private Move parseMove(String move){
        char workingChar;
        pieceTypes type;
        int row;
        int col;

        // Check for kingside/queenside castling
        if(move.equals("O-O") || move.equals("0-0")){
            return new Move(true, false, pieceTypes.K,0, 0);
        }
        else if(move.equals("O-O-O") || move.equals("0-0-0")){
            return new Move(false, true, pieceTypes.K, 0, 0);
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

        // TODO: Check for 'x' in string e.g. Bxe4
        // TODO: Check for disambiguations in moving e.g. Reb8 or R1d7

        // Remove first char and get column
        move = move.substring(1);
        workingChar = move.charAt(0);
        col = ((int)workingChar) - 96;

        // Remove first char again and get row
        move = move.substring(1);
        workingChar = move.charAt(0);
        row = workingChar-48;

        return new Move(false, false, type, col, row);
    }

    int getInteger(){
        try {
            return Integer.parseInt(reader.nextLine());
        }catch (NumberFormatException e){
            return -1;
        }
    }
}
