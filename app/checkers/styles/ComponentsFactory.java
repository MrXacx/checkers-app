package app.checkers.styles;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.Image;
import java.awt.Color;


import app.checkers.components.Player;
import app.checkers.components.Move;

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
					
					if(icon instanceof Icon ){ // Executa se houver um ícone do botão e a peça pertencer ao jogador da vez					
							if(move instanceof Move){ // Executa em caso de segundo clique indevido
								this.normalizeButtons(move.getPossibleMoves(), Color.WHITE); // Faz botões da lista voltarem ao padrão
								move = null; // Anula jogada
								return;
							}
							
							move = new Move(this.board[nLine][nColumn], nLine, nColumn); // Inicia jogada no botão de origem						
							this.normalizeButtons(move.getPossibleMoves(), Color.GREEN);														
					}
					else if(move instanceof Move && move.contains(nLine, nColumn)){ // Executa se o botão for o segundo clique
					
						this.normalizeButtons(move.getPossibleMoves(), Color.WHITE); // Faz botões da lista voltarem ao padrão
						move.moveTo(this.board[nLine][nColumn]); // Move peça para o argumento
						move = null; // Anula manipulador
						this.reverseArray(player); // Alterna jogador
						
					}
					
				});			
			}
			this.reverseArray(this.color); // Inverte posições do array das cores
		}
		this.addPieces(new ImageIcon(player[0].getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)), 0, 3);
		this.addPieces(new ImageIcon(player[1].getImage().getScaledInstance(75, 75, Image.SCALE_SMOOTH)), 5, 8);
	}

	private void addPieces(Icon icon, int fromIndex, int toIndex){
		for(int line = fromIndex; line < toIndex; line++){
			for(int column = line % 2; column < this.LENGTH; column++){
				this.board[line][column].setIcon(icon);
			}
		}
		System.out.println("ENTROU");
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

	private void normalizeButtons(int[][] positions, Color color){
		/**
		 * @param Array com as posições dos botões a serem normalizados
		 */
		for(int[] possibleMove : positions){
			if(!(this.board[possibleMove[0]][possibleMove[1]].getIcon() instanceof Icon)){
				this.board[possibleMove[0]][possibleMove[1]].setBackground(color); // Evidencia possíveis jogadas
			}
		}
	}

	private void reverseArray(Object[] genericArray){
		/**
		 * @param Array de duas posições a ser revertido
		 */
		var aux = genericArray[1];
		genericArray[1] = genericArray[0];
		genericArray[0] = aux;
	}
}

