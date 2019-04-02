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

    public Move(boolean isKingCastle, boolean isQueenCastle, pieceTypes pieceType, int col, int row){
        this.isKingCastle = isKingCastle;
        this.isQueenCastle = isQueenCastle;
        this.pieceType = pieceType;
        this.col = col;
        this.row = row;
    }
}
