package Pieces;

import java.util.ArrayList;
import java.util.List;

class WrapKing extends MovingService {
    public boolean checkValid(Move move, List<Piece> otherPieces, Piece thisPiece){
        int row = thisPiece.getRow();
        int col = thisPiece.getCol();

        // Make sure that the move is within boundaries
        if(Math.abs(move.col - col) > 1 || Math.abs(move.row - row) > 1){
            return false;
        }
        // Don't need to check if it's moving into check because we do that
        // anyways whenever we move any kind of piece
        return true;
    }

    List<Move> castling(Move move, List<Piece> otherPieces, King king, Rook rook){
        List<Move> returnMoves;
        boolean isWhite = king.getIsWhite();
        int backRow = isWhite ? 1 : 8;
        int destCol;
        int startInd;
        int endInd;

        // Check if the king or appropriate rook have moved
        if(rook.getHasMoved() || king.getHasMoved()){
            return null;
        }

        startInd = move.isKingCastle ? 6 : 2;
        endInd = move.isKingCastle ? 7 : 4;
        // Make sure there's no pieces between the king and rook
        for(int i=startInd; i<=endInd; i++) {
            if (sameColorCollision(backRow, i, isWhite, otherPieces)) {
                return null;
            }
            if (differentColorCollision(backRow, i, isWhite, otherPieces)) {
                return null;
            }
        }

        // Check if the king or the two squares next to him are threatened
        // Note: can't castle out of check
        startInd = move.isKingCastle ? 5 : 3;
        endInd = startInd + 2;
        for(int i=startInd; i<=endInd; i++) {
            if (sqaureIsThreatened(backRow, i, !isWhite, otherPieces)) {
                return null;
            }
        }

        // Success!
        returnMoves = new ArrayList<>();
        // Update king location
        destCol = move.isKingCastle ? 7 : 3;
        returnMoves.add(new Move(false, false, pieceTypes.K, backRow, destCol, backRow, 5, false));

        // Update rook location
        destCol = move.isKingCastle ? 6 : 4;
        returnMoves.add(new Move(false, false, pieceTypes.R, backRow, destCol, backRow, rook.getCol(), false));

        return returnMoves;
    }
}
