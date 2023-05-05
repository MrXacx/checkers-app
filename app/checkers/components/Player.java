package app.checkers.components;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Player {
    private ImageIcon pieceIcon;
    private ImageIcon queenIcon;
    private int numberPieces;
	
    public Player(String pieceName){
        this.pieceIcon = new ImageIcon("../src/peca.png"); //new ImageIcon("../src/PECA_"+pieceName+".png");
        this.queenIcon = new ImageIcon(pieceName);
        this.numberPieces = 12;

    }

	public Image getImage(){
		return this.pieceIcon.getImage();
	}

    public boolean contains(Icon genericPiece){
        return genericPiece.toString().equals(this.pieceIcon.toString()) || genericPiece.toString().equals(this.queenIcon.toString());
    }

    public void decrementSquad(){
        this.numberPieces--;
    }
    public void decrementSquad(int number){
        this.numberPieces -= number;
    }
}
