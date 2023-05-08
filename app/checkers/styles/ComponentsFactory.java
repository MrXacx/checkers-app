package app.checkers.styles;

import javax.swing.JButton;
import javax.swing.*;

import java.awt.Color;


import app.checkers.components.*;

public class ComponentsFactory{
	private Color[] color = {Color.WHITE, Color.BLACK};
	private Move move;
	private final int LENGTH = 8;
	protected JButton[][] board;
    
    public void createBoard(Player[] player){	 
		/**
		* @param Array de jogadores
		*/ 	
		
    	this.board  = new JButton[this.LENGTH][this.LENGTH];

		for(int line = 0; line < this.LENGTH; line++){
			for(int column = 0; column < this.LENGTH; column++){ 			
				this.board[line][column]  = styleButton(new JButton(), column%2); // Inicia botão
				final int nLine = line;
				final int nColumn = column;

				this.board[line][column].addActionListener(evt -> {
					var icon = this.board[nLine][nColumn].getIcon();
					
					if(icon instanceof Icon && player[0].contains(icon)){ // Executa se houver um ícone do botão e a peça pertencer ao jogador da vez					
							if(move instanceof Move){ // Executa em caso de segundo clique indevido
								this.paintButtons(move.getPossibleMoves(), Color.WHITE); // Faz botões da lista voltarem ao padrão
								move = null; // Anula jogada
								return;
							}
							
							move = new Move(this.board[nLine][nColumn], nLine, nColumn, player[0].getDirection()); // Inicia jogada no botão de origem
							move.setPossibleMoves(this.searchCaptures(nLine, nColumn, player));
							
							if(this.paintButtons(move.getPossibleMoves(), Color.GREEN) == 0){
								if(this.paintButtons(move.getPossibleMoves(1), Color.GREEN) == 0){
									move = null;
								}
							}
						
					}
					else if(move instanceof Move && move.contains(nLine, nColumn)){ // Executa se o botão for o segundo clique
					
						this.paintButtons(move.getPossibleMoves(), Color.WHITE); // Faz botões da lista voltarem ao padrão
						move.moveTo(this.board[nLine][nColumn]); // Move peça para o argumento
						move = null; // Anula manipulador
						this.reverseArray(player); // Alterna jogador
						
					}
					
				});			
			}
			this.reverseArray(this.color); // Inverte posições do array das cores
		}
		
		this.addPieces(player[0].getIcon(), 0, 3);
		this.addPieces(player[1].getIcon(), 5, 8);
	}

	private void addPieces(Icon icon, int fromIndex, int toIndex){
		/**
		 * @param Ícone da imagem a ser adicionada ao botão
		 * @param Índice inicial incluso na iteração
		 * @param Íncide final excluso da iteração
		 */

		for(int line = fromIndex; line < toIndex; line++){
			for(int column = line % 2; column < this.LENGTH; column += 2){
				this.board[line][column].setIcon(icon); // Define ícone
			}
		}
	}
	
	private JButton styleButton(JButton button, int index){
		/**
		 * @param Botão a ser estilizado
		 * @param Índice de cor do background
		 * @return Botão estilizado
		 */
		button.setBackground (color[index]); // Define cor do background de acordo com o módulo
		button.setFocusPainted(false); // Retira foco
		return button;
	}
	
	private int paintButtons(int[][] positions, Color color){
		/**
		 * @param Array com as posições dos botões a serem pintados
		 * @param Cor que será utilizado no background
		 * @return Número de botões pintados
		 */
		 int count = 0;
		for(int[] coord : positions){
			if(!(this.board[coord[0]][coord[1]].getIcon() instanceof ImageIcon)){
				this.board[coord[0]][coord[1]].setBackground(color); // Evidencia possíveis jogadas
				count++;
			}
		}
		return count;
	}

	private void reverseArray(Object[] genericArray){
		/**
		 * @param Array de duas posições a ser revertido
		 */

		var aux = genericArray[1];
		genericArray[1] = genericArray[0];
		genericArray[0] = aux;
	}
	

	
	private int[][] searchCaptures(int nLine, int nColumn, Player[] player){
		/**
		 * @param Linha do evento
		 * @param Coluna do evento
		 * @return Array de jogadas aptas a captura
		 */
		int[][] maxMoves = move.getMaxMoves();
		int diff, n;
		int actualMax[] = {nLine, nColumn};
		
		if(player[0].getDirection() == Direction.UP){
			diff = Math.abs(nLine - maxMoves[0][0]);
			
			if(diff >= 2){
				n = 1;
				while(diff > n){
					if(this.board[nLine-n][nColumn-n].getIcon() instanceof ImageIcon && player[1].contains(this.board[nLine-n][nColumn-n].getIcon())){
						n++;
						if(!(this.board[nLine-n][nColumn-n].getIcon() instanceof ImageIcon)){
							maxMoves[0][0] = nLine-n;
							maxMoves[0][1] = nColumn-n;
							n++;
							continue;
						}
					}
					break;
				}
			}
			
			diff = Math.abs(nLine - maxMoves[1][0]);
			if(diff >= 2){
				n = 1;
				while(diff > n){
					if(this.board[nLine-n][nColumn+n].getIcon() instanceof ImageIcon && player[1].contains(this.board[nLine-n][nColumn+n].getIcon())){
						n++;
						if(!(this.board[nLine-n][nColumn+n].getIcon() instanceof ImageIcon)){
							maxMoves[1][0] = nLine-n;
							maxMoves[1][1]= nColumn+n;
							n++;
							continue;
						}
					}
					break;
				}
			}
		}
		
		else if(player[0].getDirection() == Direction.DOWN){
			diff = Math.abs(nLine - maxMoves[0][0]);
			
			if(diff >= 2){
				n = 1;
				while(diff > n){
					if(this.board[nLine+n][nColumn-n].getIcon() instanceof ImageIcon && player[1].contains(this.board[nLine+n][nColumn-n].getIcon())){
						n++;
						if(!(this.board[nLine+n][nColumn-n].getIcon() instanceof ImageIcon)){
							maxMoves[0][0] = nLine+n;
							maxMoves[0][1] = nColumn-n;
							n++;
							continue;
						}
					}
					break;
				}
			}
			
			diff = Math.abs(nLine - maxMoves[1][0]);
			if(diff >= 2){
				n = 1;
				while(diff > n){
					if(this.board[nLine+n][nColumn+n].getIcon() instanceof ImageIcon && player[1].contains(this.board[nLine+n][nColumn+n].getIcon())){
						n++;
						if(!(this.board[nLine+n][nColumn+n].getIcon() instanceof ImageIcon)){
							maxMoves[1][0] = nLine+n;
							maxMoves[1][1]= nColumn+n;
							n++;
							continue;
						}
					}
					break;
				}
			}
		}


		


		

		

		
		return maxMoves;
	}
}

