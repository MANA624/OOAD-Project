package Pieces;

import java.util.List;

class WrapRook extends MovingService {
    public boolean checkPieceMovement(Move move, List<Piece> otherPieces, Piece thisPiece){
        int direction;
        int row;
        int col;
        boolean isWhite;

        // We have to keep values separately because Java changes the reference
        row = thisPiece.getRow();
        col = thisPiece.getCol();
        isWhite = thisPiece.getIsWhite();

        if(row != move.row && col != move.col){
            return false;
        }

        // This is the functionality if the rook is moving vertically
        if(col == move.col){
            // Set the direction that the piece is going to move
            direction = row < move.row ? 1 : -1;

            do{
                // Go ahead and make the move. Then check if it's valid
                row += direction;

                // Check if you've collided with a piece of the same color. This
                // can never happen
                if(sameColorCollision(row, col, isWhite, otherPieces)){
                    return false;
                }
                // Check if you've collided with a piece of the other color, and you
                // haven't arrived at the intended spot. This is always illegal
                if(differentColorCollision(row, col, isWhite, otherPieces) && row != move.row){
                    return false;
                }
            }while(row != move.row);
        }
        // This is the functionality if the rook is moving horizontally
        else{
            // Set the direction that the piece is going to move
            direction = col < move.col ? 1 : -1;
            do{
                // Go ahead and make the move. Then check if it's valid
                col += direction;

                // Check if you've collided with a piece of the same color. This
                // can never happen
                if(sameColorCollision(row, col, isWhite, otherPieces)){
                    return false;
                }
                // Check if you've collided with a piece of the other color, and you
                // haven't arrived at the intended spot. This is always illegal
                if(differentColorCollision(row, col, isWhite, otherPieces) && col != move.col){
                    return false;
                }
            }while(col != move.col);
        }

        return true;
    }
}
