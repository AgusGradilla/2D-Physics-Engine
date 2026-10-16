package graphicsEngine;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class FontLoader {

    private Image fontSheet;
    
    // Configuracion de la imagen original
    private final int CHAR_WIDTH = 16;  // Ancho de una letra en el PNG
    private final int CHAR_HEIGHT = 16; // Alto de una letra en el PNG
    
    // Configuración de dibujo
    private final int SCALE = 1; // Que tan grande se ve en el juego
    
    //String desde el cual se va a checar las posiciones en la imagen
    private final String guide = " !\"#$%&'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_";

    public FontLoader(String path) {
        loadFont(path);
    }

    private void loadFont(String path) {
        fontSheet = Toolkit.getDefaultToolkit().getImage(path);
    }

    public void drawText(Graphics g, String text, int x, int y) {
        // Convertimos a mayus por que la imagen solo tiene letras mayus
        text = text.toUpperCase();

        int currentX = x; // Posicion donde se dibujaremos la siguiente letra

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            
            // En que posicion esta el caracter
            int index = guide.indexOf(c);

            // Si el carácter no está en la guia, se avanza y sigue
            if (index < 0) {
                currentX += CHAR_WIDTH * SCALE;
                continue;
            }

            // Coordenadas en la imagen png
            // La imagen que vamos a usar ocupa 16 caracteres por linea, por eso son 16 aqui.
            int srcX = (index % 16) * CHAR_WIDTH;
            int srcY = (index / 16) * CHAR_HEIGHT; 


            // Coordenadas destino, o sea en la pantalla.
            int destW = CHAR_WIDTH * SCALE;
            int destH = CHAR_HEIGHT * SCALE;

            // Se dibujan las letras 
            g.drawImage(fontSheet, 
                currentX, y,                   // Destino Esquina Superior Izquierda
                currentX + destW, y + destH,   // Destino Esquina Inferior Derecha
                srcX, srcY,                    // Origen Esquina Superior Izquierda
                srcX + CHAR_WIDTH, srcY + CHAR_HEIGHT, // Origen Esquina Inferior Derecha
                null
            );

            // Se avanza la posicion para la siguiente letra
            currentX += destW;
        }
    }
}