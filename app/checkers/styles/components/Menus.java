package app.checkers.styles.components;

import java.io.File;
import  javax.swing.*;
import java.awt.*;

import app.checkers.services.Player;
import app.checkers.styles.LayoutFactory;

public class Menus extends JPanel {
    
    private PlayerContainer playeDownrContainer, playerUpContainer;
    private TimerContainer timerContainer;
    private JPanel tools = new JPanel();
    private JButton pause, reset, help;
	private Color textColor = Color.decode("#FEFEFE");
    
    public Menus(Board board, Player up, Player down) {		
       /**
		* @param Tabuleiro
		* @param Jogador da parte superior do tabuleiro
		* @param Jogador da parte inferior do tabuleiro
	    */
		this.setBackground(Color.decode("#0E0E0E")); // Define background

		playerUpContainer = new PlayerContainer(up, textColor); // Inicia container superior
		up.setContainer(playerUpContainer);
		playerUpContainer.setOpaque(false); // Torna container transparente

		
		playeDownrContainer = new PlayerContainer(down, textColor);// Inicia container inferior
		down.setContainer(playeDownrContainer);
		playeDownrContainer.setOpaque(false); // Torna container transparente	
		
		initTools(board, up, down); // Inicia painel de ferramentas
		tools.setOpaque(false); // Torna painel transparente		
		
		// Inicia temporizador
		timerContainer = new TimerContainer(new Font("Arial", Font.PLAIN, 80), textColor);
		timerContainer.setOpaque(false);
		board.setTimer(timerContainer);	
		
		// Gera tabuleiro
		board.createBoard();

		// Insere todos os componentes
		this.add(playerUpContainer, BorderLayout.CENTER);	
		this.add(timerContainer, BorderLayout.CENTER);
		this.add(tools, BorderLayout.CENTER);
		this.add(playeDownrContainer, BorderLayout.CENTER);

      	setSize(240, 640); // Define tamanho
    }
    
    
	private JButton styleButton(String name){
		/**
		 * @param nome da imagem a ser inserida
		 * @return botão estilizado
		 */

    	JButton button = LayoutFactory.styleButton(new JButton(), Color.decode("#FEFEFE"));
    	button.setIcon(new ImageIcon(new ImageIcon("../src/tools/"+name+".png").getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));
    	return button;
    }
   
	private void initTools(Board board, Player up, Player down){
		// Configura painel
		tools.setSize(240, 80);
		tools.setBackground(textColor);
		
		// Configua botão de pause
		pause = styleButton("pause");
		pause.setSelected(false);
		pause.addActionListener(evt -> pauseActionPerformed(board));

		// Configura botão de reinícion
		reset = styleButton("reset");
		reset.addActionListener(evt -> resetActionPerformed(board, up, down));

		// Configura botão de ajuda
		help = styleButton("help");
		help.addActionListener(evt -> helpActionPerformed());

		// Alinha botões no painel
		GroupLayout layout = new GroupLayout(tools);
		tools.setLayout(layout);
		
		layout.setHorizontalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addComponent(pause,80, 80, 80)
				.addComponent(reset,80, 80, 80)
				.addComponent(help,80, 80, 80))
		);
		layout.setVerticalGroup(
		layout.createParallelGroup(GroupLayout.Alignment.LEADING)
		.addGroup(layout.createSequentialGroup()
			.addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
				.addComponent(pause,80, 80, 80)
					.addComponent(reset,80, 80, 80)
					.addComponent(help,80, 80, 80)
				))
		);
    }
    
    private void pauseActionPerformed(Board board) {
		boolean isSelected = pause.isSelected();

		pause.setIcon(new ImageIcon( // Altera ícone
			new ImageIcon(String.format("../src/tools/%s.png", isSelected ? "pause" : "resume"))
			.getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));
		
		timerContainer.stopCounting(!isSelected); // Alterna continuidade da contagem
		board.disable(!isSelected); // Alterna a permissão de interação com o tabuleiro
		pause.setSelected(!isSelected);
	}
    
	private void resetActionPerformed(Board board, Player up, Player down) {
		if(JOptionPane.showConfirmDialog(board, "Tem certeza que deseja reiniciar a partida?", "Reiniciar partida", JOptionPane.YES_NO_OPTION, 1) == 0){
			// Limpa dados sobrescrevivéis
			up.reset();
			down.reset();

			board.disable(false);
			timerContainer.reset(); // Zera contador
			board.reset(up, down); // Reposiciona peças		
		}
	}
    
    private void helpActionPerformed() {
		try { // Tenta abrir manual no leitor de PDF padrão
			Desktop.getDesktop().open(new File("../src/docs/manual_do_jogo.pdf"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
}
