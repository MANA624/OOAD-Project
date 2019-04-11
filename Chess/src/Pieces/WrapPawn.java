package Pieces;

import java.util.List;

class WrapPawn extends MovingService {
    // A really basic test code to make sure pawns can move. Will need to be fixed
    // TODO: Write almost all of functionality
    public boolean checkValid(Move move, List<Piece> otherPieces, Piece thisPiece){
        // Check if we're moving the right pawn. Incorrect for taking
        if(move.col != thisPiece.getCol()){
            return false;
        }
        if(thisPiece.getIsWhite()) {
            //thisPiece.move(thisPiece.getRow() + 1, thisPiece.getCol());
        }else{
            //thisPiece.move(thisPiece.getRow() - 1, thisPiece.getCol());
        }
        //otherPieces.add(thisPiece);
        return true;
    }
}
