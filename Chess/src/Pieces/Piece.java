package Pieces;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final boolean isWhite;
    private final pieceTypes pieceType;
    private int row;
    private int col;
    private boolean hasMoved;
    List<MovingService> behaviors = new ArrayList<>();

    Piece(boolean isWhite, pieceTypes pieceType, int row, int col){
        this.isWhite = isWhite;
        this.pieceType = pieceType;
        this.row = row;
        this.col = col;
        this.hasMoved = false;
    }

    public boolean canMoveToSquare(List<Piece> pieces, int row, int col){
        boolean isCastle = false;
        Move move = new Move(isCastle, isCastle, this.getPieceType(), row, col, getRow(), getCol(), true);

        for(MovingService service : behaviors){
            if(service.checkValid(move, pieces, this)){
                return true;
            }
        }
        return false;
    }

    public List<Move> makeMove(Move move, List<Piece> pieces){
        List<Move> returnMoves;


        // Go through each moving service, then if one is valid, return it
        for(MovingService behavior : behaviors){
            if((returnMoves = behavior.makeMove(move, pieces, this)) != null){
                return returnMoves;
            }
        }
        // No successful move found
        return null;
    }


    boolean getHasMoved(){
        return this.hasMoved;
    }

    public Boolean getIsWhite(){
        return this.isWhite;
    }

    public pieceTypes getPieceType(){
        return this.pieceType;
    }

    public void move(Move move){
        this.row = move.row;
        this.col = move.col;
        this.hasMoved = true;
    }
    public void undo(Move move){
        this.row = move.startRow;
        this.col = move.startCol;
        this.hasMoved = false;
    }

    public int getRow(){
        return this.row;
    }

    public int getCol(){
        return this.col;
    }

    public String toString(){
        String color;
        String type;

        switch (pieceType){
            case R:
                type = "rook";
                break;
            case N:
                type = "knight";
                break;
            case B:
                type = "bishop";
                break;
            case Q:
                type = "queen";
                break;
            case K:
                type = "king";
                break;
            case P:
                type = "pawn";
                break;
            default:
                type = "";
        }

        color = isWhite ? "white" : "black";

        return "A " + color + " " + type + " on " + (char)(this.col+96) + this.row;
    }

    @Override
    public boolean equals(Object obj){
        // Check if the references are the same
        if(this == obj) {
            return true;
        }

        // Check if it's null, then if it's a different class
        if(obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        // Cast the object into a Piece
        Piece piece = (Piece) obj;

        // If the row and the column are the same, it is the same piece
        return (piece.row == this.row && piece.col == this.col && piece.getIsWhite() == this.isWhite);
    }
}
