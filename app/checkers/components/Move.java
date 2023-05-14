package app.checkers.components;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

public class Move{

	private JButton origin; // Botão de origem do evento
	private int line, column; // Coordenadas do botão
	private Direction direction; // Direcção que o movimento deve seguir
	private List<int[]> moves = new ArrayList<>(); // Lista de jogadas viáveis
	private List<JButton> possibleCaptures = new ArrayList<>(); // Lista de capturas viáveis

	public Move(JButton square, int line, int column, Direction direction){
		/**
		 * @param Botão de origem
		 * @param Linha do objeto no array
		 * @param Coluna do objeto na linha
		 * @param Direção que a peça deve seguir
		 */
		 
		this.origin = square; // Define origem do evento
		this.line = line;
		this.column = column;
		this.direction = direction;
		
	}
	public int[][] searchPossibleMoves(){
		// @return Array de todas as possíveis jogadas
		
		if(this.line > 0 && direction == Direction.UP ){ // Executa se o botão estiver estiver numa linha superior ao índice 0 e subindo
			this.getUpMoves();
		}
		else if(this.line < 7 && direction == Direction.DOWN){ // Executa se o botão estiver estiver numa linha inferior ao índice 7 e descendo
			this.getDownMoves();
		}
		
		return this.moves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	
	private void getUpMoves(){
		
		if(this.column > 0){
			this.moves.add(new int[]{this.line-1, this.column-1}); // Canto superior esquerdo
		}
		if(this.column < 7){
			this.moves.add(new int[]{this.line-1, this.column+1}); // Canto superior direiro
		}

	}
	
	private void getDownMoves(){		
		if(this.column > 0){
			this.moves.add(new int[]{this.line+1, this.column-1}); // Canto inferior esquerdo
		}
		if(this.column < 7){
			this.moves.add(new int[]{this.line+1, this.column+1}); // Canto inferior direito
		}
	}

	public int[][] searchCaptures( JButton[][] board, Player owner){
		/**
		* @param Tabuleiro
		* @param Dono das peças a serem capturadas
		*/
		
		if(this.line > 1){		
			if(this.column > 1 && (owner.contains(board[this.line-1][this.column-1].getIcon()) && board[this.line-2][this.column-2].getIcon() == null)){
				//this.searchCaptures(this.line-2, this.column-2, board, owner);
				this.moves.add(new int[]{this.line-2, this.column-2});	
				this.possibleCaptures.add(board[this.line-1][this.column-1]);
			}
			if(this.column < 6 && (owner.contains(board[line-1][this.column+1].getIcon()) && board[this.line-2][this.column+2].getIcon() == null)){
				//this.searchCaptures(this.line-2, this.column+2, board, owner);
				this.moves.add(new int[]{this.line-2, this.column+2});
				this.possibleCaptures.add(board[this.line-1][this.column+1]);
			}			
		}
		
		if(this.line < 6){			
			if(this.column > 1 && (owner.contains(board[this.line+1][this.column-1].getIcon()) && board[this.line+2][this.column-2].getIcon() == null)){
				//this.searchCaptures(this.line+2, this.column-2, board, owner);
				this.moves.add(new int[]{this.line+2, this.column-2});
				this.possibleCaptures.add(board[this.line+1][this.column-1]);
			}
			if(this.column < 6 && (owner.contains(board[this.line+1][this.column+1].getIcon()) && board[this.line+2][this.column+2].getIcon() == null)){
				//this.searchCaptures(this.line+2, this.column+2, board, owner);
				this.moves.add(new int[]{this.line+2, this.column+2});
				this.possibleCaptures.add(board[this.line+1][this.column+1]);
			}			
		}
		
		return moves.toArray(new int[0][]);
	}
	
	public int[][] searchCaptures(int zLine, int zColumn, JButton[][] board, Player owner){
		/**
		* @param Tabuleiro
		* @param Dono das peças a serem capturadas
		*/
		this.moves.clear();
		this.possibleCaptures.clear();
		if(zLine > 1){		
			if(zColumn > 1 && (owner.contains(board[zLine-1][zColumn-1].getIcon()) && board[zLine-2][zColumn-2].getIcon() == null)){
				//this.searchCaptures(zLine-2, zColumn-2, board, owner);
				
				this.moves.add(new int[]{zLine-2, zColumn-2});
				this.possibleCaptures.add(board[zLine-1][zColumn-1]);
			}
			if(zColumn < 6 && (owner.contains(board[line-1][zColumn+1].getIcon()) && board[zLine-2][zColumn+2].getIcon() == null)){
				//this.searchCaptures(zLine-2, zColumn+2, board, owner);
				
				this.moves.add(new int[]{zLine-2, zColumn+2});
				this.possibleCaptures.add(board[zLine-1][zColumn+1]);
			}			
		}
		
		if(zLine < 6){			
			if(zColumn > 1 && (owner.contains(board[zLine+1][zColumn-1].getIcon()) && board[zLine+2][zColumn-2].getIcon() == null)){
				//this.searchCaptures(zLine+2, zColumn-2, board, owner);
				
				this.moves.add(new int[]{zLine+2, zColumn-2});
				this.possibleCaptures.add(board[zLine+1][zColumn-1]);
			}
			if(zColumn < 6 && (owner.contains(board[zLine+1][zColumn+1].getIcon()) && board[zLine+2][zColumn+2].getIcon() == null)){
				//this.searchCaptures(zLine+2, zColumn+2, board, owner);
				
				this.moves.add(new int[]{zLine+2, zColumn+2});
				this.possibleCaptures.add(board[zLine+1][zColumn+1]);
			}			
		}
		
		return moves.toArray(new int[0][]);
	}
	
	public int[][] searchMaxMoves(){
		// @return Lista de jogadas mais longas possíveis
		int nLine, nColumn;
		
		 // Verificar movimento na diagonal superior esquerda
        for (nLine = line - 1, nColumn = column - 1; nLine >= 0 && nColumn >= 0; nLine--, nColumn--) {
            moves.add(new int[]{nLine, nColumn});
        }

        // Verificar movimento na diagonal superior direita
        for (nLine = line - 1, nColumn = column + 1; nLine >= 0 && nColumn < 8; nLine--, nColumn++) {
            moves.add(new int[]{nLine, nColumn});
        }

        // Verificar movimento na diagonal inferior esquerda
        for (nLine = line + 1, nColumn = column - 1; nLine < 8 && nColumn >= 0; nLine++, nColumn--) {
            moves.add(new int[]{nLine, nColumn});
        }

        // Verificar movimento na diagonal inferior direita
        for (nLine = line + 1, nColumn = column + 1; nLine < 8 && nColumn < 8; nLine++, nColumn++) {
            moves.add(new int[]{nLine, nColumn});
       }
        
        return this.moves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	
	public int[][] getMoves(){
		// @return Array de todas as possíveis jogadas
		return this.moves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	
	public boolean isCapture(){
		// @return Booleano de se a lista de capturas viáveis está preenchida
		return this.possibleCaptures.size() > 0;
	}
	
	public int indexOf(int line, int column){
		for(int index = 0; index < this.moves.size(); index++){
			int[] coord = this.moves.get(index);
			if(coord[0] == line && coord[1] == column){
				return index;
			}
		}
		return -1;
	}

	public boolean contains(int line, int column){
		/**
		 * @param Linha do botão no array
		 * @param Coluna do botão na linha
		 * @return Booleano da existência dos argumentos no array de jogadas possíveis
		 */
		 
		for(int[] coord : this.moves.toArray(new int[0][])){
			if(coord[0] == line && coord[1] == column){
				return true;
			}
		}
		 return false;

	}
	
	public void moveTo(JButton end){
		// @param Botão de destino da imagem
		
		end.setIcon(this.origin.getIcon()); // Adiciona imagem no botão de destion
		this.origin.setIcon(null); // Retira imagem do botão de origem
		this.origin = end;
	}
	
	public void capture(int index){
		this.possibleCaptures.get(index).setIcon(null); // Retira imagem do botão de origem
	}
}
