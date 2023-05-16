package app.checkers.styles;

import javax.swing.JButton;
import java.awt.Color;
import java.util.Arrays;

import javax.swing.ImageIcon;
import javax.swing.Icon;

import app.checkers.components.*;

class ComponentsFactory{
	private final int LENGTH = 8;
	protected JButton[][] board;
	private Color special = Color.decode("#85f785"); 
	private Color[] color = {Color.decode("#000000"), Color.decode("#FFFFFF")};
	private Move move;
	
	public void triggerEvent(int nLine, int nColumn, Player[] player){
		Icon icon = this.board[nLine][nColumn].getIcon();
					
		if(icon instanceof Icon && player[0].contains(icon)){ // Executa se houver um ícone do botão e a peça pertencer ao jogador da vez					
			if(move instanceof Move){ // Executa em caso de segundo clique indevido
				this.paintButtons(move.getMoves(), this.color[0]); // Faz botões da lista voltarem ao padrão
				move = null; // Anula jogada
				return;
			}
			
			move = new Move(this.board[nLine][nColumn], nLine, nColumn); // Inicia jogada no botão de origem

			if(this.paintButtons(move.fetchCaptures(this.board, player[1]), this.special) == 0 && 
				this.paintButtons(player[0].isQueen(icon) ? move.fetchMaxMoves(this.board) : move.fetchPossibleMoves(player[0].getDirection()), this.special) == 0){
				move = null;
			}
			
		}
		else if(this.board[nLine][nColumn].getIcon() == null && (move instanceof Move && move.contains(nLine, nColumn))){ // Executa se o botão for o segundo clique
			int countSquares = 0;

			while(true){
				this.paintButtons(move.getMoves(), this.color[0]); // Faz botões da lista voltarem ao padrão
				
				move.moveTo(this.board[nLine][nColumn], nLine, nColumn); // Move peça para a posição selecionada
				if(!move.isCapture()){ // Executa se não houver captura									
					break;					
				}
				
				move.capture(move.indexOf(nLine, nColumn)); // realiza captura
				player[1].decrementSquad(); // Decrementa plantel do adversário
				
				countSquares = this.paintButtons(move.fetchCaptures(this.board, player[1]), this.special);			
				if(countSquares != 1){
					break;
				}
				
				nLine = move.getMoves()[0][0];
				nColumn = move.getMoves()[0][1];						
			}

			if(countSquares == 0 || !move.isCapture()){ // Executa se não houver captura								
				move = null; // Finaliza manipulador
				
				if(player[0].isPromotable(nLine)){ // Executa se a éça estiver apta a tornar-se dama
					this.board[nLine][nColumn].setIcon(player[0].getQueenIcon());
				}						
				this.reverseArray(player); // Alterna jogador

			}
		}
	
	}
	
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
				this.board[line][column].addActionListener(evt -> this.triggerEvent(nLine, nColumn, player));			
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
		button.setBorder(null); // Retira foco
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

