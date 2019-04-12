package Pieces;

class Pawn extends Piece {
    Pawn(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.P, row, col);
        behaviors.add(new WrapPawn());
    }

    public void move(int row, int col){
        super.move(row, col);
    }
}
