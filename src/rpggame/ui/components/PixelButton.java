package rpggame.ui.components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//classe PixelButton estende JButton per creare un pulsante con effetto pixel art
public class PixelButton extends JButton {
    //definizione dei colori per i vari stati del pulsante
    private Color normalColor = new Color(80, 80, 80); //colore normale
    private Color hoverColor = new Color(100, 100, 100); //colore quando il mouse è sopra
    private Color pressedColor = new Color(60, 60, 60); //colore quando il pulsante è premuto
    private Color currentColor; //colore attuale del pulsante

    //costruttore della classe PixelButton
    public PixelButton(String text) {
        super(text); //imposta il testo del pulsante

        //personalizzazione dell'aspetto del pulsante
        setContentAreaFilled(false); //disabilita il riempimento del contenuto predefinito
        setFocusPainted(false); //rimuove il contorno di focus
        setBorderPainted(false); //rimuove il bordo predefinito
        setForeground(Color.WHITE); //imposta il colore del testo a bianco
        setFont(new Font("Press Start 2P", Font.PLAIN, 12)); //imposta il font

        //colore iniziale del pulsante
        currentColor = normalColor;

        //aggiunge un listener per gestire i cambiamenti del mouse
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                if (isEnabled()) { //cambia colore quando il mouse entra
                    currentColor = hoverColor;
                    repaint();
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (isEnabled()) { //ripristina il colore quando il mouse esce
                    currentColor = normalColor;
                    repaint();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (isEnabled()) { //cambia colore quando il pulsante viene premuto
                    currentColor = pressedColor;
                    repaint();
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (isEnabled()) { //ritorna al colore hover o normale a seconda della posizione del mouse
                    currentColor = contains(e.getPoint()) ? hoverColor : normalColor;
                    repaint();
                }
            }
        });
    }

    //metodo per disegnare il componente
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        //disabilita l'antialiasing per effetto pixel art
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);

        //imposta il colore di sfondo a seconda dello stato del pulsante
        if (!isEnabled()) {
            g2d.setColor(new Color(60, 60, 60)); //colore quando disabilitato
        } else {
            g2d.setColor(currentColor); //colore corrente (normale, hover o premuto)
        }

        //disegna il corpo del pulsante
        g2d.fillRect(0, 0, getWidth(), getHeight());

        //disegna il bordo del pulsante
        g2d.setColor(new Color(40, 40, 40));
        g2d.drawRect(0, 0, getWidth() - 1, getHeight() - 1);

        //aggiunge un effetto 3D per migliorare l'estetica
        g2d.setColor(new Color(120, 120, 120));
        g2d.drawLine(1, 1, getWidth() - 2, 1); //linea superiore
        g2d.drawLine(1, 1, 1, getHeight() - 2); //linea sinistra

        g2d.setColor(new Color(40, 40, 40));
        g2d.drawLine(getWidth() - 2, 1, getWidth() - 2, getHeight() - 2); //linea destra
        g2d.drawLine(1, getHeight() - 2, getWidth() - 2, getHeight() - 2); //linea inferiore

        g2d.dispose(); //rilascia le risorse grafiche

        //disegna il testo del pulsante
        super.paintComponent(g);
    }
}