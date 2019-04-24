package Pieces;

import java.util.List;

class WrapBishop extends MovingService {
    public boolean checkPieceMovement(Move move, List<Piece> otherPieces, Piece thisPiece){
        int row = thisPiece.getRow();
        int col = thisPiece.getCol();
        boolean isWhite = thisPiece.getIsWhite();

        // variables that control the movement of the bishop
        int vertMovement;
        int horizMovement;

        // Make sure the bishop is moving perfectly diagonally
        if(Math.abs(move.row-row) != Math.abs(move.col-col)){
            return false;
        }

        vertMovement = row < move.row ? 1 : -1;
        horizMovement = col < move.col ? 1 : -1;

        do{
            row += vertMovement;
            col += horizMovement;

            // Check for collisions of same color. Shouldn't happen
            if(sameColorCollision(row, col, isWhite, otherPieces)){
                return false;
            }

            // Check if you've collided with a piece of the other color, and you
            // haven't arrived at the intended spot. This is always illegal
            if(differentColorCollision(row, col, isWhite, otherPieces) && col != move.col){
                return false;
            }
        }while(col != move.col); // Could also be row != move.row

        return true;
    }
}
