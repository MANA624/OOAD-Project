import Pieces.Piece;
import Pieces.Move;
import Pieces.pieceFactory;

import java.util.ArrayList;
import java.util.List;

class Game {
    private List<Piece> pieces;
    private Piece takenPiece;
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
        List<Move> testMoves;
        boolean checkApplied;

        for(Piece piece: pieces){
            if(isWhitesTurn == piece.getIsWhite() && move.pieceType == piece.getPieceType()){
                testMoves = piece.makeMove(move, pieces);
                checkApplied = applyMoves(testMoves);
                // This is considered a successful move. Is legal
                // and doesn't cause check against moving player
                if(checkApplied && !logic.isCheck(isWhitesTurn, pieces)){
                    System.out.println(pieces);
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
                    unapplyMoves(testMoves);
                }
            }
        }
        return false;
    }

    private boolean applyMoves(List<Move> moves){
        Piece temp;
        takenPiece = null;
        boolean successful = true;
        boolean pieceFound;

        System.out.println(moves);

        if(moves == null){
            return false;
        }

        for(Move move : moves) {
            if(move.isTake) {
                temp = pieceFactory.makeTemp(move.pieceType, move.startRow, move.startCol, !isWhitesTurn);
                pieceFound = false;
                for(Piece piece : pieces){
                    if(piece.equals(temp)){
                        takenPiece = piece;
                        pieceFound = true;
                        break;
                    }
                }
                if(pieceFound)
                    pieces.remove(takenPiece);
            }
            else{
                temp = pieceFactory.makeTemp(move.pieceType, move.startRow, move.startCol, isWhitesTurn);
                pieceFound = false;
                for (Piece piece : pieces) {
                    if(piece.equals(temp)){
                        piece.move(move.row, move.col);
                        pieceFound = true;
                        break;
                    }
                }
                if(!pieceFound)
                    successful = false;
            }
        }

        return successful;
    }

    private boolean unapplyMoves(List<Move> moves){
        Piece temp;
        boolean successful = true;

        if(moves == null){
            return false;
        }
        if(takenPiece != null){
            pieces.add(takenPiece);
        }

        for(Move move : moves){
            if(!move.isTake){
                temp = pieceFactory.makeTemp(move.pieceType, move.row, move.col, isWhitesTurn);
                for(Piece piece : pieces){
                    if(piece.equals(temp)){
                        piece.move(move.startRow, move.startCol);
                        break;
                    }
                }
                successful = false;
            }
        }

        return successful;
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
