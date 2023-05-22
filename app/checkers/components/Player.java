package app.checkers.components;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.Image;

import java.util.Arrays;
import java.util.ArrayList;

public class Player{

	private String colorPiece; // Identificador da peça
    private ImageIcon pieceIcon, queenIcon; // Ícones de peça comum e de peça promovida
    private int promotiolin; // Linha em que a peça deve ser promovida
    private Direction direction; // Direção que as peças comuns devem seguir
	private ArrayList<int[]> squad = new ArrayList<>(); // Posições das peças no tabuleiro
	private ArrayList<int[]> playable = new ArrayList<>(); // Posições das peças aptas a capturar
	private ArrayList<int[]> blocked = new ArrayList<>(); // Posições das peças aptas a capturar


    public Player(String colorName, int promotiolin){
        /**
         * @param Nome da cor da peça
         * @param Linha de promoção da peça
         */
         
         this.colorPiece = colorName;
         
        this.pieceIcon = new ImageIcon(new ImageIcon(("../src/"+colorName+"_PIECE.png")).getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)); 
        this.pieceIcon.setDescription(this.colorPiece);
        
        this.queenIcon = new ImageIcon(new ImageIcon("../src/"+colorName+"_QUEEN.png").getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH));
        this.queenIcon .setDescription(this.colorPiece+"_queen");
        
        this.promotiolin = promotiolin;
        this.direction = promotiolin == 0 ? Direction.UP : Direction.DOWN;
    }

	public ImageIcon getIcon(){
        // @return Ícone da peça comum
		return this.pieceIcon;
	}

	public String getUpperColor(){
        // @return Ícone da peça comum
		return this.colorPiece.toUpperCase();
	}

    public ImageIcon getQueenIcon(){
        // @return Ícone da peça promovida
		return this.queenIcon;
	}

    public Direction getDirection(){
        // @return Direção que a peça deve seguir
		return this.direction;
	}
	
	public ArrayList<int[]> getCordinates(){
        // @return Lista das posições de todas as peças
		return this.squad;
	}

	private int indexOf(int[] position, ArrayList<int[]> list){
		/**
		* @param Posição a ser procurada
		* @param Lista em que a posição deve ser procurada
		* @return Índice da posição na lista ou -1 em caso de ausência
		*/
		
		for(int index = 0; index < list.size(); index++){
			if(Arrays.equals(list.get(index), position)){
				return index;
			}
		}
		return -1;
	}
	
    public void appendCoordinate(int[] position){
        this.squad.add(position);
        this.playable.add(position);
    }
	
	public void appendPlayble(int[] position){
		this.playable.add(position);
	}

	public void appendBlocked(int[] position){
		this.blocked.add(position);
	}

	public void clearBlocked(){
		this.blocked.clear();
	}
	
	public void clearPlayable(){
		this.playable.clear();
	}
    
	public void removeCoordinate(int[] position){	
    	int index = this.indexOf(position, squad);
    	if(index > -1){
    	 	this.squad.remove(index);
    	}        
    }

    public void updateCoordinate(int[] old, int[] replace){
		int index = this.indexOf(old, squad);
    	if(index != -1){
    	 	this.squad.set(index, replace);
    	}
    }

    public boolean contains(int[] position){
        /**
		 * @param Ícone da peça clicada
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */ 
        return this.indexOf(position, squad) != -1;
    }
    
	public boolean isLoser(){
        /**
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */
		int n = this.squad.size();
        return n == 0 || n == this.blocked.size();
    }

	public boolean isQueen(Icon genericPiece){
        /**
		 * @param Ícone da peça clicada
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */
        return genericPiece != null &&  ((ImageIcon) genericPiece).getDescription().equals(this.colorPiece+"_queen");
    }
    
    public boolean isPromotable(int line){
    	return line == this.promotiolin;
    }
    
    public boolean isPlayable(int[] position){		
    	return (this.contains(position) && playable.size() == 0) || this.indexOf(position, playable) != -1;
    }

}
