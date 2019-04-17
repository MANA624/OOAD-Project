package Pieces;

import java.util.ArrayList;
import java.util.List;

public class pieceFactory{
    private int[] numRooks = new int[]{0, 0};
    private int[] numKnights = new int[]{0, 0};
    private int[] numBishops = new int[]{0, 0};
    private int[] numPawns = new int[]{0, 0};
    private List<Piece> pieces = new ArrayList<>();

    public pieceFactory(){
        // Create all of the pieces one by one and install them in the list
        pieces.add(makeChessPiece("white", "rook"));
        pieces.add(makeChessPiece("white", "rook"));
        pieces.add(makeChessPiece("white", "knight"));
        pieces.add(makeChessPiece("white", "knight"));
        pieces.add(makeChessPiece("white", "bishop"));
        pieces.add(makeChessPiece("white", "bishop"));
        pieces.add(makeChessPiece("white", "king"));
        pieces.add(makeChessPiece("white", "queen"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));
        pieces.add(makeChessPiece("white", "pawn"));

        pieces.add(makeChessPiece("black", "rook"));
        pieces.add(makeChessPiece("black", "rook"));
        pieces.add(makeChessPiece("black", "knight"));
        pieces.add(makeChessPiece("black", "knight"));
        pieces.add(makeChessPiece("black", "bishop"));
        pieces.add(makeChessPiece("black", "bishop"));
        pieces.add(makeChessPiece("black", "king"));
        pieces.add(makeChessPiece("black", "queen"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
        pieces.add(makeChessPiece("black", "pawn"));
    }

    public List<Piece> getPieceList(){
        return this.pieces;
    }

    private Piece makeChessPiece(String color, String type){
        int row;
        int col;
        int colorInd;
        boolean isWhite;


        // This is the section of code that determines which row
        // the new piece will be placed on. Normally not a fan of
        // one-liners, it seemed appropriate here given the simple logic
        if(color.equals("black")) {
            colorInd = 0;
            isWhite = false;
            if(type.equals("pawn")) row = 7;
            else row = 8;
        }
        else{
            colorInd = 1;
            isWhite = true;
            if(type.equals("pawn")) row = 2;
            else row = 1;
        }

        // Here we find the column and actually create the piece. First
        // calculate the column, then intantiate
        switch (type) {
            case "rook" :
                col = numRooks[colorInd] == 0 ? 1 : 8;
                numRooks[colorInd]++;
                return new Rook(isWhite, row, col);
            case "knight":
                col = numKnights[colorInd] == 0 ? 2 : 7;
                numKnights[colorInd]++;
                return new Knight(isWhite, row, col);
            case "bishop":
                col = numBishops[colorInd] == 0 ? 3 : 6;
                numBishops[colorInd]++;
                return new Bishop(isWhite, row, col);
            case "queen":
                col = 4;
                return new Queen(isWhite, row, col);
            case "king":
                col = 5;
                return new King(isWhite, row, col);
            case "pawn":
                numPawns[colorInd]++;
                col = numPawns[colorInd];
                return new Pawn(isWhite, row, col);
        }
        return null;
    }

    public static Piece makeTemp(pieceTypes type, int row, int col, boolean isWhite){
        switch (type){
            case R:
                return new Rook(isWhite, row, col);
            case N:
                return new Knight(isWhite, row, col);
            case B:
                return new Bishop(isWhite, row, col);
            case Q:
                return new Queen(isWhite, row, col);
            case K:
                return new King(isWhite, row, col);
            case P:
                return new Pawn(isWhite, row, col);
            default:
                return null;
        }
    }
}
