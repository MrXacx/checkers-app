package app.checkers.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.JButton;

public class Move{
	public static JButton[][] board; // Tabuleiro
	private JButton origin; // Botão de origem do evento
	private int line, column; // Coordenadas do botão
	private List<int[]> possibleMoves = new ArrayList<>(); // Lista de jogadas viáveis
	private List<JButton> possibleCaptures = new ArrayList<>(); // Lista de capturas viáveis

	public Move(int line, int column){
		/**
		 * @param Linha do objeto no array
		 * @param Coluna do objeto na linha
		 */
		 
		this.origin = board[line][column]; // Define origem do evento
		this.line = line; // Define linha a do tabuleiro em que está o botão
		this.column = column; // Define a coluna da linha em que está o botão
		
	}
	public static void setBoard(JButton[][] checkerboard){
		// @ Tabuleiro a ser manipulado
		board = checkerboard;
	}
	public int[][] getMoves(){
		// @return Array de todas as possíveis jogadas	
		return this.possibleMoves.toArray(new int[0][]); 
	}
	public JButton getCapture(int index){
		// @return Array de todas as possíveis jogadas	
		return this.possibleCaptures.get(index); 
	}
	
	public int[][] fetchPossibleMoves(Direction direction){
		/**
		 * @param Direção que a peça deve seguir
		 * @return Array de todas os movimentos simples possíveis
		 */
		
		if(this.line > 0 && (direction == Direction.UP || direction == Direction.ALL)){ // Executa se o botão estiver estiver numa linha superior ao índice 0 e subindo
			if(this.column > 0){
				this.possibleMoves.add(new int[]{this.line-1,this.column-1}); // Canto superior esquerdo
			}
			if(this.column < 7){
				this.possibleMoves.add(new int[]{this.line-1,this.column+1}); // Canto superior direiro
			}
		}
		
		if (this.line < 7 && (direction == Direction.DOWN || direction == Direction.ALL)){ // Executa se o botão estiver estiver numa linha inferior ao índice 7 e descendo
			if(this.column > 0){
				this.possibleMoves.add(new int[]{this.line+1,this.column-1}); // Canto inferior esquerdo
			}
			if(this.column < 7){
				this.possibleMoves.add(new int[]{this.line+1,this.column+1}); // Canto inferior direito
			}
		}
		
		return this.possibleMoves.toArray(new int[0][]); // Passa possiveís jogadas	
	}

	public int[][] fetchCaptures(Player owner){
		/**
		* @param Dono das peças a serem capturadas
		* @return Lista com todas as jogadas de captura
		*/
		
		this.possibleMoves.clear(); // Limpa lista de possíveis jogadas
		this.possibleCaptures.clear(); // Limpa lista de possíveis capturas
		
		if(this.line > 1){
			// Captura na diagonal superior esquerda
			if(this.column > 1 && (owner.contains(board[this.line-1][this.column-1]) && board[this.line-2][this.column-2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line-2,this.column-2});	
				this.possibleCaptures.add(board[this.line-1][this.column-1]);
			}		
			// Captura na diagonal superior direita
			if(this.column < 6 && (owner.contains(board[this.line-1][this.column+1]) && board[this.line-2][this.column+2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line-2,this.column+2});
				this.possibleCaptures.add(board[this.line-1][this.column+1]);
			}			
		}
		
		if(this.line < 6){
			// Captura na diagonal inferior esquerda
			if(this.column > 1 && (owner.contains(board[this.line+1][this.column-1]) && board[this.line+2][this.column-2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line+2,this.column-2});
				this.possibleCaptures.add(board[this.line+1][this.column-1]);
				
			}
			// Captura na diagonal inferior direita
			if(this.column < 6 && (owner.contains(board[this.line+1][this.column+1]) && board[this.line+2][this.column+2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line+2,this.column+2});
				this.possibleCaptures.add(board[this.line+1][this.column+1]);
			}			
		}
		
		return possibleMoves.toArray(new int[0][]);
	}
	public void moveTo(int lin, int col){
		// @param Botão de destino da imagem
		
		board[lin][col].setIcon(this.origin.getIcon()); // Adiciona imagem no botão de destion
		this.origin.setIcon(null); // Retira imagem do botão de origem
		
	}
	
	public void capture(int index){
		this.possibleCaptures.get(index).setIcon(null); // Retira imagem do botão de origem
	}
	
	public boolean isCapture(){
		// @return Booleano de se a lista de capturas viáveis está preenchida	
		return this.possibleCaptures.size() > 0;
	}
	
	public int indexOf(int[] position) throws IndexOutOfBoundsException{
		/**
		* @param Posição a ser procurada
		* @return Índice da coordenada na lista dejogadas viáveis
		*/
		
		for(int index = 0; index < this.possibleMoves.size(); index++){
			if(Arrays.equals(this.possibleMoves.get(index), position)){
				return index;
			}
		}
		
		throw new IndexOutOfBoundsException(String.format("Não foi possível encontrar o índice de [%d, %d] entre as possíveis jogadas", line, column));
	}

	public boolean contains(int[] position){
		/**
		 * @param Posição do botão no array
		 * @return Booleano da existência dos argumentos no array de jogadas possíveis
		 */
		 
		for(int index = 0; index < this.possibleMoves.size(); index++){
			if(Arrays.equals(this.possibleMoves.get(index), position)){
				return true;
			}
		}
		 return false;

	}
}
