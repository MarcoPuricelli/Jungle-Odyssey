package rpggame.ui.screens;

import rpggame.ui.GameFrame;
import rpggame.ui.components.PixelButton;
import rpggame.ui.components.PixelPanel;
import rpggame.utils.ImageLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class WorldMapScreen extends PixelPanel {
    private GameFrame gameFrame;
    private Image mapImage;

    private PixelButton world1Button;
    private PixelButton world2Button;
    private PixelButton world3Button;
    private PixelButton backButton;

    //colori moderni (stessi della HomeScreen)
    private static final Color SECONDARY_COLOR = new Color(139,69,19); //Saddle Brown
    private static final Color ACCENT_COLOR = new Color(210,180,140);
    private static final Color BG_COLOR = new Color(25,25,40); //Blu scuro
    private static final Color TEXT_COLOR = new Color(240,240,255); //Bianco leggermente bluastro

    //posizioni delle isole
    private static final int ISLAND1_X = 50;
    private static final int ISLAND1_Y = 150;
    private static final int ISLAND2_X = 325;
    private static final int ISLAND2_Y = 250;
    private static final int ISLAND3_X = 600;
    private static final int ISLAND3_Y = 150;
    private static final int ISLAND_SIZE = 150;

    //classe JLabel con un contorno moderno e un effetto glow per i titoli
    private static class ModernLabel extends JLabel {
        //colore dell'effetto glow/contorno
        private Color glowColor;
        //booleano per determinare se l'etichetta è un titolo
        private boolean isTitle;

        //costruttore della classe ModernLabel
        //parametri:
        //- text: il testo da visualizzare
        //- font: il font da utilizzare
        //- glowColor: il colore del bagliore o del contorno
        //- isTitle: se true, il testo sarà stilizzato come un titolo
        public ModernLabel(String text, Font font, Color glowColor, boolean isTitle) {
            //chiama il costruttore della superclasse JLabel con il testo specificato
            super(text);
            //imposta il font della label
            setFont(font);
            //imposta il colore del testo
            setForeground(TEXT_COLOR);
            //rende il componente non opaco per consentire la trasparenza
            setOpaque(false);
            //assegna i valori dei parametri agli attributi della classe
            this.glowColor = glowColor;
            this.isTitle = isTitle;
        }

        //sovrascrittura del metodo paintComponent per disegnare il testo personalizzato
        @Override
        protected void paintComponent(Graphics g) {
            //crea una copia dell'oggetto Graphics come Graphics2D per abilitare funzionalità avanzate
            Graphics2D g2 = (Graphics2D) g.create();
            //abilita l'antialiasing per ottenere bordi più lisci
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            //abilita l'antialiasing per il testo per renderlo più chiaro
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            //ottiene il testo da visualizzare
            String text = getText();
            //ottiene le metriche del font per il calcolo della posizione
            FontMetrics fm = g2.getFontMetrics();
            //coordinate di partenza per disegnare il testo
            int x = 5; //offset a destra per evitare il taglio della prima lettera
            int y = fm.getAscent(); //altezza del carattere per l'allineamento verticale

            //se l'etichetta è un titolo, disegna l'effetto glow
            if (isTitle) {
                //imposta il colore per il bagliore con una trasparenza (alfa) di 100
                g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 100));
                //crea un effetto glow disegnando il testo più volte attorno alla posizione centrale
                for (int i = 0; i < 5; i++) {
                    //disegna il testo spostato leggermente in tutte le direzioni
                    g2.drawString(text, x, y + i); //verso il basso
                    g2.drawString(text, x, y - i); //verso l'alto
                    g2.drawString(text, x + i, y); //verso destra
                    g2.drawString(text, x - i, y); //verso sinistra
                }
            } else {
                //per label normali, disegna solo un sottile contorno
                g2.setColor(glowColor);
                //disegna il testo leggermente spostato in diagonale (simulazione di un'ombra)
                g2.drawString(text, x + 1, y + 1);
            }

            //disegna il testo principale sopra l'effetto glow o il contorno
            g2.setColor(getForeground());
            g2.drawString(text, x, y);
            //rilascia la risorsa grafica per evitare perdite di memoria
            g2.dispose();
        }
    }

    public WorldMapScreen(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        //carica l'immagine della mappa
        mapImage = ImageLoader.loadImage("/images/world_map.png");

        //usa null layout per posizionare i pulsanti liberamente
        setLayout(null);

        //titolo della mappa con effetto glow
        ModernLabel titleLabel = new ModernLabel("MAPPA DEL MONDO",
                new Font("Monospaced", Font.BOLD, 32),
                ACCENT_COLOR, true);
        titleLabel.setHorizontalAlignment(JLabel.LEFT);
        titleLabel.setBounds(30, 20, 800, 40);
        add(titleLabel);

        //crea pulsanti moderni per i mondi
        world1Button = createModernButton("SPORECRADLE WOODS", new Color(0, 150, 0));
        world2Button = createModernButton("EMBERVAULT DEPTHS", new Color(200, 50, 0));
        world3Button = createModernButton("DUSKVEIL REACH", new Color(75, 0, 130));

        //dimensioni dei pulsanti
        Dimension buttonSize = new Dimension(200, 50);
        world1Button.setPreferredSize(buttonSize);
        world2Button.setPreferredSize(buttonSize);
        world3Button.setPreferredSize(buttonSize);

        //posiziona i pulsanti sotto le isole
        world1Button.setBounds(ISLAND1_X - 25, ISLAND1_Y + ISLAND_SIZE + 20, 200, 50);
        world2Button.setBounds(ISLAND2_X - 25, ISLAND2_Y + ISLAND_SIZE + 20, 200, 50);
        world3Button.setBounds(ISLAND3_X - 25, ISLAND3_Y + ISLAND_SIZE + 20, 200, 50);

        //aggiungi i pulsanti
        add(world1Button);
        add(world2Button);
        add(world3Button);

        //pulsante indietro
        backButton = createModernButton("INDIETRO", ACCENT_COLOR);
        backButton.setBounds(350, 500, 100, 40); //posizione in basso al centro
        add(backButton);

        //aggiungi gli actionListener
        world1Button.addActionListener(e -> gameFrame.showBattleScreen("Sporecradle Woods"));
        world2Button.addActionListener(e -> gameFrame.showBattleScreen("Embervault Depths"));
        world3Button.addActionListener(e -> gameFrame.showBattleScreen("Duskveil Reach"));
        backButton.addActionListener(e -> gameFrame.showHomeScreen());
    }

    private PixelButton createModernButton(String text, Color color) {
        PixelButton button = new PixelButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //sfondo del pulsante
                GradientPaint gradient = new GradientPaint(
                        0, 0, color,
                        0, getHeight(), color.darker()
                );
                g2.setPaint(gradient);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                //bordo del pulsante
                g2.setColor(new Color(255, 255, 255, 100));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                //testo del pulsante
                g2.setFont(getFont());
                g2.setColor(Color.WHITE);
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textHeight = fm.getHeight();
                g2.drawString(getText(), (getWidth() - textWidth) / 2,
                        (getHeight() + textHeight / 2) / 2);

                g2.dispose();
            }
        };
        button.setFont(new Font("Monospaced", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //disegna lo sfondo della mappa
        if (mapImage != null) {
            g2.drawImage(mapImage, 0, 0, getWidth(), getHeight(), this);

            //overlay scuro per migliorare il contrasto
            g2.setColor(new Color(0, 0, 0, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());

            //bordi scuri
            RadialGradientPaint vignette = new RadialGradientPaint(
                    new Point(getWidth() / 2, getHeight() / 2),
                    getWidth() * 0.8f,
                    new float[] { 0.0f, 0.8f, 1.0f },
                    new Color[] {
                            new Color(0, 0, 0, 0),
                            new Color(0, 0, 0, 0),
                            new Color(0, 0, 0, 180)
                    }
            );
            g2.setPaint(vignette);
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else {
            //sfondo alternativo se l'immagine non è disponibile
            GradientPaint gradient = new GradientPaint(
                    0, 0, BG_COLOR,
                    getWidth(), getHeight(), new Color(15, 15, 30)
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());

            //effetto particelle
            g2.setColor(new Color(255, 255, 255, 100));
            for (int i = 0; i < 100; i++) {
                int x = (int)(Math.random() * getWidth());
                int y = (int)(Math.random() * getHeight());
                int size = (int)(Math.random() * 3) + 1;
                g2.fillOval(x, y, size, size);
            }

            //disegna le tre isole fluttuanti
            drawFloatingIsland(g2, ISLAND1_X, ISLAND1_Y, ISLAND_SIZE, new Color(0, 150, 0, 150));
            drawFloatingIsland(g2, ISLAND2_X, ISLAND2_Y, ISLAND_SIZE, new Color(200, 50, 0, 150));
            drawFloatingIsland(g2, ISLAND3_X, ISLAND3_Y, ISLAND_SIZE, new Color(75, 0, 130, 150));

            //disegna i nomi dei mondi sopra le isole
            g2.setColor(TEXT_COLOR);
            g2.setFont(new Font("Monospaced", Font.BOLD, 14));
            drawCenteredString(g2, "Sporecradle Woods", ISLAND1_X + ISLAND_SIZE/2, ISLAND1_Y + ISLAND_SIZE/2);
            drawCenteredString(g2, "Embervault Depths", ISLAND2_X + ISLAND_SIZE/2, ISLAND2_Y + ISLAND_SIZE/2);
            drawCenteredString(g2, "Duskveil Reach", ISLAND3_X + ISLAND_SIZE/2, ISLAND3_Y + ISLAND_SIZE/2);

            //disegna connessioni tra le isole
            g2.setColor(ACCENT_COLOR);
            g2.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
            g2.drawLine(ISLAND1_X + ISLAND_SIZE/2, ISLAND1_Y + ISLAND_SIZE/2,
                    ISLAND2_X + ISLAND_SIZE/2, ISLAND2_Y + ISLAND_SIZE/2);
            g2.drawLine(ISLAND2_X + ISLAND_SIZE/2, ISLAND2_Y + ISLAND_SIZE/2,
                    ISLAND3_X + ISLAND_SIZE/2, ISLAND3_Y + ISLAND_SIZE/2);
        }

        //effetto luce in alto
        GradientPaint topGlow = new GradientPaint(
                (float) getWidth() / 2, 0, new Color(ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(),
                ACCENT_COLOR.getBlue(), 100),
                (float) getWidth() / 2, 200, new Color(0, 0, 0, 0)
        );
        g2.setPaint(topGlow);
        g2.fillRect(0, 0, getWidth(), 200);

        g2.dispose();
    }

    //metodo per disegnare un'isola fluttuante con effetti
    private void drawFloatingIsland(Graphics2D g2, int x, int y, int size, Color color) {
        //ombra sotto l'isola
        g2.setColor(new Color(0, 0, 0, 100));
        g2.fillOval(x + 10, y + size - 20, size, 40);

        //isola principale
        g2.setColor(color);
        g2.fillOval(x, y, size, size);

        //bordo dell'isola
        g2.setColor(new Color(255, 255, 255, 100));
        g2.setStroke(new BasicStroke(2));
        g2.drawOval(x, y, size, size);

        //effetto luce sulla parte superiore
        g2.setColor(new Color(255, 255, 255, 70));
        g2.fillOval(x + size/4, y + size/4, size/2, size/4);
    }

    //metodo per disegnare testo centrato in una posizione
    private void drawCenteredString(Graphics2D g2, String text, int x, int y) {
        FontMetrics fm = g2.getFontMetrics();
        int textWidth = fm.stringWidth(text);

        //disegna contorno nero per leggibilità
        g2.setColor(Color.BLACK);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx != 0 || dy != 0) {
                    g2.drawString(text, x - textWidth/2 + dx, y + dy);
                }
            }
        }

        //disegna il testo principale
        g2.setColor(TEXT_COLOR);
        g2.drawString(text, x - textWidth/2, y);
    }
}