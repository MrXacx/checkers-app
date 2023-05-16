package app.checkers.components;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.swing.JButton;

public class Move{

	private JButton origin; // Botão de origem do evento
	private int line, column; // Coordenadas do botão
	private List<int[]> possibleMoves = new ArrayList<>(); // Lista de jogadas viáveis
	private List<JButton> possibleCaptures = new ArrayList<>(); // Lista de capturas viáveis

	public Move(JButton square, int line, int column){
		/**
		 * @param Botão de origem
		 * @param Linha do objeto no array
		 * @param Coluna do objeto na linha
		 */
		 
		this.origin = square; // Define origem do evento
		this.line = line; // Define linha a do tabuleiro em que está o botão
		this.column = column; // Define a coluna da linha em que está o botão
		
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

	public int[][] fetchCaptures(JButton[][] board, Player owner){
		/**
		* @param Tabuleiro
		* @param Dono das peças a serem capturadas
		* @return Lista com todas as jogadas de captura
		*/
		
		this.possibleMoves.clear(); // Limpa lista de possíveis jogadas
		this.possibleCaptures.clear(); // Limpa lista de possíveis capturas
		
		if(this.line > 1){
			// Captura na diagonal superior esquerda
			if(this.column > 1 && (owner.contains(board[this.line-1][this.column-1].getIcon()) && board[this.line-2][this.column-2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line-2, this.column-2});	
				this.possibleCaptures.add(board[this.line-1][this.column-1]);
			}		
			// Captura na diagonal superior direita
			if(this.column < 6 && (owner.contains(board[line-1][this.column+1].getIcon()) && board[this.line-2][this.column+2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line-2, this.column+2});
				this.possibleCaptures.add(board[this.line-1][this.column+1]);
			}			
		}
		
		if(this.line < 6){
			// Captura na diagonal inferior esquerda
			if(this.column > 1 && (owner.contains(board[this.line+1][this.column-1].getIcon()) && board[this.line+2][this.column-2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line+2, this.column-2});
				this.possibleCaptures.add(board[this.line+1][this.column-1]);
			}
			// Captura na diagonal inferior direita
			if(this.column < 6 && (owner.contains(board[this.line+1][this.column+1].getIcon()) && board[this.line+2][this.column+2].getIcon() == null)){
				this.possibleMoves.add(new int[]{this.line+2, this.column+2});
				this.possibleCaptures.add(board[this.line+1][this.column+1]);
			}			
		}
		
		return possibleMoves.toArray(new int[0][]);
	}
	
	public int[][] fetchMaxMoves(JButton[][] board){
		/**
		* @param Tabuleiro
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
	
	public boolean isCapture(){
		// @return Booleano de se a lista de capturas viáveis está preenchida
		return this.possibleCaptures.size() > 0;
	}
	
	public int indexOf(int line, int column) throws IndexOutOfBoundsException{
		/**
		* @param Linha a ser procurada
		* @param Colua a ser procurada
		* @return Índice da coordenada na lista dejogadas viáveis
		*/
		int[] wanted = new int[]{line, column};
		for(int index = 0; index < this.possibleMoves.size(); index++){
			if(Arrays.equals(this.possibleMoves.get(index), wanted)){
				return index;
			}
		}
		
		throw new IndexOutOfBoundsException(String.format("Não foi possível encontrar o índice de [%d, %d] entre as possíveis jogadas", line, column));
	}

	public boolean contains(int line, int column){
		/**
		 * @param Linha do botão no array
		 * @param Coluna do botão na linha
		 * @return Booleano da existência dos argumentos no array de jogadas possíveis
		 */
		 
		int[] wanted = new int[]{line, column};
		for(int index = 0; index < this.possibleMoves.size(); index++){
			if(Arrays.equals(this.possibleMoves.get(index), wanted)){
				return true;
			}
		}
		 return false;

	}
	
	public void moveTo(JButton end, int lin, int col){
		// @param Botão de destino da imagem
		
		
		end.setIcon(this.origin.getIcon()); // Adiciona imagem no botão de destion
		this.origin.setIcon(null); // Retira imagem do botão de origem
		this.origin = end;
		this.line = lin;
		this.column = col;
	}

	public int[] getPosition(){
		return new int[]{this.line, this.column};
	}
	
	public void capture(int index){
		this.possibleCaptures.get(index).setIcon(null); // Retira imagem do botão de origem
	}
}
