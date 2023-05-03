package app.checkers.components;
 import java.util.Arrays;

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
		int[][] moves = {{line-1, column-1}, {line-1, column+1}, {line+1, column-1}, {line+1, column+1}}; // Lista possíveis jogadas
		
		for(int[] move : moves){
			if((move[0] < 0 || move[0] > 7) || (move[1] < 0 || move[1] > 7)){ // Evita índices superiores a 7 ou inferiores a 0
				//moves.remove(move);
			}
		}

		this.possibleMoves =  moves; // Passa possiveís jogadas
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
