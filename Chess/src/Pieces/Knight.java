package Pieces;

class Knight extends Piece {
    Knight(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.N, row, col);
        behaviors.add(new WrapKnight());
    }

}
