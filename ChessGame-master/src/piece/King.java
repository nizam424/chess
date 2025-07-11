package piece;

import main.GamePanel;
import main.Type;

public class King extends Piece{
    public King(int col, int row, int color) {
        super(col, row, color);

        type = Type.KING;

        if (color == GamePanel.WHITE) {
            image = getImage("D:\\test chess\\ChessGame-master\\res\\piece\\w-king.png");
        }else {
            image = getImage("D:\\test chess\\ChessGame-master\\res\\piece\\b-king.png");
        }
    }

    public boolean canMove(int targetCol,int targetRow){
        if (isWithThinBoard(targetCol,targetRow)){
            //Movement
            if (Math.abs(targetCol - preCol) +
                    Math.abs(targetRow - preRow) == 1 ||
                Math.abs(targetCol - preCol) * Math.abs(targetRow - preRow) == 1 ) {

                if(isValidSquare(targetCol, targetRow)){
                    return true;
                }
            }
            //Castling
            if(!moved){

                //Right casling
                if (targetCol == preCol + 2 && targetRow == preRow && !pieceIsOnStraightLine(targetCol,targetRow)){
                    for (Piece piece : GamePanel.simPieces){
                        if (piece.col == preCol + 3 && piece.row == preRow && !piece.moved){
                            GamePanel.castlingP = piece;
                            return true;
                        }
                    }
                }
                //Left casling
                if (targetCol == preCol + 2 && targetRow == preRow && !pieceIsOnStraightLine(targetCol,targetRow)){
                    Piece[] p = new Piece[2];
                    for (Piece piece : GamePanel.simPieces){
                        if (piece.col == preCol - 3 && piece.row == targetRow){
                            p[0] = piece;
                        }
                        if (piece.col == preCol - 4 && piece.row == targetRow){
                            p[1] = piece;
                        }
                        if (p[0] == null && p[1] != null && !p[1].moved){
                            GamePanel.castlingP = p[1];
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
