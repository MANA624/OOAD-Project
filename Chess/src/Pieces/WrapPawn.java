package Pieces;

import java.util.List;

class WrapPawn extends MovingService {
    // A really basic test code to make sure pawns can move. Will need to be fixed
    // TODO: Write almost all of functionality
    public boolean checkValid(Move move, List<Piece> otherPieces, Piece thisPiece){
        Pawn pawn = (Pawn)thisPiece;
        int direction = pawn.getIsWhite() ? 1 : -1;
        int row = pawn.getRow();
        int col = pawn.getCol();
        boolean isWhite = pawn.getIsWhite();
        int numMoves = pawn.getHasMoved() ? 1 : 2;


        // Check if we're moving the right pawn. Incorrect for taking
        if(move.col != thisPiece.getCol()){
            return takePiece(move, otherPieces, pawn);
        }

        for(int i=0; i<numMoves; i++) {
            row += direction;
            if (row == move.row &&
                    !differentColorCollision(row, col, isWhite, otherPieces) &&
                    !sameColorCollision(row, col, isWhite, otherPieces)) {
                return true;
            }
        }

        return false;
    }

    private boolean takePiece(Move move, List<Piece> otherpieces, Pawn pawn){
        int takeDistance = pawn.getIsWhite() ? 1 : -1;

        // Make sure that the pawn isn't taking further over from the next column
        if(Math.abs(pawn.getCol() - move.col) > 1){
            return false;
        }
        // Make sure the pawn is taking up and to the right one square
        if(move.row - pawn.getRow() != takeDistance){
            return false;
        }
        // Make sure the pawn is taking another piece
        if(!differentColorCollision(move.row, move.col, pawn.getIsWhite(), otherpieces)){
            if(!isEnPassant(pawn, otherpieces)){
                return false;
            }
        }

        // Should meet all the conditions
        return true;
    }

    private boolean isEnPassant(Pawn pawn, List<Piece> otherPieces){
        return false;
    }
}
