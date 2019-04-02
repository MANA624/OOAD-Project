package Pieces;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final boolean isWhite;
    private final pieceTypes pieceType;
    private int row;
    private int col;
    protected List<MovingService> behaviors = new ArrayList<>();

    Piece(boolean isWhite, pieceTypes pieceType, int row, int col){
        this.isWhite = isWhite;
        this.pieceType = pieceType;
        this.row = row;
        this.col = col;
    }

    public Boolean getIsWhite(){
        return this.isWhite;
    }

    public pieceTypes getPieceType(){
        return this.pieceType;
    }

    public void move(int row, int col){
        this.row = row;
        this.col = col;
    }

    int getRow(){
        return this.row;
    }

    int getCol(){
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

    public List<Piece> makeMove(Move move, List<Piece> pieces){
        List<Piece> temp;
        List<Piece> passList = new ArrayList<>(pieces);

        // Remove the moving piece out of the list to pass to the moving service
        // This way the service knows which piece is being moved
        for(int i=0; i<pieces.size(); i++){
            if(pieces.get(i).equals(this)){
                passList.remove(i);
                break;
            }
        }

        for(MovingService behavior : behaviors){
            // Should yield complete list with piece re-added
            if((temp = behavior.makeMove(move, passList, this)) != null){
                return temp;
            }
        }
        // No successful move found
        return null;
    }

    @Override
    public boolean equals(Object obj){
        // Check if the references are the same
        if(this == obj) {
            return true;
        }

        // Check if it's null, then if it's a different class
        if(obj == null || obj.getClass()!= this.getClass()) {
            return false;
        }

        // Cast the object into a Piece
        Piece piece = (Piece) obj;

        // If the row and the column are the same, it is the same piece
        return (piece.row == this.row && piece.col == this.col);
    }
}
