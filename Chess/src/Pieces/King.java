package Pieces;

import java.util.ArrayList;
import java.util.List;

class King extends Piece {
    King(boolean isWhite, int row, int col){
        super(isWhite, pieceTypes.K, row, col);
        behaviors.add(new WrapKing());
    }

    public List<Piece> makeMove(Move move, List<Piece> pieces){
        if(move.isKingCastle || move.isQueenCastle){
            return castling(pieces, move);
        }

        return super.makeMove(move, pieces);
    }

    private List<Piece> castling(List<Piece> pieces, Move move){
        King king = this;
        Rook rook = null;
        int rookCol = move.isKingCastle ? 8 : 1;
        int backRow = this.getIsWhite() ? 1 : 8;
        int kingInd = -1;
        int rookInd = -1;
        int destCol;
        List<Piece> temp;

        List<Piece> passList = new ArrayList<>(pieces);

        // Remove the moving piece out of the list to pass to the moving service
        // This way the service knows which piece is being moved
        for(int i=0; i<pieces.size(); i++){
            if(pieces.get(i).getPieceType() == pieceTypes.R &&
                    pieces.get(i).getCol() == rookCol &&
                    pieces.get(i).getRow() == backRow){
                rook = (Rook)passList.get(i);
                rookInd = i;
            }
            if(pieces.get(i).equals(this)){
                kingInd = i;
            }
        }
        if(rookInd == -1 || kingInd == -1){
            return null;
        }
        if(rookInd > kingInd) {
            passList.remove(rookInd);
            passList.remove(kingInd);
        }else{
            passList.remove(kingInd);
            passList.remove(rookInd);
        }

        // This code makes the assumption that the king is only
        // ever going to have one service. If more is added, just
        // make sure that the king service is first
        WrapKing service = (WrapKing)behaviors.get(0);
        if((temp = service.castling(move, passList, king, rook)) != null){
            king.madeMove();
            rook.madeMove();
            return temp;
        }

        return null;
    }
}
