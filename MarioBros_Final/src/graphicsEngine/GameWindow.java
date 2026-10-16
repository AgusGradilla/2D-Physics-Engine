package graphicsEngine;

import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class GameWindow extends JFrame{

	//JFrame unicamente crea la ventana, no las cosas que estan dentro de la ventana. 
	private JFrame jframe;
	private Image image = Toolkit.getDefaultToolkit().getImage("src/images/spr_QBlock.png");
	
	public GameWindow(graphicsEngine.GamePanel gamePanel) {
		jframe = new JFrame(); //Nueva ventana
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //El programa acaba al cerrarse la ventana
		jframe.add(gamePanel); 	//Se pone el gamePanel para que aparezca dentro de la ventana
		jframe.setResizable(false);
		jframe.setIconImage(image);
		jframe.pack(); //Cambia al tamaño preferido de Component
		jframe.setVisible(true); //Si no, no se muestra. Debe estar al final, para que sea visible solo al haberse cargado todo.
	}
}
