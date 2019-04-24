package Pieces;

import java.util.List;

class WrapKnight extends MovingService {
    public boolean checkPieceMovement(Move move, List<Piece> otherPieces, Piece thisPiece){
        int row = thisPiece.getRow();
        int col = thisPiece.getCol();
        boolean isWhite = thisPiece.getIsWhite();

        if((Math.abs(move.col-col) == 2 && Math.abs(move.row-row) == 1) ||
                (Math.abs(move.col-col) == 1 && Math.abs(move.row-row) == 2)){
             // I don't think this is necessary. Just redundant code
            row = move.row;
            col = move.col;
            if(!sameColorCollision(row, col, isWhite, otherPieces)){
                return true;
            }
        }

        return false;
    }
}
