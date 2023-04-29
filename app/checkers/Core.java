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
		
		frame.add(this.getLayout(new LayoutFactory())); // Adiciona tabuleiro ao frame
		frame.setSize(480, 480); // Define dimensões
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que encerramento do frame equivale ao encerramento do código

       frame.setLocationRelativeTo(null); // Centraliza frame
       frame.setVisible(true); // Torna frame visível	
	}
	
	public JPanel getLayout(LayoutFactory layout){
		/**
		* @param Instância do layout do tabuleiro
		* @return panel com design do tabuleiro
		*/
		JPanel panel = new JPanel();
		GroupLayout container = GroupLayout(panel); 
		
		layout.alignGroups(container); // Define layout do painel
		panel.setLayout(container); // Define o layout que o painel deve utilizar	

		return panel;
	}
	
}
