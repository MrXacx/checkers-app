package app.checkers.styles;

// Classe de componentes
import javax.swing.JComponent;
import  javax.swing.JPanel;

// Classes de layout
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;

import app.checkers.styles.Menus;
import app.checkers.components.Player;

public class LayoutFactory{
	private GroupLayout layout;
	
	private Menus menu;
	private Board board;
	
	public LayoutFactory(Player[] players){
		board = new Board();
		board.createBoard(players);
		board.alignGroups();
		menu = new Menus(/*board, players*/);
		menu.alignGroups();
	}

	public void setLayout(GroupLayout layout){
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
