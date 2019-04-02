package Pieces;

import java.util.List;

abstract class MovingService {
    public abstract List<Piece> makeMove(Move move, List<Piece> otherPieces, Piece thisPiece);
}
