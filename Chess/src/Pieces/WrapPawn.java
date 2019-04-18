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
            if(!isEnPassant(move, pawn, otherpieces)){
                return false;
            }
        }

        // Should meet all the conditions
        return true;
    }

    // For the special case of En Passant. Doesn't enforce the fact that
    // it must be a double move and taken immediately after. Maybe later
    private boolean isEnPassant(Move move, Pawn pawn, List<Piece> otherPieces){
        int desiredRow = pawn.getIsWhite() ? 5 : 4;
        boolean isWhite = pawn.getIsWhite();
        boolean pawnExists;

        if(pawn.getRow() != desiredRow){
            return false;
        }
        // If the pawn just moved two spaces, no piece can be behind it
        // The pawn cannot capture two pieces
        if(sameColorCollision(move.row, move.col, isWhite, otherPieces)){
            return false;
        }

        // Check if a pawn exists in the same column to the side of the
        // moving pawn
        pawnExists = false;
        for(Piece piece : otherPieces){
            if(piece.getIsWhite() != isWhite &&
                    piece.getPieceType() == pieceTypes.P &&
                    piece.getCol() == move.col &&
                    piece.getRow() == desiredRow){
                pawnExists = true;
            }
        }

        // TODO: Check and make sure that the other piece just moved 2 spaces last turn
        return pawnExists;
    }
}
