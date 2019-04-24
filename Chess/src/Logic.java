import java.util.ArrayList;
import java.util.List;
import Pieces.*;

public class Logic {
    private Piece takenPiece;

    // One of the trickier functions to write, just because of all of the nuances in the
    // game. There are three conditions to define checkmate, and I coded them in this order:
    // 1) The king cannot move out of check
    // 2) No piece can take the offending piece (there's only one)
    // 3) No piece can move between the offender and the king
    boolean isCheckmate(boolean isAgainstWhite, List<Piece> pieces) {
        List<Piece> offenders = new ArrayList<>();
        King king = null;
        Piece offender;
        Piece temp;
        Move tempMove;

        final boolean isCastle = false;
        int kingRow, kingCol;  // Nat King Cole
        int[][] tweenCoords;
        int coord, rowCoord, colCoord;

        // Make sure we are even dealing with check
        if(!isCheck(isAgainstWhite, pieces)){
            return false;
        }

        // Find out which piece is the king
        for(Piece piece : pieces){
            if(piece.getPieceType() == pieceTypes.K && piece.getIsWhite() == isAgainstWhite){
                king = (King)piece;
                break;
            }
        }
        if(king == null){
            return false;
        }

        // Find out which piece(s) threaten the king
        for(Piece piece : pieces){
            if(piece.getIsWhite() != isAgainstWhite && piece.canMoveToSquare(pieces, king.getRow(), king.getCol())){
                offenders.add(piece);
            }
        }

        // Step 1: The king cannot move out of check
        kingRow = king.getRow();
        kingCol = king.getCol();
        for(int newRow=kingRow-1; newRow<=kingRow+1; newRow++){
            for(int newCol=kingCol-1; newCol<=kingCol+1; newCol++) {
                // Ignore the square the king is already on
                if(newRow == kingRow && newCol == kingCol){
                    continue;
                }
                if(king.canMoveToSquare(pieces, newRow, newCol)){
                    tempMove = new Move(isCastle, isCastle, king.getPieceType(), newRow,
                            newCol, kingRow, kingCol, false);
                    if(moveAvoidsCheck(tempMove, king, pieces, isAgainstWhite)){
                        return false;
                    }
                }
            }
        }

        // Steps 2 and 3 are only possible if only one piece threatens the king. Both happen in same loop
        if(offenders.size() == 1) {
            offender = offenders.get(0);
            // Note: Don't change to enhanced for loop. Causes bugs with ConcurrentModification
            for(int i=0; i<pieces.size(); i++) {
                temp = pieces.get(i);

                // Step 2: If only one piece threatens the king, find out if we can take it
                // Get the pieces that can take the offender. Remember we
                // still need to see if the position results in check
                if(temp.getIsWhite() == isAgainstWhite &&
                        temp.canMoveToSquare(pieces, offender.getRow(), offender.getCol())) {
                    tempMove = new Move(isCastle, isCastle, temp.getPieceType(), offender.getRow(),
                            offender.getCol(), temp.getRow(), temp.getCol(), false);
                    if(moveAvoidsCheck(tempMove, temp, pieces, isAgainstWhite)){
                        return false;
                    }
                }

                // Step 3: No piece can move between the offending piece and the king
                // Doesn't work for knights and pawns
                if(offender.getPieceType() != pieceTypes.N && offender.getPieceType() != pieceTypes.P){
                    // Assume pieces move linearly. See if we're moving horizontally, vertically, or both
                    tweenCoords = getTweenCoords(kingRow, kingCol, offender.getRow(), offender.getCol());
                    coord = 0;
                    if(tweenCoords != null){
                        while(tweenCoords[coord][0] != -1) {
                            rowCoord = tweenCoords[coord][0];
                            colCoord = tweenCoords[coord][1];
                            tempMove = new Move(isCastle, isCastle, temp.getPieceType(), rowCoord,
                                    colCoord, temp.getRow(), temp.getCol(), false);
                            ++coord;
                            if(moveAvoidsCheck(tempMove, temp, pieces, isAgainstWhite)){
                                return false;
                            }
                        }
                    }
                }
            }
        }

        // None of the above conditions prevent checkmate. It is, therefore, checkmate. 'Grats
        return true;
    }


    // Given a move, a piece to move, and a test who the checkmate is against,
    // check and see if following through on that move will make the player stay in check
    private boolean moveAvoidsCheck(Move move, Piece testPiece, List<Piece> pieces, boolean isAgainstWhite){
        List<Move> testMoves;
        boolean checkApplied;
        boolean avoidsCheck = false;

        testMoves = testPiece.makeMove(move, pieces);
        checkApplied = applyMoves(testMoves, pieces, isAgainstWhite);
        if(checkApplied && !isCheck(isAgainstWhite, pieces)){
            avoidsCheck = true;
        }
        unapplyMoves(testMoves, pieces, isAgainstWhite);

        return avoidsCheck;
    }

    // Is the user againstWhite in check?
    boolean isCheck(boolean againstWhite, List<Piece> pieces){
        Piece king = null;
        for(Piece piece: pieces){
            if(piece.getPieceType() == pieceTypes.K && piece.getIsWhite() == againstWhite){
                king = piece;
                break;
            }
        }

        // This shouldn't have to exist, but it makes Java/IntelliJ happy
        if(king == null){
            return false;
        }

        for(Piece piece : pieces){
            if(piece.getIsWhite() != againstWhite){
                if(piece.canMoveToSquare(pieces, king.getRow(), king.getCol())){
                    return true;
                }
            }
        }

        return false;
    }

    boolean applyMoves(List<Move> moves, List<Piece> pieces, boolean isWhitesTurn){
        Piece temp;
        takenPiece = null;
        boolean successful = true;
        boolean pieceFound;

        if(moves == null){
            return false;
        }

        for(Move move : moves) {
            if(move.isTake) {
                temp = pieceFactory.makeTemp(move.pieceType, move.startRow, move.startCol, !isWhitesTurn);
                pieceFound = false;
                for(Piece piece : pieces){
                    if(piece.equals(temp)){
                        takenPiece = piece;
                        pieceFound = true;
                        break;
                    }
                }
                if(pieceFound)
                    pieces.remove(takenPiece);
            }
            else{
                temp = pieceFactory.makeTemp(move.pieceType, move.startRow, move.startCol, isWhitesTurn);
                pieceFound = false;
                for (Piece piece : pieces) {
                    if(piece.equals(temp)){
                        piece.applyMove(move);
                        pieceFound = true;
                        break;
                    }
                }
                if(!pieceFound)
                    successful = false;
            }
        }

        return successful;
    }

    boolean unapplyMoves(List<Move> moves, List<Piece> pieces, boolean isWhitesTurn){
        Piece temp;
        boolean successful = true;

        if(moves == null){
            return false;
        }
        if(takenPiece != null){
            pieces.add(takenPiece);
        }

        for(Move move : moves){
            if(!move.isTake){
                temp = pieceFactory.makeTemp(move.pieceType, move.row, move.col, isWhitesTurn);
                for(Piece piece : pieces){
                    if(piece.equals(temp)){
                        piece.undo(move);
                        break;
                    }
                }
                successful = false;
            }
        }

        return successful;
    }

    // This method will likely be the hardest method to implement, and
    // furthermore is the least likely to occur. In order to check there
    // will need to be an equality check between lists of lists of pieces,
    // and check if the same three situations have occurred. This likely
    // won't get implemented for this project for CSCI 4/5448
    public boolean isDraw(){
        return false;
    }

    // A helper function that returns a list of coordinates, which is helpful to determine if any pieces can
    // move to any of those squares to prevent checkmate. It currently isn't the fastest, but was the most
    // elegant solution I could come up with - check all 64 squares and add the ones between them
    private int[][] getTweenCoords(int row1, int col1, int row2, int col2){
        int minRow, maxRow, minCol, maxCol;
        int[][] returnArray = new int[7][2];
        int coord = 0;

        // Make sure the two spots aren't the same. Should never happen
        if(row1 == row2 && col1 == col2){
            return null;
        }

        // Ordering is not enforced in the parameters, so check here
        if(row1 < row2){
            minRow = row1;
            maxRow = row2;
        } else{
            minRow = row2;
            maxRow = row1;
        }
        if(col1 < col2){
            minCol = col1;
            maxCol = col2;
        } else{
            maxCol = col2;
            minCol = col1;
        }

        // Magic code. Sorry about that
        for(int i=1; i<=8; i++){
            for(int j=1; j<=8; j++){
                if((i < maxRow && i > minRow) || (minRow == maxRow && minRow == i)){
                    if((j < maxCol && j > minCol) || (maxCol == minCol && minCol == j)){
                        if((minRow != maxRow && minCol != maxCol) && Math.abs(col1-j) != Math.abs(row1-i)){
                            continue;
                        }
                        returnArray[coord][0] = i;
                        returnArray[coord][1] = j;
                        coord++;
                    }
                }
            }
        }

        // Signal that we're done now
        returnArray[coord][0] = -1;

        return returnArray;
    }
}
