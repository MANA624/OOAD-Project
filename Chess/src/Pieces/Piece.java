package Pieces;

import java.util.ArrayList;
import java.util.List;

public abstract class Piece {
    private final boolean isWhite;
    private final pieceTypes pieceType;
    private int row;
    private int col;
    private boolean hasMoved;
    protected List<MovingService> behaviors = new ArrayList<>();

    Piece(boolean isWhite, pieceTypes pieceType, int row, int col){
        this.isWhite = isWhite;
        this.pieceType = pieceType;
        this.row = row;
        this.col = col;
        this.hasMoved = false;
    }

    public boolean canMoveToSquare(List<Piece> pieces, int row, int col){
        boolean isCastle = false;
        Move move = new Move(isCastle, isCastle, this.getPieceType(), col, row, getRow(), getCol(), true);
        List<Piece> passList = removePiece(this, pieces);

        for(MovingService service : behaviors){
            if(service.checkValid(move, passList, this)){
                return true;
            }
        }
        return false;
    }

    public List<Piece> makeMove(Move move, List<Piece> pieces){
        List<Piece> temp;
        List<Piece> passList = removePiece(this, pieces);


        for(MovingService behavior : behaviors){
            // Should yield complete list with piece re-added
            if((temp = behavior.makeMove(move, passList, this)) != null){
                this.madeMove();
                return temp;
            }
        }
        // No successful move found
        return null;
    }

    void madeMove(){
        this.hasMoved = true;
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

    public void move(int row, int col){
        this.row = row;
        this.col = col;
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
        return (piece.row == this.row && piece.col == this.col);
    }

    // Turned this into a helper function because it was done a few times. It's a strange
    // way of going about it, but before calling it you should make a copy of the list of
    // pieces that you want to alter, then pass in the original list, the copy, and the row
    // and column of the piece you want to remove
    // TODO: Implement and refactor where it's used other places
    private List<Piece> removePiece(Piece piece, List<Piece> allPieces){
        List<Piece> newList = new ArrayList<>(allPieces);

        // Remove the moving piece out of the list to pass to the moving service
        // This way the service knows which piece is being moved
        for(int i=0; i<allPieces.size(); i++){
            if(allPieces.get(i).equals(this)){
                newList.remove(i);
                break;
            }
        }

        return newList;
    }
}
