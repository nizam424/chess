package piece;

import main.GamePanel;
import main.Type;

public class Rook extends Piece{
    public Rook(int col, int row, int color) {
        super(col, row, color);

        type = Type.ROOK;

        if (color == GamePanel.WHITE) {
            image = getImage("D:\\test chess\\ChessGame-master\\res\\piece\\w-rook.png");
        }else {
            image = getImage("D:\\test chess\\ChessGame-master\\res\\piece\\b-rook.png");
        }
    }

    public boolean canMove(int targetCol, int targetRow){
        if (isWithThinBoard(targetCol, targetRow) && !isSameSquare(targetCol, targetRow)){
            // Rook can move as long as either its col or row is the same
            if (targetCol == preCol || targetRow == preRow){
                if (isValidSquare(targetCol,targetRow) && !pieceIsOnStraightLine(targetCol, targetRow)){
                    return true;
                }
            }
        }
        return false;
    }
}
