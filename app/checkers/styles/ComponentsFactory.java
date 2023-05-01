package app.checkers.styles;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.awt.Color;

import java.io.File;
import java.util.Arrays;

public class ComponentsFactory{
	private Color[] color = {Color.BLACK, Color.WHITE};
	protected JButton[][] checkerboard;
    
    protected JButton[][] createCheckerboard(){	
    	
    	JButton square[][] = new JButton[8][8];
    	Image scaledImg = (new ImageIcon("../src/peca.png")).getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
		for(int line = 0; line < square.length; line++){
			for(int column = 0; column < square.length; column++){ 			
				square[line][column]  = styleButton(new JButton(), column%2); // Inicia botão
				
				square[line][column].setIcon(new ImageIcon(scaledImg));
				
				square[line][column].addActionListener(evt -> {
					// Evento
				});			
			}
			reverseColorArray(); // Inverte posições do array das cores
		}
		return square;
        
	}
	public JButton styleButton(JButton button, int index){
	
		button.setBackground (color[index]); // Define cor do background de acordo com o módulo
		button.setFocusPainted(false); // Retira foco
		return button;
	}
	private void reverseColorArray(){
		var aux = this.color[1];
		this.color[1] = this.color[0];
		this.color[0] = aux;
	}
}

