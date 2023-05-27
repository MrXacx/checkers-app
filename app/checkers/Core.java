package app.checkers;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.Collections;

import javax.swing.GroupLayout;

import app.checkers.components.Player;
import app.checkers.styles.LayoutFactory;

public class Core extends JFrame{

	private Player[] players = new Player[2];
	private static ArrayList<String> colors = new ArrayList<>();
	
	public static void main(String[] args){	
		colors.add("red");
		colors.add("yellow");
		new Core();
	}
	public Core(){
		super("Damas inglesas");
		super.setSize(655+240, 680); // Define dimensões
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Determina que encerramento do frame equivale ao encerramento do código
		super.setLocationRelativeTo(null); // Centraliza frame
		super.setResizable(false);//nao mexer na tela
		this.start();
	}
	public void start(){
		
		Collections.shuffle(colors);
		players[0] =  new Player(colors.get(0), 0); // Inicia jogador das preças pretas
		players[1] =  new Player(colors.get(1), 7); // Inicia jogador das peças brancas
		
		
		super.add(this.getLayout(new LayoutFactory(players))); // Adiciona tabuleiro ao frame
		super.setVisible(true); // Torna frame visível
	}
	
	public JPanel getLayout(LayoutFactory layout){
		/**
		* @param Instância do layout do tabuleiro
		* @return panel com design do tabuleiro
		*/
		JPanel panel = new JPanel();
		GroupLayout container = new GroupLayout(panel); 
		panel.setLayout(container); // Define o layout que o painel deve utilizar	
		
		layout.setLayout(container); // Define layout do painel	
		layout.alignGroups(); // Define layout do painel		
		return panel;
	}
	
	public static void stop(String message){
		JOptionPane.showMessageDialog(null, message, "Fim de jogo", 1);
	}
}
