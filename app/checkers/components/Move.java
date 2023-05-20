package app.checkers.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import javax.swing.*;

public class Move{
	public static JButton[][] board;
	private JButton origin; // Botão de origem do evento
	private int line, column; // Coordenadas do botão
	private List<int[]> possibleMoves = new ArrayList<>(); // Lista de jogadas viáveis
	private List<int[]> possibleCaptures = new ArrayList<>(); // Lista de capturas viáveis

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
		board = checkerboard;
	}
	public int[][] fetchPossibleMoves(Direction direction){
		/**
		 * @param Direção que a peça deve seguir
		 * @return Array de todas os movimentos simples possíveis possíveis jogadas
		 */
		
		if(this.line > 0 && direction == Direction.UP ){ // Executa se o botão estiver estiver numa linha superior ao índice 0 e subindo
			this.getUpMoves();
		}
		else if(this.line < 7 && direction == Direction.DOWN){ // Executa se o botão estiver estiver numa linha inferior ao índice 7 e descendo
			this.getDownMoves();
		}
		
		return this.possibleMoves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	
	private void getUpMoves(){
		
		if(this.column > 0){
			this.possibleMoves.add(new int[]{this.line-1, this.column-1}); // Canto superior esquerdo
		}
		if(this.column < 7){
			this.possibleMoves.add(new int[]{this.line-1, this.column+1}); // Canto superior direiro
		}

	}
	
	private void getDownMoves(){		
		if(this.column > 0){
			this.possibleMoves.add(new int[]{this.line+1, this.column-1}); // Canto inferior esquerdo
		}
		if(this.column < 7){
			this.possibleMoves.add(new int[]{this.line+1, this.column+1}); // Canto inferior direito
		}
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
			if(this.column > 1 && (owner.contains(new int[]{this.line-1,this.column-1}) && board[this.line-2][this.column-2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line-2, this.column-2});	
				this.possibleCaptures.add(new int[]{this.line-1,this.column-1});
			}		
			// Captura na diagonal superior direita
			if(this.column < 6 && (owner.contains(new int[]{line-1,this.column+1}) && board[this.line-2][this.column+2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line-2, this.column+2});
				this.possibleCaptures.add(new int[]{this.line-1,this.column+1});
			}			
		}
		
		if(this.line < 6){
			// Captura na diagonal inferior esquerda
			if(this.column > 1 && (owner.contains(new int[]{this.line+1,this.column-1}) && board[this.line+2][this.column-2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line+2, this.column-2});
				this.possibleCaptures.add(new int[]{this.line+1,this.column-1});
			}
			// Captura na diagonal inferior direita
			if(this.column < 6 && (owner.contains(new int[]{this.line+1,this.column+1}) && board[this.line+2][this.column+2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line+2, this.column+2});
				this.possibleCaptures.add(new int[]{this.line+1,this.column+1});
			}			
		}
		
		return possibleMoves.toArray(new int[0][]);
	}
	
	public int[][] fetchMaxMoves(){
		/**
		* @return Lista de jogadas mais longas possíveis
		*/
		int nLine, nColumn;
		
		 // Verificar movimento na diagonal superior esquerda
        for (nLine = line - 1, nColumn = column - 1; (nLine >= 0 && nColumn >= 0) && board[nLine][nColumn].getIcon() == null; nLine--, nColumn--) {
            possibleMoves.add(new int[]{nLine, nColumn});
        }

        // Verificar movimento na diagonal superior direita
        for (nLine = line - 1, nColumn = column + 1; (nLine >= 0 && nColumn < 8) && board[nLine][nColumn].getIcon() == null; nLine--, nColumn++) {
            possibleMoves.add(new int[]{nLine, nColumn});
        }

        // Verificar movimento na diagonal inferior esquerda
        for (nLine = line + 1, nColumn = column - 1; (nLine < 8 && nColumn >= 0) && board[nLine][nColumn].getIcon() == null; nLine++, nColumn--) {
            possibleMoves.add(new int[]{nLine, nColumn});
        }

        // Verificar movimento na diagonal inferior direita
        for (nLine = line + 1, nColumn = column + 1; (nLine < 8 && nColumn < 8) && board[nLine][nColumn].getIcon() == null; nLine++, nColumn++) {
            possibleMoves.add(new int[]{nLine, nColumn});
       }
        
        return this.possibleMoves.toArray(new int[0][]);
	}
	
	public int[][] getMoves(){
		// @return Array de todas as possíveis jogadas
		return this.possibleMoves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	public int[] getCapture(int index){
		// @return Array de todas as possíveis jogadas
		return this.possibleCaptures.get(index); // Passa possiveís jogadas	
	}
	
	public boolean isCapture(){
		// @return Booleano de se a lista de capturas viáveis está preenchida
		return this.possibleCaptures.size() > 0;
	}
	
	public int indexOf(int[] position) throws IndexOutOfBoundsException{
		/**
		* @param Linha a ser procurada
		* @param Colua a ser procurada
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
		 * @param Linha do botão no array
		 * @param Coluna do botão na linha
		 * @return Booleano da existência dos argumentos no array de jogadas possíveis
		 */
		 
		for(int index = 0; index < this.possibleMoves.size(); index++){
			if(Arrays.equals(this.possibleMoves.get(index), position)){
				return true;
			}
		}
		 return false;

	}
	
	public void moveTo(int lin, int col){
		// @param Botão de destino da imagem
		
		board[lin][col].setIcon(this.origin.getIcon()); // Adiciona imagem no botão de destion
		this.origin.setIcon(null); // Retira imagem do botão de origem

		this.origin = board[lin][col];
		this.line = lin;
		this.column = col;
	}

	public int[] getPosition(){
		return new int[]{this.line, this.column};
	}
	
	public void capture(int[] captured, Player owner){
		owner.remove(captured); // Decrementa plantel do adversário
		this.board[captured[0]][captured[1]].setIcon(null); // Retira imagem do botão de origem
	}
}
