package app.checkers.styles;

import javax.swing.*;
import javax.swing.GroupLayout.*;
import java.awt.Color;

import app.checkers.services.Player;
import app.checkers.styles.components.Board;
import app.checkers.styles.components.Menus;

public class LayoutFactory{
	private GroupLayout layout;	
	private Menus menu;
	private Board board;
	
	public LayoutFactory(Player[] players){
		// @param Array de jogadores
		board = new Board(players); // Inicia tabuleiro
		menu = new Menus(board, players[1], players[0]); // Inicia menu
		board.alignGroups(); // Define alinhamento do tabuleiro
	}
	
	public static JButton styleButton(JButton button, Color color){
		/**
		 * @param Botão a ser estilizado
		 * @param Cor do background
		 * @return Botão estilizado
		 */
		 
		button.setBackground(color); // Define cor do background de acordo com o módulo
		button.setFocusPainted(false); // Retira foco
		button.setBorder(null); // Retira borda
		return button;
	}
	
	public void setLayout(GroupLayout layout){
		// @param Layout a ser estilizado
		this.layout = layout;
	}
	
    private Group alignComponents(Group group, JComponent[] componentList, int[] size){	
  		/**
  		* @param Objeto do grupo em que os componentes devem ser alinhados
  		* @param Array com os componentes a serem alinhados
  		* @return Componentes alinhados no grupo
  		*/
    	for(int index = 0; index < size.length; index++){ // Itera o array
    		group = group.addComponent(componentList[index], size[index], size[index], size[index]);  // Adiciona componente ao grupo
		}
    	return group;
    }
	
	public void alignGroups(){

    	layout.setHorizontalGroup( // Define alinhamento horizontal
    		layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(this.alignComponents(layout.createSequentialGroup(), new JPanel[]{this.board, this.menu}, new int[]{80*8, 80*3}))
    	);  
    	
    	layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup( // Define alinhamento vertical
    		layout.createSequentialGroup().addGroup(this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), new JPanel[]{this.board, this.menu}, new int[]{80*8, 80*8}))
    	));
    }
    
}
