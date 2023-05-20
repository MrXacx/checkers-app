package app.checkers.styles;

import javax.swing.JButton;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.Icon;

import app.checkers.components.*;

class ComponentsFactory{
	private final int LENGTH = 8; // Dimensão base do tabuleiro
	protected JButton[][] board; // tabuleiro
	private Color special = Color.decode("#85f785");  // Cor de ênfase
	private Color[] color = {Color.decode("#000000"), Color.decode("#FFFFFF")}; // Tema do tabuleiro
	private Move move; // Manipulador de movimento
	private int[] previousClick; // Clique anterior
	
    public void createBoard(Player[] player){	 
		// @param Array de jogadores	
		
    	this.board  = new JButton[this.LENGTH][this.LENGTH];
		
		for(int line = 0; line < this.LENGTH; line++){
			for(int column = 0; column < this.LENGTH; column++){ 			
				this.board[line][column]  = styleButton(new JButton(), column%2); // Inicia botão
				
				final int clonedLine = line;
				final int clonedColumn = column;
				this.board[line][column].addActionListener(evt -> this.triggerEvent(clonedLine, clonedColumn, player));			
			}
			this.reverseArray(this.color); // Inverte posições do array das cores
		}
		
		this.addPieces(player[0], 5, 8);  // Adicina peças do primeiro jogador
		this.addPieces(player[1], 0, 3); // Adiciona peças do segundo jogador

		Move.setBoard(this.board); // Define tabuleiro a ser manipulado
	}

	private JButton styleButton(JButton button, int index){
		/**
		 * @param Botão a ser estilizado
		 * @param Índice de cor do background
		 * @return Botão estilizado
		 */
		 
		button.setBackground (color[index]); // Define cor do background de acordo com o módulo
		button.setFocusPainted(false); // Retira foco
		button.setBorder(null); // Retira borda
		return button;
	}
	
	public void triggerEvent(int clonedLine, int clonedColumn, Player[] player){
		/**
		*	@param Linha em que o botão foi acionado
		* @param Coluna em que o botão foi acionado
		* @param Array de jogadores
		*/
		
		int[] position = new int[]{clonedLine, clonedColumn}; // Armazena posição do botão
		Icon icon = this.board[clonedLine][clonedColumn].getIcon(); // Armazena o ícone do botão
		if(icon instanceof Icon && player[0].isPlayable(position)){ // Executa se houver um ícone do botão e se a peça for jogável pelo atual jogadot				
			
			if(move instanceof Move){ // Executa em caso de segundo clique indevido
				this.paintButtons(move.getMoves(), this.color[0]); // Faz botões da lista voltarem ao padrão
				move = null; // Anula jogada
				previousClick = null; // Anula clique anterior
				return; // Evita que o evento continue
			}
			
			move = new Move(clonedLine, clonedColumn); // Inicia jogada no botão de origem
			previousClick = position; // Guarda posição para operações futuras
			
			if(this.paintButtons(move.fetchCaptures(player[1]), this.special) == 0 && // Testa possíveis capturas
				this.paintButtons(player[0].isQueen(icon) ? move.fetchMaxMoves() : move.fetchPossibleMoves(player[0].getDirection()), this.special) == 0){ // Testa movimentação
				// Executa se a peça não houver como mover a peça
				move = null; // Anula jogada
				previousClick = null; // Anula clique anterior
			}
			
		}
		else if(this.board[clonedLine][clonedColumn].getIcon() == null && (move instanceof Move && move.contains(position))){ // Executa se um for um segundo clique válido
			int moveCount = 0; // Inicia contagem de possíveis jogadas sequenciais

			while(true){
				this.paintButtons(move.getMoves(), this.color[0]); // Faz botões da lista voltarem ao padrão
				
				move.moveTo(clonedLine, clonedColumn); // Move peça para a posição selecionada
				player[0].updateCoordinate(previousClick, position); // Informa jogador a nova posição
				
				if(!move.isCapture()){ // Executa se não houver captura									
					break;					
				}
				
				int[] captured = move.getCapture(move.indexOf(position)); // Obtém posição da peça capturada
				move.capture(captured, player[1]); // Retira peça
				moveCount = this.paintButtons(move.fetchCaptures(player[1]), this.special);	// Obtém número de quadrados de possíveis jogadas
				
				if(moveCount != 1){ // Executa caso a jogada não seja óbvia ou não exista
					break;
				}
				
				previousClick = position; // Guarda posição para a próxima operação
				
				// Guarda nova posição
				position = move.getMoves()[0]; 
				clonedLine = position[0]; 
				clonedColumn = position[1];					
			}
			if(moveCount == 0 || !move.isCapture()){ // Executa se não houver jogada sequencial
				move = null; // Finaliza manipulador		
				if(player[0].isPromotable(clonedLine)){ // Executa se a éça estiver apta a tornar-se dama
					this.board[clonedLine][clonedColumn].setIcon(player[0].getQueenIcon());
				}
				
				this.reverseArray(player); // Alterna jogador
				
				player[0].clearPlayable(); // Limpa lista que podem capturar
				player[0].getCordinates().forEach( piece -> { // Analisa todas as peças em busca de capturas
					Move nMove = new Move(piece[0], piece[1]);
					if(nMove.fetchCaptures(player[1]).length != 0){
						player[0].appendPlayble(piece); // Insere peça lista
					}
				});
			}

		}
	}
	
	private int paintButtons(int[][] positions, Color color){
		/**
		 * @param Array com as posições dos botões a serem pintados
		 * @param Cor que será utilizado no background
		 * @return Número de botões pintados
		 */
		 
		 int count = 0;
		for(int[] coord : positions){		
			if(!(this.board[coord[0]][coord[1]].getIcon() instanceof ImageIcon)){ // Evita posições ocupadas
				this.board[coord[0]][coord[1]].setBackground(color); // Altera cor do background
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
	
	private void addPieces(Player owner, int fromIndex, int toIndex){
		/**
		 * @param Dono da peça
		 * @param Índice inicial incluso na iteração
		 * @param Íncide final excluso da iteração
		 */
		
		// Itera tabuleiro em colunas alternadas
		for(int line = fromIndex; line < toIndex; line++){
			for(int column = line % 2; column < this.LENGTH; column += 2){
				this.board[line][column].setIcon(owner.getIcon()); // Define ícone
				owner.appendCoordinate(new int[]{line, column}); // Informa posição ao dono da peça
			}
		}
	}

}
