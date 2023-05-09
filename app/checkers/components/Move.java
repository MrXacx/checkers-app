package app.checkers.components;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JButton;

public class Move{
	private List<int[]> possibleMoves = new ArrayList<>();
	private JButton origin;
	private int line, column;
	private Direction direction;
	private int skip = 0;


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

	public int[][] getPossibleMoves(int skip){
		/**
		 * @return Array de todas as possíveis jogadas
		 */

		 this.skip = skip;

		if(this.line > (this.skip-1) && direction == Direction.UP ){ // Executa se o botão estiver estiver numa linha superior ao índice 0 e subindo
			this.getUpMoves();
		}
		else if(this.line < (8 - this.skip) && direction == Direction.DOWN){ // Executa se o botão estiver estiver numa linha inferior ao índice 7 e descendo
			this.getDownMoves();
		}
		

		return this.possibleMoves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	
	public int[][] getPossibleMoves(){
		/**
		 * @return Array de todas as possíveis jogadas
		 */

		return this.possibleMoves.toArray(new int[0][]); // Passa possiveís jogadas	
	}
	
	public void setPossibleMoves(int[][] moves){
		/**
		 * @return Array de todas as possíveis jogadas
		 */

		this.possibleMoves.addAll(Arrays.asList(moves)); // Passa possiveís jogadas	
	}

	public int getSkip(){
		return this.skip;
	}

	public int indexOf(int line, int column){
		for(int index = 0; index < this.possibleMoves.size(); index++){
			int[] coord = this.possibleMoves.get(index);
			if(coord[0] == line && coord[1] == column){
				return index;
			}
		}
		return -1;
	}

	public int[][] getMaxMoves(){
		/**
		 * @return Lista de jogadas mais longas possíveis
		 */

		ArrayList<int[]> maxMoves = new ArrayList<>(); // Inicia lista
		final int lineToMax = 7 - this.line; // Intervalo entre a posição da linha e o 7
		final int columnToMax = 7 - this.column; // Intervalo entre a posição da coluna e o 7
		
		if(this.direction == Direction.UP){
			maxMoves.add(this.line < this.column ? new int[]{0, this.column - this.line} : new int[]{this.line - this.column, 0}); // Movimenta na diagonal superior esquerda
			maxMoves.add(this.line < columnToMax ? new int[]{0, this.column + this.line} : new int[]{this.line - columnToMax, 7}); // Moviemnta na diagonal superior direita
		}
		
		if(this.direction == Direction.DOWN){
			maxMoves.add(lineToMax < this.column ? new int[]{7, this.column - lineToMax} : new int[]{this.line + this.column, 0}); // Movimenta na diagonal inferior esquerda
			maxMoves.add(lineToMax < columnToMax ? new int[]{7, this.column + lineToMax} : new int[]{this.line + columnToMax, 7}); // Moivmenta na diagonal inferior direita
		}
		
		return maxMoves.toArray(new int[0][]);
	}
	
	private void getUpMoves(){
		
		if(this.column > (this.skip-1)){
			this.possibleMoves.add(new int[]{this.line-this.skip, this.column-this.skip});
		}
		if(this.column < (8-skip)){
			this.possibleMoves.add(new int[]{this.line-this.skip, this.column+this.skip});
		}

	}
	
	private void getDownMoves(){
		
		if(this.column > (this.skip-1)){
			this.possibleMoves.add(new int[]{this.line+this.skip, this.column-this.skip});
		}
		if(this.column < (8-skip)){
			this.possibleMoves.add(new int[]{this.line+this.skip, this.column+this.skip});
		}

	}

	public boolean contains(int line, int column){
		/**
		 * @param Linha do botão no array
		 * @param Coluna do botão na linha
		 * @return Booleano da existência dos argumentos no array de jogadas possíveis
		 */
		for(int[] coord : this.possibleMoves.toArray(new int[0][])){
			if(coord[0] == line && coord[1] == column){
				return true;
			}
		}
		 return false;

	}
	
	public void moveTo(JButton end){
		/**
		 * @param Botão de destino da imagem
		 */

		end.setIcon(this.origin.getIcon()); // Adiciona imagem no botão de destion
		this.origin.setIcon(null); // Retira imagem do botão de origem
	}
}
