package app.checkers.components;

import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.Image;
import javax.swing.JButton;

import java.util.ArrayList;
import app.checkers.styles.PlayerContainer;
public class Player{

	private String colorPiece; // Identificador da peça
    private ImageIcon pieceIcon, queenIcon; // Ícones de peça comum e de peça promovida
    private int promotionLine; // Linha em que a peça deve ser promovida
    private Direction direction; // Direção que as peças comuns devem seguir
	private ArrayList<JButton> squad = new ArrayList<>(); // Posições das peças no tabuleiro
	private ArrayList<JButton> playable = new ArrayList<>(); // Posições das peças aptas a capturar
	private ArrayList<JButton> blocked = new ArrayList<>(); // Posições das peças aptas a capturar
	private PlayerContainer container;

	private int totalQueens = 0;

    public Player(String colorName, int promotionLine){
        /**
         * @param Nome da cor da peça
         * @param Linha de promoção da peça
         */
         
         this.colorPiece = colorName;
         
        this.pieceIcon = new ImageIcon(new ImageIcon("../src/game/"+colorName+"_piece.png").getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH));
        this.pieceIcon.setDescription(this.colorPiece);
        
        this.queenIcon = new ImageIcon(new ImageIcon("../src/game/"+colorName+"_queen.png").getImage().getScaledInstance(65, 65, Image.SCALE_SMOOTH));
        this.queenIcon .setDescription(this.colorPiece+"_queen");
        
        this.promotionLine = promotionLine;
        this.direction = promotionLine == 0 ? Direction.UP : Direction.DOWN;
    }

	public void reset(){
		this.squad.clear();
		this.playable.clear();
		this.blocked.clear();
		totalQueens = 0;
	}
	public void setContainer (PlayerContainer playerContainer){
		this.container = playerContainer;
	}
	public PlayerContainer getContainer (){
		return this.container;
	}

	public ImageIcon getIcon(){
        // @return Ícone da peça comum
		return this.pieceIcon;
	}
	
	public ImageIcon getIcon(int size){
        /**
        * @param Dimensão do ícone
        * @return Ícone da peça comum
        */
		return new ImageIcon(this.pieceIcon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH));
	}

	public String getUpperColor(){
        // @return Ícone da peça comum
		return this.colorPiece.toUpperCase();
	}

    public ImageIcon getQueenIcon(){
        // @return Ícone da peça promovida
		this.container.updateDetails(this.squad.size(), ++this.totalQueens);
		return this.queenIcon;
	}

    public Direction getDirection(){
        // @return Direção que a peça deve seguir
		return this.direction;
	}
	
	public ArrayList<JButton> getCordinates(){
        // @return Lista das posições de todas as peças
		return this.squad;
	}

	private int indexOf(JButton searched, ArrayList<JButton> list){
		/**
		* @param Posição a ser procurada
		* @param Lista em que a posição deve ser procurada
		* @return Índice da posição na lista ou -1 em caso de ausência
		*/
		
		for(int index = 0; index < list.size(); index++){
			if(list.get(index).getName().equals(searched.getName())){
				return index;
			}
		}
		return -1;
	}
	
    public void appendCoordinate(JButton button){
        this.squad.add(button);
        this.playable.add(button);
    }
	
	public void appendPlayble(JButton button){
		this.playable.add(button);
	}

	public void appendBlocked(JButton button){
		this.blocked.add(button);
	}

	public void clearBlocked(){
		this.blocked.clear();
	}
	
	public void clearPlayable(){
		this.playable.clear();
	}
    
	public void removeCoordinate(JButton button){	
    	int index = this.indexOf(button, squad);
    	if(index > -1){
    	 	this.squad.remove(index);
			this.container.updateDetails(this.squad.size(), this.isQueen(button.getIcon()) ? --this.totalQueens : this.totalQueens);
    	}
    }

    public void updateCoordinate(JButton old, JButton replace){
		int index = this.indexOf(old, squad);
    	if(index != -1){
    	 	this.squad.set(index, replace);
    	}    	
    }

    public boolean contains(JButton button){
        /**
		 * @param Ícone da peça clicada
		 * @return Booleano da equivalência entre a peça selecionada e as peças que o jogador possui
		 */ 
        return this.indexOf(button, squad) != -1;
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
    	return line == this.promotionLine;
    }
    
    public boolean isPlayable(JButton button){		
    	return (this.contains(button) && playable.size() == 0) || this.indexOf(button, playable) != -1;
    }

}
