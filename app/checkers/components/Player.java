package app.checkers.components;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;

public class Player {
    public Image pieceIcon; // Imagem das peças comuns
    public Image queenIcon; // Imagem das peças promovidas
    private int numberPieces = 12; // Total de peças que jogador possui
    private Direction direction; // Direção que as peças comuns devem seguir
    private String colorPiece;
	
    public Player(String colorName, Direction direction){
        /**
         * @param Nome da cor da peça
         * @param Direção original que as peças devem seguir
         */
         this.colorPiece = colorName;
        this.pieceIcon = (new ImageIcon("../src/"+colorName+"_PIECE.png")).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH); //new ImageIcon("../src/PECA_"+pieceName+".png");
        this.queenIcon = (new ImageIcon("../src/dama.png")).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH); //new ImageIcon("../src/PECA_"+pieceName+".png");
        this.direction = direction;
    }

	public ImageIcon getIcon(){
        /**
         * @return Ícone da peça comum
         */
         
		var icon = new ImageIcon(this.pieceIcon);
		icon.setDescription(this.colorPiece);
		return icon;
	}

    public ImageIcon getQueenIcon(){
        /**
         * @return Ícone da peça comum
         */
         
		var icon = new ImageIcon(this.queenIcon);
		icon.setDescription(this.colorPiece);
		return icon;
	}

    public Direction getDirection(){
        /**
         * @return Direção que a peça deve seguir
         */
		return this.direction;
	}

    public boolean contains(Icon genericPiece){
        /**
		 * @param Ícone da peça clicada
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */
        return genericPiece != null &&  ((ImageIcon) genericPiece).getDescription().equals(this.colorPiece);
    }

    public void decrementSquad(){
        this.numberPieces--; // Reduz número de peças em uma unidade
    }
    public void decrementSquad(int number){
        /**
         * @paran número de peças a ser decrementado
         */
        this.numberPieces -= number; // Reduz número de peças em em quantidade variada
    }
}
