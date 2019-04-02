package Pieces;

class Rook extends Piece {
    Rook(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.Q, row, col);
        behaviors.add(new WrapRook());
    }
}
