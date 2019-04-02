package Pieces;

class Pawn extends Piece {
    Pawn(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.P, row, col);
        behaviors.add(new WrapPawn());
    }
}
