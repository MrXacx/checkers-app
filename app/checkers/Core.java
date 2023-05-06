package app.checkers;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;

import app.checkers.components.Direction;
import app.checkers.components.Player;
import app.checkers.styles.LayoutFactory;

public class Core{
	private static JFrame frame;
	private static Player[] players = new Player[2];
	
	public static void main(String[] args){
		players[0] =  new Player("RED", Direction.DOWN); // Inicia jogador das preças pretas
		players[1] =  new Player("YELLOW", Direction.UP); // Inicia jogador das peças brancas

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
		layout.createBoard(players); // Cria tabuleiro 8x8
		JPanel panel = new JPanel();
		GroupLayout container = new GroupLayout(panel); 
		panel.setLayout(container); // Define o layout que o painel deve utilizar	
		
		layout.setLayout(container); // Define layout do painel
		
		layout.alignGroups(); // Define layout do painel
		//layout.setVerticalGroup(); // Define layout do painel
		
		return panel;
	}
	
}
