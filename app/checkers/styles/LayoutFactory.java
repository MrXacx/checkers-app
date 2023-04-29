package app.checkers.styles;

// Classe de componentes
import java.awt.Component;

// Classes de layout
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Group;

// Classes de vetores
import java.util.Arrays;
import java.util.List;


public class LayoutFactory extends ComponentsFactory{
	private final Group sequential_group = layout.createSequentialGroup();  // Objeto de grupo sequencial
	private final Group  parallel_group_baseline = layout.createParallelGroup(GroupLayout.Alignment.BASELINE); // Objeto de grupo paralelo
	private final Group  parallel_group_leading = layout.createParallelGroup(GroupLayout.Alignment.LEADING); // Objeto de grupo paralelo
	private final int SIZE = 80; // Tamanho dos componentes alinhados 
	
	public LayoutFactory(){
		this.createCheckerboard(new JButton[8][8]); // Cria tabuleiro 8x8
	}
    private Group alignComponents(Group group, Component[] componentList){	
  		/**
  		* @param Objeto do grupo em que os componentes devem ser alinhados
  		* @param Array com os componentes a serem alinhados
  		* @return Componentes alinhados no grupo
  		*/
    	for(Component comp : componentList)){ // Itera o array
    		group = group.addComponent(comp, this.SIZE, this.SIZE, this.SIZE);  // Adiciona componente ao grupo
		}
    	return group;
    }

    public void alignHorizontalGroup(GroupLayout layout){
    	/**
    	* @param layout do JPanel principal
    	*/
    	var parallelGroup = parallel_group_leading; // Obtém instância de um grupo paralelo
    	for(Component line : this.checkerboard){ // IItera array
    		parallelGroup = parallelGroup.addGroup(this.alignComponents(sequential_group, line));  // Alinha componentes da linha em relação aos componentes
    	}   	
    	layout.setHorizontalGroup(parallelGroup);  // Define alinhamento
    }
    
    public void alignVerticalGroup(GroupLayout layout){
    	/**
    	* @param layout do JPanel principal
    	*/
    	var sequentialGroup = sequential_group;  // Obtém instância de um grupo sequencial
    	for(Component line : this.checkerboard){ // IItera array
    		sequentialGroup = sequentialGroup.addGroup(this.alignComponents(parallel_group_baseline, line)); // Alinha componentes da linha em relação às outras linhas
    	}   
    	layout.setVerticalGroup(parallel_group_leading.addGroup(sequentialGroup)); // Define alinhamento
	}	
}
