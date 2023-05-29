package app.checkers.styles.components;

import java.awt.*;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;
import javax.swing.JPanel;


class TimerContainer extends JPanel {
    private JLabel minutes = new JLabel(), seconds = new JLabel();
    private int minuteCounter = -1, secondCounter = -1;
    private boolean runnig = true;

    public TimerContainer(Font timerFont, Color textColor){
		/**
		 * @param Fonte a ser aplicada
		 * @param Cor a ser aplicada no texto
		 */
		super(new GridLayout(2, 1)); // Posiciona labels em coluna
		this.setSize(240, 240);

    	// Configura as labels de minutos e segundos
		minutes.setFont(timerFont);
		seconds.setFont(timerFont);
		
		minutes.setForeground(textColor);
		seconds.setForeground(textColor);

		minutes.setText("00");
		seconds.setText("00");

		minutes.setSize(240, 80);
		seconds.setSize(240, 80);		

		this.add(minutes, BorderLayout.CENTER);
		this.add(seconds, BorderLayout.CENTER);
    }
    public void reset(){
		minutes.setText("00");
		seconds.setText("00");
		minuteCounter = -1;
		secondCounter = -1;
		runnig = false;
	}
	
	public boolean isCountingTime(){
		// @return Booleano de se a contagem foi iniciada
		return secondCounter >= 0;
	}

    public void stopCounting(boolean turn){
		// @param Booleano de se a contagem de ser parada
        this.runnig = !turn;
    }

	public void countTime(){
		// Zera contadores
		minuteCounter = 0;
		secondCounter = 0;
		(new Timer()).scheduleAtFixedRate(
			new TimerTask() {
				public void run() {
					if(runnig){
						if(++secondCounter == 60){
							secondCounter = 0;
							minutes.setText(String.format("%02d", ++minuteCounter)); // atualiza minutos
						}
					
						seconds.setText(String.format("%02d", secondCounter)); // atualiza segundos
					}
				}
			},
			0, 1000
		);
	}
       
    
    
}
