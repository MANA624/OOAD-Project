import Pieces.Piece;
import Pieces.Move;
import Pieces.pieceFactory;

import java.util.List;

class Game {
    private List<Piece> pieces;
    private Boolean isWhitesTurn;
    private Logic logic;
    private boolean gameIsFinished;

    Game(){
        logic = new Logic();
        pieceFactory createPieces = new pieceFactory();
        pieces = createPieces.getPieceList();

        // Starts as white's turn
        isWhitesTurn = true;
        gameIsFinished = false;
    }

    boolean makeMove(Move move){
        List<Piece> testMove;

        for(Piece piece: pieces){
            if(isWhitesTurn == piece.getIsWhite() && move.pieceType == piece.getPieceType()){
                testMove = piece.makeMove(move, pieces);
                // This is considered a successful move. Is legal
                // and doesn't cause check against moving player
                if(testMove != null && !logic.isCheck(isWhitesTurn, testMove)){
                    this.pieces = testMove;
                    this.isWhitesTurn = !this.isWhitesTurn;
                    System.out.println(pieces);
                    if(logic.isCheck(!isWhitesTurn, pieces)){

                    }
                    return true;
                }
            }
        }
        return false;
    }

    boolean isGameEnd(){
        return gameIsFinished;
    }

    boolean isCheck(){
        return logic.isCheck(isWhitesTurn, pieces);
    }

    String getPlayerTurn(){
        return isWhitesTurn ? "white" : "black";
    }
}
