package app.checkers;
import java.awt.Desktop;
import java.io.File;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;


public class Menu{
    public Menu(){
        JFrame poupUp = new JFrame();

        JButton play = this.styleButton(new JButton("Jogar"));
        JButton help = this.styleButton(new JButton("Tutorial"));


        JPanel option = new JPanel();
        option.add(play);
        option.add(help);
        poupUp.add(option);
          
        poupUp.setTitle("Menu"); 
		  poupUp.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//fecha o jframe
		  poupUp.setSize(400, 120); //largura e tamanho
		  poupUp.setVisible(true);
		  poupUp.setResizable(false);//nao mexer na tela
        poupUp.setLocationRelativeTo(null);//tela no centro
        poupUp.setLayout(null);  
		
        play.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // direcionar para o JFrame da classe Core
                Core.start();
                poupUp.dispose(); // Fecha o JFrame atual
            }
        });

        help.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Abrir o Tutorial
                try {
                    File file = new File("../src/ManualdoJogo.pdf");
                    Desktop desktop = Desktop.getDesktop();
                    desktop.open(file);
                    
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    public JButton styleButton(JButton button){
        //button.setBounds(100, 200, 200, 70);
        button.setFont(new Font("Arial", Font.BOLD, 35));
        return button;
    }
    
}
