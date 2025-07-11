package piece;

import main.Board;
import main.GamePanel;
import main.Type;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class Piece {

    public Type type;
    public BufferedImage image;
    public int x, y;
    public int col, row, preCol, preRow;
    public int color;
    public Piece hittingP;
    public boolean moved, twoStepped;

    public Piece(int col, int row, int color) {
        this.col = col;
        this.row = row;
        this.color = color;
        x = getX(col);
        y = getY(row);
        preCol = col;
        preRow = row;
    }

    public BufferedImage getImage(String imagePath) {
        BufferedImage image = null;
        try {
            File imageFile = new File(imagePath);
            if (imageFile.exists()) {
                image = ImageIO.read(imageFile);
            } else {
                System.err.println("Image not found: " + imagePath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }




    public int getX(int col) {
        return col* Board.SQUARE_SIZE;
    }

    public int getY(int row) {
        return row* Board.SQUARE_SIZE;
    }

    public int getCol(int x) {return (x + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;}

    public int getRow(int y) {return (y + Board.HALF_SQUARE_SIZE)/Board.SQUARE_SIZE;}

    public Piece getHittingP(int targetCol, int targetRow){
        for (Piece piece : GamePanel.simPieces){
            if (piece.col == targetCol && piece.row == targetRow && piece != this){
                return piece;
            }
        }
        return null;
    }

    public int getIndex(){
        for (int index = 0; index < GamePanel.simPieces.size(); index++) {
            if(GamePanel.simPieces.get(index) == this){
                return index;
            }
        }
        return 0;
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(image, x, y, Board.SQUARE_SIZE, Board.SQUARE_SIZE, null);
    }

    public void updatePosition() {

        //To check En Passant
        if (type == Type.PAWN){
            if (Math.abs(row - preRow) == 2){
                twoStepped = true;
            }
        }


        x = getX(col);
        y = getY(row);
        preCol = getCol(x);
        preRow = getRow(y);
        moved = true;
    }

    public boolean canMove(int targetCol,int targetRow){
        return false;
    }

    public boolean isWithThinBoard(int targetCol, int targetRow){
        if (targetCol >= 0 && targetRow <= 7 && targetRow >= 0 &&
        targetCol <= 7){
            return true;
        }
        return false;
    }

    public void resetPosition() {
        col = preCol;
        row = preRow;
        x = getX(col);
        y = getY(row);
    }

    public  boolean isValidSquare(int targetCol, int targetRow){
        hittingP = getHittingP(targetCol, targetRow);
        if (hittingP == null){ // This square is VACANT
            return true;
        }else { // this square is OCCUPIED
            if (hittingP.color != this.color){ // If the color is different, it can be captured
                return true;
            }else {
                hittingP = null;
            }
        }
        return false;
    }

    public boolean isSameSquare(int targetCol, int targerRow){
        if (targetCol == preCol && targerRow == preRow){
            return true;
        }
        return false;
    }

    public boolean pieceIsOnStraightLine(int targetCol, int targetRow){
        //When this piece is moving to the left
        for (int c = preCol - 1; c > targetCol ; c--) {
            for (Piece piece : GamePanel.simPieces){
                if (piece.col == c && piece.row == targetRow){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //When this piece is moving to the right
        for (int c = preCol + 1; c < targetCol ; c++) {
            for (Piece piece : GamePanel.simPieces){
                if (piece.col == c && piece.row == targetRow){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //When this piece is moving up
        for (int r = preRow - 1; r > targetRow ; r--) {
            for (Piece piece : GamePanel.simPieces){
                if (piece.col == targetCol && piece.row == r){
                    hittingP = piece;
                    return true;
                }
            }
        }
        //When this piece is moving down
        for (int r = preRow + 1; r < targetRow ; r++) {
            for (Piece piece : GamePanel.simPieces){
                if (piece.col == targetCol && piece.row == r){
                    hittingP = piece;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean pieceIsOnDiagonalLine(int targetCol, int targetRow) {

        if (targetRow < preRow) {
            //Up left
            for (int c = preCol - 1; c > targetCol; c--) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow - diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }
            //Up right
            for (int c = preCol + 1; c < targetCol; c++) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow - diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }

        if (targetRow > preRow) {
            //Down left
            for (int c = preCol - 1; c > targetCol; c--) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces) {
                    if (piece.col == c && piece.row == preRow + diff) {
                        hittingP = piece;
                        return true;
                    }
                }
            }
            //Down right
            for (int c = preCol + 1; c < targetCol ; c++) {
                int diff = Math.abs(c - preCol);
                for (Piece piece : GamePanel.simPieces){
                    if (piece.col == c && piece.row == preRow + diff){
                        hittingP = piece;
                        return true;
                    }
                }
            }
        }
        return false;
    }

}
