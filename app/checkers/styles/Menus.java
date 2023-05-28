package app.checkers.styles;

import java.io.File;
import  javax.swing.*;
import java.awt.*;

import app.checkers.components.Player;

class Menus extends JPanel {
	 // Variables declaration - do not modify//GEN-BEGIN:variables
    
    private JPanel playerContainerDown;
   
    private JPanel playerContainerUp;
    
    private JPanel timerContainer = new JPanel();
   
    private JPanel tools = new JPanel();
    private JButton pause;
    private JButton reset;
    private JButton help;
    
    // End of variables declaration//GEN-END:variables
    
    public Menus(Board board, Player up, Player down) {

		up.setContainer(new PlayerContainer(up));
		down.setContainer(new PlayerContainer(down));
			
		playerContainerUp = up.getContainer();
		playerContainerDown = down.getContainer();

		initTimer();
		initTools(board, up, down);
      	setSize(240, 640);
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
    private void initTools(Board board, Player up, Player down){
		tools.setSize(240, 80);
		
		pause = styleButton("pause");
		pause.setSelected(false);
		pause.addActionListener(evt -> pauseActionPerformed(board));

		reset = styleButton("reset");
		reset.addActionListener(evt -> resetActionPerformed(board, up, down));

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
    
    private void pauseActionPerformed(Board board) {
		if(pause.isSelected()){
			pause.setIcon(new ImageIcon(new ImageIcon("../src/tools/pause.png").getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));		
		}
		else{			
			pause.setIcon(new ImageIcon(new ImageIcon("../src/tools/resume.png").getImage().getScaledInstance(-1, 45, Image.SCALE_DEFAULT)));
		}
		board.disable(!pause.isSelected());
		pause.setSelected(!pause.isSelected());
	}
    private void resetActionPerformed(Board board, Player up, Player down) {
		if(JOptionPane.showConfirmDialog(board, "Tem certeza que deseja reiniciar a partida?", "Reiniciar partida", JOptionPane.YES_NO_OPTION, 1) == 0){
			up.reset();
			down.reset();
			board.reset();
		}
	}
    
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
