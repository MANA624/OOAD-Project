package Pieces;

import java.util.List;

class King extends Piece {
    King(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.K, row, col);
        behaviors.add(new WrapKing());
    }

    public List<Piece> makeMove(Move move, List<Piece> pieces){
        if(move.isKingCastle){
            return kingSideCastle(pieces);
        }
        else if(move.isQueenCastle){
            return queenSideCastle(pieces);
        }

        return super.makeMove(move, pieces);
    }

    private List<Piece> kingSideCastle(List<Piece> pieces){
        return null;
    }
    private List<Piece> queenSideCastle(List<Piece> pieces){
        return null;
    }
}
