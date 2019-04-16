import java.util.List;
import Pieces.*;

public class Logic {
    public boolean isCheckmate(){
        return false;
    }

    boolean isCheck(boolean againstWhite, List<Piece> pieces){
        Piece king = null;
        for(Piece piece: pieces){
            if(piece.getPieceType() == pieceTypes.K && piece.getIsWhite() == againstWhite){
                king = piece;
                break;
            }
        }

        // This shouldn't have to exist, but it makes Java happy
        if(king == null){
            return false;
        }

        for(Piece piece : pieces){
            if(piece.getIsWhite() != againstWhite){
                if(piece.canMoveToSquare(pieces, king.getRow(), king.getCol())){
                    return true;
                }
            }
        }

        return false;
    }

    // This method will likely be the hardest method to implement, and
    // furthermore is the least likely to occur. In order to check there
    // will need to be an equality check between lists of lists of pieces,
    // and check if the same three situations have occurred. This likely
    // won't get implemented for this project for CSCI 4/5448
    public boolean isDraw(){
        return false;
    }
}
