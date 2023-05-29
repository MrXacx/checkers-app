package app.checkers.styles.components;

import javax.swing.*;
import javax.swing.GroupLayout.*;
import java.awt.Color;
import java.util.Arrays;

import app.checkers.services.*;
import app.checkers.styles.LayoutFactory;

public class Board extends JPanel{
	private final int LENGTH = 8; // Dimensão base do tabuleiro
	private final int SIZE = 80; // Tamanho dos componentes alinhados genericamente
	public JButton[][] board; // tabuleiro
	private Color special = Color.decode("#85f785");  // Cor de ênfase
	private Color[] color = {Color.decode("#000000"), Color.decode("#FFFFFF")}; // Tema do tabuleiro
	private Move move; // Manipulador de movimento
	private JButton previousClickedButton; // Clique anterior
	private Player[] player;
	private TimerContainer timer;
	
	public Board(Player[] players){
		// @param Array de jogadores
		player = players;
	}

	public void setTimer(TimerContainer timer){
		// @param Objeto do temporizador
		this.timer = timer;
	}

    public void createBoard(){	 
		// @param Array de jogadores	
		
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
	
	public void reset(Player up, Player down){
		/**
		 * @param Jogador que deve ficar na parte superior do tabuleiro
		 * @param Jogador que deve ficar na parte inferior do tabuleiro
		 */
		for(JButton[] lin : board){
			Arrays.asList(lin).forEach(button -> button.setIcon(null));
		}
		
		previousClickedButton = null;
		this.addPieces(down, 5, 8);  // Adicina peças do primeiro jogador
		this.addPieces(up, 0, 3); // Adiciona peças do segundo jogador
	}
	
	private void triggerEvent(int clonedLine, int clonedColumn){
		/**
		* @param Linha em que o botão foi acionado
		* @param Coluna em que o botão foi acionado
		*/
		if(!timer.isCountingTime()){ // Inicia contagem no primeiro clique
			timer.stopCounting(false);
			timer.countTime();
		}
		JButton presentClickedButton = this.board[clonedLine][clonedColumn]; // Armazena botão clicado
		Icon icon = presentClickedButton.getIcon(); // Armazena o ícone do botão
		
		if(icon instanceof Icon && player[0].isPlayable(presentClickedButton)){ // Executa se houver um ícone do botão e se a peça for jogável pelo atual jogador			
			
			if(move instanceof Move){ // Executa em caso de jogada incompleta
				this.paintButtons(move.getMoves(), this.color[0]); // Faz botões da lista voltarem ao padrão
				move = null; // Anula jogada
				previousClickedButton = null; // Anula clique anterior
				return; // Evita que o evento continue
			}
			
			move = new Move(clonedLine, clonedColumn); // Inicia jogada no botão de origem
			previousClickedButton = presentClickedButton; // Guarda botão para operações futuras
			
			if(this.paintButtons(move.fetchCaptures(player[1]), this.special) == 0 && // Testa possíveis capturas														
				this.paintButtons(move.fetchPossibleMoves(player[0].isQueen(icon) ? Direction.ALL : player[0].getDirection()), this.special) == 0){ // Testa movimentação
				// Executa se não houver como mover a peça
				move = null; // Anula jogada
				previousClickedButton = null; // Anula clique anterior
			}
			
		}
		else if(this.board[clonedLine][clonedColumn].getIcon() == null && (move instanceof Move && move.contains(new int[]{clonedLine, clonedColumn}))){ // Executa se o jogador estiver selecionando o destino da peça
			int moveCount = 0; // Inicia contagem de possíveis jogadas alternativas

			while(true){
				this.paintButtons(move.getMoves(), this.color[0]); // Padroniza tabuleiro
				
				move.moveTo(clonedLine, clonedColumn); // Move peça para a posição selecionada			
				player[0].updateCoordinate(previousClickedButton, presentClickedButton); // Informa ao jogador o botão atual
				
				if(move.isCapture()){ // Executa se não houver captura															
					int index = move.indexOf(new int[]{clonedLine, clonedColumn}); // Busca índice do da coordenada selecionada
					player[1].removeCoordinate(move.getCapture(index)); // Decrementa plantel do adversário
					move.capture(index); // Retira peça do tabuleiro
					
					move = new Move(clonedLine, clonedColumn); // Inicia nova jogada
					moveCount = this.paintButtons(move.fetchCaptures(player[1]), this.special);	// Obtém número de possíveis jogadas

					if(moveCount == 1){ // Executa caso a jogada de alternativa única 
						previousClickedButton = presentClickedButton; // Guarda botão como "anterior" para o próximo movimento
					
						// Guarda nova posição da peça
						clonedLine = move.getMoves()[0][0];
						clonedColumn = move.getMoves()[0][1];

						presentClickedButton = this.board[clonedLine][clonedColumn]; // Atualiza botão atual
						continue; // Continua o loop
					}
				}
				break;
			}
			
			if(moveCount == 0){ // Executa se não houver jogada sequencial
				move = null; // Finaliza manipulador		
				if(player[0].isPromotable(clonedLine)){ // Executa se a peça estiver apta a tornar-se dama
					this.board[clonedLine][clonedColumn].setIcon(player[0].getQueenIcon());
				}

				this.reverseArray(player); // Alterna jogador

				player[0].clearPlayable(); // Limpa lista de peças que devem realizar capturas
				player[0].getCordinates().forEach(piece -> { // Analisa todas as peças em busca de jogadas
					// Obtém coordenada do botão
					String[] coordinate = piece.getName().split("-"); 
					int auxLine = Integer.parseInt(coordinate[0]);
					int auxColumn = Integer.parseInt(coordinate[1]);
					
					Move nMove = new Move(auxLine, auxColumn); // Inicia jogada temporária
					if(nMove.fetchCaptures(player[1]).length != 0){ // Busca capturas
						player[0].appendPlayble(piece); // Insere peça lista
					}
					else if(nMove.fetchPossibleMoves(player[0].isQueen(board[auxLine][auxColumn].getIcon()) ? Direction.ALL : player[0].getDirection()).length == 0){ // Busca peças sem movimentação viável
						player[0].appendBlocked(piece); // Insere peça lista
					}
				});
				
				if(player[0].isLoser()){ // Executa se o jogador da vez perdeu
					app.checkers.Core.stop(player[1].getUpperColor() + "S  VENCEM!"); // Informa vitória
				}
				else{
					player[0].clearBlocked();
				}
			}
		}
	}
	
	public void disable(boolean turn){
		// @param Booleano de se o tabuleiro deve ser desativado
		for(JButton[] lin : board){
			Arrays.asList(lin).forEach(button -> {button.setEnabled(!turn);});
		}
	}

	private int paintButtons(int[][] presentClickedButtons, Color color){
		/**
		 * @param Array com as posições dos botões a serem pintados
		 * @param Cor que será utilizado no background
		 * @return Número de botões pintados
		 */
		 
		 int count = 0;
		for(int[] coord : presentClickedButtons){		
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
