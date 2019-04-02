package Pieces;

class Bishop extends Piece {
    Bishop(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.B, row, col);
        behaviors.add(new WrapBishop());
    }
}
