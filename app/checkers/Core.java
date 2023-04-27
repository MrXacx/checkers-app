package app.checkers;

import javax.swing.JFrame;
import app.checkers.styles.StyleManager;

/**
* @author Ariel Santos
* @version 1.0
*/

public class Core{
	private static JFrame frame;
	
	public static void main(String[] args){
		frame = new JFrame("Damas"); // Cria frame
		
		frame.add(new StyleManager()); // Adiciona tabuleiro ao frame
		frame.setSize(480, 480); // Define dimensões
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que encerramento do frame equivale ao encerramento do código

       frame.setLocationRelativeTo(null); // Centraliza frame
       frame.setVisible(true); // Torna frame visível	
	}
}
