package Pieces;

// This class should be thought of as a data structure more than a class, which
// is why it is designed as one. It has no responsibilities other than to make
// passing information around easier
public class Move {
    public final boolean isKingCastle;
    public final boolean isQueenCastle;
    public final pieceTypes pieceType;
    public final int col;
    public final int row;
    public final int startRow;
    public final int startCol;
    public final boolean isTake;

    public Move(boolean isKingCastle, boolean isQueenCastle, pieceTypes pieceType, int row, int col,
                int startRow, int startCol, boolean isTake){
        this.isKingCastle = isKingCastle;
        this.isQueenCastle = isQueenCastle;
        this.pieceType = pieceType;
        this.col = col;
        this.row = row;
        // These next two are optional. The default is -1 if they're not used
        this.startRow = startRow;
        this.startCol = startCol;
        this.isTake = isTake; // This shouldn't get used. The 'x' is optional here
    }

    // Just for debugging. Please remove if found.
    public String toString(){
        return "Piecetype:" + pieceType + "\trow:" + row + "\tcol:" + col + "\tstartRow:" + startRow + "\tstartCol:" + startCol + "\tisTake:" + isTake;
    }
}
