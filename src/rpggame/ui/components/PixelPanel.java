package rpggame.ui.components;

import javax.swing.*;
import java.awt.*;

public class PixelPanel extends JPanel {
    public PixelPanel() {
        setOpaque(false);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //implementazione di base, le sottoclassi possono sovrascrivere questo metodo
        //per disegnare sfondi personalizzati
    }
}