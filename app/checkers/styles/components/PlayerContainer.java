package app.checkers.styles.components;

import  javax.swing.*;

import app.checkers.services.Player;

import java.awt.Color;
import java.awt.Font;

public class PlayerContainer extends JPanel {

    private JLabel pic = new JLabel();
    private JLabel name = new JLabel();
	private JLabel totalPieces = new JLabel();
	private JLabel totalQueens = new JLabel();
	private Font font = new Font("Arial", Font.PLAIN, 17);
    
    public PlayerContainer(Player player, Color textColor) {
		/**
		 * @param Dono do container
		 * @param Cor a ser utilizada nos textos
		 */

		// Define cor do texto
		name.setForeground(textColor);
		totalPieces.setForeground(textColor);
		totalQueens.setForeground(textColor);

		// Define fonte
		name.setFont(font);
		totalPieces.setFont(font);
		totalQueens.setFont(font);

		this.setSize(240, 160);	
		
		// Identifica dono do container
		pic.setIcon(player.getIcon(30));
		name.setText(player.getUpperColor());

		updateDetails(player.getCordinates().size(), 0); // Define informações do jogador
		this.setLayout(settingPlayerContainer(new GroupLayout(this))); //Alinha components
		
    }

    
    private GroupLayout settingPlayerContainer(GroupLayout container){
		
		container.setHorizontalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
			
		    .addGroup(GroupLayout.Alignment.TRAILING, container.createSequentialGroup()
		        .addComponent(pic, 30, 30, 30)
		        .addGap(8)
		        .addComponent(name, 125, 125, 125)
		    )
            .addComponent(totalPieces, 151, 151, 151)
            .addComponent(totalQueens, 151, 151, 151)

            
		);
		container.setVerticalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(container.createSequentialGroup()
		    	.addGap(24)
		        .addGroup(container.createParallelGroup(GroupLayout.Alignment.TRAILING)
		        	.addComponent(pic, 30, 30, 30)
		            .addComponent(name, 30, 30, 30)
		        )
                .addGap(15)
                .addGroup(container.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGap(8)
                    .addComponent(totalPieces, 30, 30, 30)
                    .addGap(8)
					
                )
                .addGroup(container.createParallelGroup(GroupLayout.Alignment.TRAILING)
                    .addGap(8)
                    .addComponent(totalQueens, 30, 30, 30)
                    .addGap(8)

                )
				.addGap(24)
            )
		);
        return container;
	}

    public void updateDetails(int total, int queens){
		/**
		 * @param Total de peças comuns
		 * @param Número de damas ativas
		 */

        totalPieces.setText("Total de pedras: " + total);
        totalQueens.setText("Damas ativas: " + queens);
    }
}