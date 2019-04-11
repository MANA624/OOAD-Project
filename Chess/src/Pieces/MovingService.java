package Pieces;

import java.util.List;

abstract class MovingService {
    abstract boolean checkValid(Move move, List<Piece> otherPieces, Piece thisPiece);

    // Contains the functionality that all pieces will have
    List<Piece> makeMove(Move move, List<Piece> otherPieces, Piece thisPiece){
        if(!isOnBoard(move, thisPiece)){
            return null;
        }

        // The only thing that varies between pieces
        if(!checkValid(move, otherPieces, thisPiece)){
            return null;
        }

        thisPiece.move(move.row, move.col);

        if(!checkTake(thisPiece, otherPieces)){
            return null;
        }

        otherPieces.add(thisPiece);
        return otherPieces;
    }

    private boolean checkTake(Piece thisPiece, List<Piece> otherPieces){
        Piece piece;
        boolean isTake = false;
        int i;

        for(i=0; i<otherPieces.size(); i++){
            piece = otherPieces.get(i);
            if(thisPiece.getCol() == piece.getCol() && thisPiece.getRow() == piece.getRow()){
                // A piece is captured. Remove that piece
                if(thisPiece.getIsWhite() != piece.getIsWhite()){
                    isTake = true;
                    break;
                }
                // A piece moves to a square of another piece of the same color
                // This code running means there's a bug
                else{
                    return false;
                }
            }
        }

        if(isTake){
            otherPieces.remove(i);
        }
        return true;
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
}
