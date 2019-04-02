import java.util.List;
import Pieces.Piece;

public class Logic {
    public boolean isCheckmate(){
        return false;
    }

    public boolean isCheck(boolean onWhite, List<Piece> pieces){
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
