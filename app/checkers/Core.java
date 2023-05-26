package app.checkers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.GroupLayout;

import app.checkers.components.Player;
import app.checkers.styles.LayoutFactory;

public class Core{
	public static JFrame frame;
	private static Player[] players = new Player[2];
	private static ArrayList<String> colors = new ArrayList<>();
	private static JPanel panel;
	
	public static void main(String[] args){	
		colors.add("RED");
		colors.add("YELLOW");
		start();
		//new Menu();
	}

	public static void start(){
		
		Collections.shuffle(colors);
		players[0] =  new Player(colors.get(0), 0); // Inicia jogador das preças pretas
		players[1] =  new Player(colors.get(1), 7); // Inicia jogador das peças brancas
		

		frame = new JFrame("Damas"); // Cria frame
		frame.setSize(875, 676); // Define dimensões
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que encerramento do frame equivale ao encerramento do código
		frame.setLocationRelativeTo(null); // Centraliza frame
		frame.setResizable(false);//nao mexer na tela
		frame.add(getLayout(new LayoutFactory(players))); // Adiciona tabuleiro ao frame
		frame.setVisible(true); // Torna frame visível
	}
	
	public static JPanel getLayout(LayoutFactory layout){
		/**
		* @param Instância do layout do tabuleiro
		* @return panel com design do tabuleiro
		*/
		panel = new JPanel();
		GroupLayout container = new GroupLayout(panel); 
		panel.setLayout(container); // Define o layout que o painel deve utilizar	
		
		layout.setLayout(container); // Define layout do painel	
		layout.alignGroups(); // Define layout do painel		
		return panel;
	}
	
	public static void stop(String message){
		if(JOptionPane.showConfirmDialog(null, "Fim de jogo: " + message + "!\nDeseja jogar uma nova partida?", "Fim de jogo", JOptionPane.YES_NO_OPTION) == 0){
			frame.dispose();
			start();
		}
		else{
			JOptionPane.showMessageDialog(null, "Obrigado por jogar, volte sempre!", "Fim de jogo",1);
			System.exit(0);
		}
	}
}
