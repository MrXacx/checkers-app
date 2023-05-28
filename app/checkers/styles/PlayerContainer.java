package app.checkers.styles;

import  javax.swing.*;
import java.awt.Color;

import app.checkers.components.Player;

public class PlayerContainer extends JPanel {

    
    private JLabel pic = new JLabel();
    private JLabel name = new JLabel();
	private JLabel totalPieces = new JLabel();
	private JLabel totalQueens = new JLabel();

    
    // End of variables declaration//GEN-END:variables
    
    public PlayerContainer(Player player) {
		this.setBackground(Color.decode("#a9966d"));
		this.setSize(240, 160);	
		
		pic.setIcon(player.getIcon(30));
		name.setText(player.getUpperColor());
		updateDetails(player.getCordinates().size(), 0);
		this.setLayout(settingPlayerContainer(new GroupLayout(this)));
		
    }

    
    private GroupLayout settingPlayerContainer(GroupLayout container){
		
		container.setHorizontalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(GroupLayout.Alignment.TRAILING, container.createSequentialGroup()
		        .addGap(8)
		        .addComponent(pic, 30, 30, 30)
		        .addGap(8)
		        .addComponent(name, 125, 125, 125)
		    )
            .addGroup(GroupLayout.Alignment.TRAILING, container.createSequentialGroup()
		        .addGap(8)
		        .addComponent(totalPieces, 151, 151, 151)
		        .addGap(8)
		    )
            .addGroup(GroupLayout.Alignment.TRAILING, container.createSequentialGroup()
		        .addGap(8)
		        .addComponent(totalQueens, 151, 151, 151)
		        .addGap(8)
		    )

            
		);
		container.setVerticalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(container.createSequentialGroup()
		    	.addGap(15)
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
            )
		);
        return container;
	}

    public void updateDetails(int total, int queens){
        totalPieces.setText("Total de pedras: " + total);
        totalQueens.setText("Damas: " + queens);
    }
}