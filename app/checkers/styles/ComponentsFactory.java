package app.checkers.styles;

import javax.swing.JButton;
import java.awt.Color;
import javax.swing.ImageIcon;
import javax.swing.Icon;

import app.checkers.components.*;

class ComponentsFactory{
	private final int LENGTH = 8;
	protected JButton[][] board;
	private Color[] color = {Color.WHITE, Color.BLACK};
	private Move move;

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
					
					
					Icon icon = this.board[nLine][nColumn].getIcon();
					
					if(icon instanceof Icon && player[0].contains(icon)){ // Executa se houver um ícone do botão e a peça pertencer ao jogador da vez					
						if(move instanceof Move){ // Executa em caso de segundo clique indevido
							this.paintButtons(move.getMoves(), Color.WHITE); // Faz botões da lista voltarem ao padrão
							move = null; // Anula jogada
							return;
						}
						
						move = new Move(this.board[nLine][nColumn], nLine, nColumn, player[0].getDirection()); // Inicia jogada no botão de origem

						if(this.paintButtons(move.searchCaptures(this.board, player[1]), Color.GREEN) == 0 && 
							this.paintButtons(player[0].isQueen(icon) ? move.searchMaxMoves() : move.searchPossibleMoves(), Color.GREEN) == 0){
							move = null;
						}
						
					}
					else if(move instanceof Move && move.contains(nLine, nColumn)){ // Executa se o botão for o segundo clique
						int lin = nLine;
						int col = nColumn;
						
						boolean teste;
						int[][] x = move.getMoves();
						do{
							this.paintButtons(move.getMoves(), Color.WHITE); // Faz botões da lista voltarem ao padrão
							move.moveTo(this.board[lin][col]); // Move peça para a posição selecionada

							if(move.isCapture()){ // Executa se não houver captura								
								move.capture(move.indexOf(lin, col)); // realiza captura
								player[1].decrementSquad(); // Decrementa plantel do adversário	

								
								x = move.searchCaptures(lin, col, this.board, player[1]);
								System.out.println(x.length);
								teste = x.length == 1;
								if(teste){
									lin = x[0][0];
									col = x[0][1];	
									continue;							
								}								
							}
							break;						

						}while(this.paintButtons(move.searchCaptures(lin, col, this.board, player[1]), Color.GREEN) == 1);

						if(!move.isCapture()){ // Executa se não houver captura								
							move = null; // Finaliza manipulador
							if(player[0].isPromotable(lin)){ // Executa se a éça estiver apta a tornar-se dama
								this.board[lin][col].setIcon(player[0].getQueenIcon());
							}
							
							this.reverseArray(player); // Alterna jogador

						}
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
		// @param Array de duas posições a ser revertido
		
		Object aux = genericArray[1];
		genericArray[1] = genericArray[0];
		genericArray[0] = aux;
	}

}

