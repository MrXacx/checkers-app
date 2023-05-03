package app.checkers.components;

import javax.swing.Icon;
import javax.swing.ImageIcon;

public class Player {
    private ImageIcon pieceIcon;
    private ImageIcon queenIcon;
    private int numberPieces;
    
    public Player(String pieceName){
        this.pieceIcon = new ImageIcon(pieceName);
        this.queenIcon = new ImageIcon(pieceName);
        this.numberPieces = 12;
    }

    public boolean contains(Icon genericPiece){
        return genericPiece.equals(this.pieceIcon) || genericPiece.equals(this.queenIcon);
    }

    public void decrementSquad(){
        this.numberPieces--;
    }
    public void decrementSquad(int number){
        this.numberPieces -= number;
    }
}
