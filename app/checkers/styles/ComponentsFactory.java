package app.checkers.styles;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;


import app.checkers.components.*;

public class ComponentsFactory{
	private Color[] color = {Color.BLACK, Color.WHITE};
	private Move move;

	protected JButton[][] checkerboard;
    
    public void createCheckerboard(Player[] player){	
    	
    	JButton square[][] = new JButton[8][8];

    	//Image scaledImg = (new ImageIcon("../src/peca.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		
		for(int line = 0; line < square.length; line++){
			for(int column = 0; column < square.length; column++){ 			
				square[line][column]  = styleButton(new JButton(), column%2); // Inicia botão
				
				//square[line][column].setIcon(new ImageIcon(scaledImg));

				final int nLine = line;
				final int nColumn = column;

				square[line][column].addActionListener(evt -> {
					var icon = square[nLine][nColumn].getIcon();
					if(icon instanceof ImageIcon && player[0].contains(icon)){ // Executa se houver um ícone do botão e a peça pertencer ao jogador da vez

							if(move instanceof Move){ // Executa em caso de segundo clique indevido
								this.normalizeButtons(move.getPossibleMoves()); // Faz botões da lista voltarem ao padrão
								move = null; // Anula jogada
							}
							else{
								move = new Move(square[nLine][nColumn], nLine, nColumn); // Inicia jogada no botão de origem
								for(int[] possibleMove : move.getPossibleMoves()){
									if(!(square[possibleMove[0]][possibleMove[1]].getIcon() instanceof ImageIcon)){
										square[possibleMove[0]][possibleMove[1]].setBackground(Color.GREEN); // Evidencia possíveis jogadas
									}
								}
							}
						
					}
					else if(move instanceof Move && move.contains(nLine, nColumn)){ // Executa se o botão for o segundo clique
						move.moveTo(square[nLine][nColumn]);
						this.normalizeButtons(move.getPossibleMoves());
						move = null;
						this.reverseArray(player);
					}
				});			
			}
			this.reverseArray(this.color); // Inverte posições do array das cores
		}
		this.checkerboard =  square;
        
	}
	public JButton styleButton(JButton button, int index){
		/**
		 * @param Botão a ser estilizado
		 * @param Índice de cor do background
		 * @return Botão estilizado
		 */
		button.setBackground (color[index]); // Define cor do background de acordo com o módulo
		button.setFocusPainted(false); // Retira foco
		return button;
	}

	private void normalizeButtons(int[][] positions){
		/**
		 * @param Array com as posições dos botões a serem normalizados
		 */
		for(int[] position : positions){
			this.checkerboard[position[0]][position[1]].setBackground(Color.BLACK); // Limpa possíveis jogadas
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

