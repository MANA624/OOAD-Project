package Pieces;

class Queen extends Piece {
    Queen(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.Q, row, col);
        behaviors.add(new WrapBishop());
        behaviors.add(new WrapRook());
    }
}
