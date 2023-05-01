package app.checkers.styles;

// Classe de componentes
import javax.swing.JComponent;

// Classes de layout
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;

import java.util.Collection;

import app.checkers.styles.ComponentsFactory;

public class LayoutFactory extends ComponentsFactory{
	private GroupLayout layout;
	
	private final int SIZE = 80; // Tamanho dos componentes alinhados genericamente
	
	public LayoutFactory(){
		this.checkerboard = this.createCheckerboard(); // Cria tabuleiro 8x8
	}
	public void setLayout(GroupLayout layout){
		this.layout = layout;
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
    	var parallelGroup = layout.createParallelGroup(GroupLayout.Alignment.LEADING); // Adiciona grupo paralelo
    	var sequentialGroup = 	layout.createSequentialGroup();  // Adiciona grupo sequencial
    	
    	for(JComponent[] line : this.checkerboard){ // IItera array
    		parallelGroup = parallelGroup.addGroup(this.alignComponents(layout.createSequentialGroup(), line));  // Alinha componentes da linha em relação aos componentes
    		sequentialGroup = sequentialGroup.addGroup(this.alignComponents(layout.createParallelGroup(GroupLayout.Alignment.BASELINE), line)); // Alinha componentes da linha em relação às outras linhas
    	}   	
    	
    	layout.setHorizontalGroup(parallelGroup);  // Define alinhamento horizontal
    	layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(sequentialGroup)); // Define alinhamento vertical
    }
    
}
