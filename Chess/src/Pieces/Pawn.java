package Pieces;

class Pawn extends Piece {
    private boolean hasMoved;
    Pawn(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.P, row, col);
        behaviors.add(new WrapPawn());
        this.hasMoved = false;
    }

    boolean getHasMoved(){
        return this.hasMoved;
    }

    public void move(int row, int col){
        this.hasMoved = true;
        super.move(row, col);
    }
}
