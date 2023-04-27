package app.checkers.styles;

import javax.swing.JPanel;
import javax.swing.JButton;

public class ComponentsFactory extends JPanel{
	protected JButton[][] checkerboard;
    
    public JButton[][] createCheckerboard( JButton[][] square){
		for(JButton[] line : square){ // Itera linhas do array
			for(JButton column: line){ // itera colunas da linha do array
			
				column = new JButton(); // inicia botão
				column.addActionListener(evt -> {
					// Evento
				});
				
			}
		}
		return square;
        
	} 
}

