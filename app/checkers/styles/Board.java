package app.checkers.styles;

import javax.swing.*;
import java.awt.Color;
import java.util.Arrays;
import javax.swing.GroupLayout.*;

import app.checkers.components.*;
import app.checkers.styles.LayoutFactory;

class Board extends JPanel{
	private final int LENGTH = 8; // Dimensão base do tabuleiro
	private final int SIZE = 80; // Tamanho dos componentes alinhados genericamente
	public JButton[][] board; // tabuleiro
	private Color special = Color.decode("#85f785");  // Cor de ênfase
	private Color[] color = {Color.decode("#000000"), Color.decode("#FFFFFF")}; // Tema do tabuleiro
	private Move move; // Manipulador de movimento
	private JButton previousClick; // Clique anterior
	private Player[] player;
	
    public void createBoard(Player[] players){	 
		// @param Array de jogadores	
		player = players;
    	this.board  = new JButton[this.LENGTH][this.LENGTH];
		
		for(int line = 0; line < this.LENGTH; line++){
			for(int column = 0; column < this.LENGTH; column++){ 			
				this.board[line][column]  = LayoutFactory.styleButton(new JButton(), color[column%2]); // Inicia botão
				
				final int clonedLine = line;
				final int clonedColumn = column;
				this.board[line][column].setName(line + "-" + column);
				this.board[line][column].addActionListener(evt -> this.triggerEvent(clonedLine, clonedColumn));
				
			}
			this.reverseArray(this.color); // Inverte posições do array das cores
		}
		
		this.addPieces(player[0], 5, 8);  // Adicina peças do primeiro jogador
		this.addPieces(player[1], 0, 3); // Adiciona peças do segundo jogador

		Move.setBoard(this.board); // Define tabuleiro a ser manipulado
	}

	
	
	private void triggerEvent(int clonedLine, int clonedColumn){
		/**
		* @param Linha em que o botão foi acionado
		* @param Coluna em que o botão foi acionado
		* @param Array de jogadores
		*/
		
		JButton position = this.board[clonedLine][clonedColumn]; // Armazena posição do botão
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
				this.paintButtons(move.fetchPossibleMoves(player[0].isQueen(icon) ? Direction.ALL : player[0].getDirection()), this.special) == 0){ // Testa movimentação
				// Executa se a peça não houver como mover a peça
				move = null; // Anula jogada
				previousClick = null; // Anula clique anterior
			}
			
		}
		else if(this.board[clonedLine][clonedColumn].getIcon() == null && (move instanceof Move && move.contains(new int[]{clonedLine, clonedColumn}))){ // Executa se um for um segundo clique válido
			int moveCount = 0; // Inicia contagem de possíveis jogadas sequenciais

			while(true){
				this.paintButtons(move.getMoves(), this.color[0]); // Faz botões da lista voltarem ao padrão
				
				move.moveTo(clonedLine, clonedColumn); // Move peça para a posição selecionada			
				player[0].updateCoordinate(previousClick, position); // Informa jogador a nova posição
				
				if(!move.isCapture()){ // Executa se não houver captura									
					break;					
				}
				int index = move.indexOf(new int[]{clonedLine, clonedColumn});
				player[1].removeCoordinate(move.getCapture(index)); // Decrementa plantel do adversário
				move.capture(index); // Retira peça
				
				move = new Move(clonedLine, clonedColumn);
				moveCount = this.paintButtons(move.fetchCaptures(player[1]), this.special);	// Obtém número de quadrados de possíveis jogadas
				if(moveCount != 1){ // Executa caso a jogada não seja óbvia ou não exista
					break;
				}
				
				previousClick = position; // Guarda posição para a próxima operação
				
				// Guarda nova posição
				clonedLine = move.getMoves()[0][0];
				clonedColumn = move.getMoves()[0][1];
				position = this.board[clonedLine][clonedColumn];
			}
			
			if(moveCount == 0){ // Executa se não houver jogada sequencial
				move = null; // Finaliza manipulador		
				if(player[0].isPromotable(clonedLine)){ // Executa se a éça estiver apta a tornar-se dama
					this.board[clonedLine][clonedColumn].setIcon(player[0].getQueenIcon());
				}
				this.reverseArray(player); // Alterna jogador
				player[0].clearPlayable(); // Limpa lista que podem capturar
				player[0].getCordinates().forEach(piece -> { // Analisa todas as peças em busca de capturas
					String[] coordinate = piece.getName().split("-");
					int auxLine = Integer.parseInt(coordinate[0]);
					int auxColumn = Integer.parseInt(coordinate[1]);
					
					Move nMove = new Move(auxLine, auxColumn);
					if(nMove.fetchCaptures(player[1]).length != 0){
						player[0].appendPlayble(piece); // Insere peça lista
					}
					else if(nMove.fetchPossibleMoves(player[0].isQueen(board[auxLine][auxColumn].getIcon()) ? Direction.ALL : player[0].getDirection()).length == 0){
						player[0].appendBlocked(piece); // Insere peça lista
					}
				});
				
				if(player[0].isLoser()){
					this.disableBoard();
					app.checkers.Core.stop(player[1].getUpperColor() + "S  VENCEM!");					
				}
				else{
					player[0].clearBlocked();
				}
			}
		}
	}
	
	public void disableBoard(){
		for(JButton[] lin : board){
			Arrays.asList(lin).forEach(button -> {button.setEnabled(false);});
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
				owner.appendCoordinate(this.board[line][column]); // Informa posição ao dono da peça
			}
		}
	}
	private Group alignComponents(Group group, JComponent[] componentList){	
  		/**
  		* @param Objeto do grupo em que os componentes devem ser alinhados
  		* @param Array com os componentes a serem alinhados
  		* @return Componentes alinhados no grupo
  		*/
    	for(JComponent comp : componentList){ // Itera o array
    		group = group.addComponent(comp, this.SIZE, this.SIZE, this.SIZE);  // Adiciona componente ao grupo
		}
    	return group;
    }
	
	public void alignGroups(){
    	/**
    	* @param layout do JPanel principal
    	*/
    	GroupLayout layout = new GroupLayout(this);
    	ParallelGroup verticalAlign = layout.createParallelGroup(GroupLayout.Alignment.LEADING); // Adiciona grupo paralelo
    	SequentialGroup horizontalAlign =	layout.createSequentialGroup();  // Adiciona grupo sequencial
    	
    	for(JComponent[] line : this.board){ // IItera array
    		verticalAlign = verticalAlign.addGroup(this.alignComponents(layout.createSequentialGroup(), line));  // Alinha componentes da linha em relação aos componentes
    		horizontalAlign = horizontalAlign.addGroup(this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), line)); // Alinha componentes da linha em relação às outras linhas
    	}   	
    	
    	layout.setHorizontalGroup(verticalAlign);  // Define alinhamento horizontal 	
    	layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(horizontalAlign)); // Define alinhamento vertical
    	
    	this.setLayout(layout);
    }
}
