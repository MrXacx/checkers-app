package app.checkers;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;

import app.checkers.styles.LayoutFactory;

/**
* @author Ariel Santos
* @version 1.0
*/

public class Core{
	private static JFrame frame;
	
	public static void main(String[] args){
		frame = new JFrame("Damas"); // Cria frame
		
		frame.add(getLayout(new LayoutFactory())); // Adiciona tabuleiro ao frame
		frame.setSize(640, 680); // Define dimensões
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que encerramento do frame equivale ao encerramento do código

       frame.setLocationRelativeTo(null); // Centraliza frame
       frame.setVisible(true); // Torna frame visível	
	}
	
	public static JPanel getLayout(LayoutFactory layout){
		/**
		* @param Instância do layout do tabuleiro
		* @return panel com design do tabuleiro
		*/
		JPanel panel = new JPanel();
		GroupLayout container = new GroupLayout(panel); 
		panel.setLayout(container); // Define o layout que o painel deve utilizar	
		
		layout.setLayout(container); // Define layout do painel
		
		layout.alignGroups(); // Define layout do painel
		//layout.setVerticalGroup(); // Define layout do painel
		
		

		return panel;
	}
	
}
