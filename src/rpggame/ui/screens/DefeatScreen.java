package rpggame.ui.screens;

import rpggame.ui.GameFrame;
import rpggame.ui.components.PixelButton;
import rpggame.ui.components.PixelPanel;
import rpggame.utils.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class DefeatScreen extends PixelPanel {
    private GameFrame gameFrame;
    private Image defeatImage;

    //colori moderni
    private static final Color PRIMARY_COLOR = new Color(139, 0, 0); //dark Red
    private static final Color SECONDARY_COLOR = new Color(178, 34, 34); //firebrick
    private static final Color ACCENT_COLOR = new Color(220, 20, 60); //crimson
    private static final Color BG_COLOR = new Color(25, 25, 40); //blu scuro
    private static final Color TEXT_COLOR = new Color(240, 240, 255); //bianco leggermente bluastro

    //classe JLabel con contorno moderno
    private static class ModernLabel extends JLabel {
        private Color glowColor;
        private boolean isTitle;

        public ModernLabel(String text, Font font, Color glowColor, boolean isTitle) {
            super(text);
            setFont(font);
            setForeground(TEXT_COLOR);
            setOpaque(false);
            this.glowColor = glowColor;
            this.isTitle = isTitle;
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

            String text = getText();
            FontMetrics fm = g2.getFontMetrics();
            int x = 0;
            int y = fm.getAscent();

            //effetto glow per i titoli
            if (isTitle) {
                g2.setColor(new Color(glowColor.getRed(), glowColor.getGreen(), glowColor.getBlue(), 100));
                for (int i = 0; i < 5; i++) {
                    g2.drawString(text, x, y + i);
                    g2.drawString(text, x, y - i);
                    g2.drawString(text, x + i, y);
                    g2.drawString(text, x - i, y);
                }
            } else {
                //sottile contorno per le label normali
                g2.setColor(glowColor);
                g2.drawString(text, x + 1, y + 1);
            }

            //testo principale
            g2.setColor(getForeground());
            g2.drawString(text, x, y);
            g2.dispose();
        }
    }

    //pannello moderno con bordi arrotondati e sfondo semitrasparente
    private static class ModernPanel extends JPanel {
        private Color bgColor;
        private float alpha;
        private int cornerRadius;

        public ModernPanel(Color bgColor, float alpha, int cornerRadius) {
            this.bgColor = bgColor;
            this.alpha = alpha;
            this.cornerRadius = cornerRadius;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //sfondo semitrasparente con bordi arrotondati
            g2.setColor(new Color(bgColor.getRed(), bgColor.getGreen(), bgColor.getBlue(),
                    (int)(alpha * 255)));
            g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), cornerRadius, cornerRadius));

            //bordo sottile
            g2.setColor(new Color(SECONDARY_COLOR.getRed(), SECONDARY_COLOR.getGreen(),
                    SECONDARY_COLOR.getBlue(), 150));
            g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1,
                    cornerRadius, cornerRadius));
            g2.dispose();
        }
    }

    public DefeatScreen(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        //carica l'immagine della sconfitta
        defeatImage = ImageLoader.loadImage("/images/defeat.png");

        setLayout(new BorderLayout());

        //pannello principale con layout a griglia
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //titolo con effetto glow
        ModernLabel defeatLabel = new ModernLabel("SCONFITTA",
                new Font("Monospaced", Font.BOLD, 48),
                ACCENT_COLOR, true);
        defeatLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        mainPanel.add(defeatLabel, gbc);

        //pannello contenitore per il messaggio di sconfitta
        ModernPanel contentPanel = new ModernPanel(PRIMARY_COLOR, 0.7f, 20);
        contentPanel.setLayout(new GridBagLayout());
        contentPanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        contentPanel.setPreferredSize(new Dimension(500, 300));

        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.insets = new Insets(10, 10, 10, 10);
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        innerGbc.anchor = GridBagConstraints.CENTER;

        //icona di sconfitta (se disponibile)
        JLabel defeatIcon = new JLabel();
        if (defeatImage != null) {
            Image scaledDefeat = defeatImage.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            defeatIcon.setIcon(new ImageIcon(scaledDefeat));
        }
        defeatIcon.setHorizontalAlignment(JLabel.CENTER);
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.gridwidth = 1;
        contentPanel.add(defeatIcon, innerGbc);

        //messaggio di sconfitta
        ModernLabel messageLabel = new ModernLabel("Sei stato sconfitto nella tua avventura...",
                new Font("Monospaced", Font.BOLD, 18),
                ACCENT_COLOR, false);
        messageLabel.setHorizontalAlignment(JLabel.CENTER);
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        contentPanel.add(messageLabel, innerGbc);

        //messaggio di incoraggiamento
        ModernLabel encourageLabel = new ModernLabel("Ma la giungla ti aspetta per una nuova sfida!",
                new Font("Monospaced", Font.PLAIN, 16),
                SECONDARY_COLOR, false);
        encourageLabel.setHorizontalAlignment(JLabel.CENTER);
        innerGbc.gridx = 0;
        innerGbc.gridy = 2;
        contentPanel.add(encourageLabel, innerGbc);

        //pulsante per tornare al menu
        PixelButton continueButton = new PixelButton("TORNA AL MENU") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //sfondo del pulsante
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(178, 34, 34),    //top: Firebrick
                        0, getHeight(), new Color(128, 0, 0) //bottom: Maroon
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
        continueButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        continueButton.setForeground(Color.WHITE);
        continueButton.setPreferredSize(new Dimension(200, 50));
        continueButton.setBorderPainted(false);
        continueButton.setContentAreaFilled(false);
        continueButton.setFocusPainted(false);
        continueButton.addActionListener(e -> gameFrame.showHomeScreen());

        innerGbc.gridx = 0;
        innerGbc.gridy = 3;
        innerGbc.insets = new Insets(20, 10, 10, 10);
        contentPanel.add(continueButton, innerGbc);

        //aggiungi il pannello dei contenuti al pannello principale
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(contentPanel, gbc);

        //aggiungi il pannello principale al centro
        add(mainPanel, BorderLayout.CENTER);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //sfondo base
        if (defeatImage != null) {
            //usa l'immagine come sfondo ma con effetti
            g2.drawImage(defeatImage, 0, 0, getWidth(), getHeight(), this);

            //overlay rosso per dare un tono di sconfitta
            g2.setColor(new Color(100, 0, 0, 100));
            g2.fillRect(0, 0, getWidth(), getHeight());
        } else {
            //sfondo alternativo se l'immagine non è disponibile
            GradientPaint gradient = new GradientPaint(
                    0, 0, new Color(50, 0, 0),
                    getWidth(), getHeight(), new Color(100, 0, 0)
            );
            g2.setPaint(gradient);
            g2.fillRect(0, 0, getWidth(), getHeight());
        }

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

        //effetto particelle
        g2.setColor(new Color(169, 169, 169, 100)); //grigio semi-trasparente
        for (int i = 0; i < 150; i++) {
            int x = (int)(Math.random() * getWidth());
            int y = (int)(Math.random() * getHeight());
            int size = (int)(Math.random() * 3) + 1;
            g2.fillRect(x, y, size, size);
        }

        //effetto luce in alto
        GradientPaint topGlow = new GradientPaint(
                (float) getWidth() / 2, 0, new Color(220, 20, 60, 100), //crimson
                (float) getWidth() / 2, 200, new Color(0, 0, 0, 0)
        );
        g2.setPaint(topGlow);
        g2.fillRect(0, 0, getWidth(), 200);

        g2.dispose();
    }
}