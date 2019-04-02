package Pieces;

import java.util.List;

class WrapPawn extends MovingService {
    // A really basic test code to make sure pawns can move. Will need to be fixed
    // TODO: Write almost all of functionality
    public List<Piece> makeMove(Move move, List<Piece> otherPieces, Piece thisPiece){
        // Check if we're moving the right pawn. Incorrect for taking
        if(move.col != thisPiece.getCol()){
            return null;
        }
        if(thisPiece.getIsWhite()) {
            thisPiece.move(thisPiece.getRow() + 2, thisPiece.getCol());
        }else{
            thisPiece.move(thisPiece.getRow() - 2, thisPiece.getCol());
        }
        otherPieces.add(thisPiece);
        return otherPieces;
    }
}
