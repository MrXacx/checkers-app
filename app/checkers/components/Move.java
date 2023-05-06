package app.checkers.components;

import java.util.ArrayList;
import javax.swing.JButton;

public class Move{
	private int[][] possibleMoves;
	private JButton origin;

	public Move(JButton square, int line, int column, Direction direction){
		/**
		 * @param Botão de origem
		 * @param Linha do objeto no array
		 * @param Coluna do objeto na linha
		 * @param Direção que a peça deve seguir
		 */
		this.origin = square; // Define origem do evento
		ArrayList<int[]> moves = new ArrayList<>();
		
		if(line > 0 && direction == Direction.UP ){ // Executa se o botão estiver estiver numa linha superior ao índice 0 e subindo
			this.getUpMoves(moves, line, column);
			
		}
		else if(line < 7 && direction == Direction.DOWN){ // Executa se o botão estiver estiver numa linha inferior ao índice 7 e descendo
			this.getDownMoves(moves, line, column);
		}
		
		
		this.possibleMoves =  moves.toArray(new int[0][]); // Passa possiveís jogadas		
	}
	private void getUpMoves(ArrayList<int[]> list, int line, int column){
		/**
		* @param Collection de possíveis jogadas
		* @param Linha em que botão está localizado
		* @param Coluna em que o botão está localizado
		*/
		
		if(column > 0){
			list.add(new int[]{line-1, column-1});
		}
		if(column < 7){
			list.add(new int[]{line-1, column+1});
		}

	}
	
	private void getDownMoves(ArrayList<int[]> list, int line, int column){
		/**
		* @param Collection de possíveis jogadas
		* @param Linha em que botão está localizado
		* @param Coluna em que o botão está localizado
		*/
		
		if(column > 0){
			list.add(new int[]{line+1, column-1});
		}
		if(column < 7){
			list.add(new int[]{line+1, column+1});
		}

	}

	public int[][] getPossibleMoves(){
		/**
		 * @return Array de todas as possíveis jogadas
		 */
		return this.possibleMoves;
	}
	
	public boolean contains(int line, int column){
		/**
		 * @param Linha do botão no array
		 * @param Coluna do botão na linha
		 * @return Booleano da existência dos argumentos no array de jogadas possíveis
		 */
		for(int[] moves : this.getPossibleMoves()){
			if(moves[0] == line && moves[1] == column){ // Executa se a posição estiver no array de inteiros
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
