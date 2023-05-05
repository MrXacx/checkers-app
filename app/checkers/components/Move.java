package app.checkers.components;

import java.util.ArrayList;
import javax.swing.JButton;

public class Move{
	private int[][] possibleMoves;
	private JButton origin;

	public Move(JButton square, int line, int column){
		/**
		 * @param Botão de origem
		 * @param Linha do objeto no array
		 * @param Coluna do objeto na linha
		 */
		this.origin = square; // Define origem do evento
		ArrayList<int[]> moves = new ArrayList<>(); 
		
		if(line > 0){
			if(column > 0){
				moves.add(new int[]{line-1, column-1});
			}
			if(column < 7){
				moves.add(new int[]{line-1, column+1});
			}
		}

		if(line < 7){
			if(column > 0){
				moves.add(new int[]{line+1, column-1});
			}
			if(column < 7){
				moves.add(new int[]{line+1, column+1});
			}	
		}
		
		this.possibleMoves =  moves.toArray(new int[0][]); // Passa possiveís jogadas
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
