package app.checkers.styles;

import javax.swing.JPanel;
import javax.swing.JButton;

import java.awt.Color;

public class ComponentsFactory{
	private List<Color> color =  Arrays.asList(Color.BLACK, Color.WHITE);
	protected JButton[][] checkerboard;
    
    protected JButton[][] createCheckerboard( JButton[][] square){
    	
		for(int line = 0; line < square.lenght; line++){
			for(int column = 0; column < square.lenght; column++){ 
			
				square[line][column]  = styleButton(new JButton()); // Inicia botão
				square[line][column].addActionListener(evt -> {
					// Evento
				});
				
			}
			Collections.reverse(color); // Inverte posições do array das cores
		}
		return square;
        
	}
	public JButton styleButton(JButton button){
	
		button.setBackground (color.get(column % 2)); // Define cor do background de acordo com o módulo
		button.setFocusPainted(false); // Retira foco
		
		return button;
	}
}

