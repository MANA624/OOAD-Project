import Pieces.Piece;
import Pieces.Move;
import Pieces.pieceFactory;
import Pieces.pieceTypes;

import java.util.List;

class Game {
    private List<Piece> pieces;
    private Boolean isWhitesTurn;
    private Logic logic;
    private boolean gameIsFinished;
    private boolean whiteWon;

    Game(){
        logic = new Logic();
        pieceFactory createPieces = new pieceFactory();
        pieces = createPieces.getPieceList();

        // Starts as white's turn
        isWhitesTurn = true;
        gameIsFinished = false;
    }

    boolean makeMove(Move move){
        Piece piece;
        List<Move> testMoves;
        boolean checkApplied;

        for(int i=0; i<pieces.size(); i++){
            piece = pieces.get(i);
            if(isWhitesTurn == piece.getIsWhite() && move.pieceType == piece.getPieceType()){
                testMoves = piece.makeMove(move, pieces);
                checkApplied = logic.applyMoves(testMoves, pieces, isWhitesTurn);
                // This is considered a successful move. Is legal
                // and doesn't cause check against moving player
                if(checkApplied && !logic.isCheck(isWhitesTurn, pieces)){
                    // Check for a pawn promotion
                    if(move.pieceType == pieceTypes.P && move.row == 8){
                        pieces.set(i, pieceFactory.makeNewPiece(pieceTypes.Q, move.row, move.col, isWhitesTurn));
                    }
                    System.out.println(pieces);

                    // Check for check/checkmate
                    if(logic.isCheck(!isWhitesTurn, pieces)){
                        if(logic.isCheckmate(!isWhitesTurn, pieces)){
                            gameIsFinished = true;
                            whiteWon = isWhitesTurn;
                        }
                    }

                    this.isWhitesTurn = !this.isWhitesTurn;
                    return true;
                }
                else{
                    logic.unapplyMoves(testMoves, pieces, isWhitesTurn);
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
