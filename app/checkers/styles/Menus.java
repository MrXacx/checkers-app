package app.checkers.styles;

import java.awt.Desktop;
import java.io.File;

import  javax.swing.*;

import java.awt.Color;

public class Menus extends JPanel {
	 // Variables declaration - do not modify//GEN-BEGIN:variables
    
    private JPanel playerContainerDown;
    private JTextPane namePlayerDown = new JTextPane();
    private JPanel picPlayerDown = new JPanel();
    private JButton confirmPlayerDown = new JButton();
    
    private JPanel playerContainerUp;
    private JPanel picPlayerUp = new JPanel();
    private JTextPane namePlayerUp = new JTextPane();
    private JButton confirmPlayerUp = new JButton();
   
    private JPanel timerContainer = new JPanel();
   
    private JPanel tools = new JPanel();
    private JToggleButton pause  = new JToggleButton();
    private JButton reset = new JButton();
    private JButton help = new JButton();
    
    // End of variables declaration//GEN-END:variables
    
    public Menus() {
		playerContainerUp = configContainer(new JPanel(), "JOGADOR1", picPlayerUp, namePlayerUp, confirmPlayerUp);
		playerContainerDown = configContainer(new JPanel(), "JOGADOR2", picPlayerDown, namePlayerDown, confirmPlayerDown);
		
		initTimer();
		initTools();
      setSize(new java.awt.Dimension(240, 640));
    }
    
    private JPanel configContainer(JPanel container, String name, JPanel picBox, JTextPane nameBox, JButton confirm){
    	container.setBackground(new Color(160, 100, 80));
		container.setSize(240, 160);	
		container.setName(name);
		
		picBox.setBackground(new Color(150, 130, 40));
		nameBox.setText(name);
		
		confirm.setBackground(Color.GREEN);
		confirm.addActionListener(evt -> confirmActionPerformed(evt));
		
		container.setLayout(settingPlayerContainer(new GroupLayout(container), picBox, nameBox, confirm));
		
		return container;
    }
    
        private void confirmActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
    }
    
    
    private GroupLayout settingPlayerContainer(GroupLayout container, JPanel picPlayer, JTextPane namePlayer, JButton confirm){
		
		container.setHorizontalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(GroupLayout.Alignment.TRAILING, container.createSequentialGroup()
		        .addGap(8)
		        .addComponent(picPlayer, 30, 30, 30)
		        .addGap(8)
		        .addComponent(namePlayer, 125, 125, 125)
		        .addGap(8)
		        .addComponent(confirm, 30, 30, 30)
		        )
		);
		container.setVerticalGroup(
		    container.createParallelGroup(GroupLayout.Alignment.LEADING)
		    .addGroup(container.createSequentialGroup()
		    	.addGap(15)
		        .addGroup(container.createParallelGroup(GroupLayout.Alignment.TRAILING)
		        	.addComponent(picPlayer, 30, 30, 30)
		            .addComponent(namePlayer, 30, 30, 30)
		            .addComponent(confirm, 30, 30, 30)
		            ))
		);
		return container;
	}
    
    
    
    private void initTimer(){
    	JLabel timer = new JLabel();
    	timer.setText("00:00");
		timerContainer.setBackground(new Color(80, 80, 80));
		timerContainer.setSize(240, 240);
		timerContainer.add(timer);
		//timerContainer.setLayout(null);
    }
    
    private void initTools(){
		tools.setSize(240, 80);
		
		pause.setSelected(true);
		pause.setText("pause");

		pause.addActionListener(evt -> pauseActionPerformed(evt));

		reset.setText("reset");

		reset.addActionListener(evt -> resetActionPerformed(evt));

		help.setText("help");
	
		help.addActionListener(evt -> helpActionPerformed(evt));

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
    
    private void pauseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
    }
    private void resetActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
        // TODO add your handling code here:
    }
    private void helpActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pauseActionPerformed
		try {
			File file = new File("../src/ManualdoJogo.pdf");
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
