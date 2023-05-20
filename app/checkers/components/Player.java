package app.checkers.components;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.Image;

import java.util.Arrays;
import java.util.ArrayList;

public class Player {

	private String colorPiece;
    private ImageIcon pieceIcon, queenIcon; // Ícones de peça comum e de peça promovida
    private int promotionLine; // Linha em que a peça deve ser promovida
    private Direction direction; // Direção que as peças comuns devem seguir
	private ArrayList<int[]> squad = new ArrayList<>();

    public Player(String colorName, int promotionLine){
        /**
         * @param Nome da cor da peça
         * @param Direção original que as peças devem seguir
         */
         this.colorPiece = colorName;
         
        this.pieceIcon = new ImageIcon(new ImageIcon(("../src/"+colorName+"_PIECE.png")).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)); 
        this.pieceIcon.setDescription(this.colorPiece);
        
        this.queenIcon = new ImageIcon(new ImageIcon("../src/dama.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        this.queenIcon .setDescription(this.colorPiece+"_queen");
        
        this.promotionLine = promotionLine;
        this.direction = promotionLine == 0 ? Direction.UP : Direction.DOWN;
        
    }

	public ImageIcon getIcon(){
        /**
         * @return Ícone da peça comum
         */
		return this.pieceIcon;
	}

    public ImageIcon getQueenIcon(){
        /**
         * @return Ícone da peça comum
         */
		return this.queenIcon;
	}

    public Direction getDirection(){
        /**
         * @return Direção que a peça deve seguir
         */
		return this.direction;
	}
	
	private int indexOf(int[] position){
		for(int index = 0; index < this.squad.size(); index++){
			if(Arrays.equals(this.squad.get(index), position)){
				return index;
			}
		}
		return -1;
	}
	
    public void add(int[] position){
        this.squad.add(position);
    }

    public void remove(int[] position){
    	
    	int index = this.indexOf(position);
    	if(index > -1){
    	 	this.squad.remove(index);
    	}
        
   		
    }

    public void replace(int[] old, int[] replace){
        int index = this.indexOf(old);
    	if(index != -1){
    	 	this.squad.set(index, replace);
    	} 
    }

    public boolean contains(int[] position){
        /**
		 * @param Ícone da peça clicada
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */ 
        return this.indexOf(position) != -1;
    }
    
     public boolean isQueen(Icon genericPiece){
        /**
		 * @param Ícone da peça clicada
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */
        return genericPiece != null &&  ((ImageIcon) genericPiece).getDescription().equals(this.colorPiece+"_queen");
    }
    
    public boolean isPromotable(int line){
    	return line == this.promotionLine;
    }
}
