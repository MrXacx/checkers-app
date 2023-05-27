package app.checkers.styles;

import java.io.File;
import  javax.swing.*;
import java.awt.*;

import app.checkers.components.Player;

class Menus extends JPanel {
	 // Variables declaration - do not modify//GEN-BEGIN:variables
    
    private JPanel playerContainerDown;
    private JLabel namePlayerDown = new JLabel();
    private JLabel picPlayerDown = new JLabel();
    
    private JPanel playerContainerUp;
    private JLabel picPlayerUp = new JLabel();
    private JLabel namePlayerUp = new JLabel();
   
    private JPanel timerContainer = new JPanel();
   
    private JPanel tools = new JPanel();
    private JButton pause;
    private JButton reset;
    private JButton help;
    
    // End of variables declaration//GEN-END:variables
    
    public Menus(Board board, Player[] players) {
		playerContainerUp = initContainer(new JPanel(), players[1], picPlayerUp, namePlayerUp);
		playerContainerDown = initContainer(new JPanel(), players[0], picPlayerDown, namePlayerDown);
		
		initTimer();
		initTools();
      setSize(new java.awt.Dimension(240, 640));
    }
    
    private JPanel initContainer(JPanel container, Player player, JLabel picBox, JLabel nameBox){
    	container.setBackground(Color.decode("#a9966d"));
		container.setSize(240, 160);	
		
		picBox.setIcon(player.getIcon(30));
		nameBox.setText(player.getUpperColor());
		
		container.setLayout(settingPlayerContainer(new GroupLayout(container), picBox, nameBox));
		
		return container;
    }
    
    private GroupLayout settingPlayerContainer(GroupLayout container, JLabel picPlayer, JLabel namePlayer){
		
		container.setHorizontalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(GroupLayout.Alignment.TRAILING, container.createSequentialGroup()
		        .addGap(8)
		        .addComponent(picPlayer, 30, 30, 30)
		        .addGap(8)
		        .addComponent(namePlayer, 125, 125, 125)
		        )
		);
		container.setVerticalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(container.createSequentialGroup()
		    	.addGap(15)
		        .addGroup(container.createParallelGroup(GroupLayout.Alignment.TRAILING)
		        	.addComponent(picPlayer, 30, 30, 30)
		            .addComponent(namePlayer, 30, 30, 30)
		            ))
		);
		return container;
	}
    
    
    
    private void initTimer(){
    	JLabel timer = new JLabel();
    	timer.setText("00:00");
		timerContainer.setBackground(Color.decode("#b7b6b0"));
		timerContainer.setSize(240, 240);
		timerContainer.add(timer);
		//timerContainer.setLayout(null);
    }
    private JButton styleButton(String name){
    	JButton button = LayoutFactory.styleButton(new JButton(), Color.decode("#FEFEFE"));
    	button.setIcon(new ImageIcon(new ImageIcon("../src/tools/"+name+".png").getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));
    	return button;
    }
    private void initTools(){
		tools.setSize(240, 80);
		
		pause = styleButton("pause");
		pause.setSelected(false);
		pause.addActionListener(evt -> pauseActionPerformed());

		reset = styleButton("reset");
		reset.addActionListener(evt -> resetActionPerformed());

		help = styleButton("help");
		help.addActionListener(evt -> helpActionPerformed());

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
    
    private void pauseActionPerformed() {
		if(pause.isSelected()){
			pause.setIcon(new ImageIcon(new ImageIcon("../src/tools/pause.png").getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));
		}
		else{
			pause.setIcon(new ImageIcon(new ImageIcon("../src/tools/resume.png").getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));
		}
		pause.setSelected(!pause.isSelected());
	}
    private void resetActionPerformed() {}
    
    private void helpActionPerformed() {
		try {
			File file = new File("../src/docs/manual_do_jogo.pdf");
			Desktop desktop = Desktop.getDesktop();
			desktop.open(file);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
    }
    
    public void alignGroups() {
		GroupLayout layout = new GroupLayout(this);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addComponent(playerContainerUp, 240, 240, 240)
				.addComponent(timerContainer, 240, 240, 240)
				.addComponent(tools, 240, 240, 240)
				.addComponent(playerContainerDown, 240, 240, 240)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
            .addGroup(layout.createSequentialGroup()
					.addComponent(playerContainerUp, 160, 160, 160)
					.addComponent(timerContainer, 240, 240, 240)
					.addComponent(tools, 80, 80, 80)
					.addComponent(playerContainerDown, 160, 160, 160))
        );
        this.setLayout(layout);
    }
	
	
	
    
    
   
}
