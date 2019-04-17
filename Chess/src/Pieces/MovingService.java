package Pieces;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

abstract class MovingService {
    abstract boolean checkValid(Move move, List<Piece> pieces, Piece thisPiece);

    // Contains the functionality that all pieces will have
    List<Move> makeMove(Move move, List<Piece> pieces, Piece thisPiece){
        List<Piece> otherPieces = removePiece(thisPiece, pieces);
        List<Move> resultingMoves = null;
        pieceTypes taking;

        if(!isOnBoard(move, thisPiece)){
            return null;
        }

        // The only thing that varies between pieces
        if(!checkValid(move, otherPieces, thisPiece)){
            return null;
        }
        // Check if we moved to a square of a piece of the same color
        // Shouldn't ever be not null for a legal move
        if(checkTake(move.row, move.col, !thisPiece.getIsWhite(), otherPieces) != null){
            System.out.println("here");
            return null;
        }

        taking = checkTake(move.row, move.col, thisPiece.getIsWhite(), otherPieces);

        resultingMoves = new LinkedList<>();
        resultingMoves.add(new Move(false, false, thisPiece.getPieceType(), move.row, move.col,
                thisPiece.getRow(), thisPiece.getCol(), false));
        if(taking != null){
            resultingMoves.add(new Move(false, false, taking, -1, -1, move.row, move.col, true));
        }

        return resultingMoves;
    }

    // Checks if there's a piece on the square you're moving to. If yes, then it returns the
    // type of piece that it is. If no, then it returns null
    private pieceTypes checkTake(int row, int col, boolean isWhite, List<Piece> pieces){

        for(Piece piece : pieces){
            if(row == piece.getRow() && col == piece.getCol()){
                // A piece is captured. Return that piece type
                if(isWhite != piece.getIsWhite()){
                    return piece.getPieceType();
                }
            }
        }

        return null;
    }

    // Check for squares that are illegal for all pieces
    // Currently just checks that it moves on one of the
    // 63 pieces that aren't its own
    private boolean isOnBoard(Move move, Piece piece){
        if(move.row <= 0 || move.row > 8){
            return false;
        }
        else if(move.col <= 0 || move.col > 8){
            return false;
        }
        else if(move.col == piece.getCol() && move.row == piece.getRow()){
            return false;
        }
        return true;
    }

    // Checks if the piece is moving to the same square as another piece of the same color
    boolean sameColorCollision(int row, int col, boolean isWhite, List<Piece> otherPieces){
        for(Piece piece : otherPieces){
            if(col == piece.getCol() &&
                    row == piece.getRow() &&
                    isWhite == piece.getIsWhite()){
                return true;
            }
        }
        return false;
    }

    // Checks if the piece is moving to the same square as a piece of the other color
    boolean differentColorCollision(int row, int col, boolean isWhite, List<Piece> otherPieces){
        for(Piece piece : otherPieces){
            if(col == piece.getCol() &&
                    row == piece.getRow() &&
                    isWhite != piece.getIsWhite()){
                return true;
            }
        }
        return false;
    }

    // This is a nice helper function. Could be useful when determining if the king is in
    // check. Also useful for determining if a player can castle (The two spaces closest
    // to the king are not threatened
    // TODO: Implement this function
    boolean sqaureIsThreatened(int row, int col, boolean isWhite, List<Piece> otherPieces){
        return false;
    }

    // Turned this into a helper function because it was done a few times. It's a strange
    // way of going about it, but before calling it you should make a copy of the list of
    // pieces that you want to alter, then pass in the original list, the copy, and the row
    // and column of the piece you want to remove
    // TODO: Implement and refactor where it's used other places
    private List<Piece> removePiece(Piece piece, List<Piece> allPieces){
        List<Piece> newList = new ArrayList<>(allPieces);

        // Remove the moving piece out of the list to pass to the moving service
        // This way the service knows which piece is being moved
        for(int i=0; i<allPieces.size(); i++){
            if(allPieces.get(i).equals(piece)){
                newList.remove(i);
                break;
            }
        }

        return newList;
    }
}
