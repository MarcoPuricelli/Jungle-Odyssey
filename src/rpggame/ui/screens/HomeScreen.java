package rpggame.ui.screens;

import rpggame.characters.Agrimancer;
import rpggame.characters.Character;
import rpggame.characters.Rootbane;
import rpggame.characters.Trailblazer;
import rpggame.characters.Voltavoc;
import rpggame.ui.GameFrame;
import rpggame.ui.components.PixelButton;
import rpggame.ui.components.PixelPanel;
import rpggame.utils.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HomeScreen extends PixelPanel {
    private GameFrame gameFrame;
    private JTextField nameField;
    private JComboBox<String> characterSelector;
    private JTextArea characterDescription;
    private PixelButton playButton;
    private PixelButton blessingButton;
    private Character selectedCharacter;
    private JPanel characterPreviewPanel;

    private Image backgroundImage;
    private Image blessingImage;
    private Image[] characterImages = new Image[4]; //array per le immagini dei personaggi

    //colori moderni
    private static final Color PRIMARY_COLOR = new Color(34, 139, 34); //forest Green
    private static final Color SECONDARY_COLOR = new Color(139, 69, 19); //saddle Brown
    private static final Color ACCENT_COLOR = new Color(210, 180, 140);
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
                //sottile contorno per le etichette normali
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

    public HomeScreen(GameFrame gameFrame) {
        this.gameFrame = gameFrame;

        //carica le immagini
        backgroundImage = ImageLoader.loadImage("/images/home_background.png");
        blessingImage = ImageLoader.loadImage("/images/blessing.png");

        //carica le immagini dei personaggi
        characterImages[0] = ImageLoader.loadImage("/images/characters/voltavoc.png");
        characterImages[1] = ImageLoader.loadImage("/images/characters/agrimancer.png");
        characterImages[2] = ImageLoader.loadImage("/images/characters/rootbane.png");
        characterImages[3] = ImageLoader.loadImage("/images/characters/trailblazer.png");

        setLayout(new BorderLayout());

        //pannello principale con layout a griglia
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        //titolo del gioco con effetto glow
        ModernLabel titleLabel = new ModernLabel("JUNGLE ODYSSEY",
                new Font("Monospaced", Font.BOLD, 36),
                ACCENT_COLOR, true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);

        //pannello contenitore per i controlli principali
        ModernPanel controlPanel = new ModernPanel(PRIMARY_COLOR, 0.7f, 20);
        controlPanel.setLayout(new GridBagLayout());
        controlPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.insets = new Insets(10, 10, 10, 10);
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        innerGbc.anchor = GridBagConstraints.WEST;

        //campo nome utente con stile moderno
        ModernLabel nameLabel = new ModernLabel("NOME:",
                new Font("Monospaced", Font.BOLD, 14),
                SECONDARY_COLOR, false);
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.gridwidth = 1;
        controlPanel.add(nameLabel, innerGbc);

        nameField = new JTextField(gameFrame.getGameData().getUsername(), 15);
        nameField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        nameField.setForeground(TEXT_COLOR);
        nameField.setBackground(new Color(45, 45, 65));
        nameField.setBorder(new CompoundBorder(
                new LineBorder(SECONDARY_COLOR, 1),
                new EmptyBorder(5, 5, 5, 5)
        ));
        nameField.setCaretColor(TEXT_COLOR);
        innerGbc.gridx = 1;
        innerGbc.gridy = 0;
        controlPanel.add(nameField, innerGbc);

        //selettore personaggio con stile moderno
        ModernLabel characterLabel = new ModernLabel("PERSONAGGIO:",
                new Font("Monospaced", Font.BOLD, 14),
                SECONDARY_COLOR, false);
        innerGbc.gridx = 0;
        innerGbc.gridy = 1;
        controlPanel.add(characterLabel, innerGbc);

        String[] characters = {"Voltavoc", "Agrimancer", "Rootbane", "Trailblazer"};
        characterSelector = new JComboBox<>(characters);
        characterSelector.setFont(new Font("Monospaced", Font.PLAIN, 14));
        characterSelector.setForeground(TEXT_COLOR);
        characterSelector.setBackground(new Color(45, 45, 65));

        //renderer custom per il menu a tendina
        characterSelector.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setOpaque(true);
                label.setBackground(new Color(45, 45, 65)); //sfondo nero
                label.setForeground(TEXT_COLOR); //colore del testo
                if (isSelected) {
                    label.setBackground(SECONDARY_COLOR); //colore quando selezionato
                    label.setForeground(Color.WHITE);
                }
                return label;
            }
        });

        //UI custom per comboBox
        characterSelector.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton button = super.createArrowButton();
                button.setBackground(new Color(45, 45, 65));
                button.setForeground(TEXT_COLOR);
                return button;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(new Color(45, 45, 65));
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        characterSelector.setBorder(new LineBorder(SECONDARY_COLOR, 1));
        innerGbc.gridx = 1;
        innerGbc.gridy = 1;
        controlPanel.add(characterSelector, innerGbc);

        //pannello per l'anteprima del personaggio
        characterPreviewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //sfondo del pannello anteprima
                g2.setColor(new Color(35, 35, 55, 200));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                //bordo del pannello anteprima
                g2.setColor(ACCENT_COLOR);
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                //disegna l'immagine del personaggio selezionato
                int index = characterSelector.getSelectedIndex();
                if (index >= 0 && index < characterImages.length && characterImages[index] != null) {
                    Image img = characterImages[index];
                    int imgWidth = img.getWidth(this);
                    int imgHeight = img.getHeight(this);

                    //calcola le dimensioni mantenendo le proporzioni
                    double scale = Math.min((double)getWidth() / imgWidth, (double)getHeight() / imgHeight) * 0.8;
                    int scaledWidth = (int)(imgWidth * scale);
                    int scaledHeight = (int)(imgHeight * scale);

                    //centra l'immagine
                    int x = (getWidth() - scaledWidth) / 2;
                    int y = (getHeight() - scaledHeight) / 2;

                    g2.drawImage(img, x, y, scaledWidth, scaledHeight, this);
                }

                g2.dispose();
            }
        };
        characterPreviewPanel.setPreferredSize(new Dimension(150, 150));
        innerGbc.gridx = 2;
        innerGbc.gridy = 0;
        innerGbc.gridheight = 2;
        innerGbc.insets = new Insets(10, 20, 10, 10);
        controlPanel.add(characterPreviewPanel, innerGbc);

        //descrizione personaggio
        characterDescription = new JTextArea(4, 20);
        characterDescription.setFont(new Font("Monospaced", Font.PLAIN, 12));
        characterDescription.setForeground(TEXT_COLOR);
        characterDescription.setBackground(new Color(35, 35, 55));
        characterDescription.setEditable(false);
        characterDescription.setLineWrap(true);
        characterDescription.setWrapStyleWord(true);
        characterDescription.setBorder(new CompoundBorder(
                new LineBorder(SECONDARY_COLOR, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));

        JScrollPane scrollPane = new JScrollPane(characterDescription);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = SECONDARY_COLOR;
                this.trackColor = new Color(35, 35, 55);
            }
        });

        innerGbc.gridx = 0;
        innerGbc.gridy = 2;
        innerGbc.gridwidth = 3;
        innerGbc.gridheight = 1;
        innerGbc.insets = new Insets(10, 10, 10, 10);
        controlPanel.add(scrollPane, innerGbc);

        //pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setOpaque(false);

        //pulsante per giocare
        playButton = new PixelButton("GIOCA") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //sfondo del pulsante
                GradientPaint gradient = new GradientPaint(
                        0, 0, new Color(160, 82, 45),    //top: Sienna
                        0, getHeight(), new Color(101, 67, 33) //bottom: Bistre
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
        playButton.setFont(new Font("Monospaced", Font.BOLD, 16));
        playButton.setForeground(Color.WHITE);
        playButton.setPreferredSize(new Dimension(150, 50));
        playButton.setBorderPainted(false);
        playButton.setContentAreaFilled(false);
        playButton.setFocusPainted(false);
        buttonPanel.add(playButton);

        //pulsante per la benedizione con stile moderno
        blessingButton = new PixelButton("BENEDIZIONE") {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //sfondo del pulsante
                if (isEnabled()) {
                    GradientPaint gradient = new GradientPaint(
                            0, 0, ACCENT_COLOR,
                            0, getHeight(), new Color(200, 50, 150)
                    );
                    g2.setPaint(gradient);
                } else {
                    g2.setColor(new Color(100, 100, 120));
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

                //bordo del pulsante
                g2.setColor(new Color(255, 255, 255, isEnabled() ? 100 : 50));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, 15, 15);

                //icona della benedizione
                if (blessingImage != null && isEnabled()) {
                    g2.drawImage(blessingImage, 10, (getHeight() - 30) / 2, 30, 30, this);
                }

                //testo del pulsante
                g2.setFont(getFont());
                g2.setColor(isEnabled() ? Color.WHITE : new Color(200, 200, 200));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(getText());
                int textX = blessingImage != null && isEnabled() ? 50 : (getWidth() - textWidth) / 2;
                g2.drawString(getText(), textX, (getHeight() + fm.getHeight() / 2) / 2);

                g2.dispose();
            }
        };
        blessingButton.setFont(new Font("Monospaced", Font.BOLD, 14));
        blessingButton.setForeground(Color.WHITE);
        blessingButton.setPreferredSize(new Dimension(150, 50));
        blessingButton.setBorderPainted(false);
        blessingButton.setContentAreaFilled(false);
        blessingButton.setFocusPainted(false);
        buttonPanel.add(blessingButton);

        innerGbc.gridx = 0;
        innerGbc.gridy = 3;
        innerGbc.gridwidth = 3;
        innerGbc.fill = GridBagConstraints.HORIZONTAL;
        controlPanel.add(buttonPanel, innerGbc);

        //statistiche con stile moderno
        ModernLabel statsLabel = new ModernLabel(
                "Vittorie totali: " + gameFrame.getGameData().getVictories(),
                new Font("Monospaced", Font.BOLD, 14),
                ACCENT_COLOR, false
        );
        statsLabel.setHorizontalAlignment(JLabel.CENTER);
        innerGbc.gridx = 0;
        innerGbc.gridy = 4;
        innerGbc.gridwidth = 3;
        controlPanel.add(statsLabel, innerGbc);

        //aggiungi il pannello dei controlli al pannello principale
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.BOTH;
        mainPanel.add(controlPanel, gbc);

        //aggiungi il pannello principale al centro
        add(mainPanel, BorderLayout.CENTER);

        //Listener
        characterSelector.addActionListener(e -> {
            updateCharacterDescription();
            characterPreviewPanel.repaint(); //aggiorna l'anteprima quando cambia la selezione
        });

        playButton.addActionListener(e -> {
            gameFrame.getGameData().setUsername(nameField.getText());
            createSelectedCharacter();
            gameFrame.getGameData().setPlayerCharacter(selectedCharacter);
            gameFrame.getGameData().saveData();
            gameFrame.showWorldMapScreen();
        });

        blessingButton.addActionListener(e -> activateBlessing());

        //inizializzazioni
        updateCharacterDescription();
        updateBlessingButton();
    }

    private void updateCharacterDescription() {
        String selectedChar = (String) characterSelector.getSelectedItem();

        switch (selectedChar) {
            case "Voltavoc":
                characterDescription.setText("Voltavoc: un mago elettrico con potenti attacchi a distanza. Specializzato in danni elevati ma con poca vita.");
                break;
            case "Agrimancer":
                characterDescription.setText("Agrimancer: un druido che controlla le piante. Equilibrato tra attacco e difesa, con abilità di cura.");
                break;
            case "Rootbane":
                characterDescription.setText("Rootbane: un guerriero della terra con alta resistenza. Specializzato nella difesa e negli attacchi ravvicinati.");
                break;
            case "Trailblazer":
                characterDescription.setText("Trailblazer: un esploratore veloce e agile. Specializzato nell'evasione e negli attacchi rapidi multipli.");
                break;
        }
    }

    private void createSelectedCharacter() {
        String selectedChar = (String) characterSelector.getSelectedItem();

        switch (selectedChar) {
            case "Voltavoc":
                selectedCharacter = new Voltavoc();
                break;
            case "Agrimancer":
                selectedCharacter = new Agrimancer();
                break;
            case "Rootbane":
                selectedCharacter = new Rootbane();
                break;
            case "Trailblazer":
                selectedCharacter = new Trailblazer();
                break;
        }

        if (gameFrame.getGameData().isBlessingActive() &&
                gameFrame.getGameData().getBlessedCharacter().equals(selectedChar)) {
            selectedCharacter.applyBlessing();
        }
    }

    private void activateBlessing() {
        if (isBlessingAvailable()) {
            String selectedChar = (String) characterSelector.getSelectedItem();

            gameFrame.getGameData().setBlessingDate(LocalDate.now());
            gameFrame.getGameData().setBlessingActive(true);
            gameFrame.getGameData().setBlessedCharacter(selectedChar);
            gameFrame.getGameData().setBlessingGamesLeft(3);
            gameFrame.getGameData().saveData();

            //dialog moderno per la benedizione
            JDialog blessingDialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Benedizione Attivata", true);
            blessingDialog.setLayout(new BorderLayout());
            blessingDialog.setSize(400, 200);
            blessingDialog.setLocationRelativeTo(this);

            JPanel dialogPanel = new ModernPanel(PRIMARY_COLOR, 0.9f, 20);
            dialogPanel.setLayout(new BorderLayout(20, 20));
            dialogPanel.setBorder(new EmptyBorder(20, 20, 20, 20));

            JLabel blessingIcon = new JLabel(new ImageIcon(blessingImage.getScaledInstance(64, 64, Image.SCALE_SMOOTH)));
            dialogPanel.add(blessingIcon, BorderLayout.WEST);

            JTextArea messageArea = new JTextArea(
                    "Benedizione degli Antichi attivata per " + selectedChar + "!\n\n" +
                            "Le abilità saranno potenziate del 10% per le prossime 3 partite."
            );
            messageArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
            messageArea.setForeground(TEXT_COLOR);
            messageArea.setBackground(new Color(0, 0, 0, 0));
            messageArea.setEditable(false);
            messageArea.setLineWrap(true);
            messageArea.setWrapStyleWord(true);
            messageArea.setOpaque(false);
            dialogPanel.add(messageArea, BorderLayout.CENTER);

            JButton okButton = new JButton("OK") {
                @Override
                protected void paintComponent(Graphics g) {
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    //sfondo del pulsante
                    GradientPaint gradient = new GradientPaint(
                            0, 0, ACCENT_COLOR,
                            0, getHeight(), new Color(200, 50, 150)
                    );
                    g2.setPaint(gradient);
                    g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                    //bordo del pulsante
                    g2.setColor(new Color(255, 255, 255, 100));
                    g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 10, 10);

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
            okButton.setFont(new Font("Monospaced", Font.BOLD, 14));
            okButton.setPreferredSize(new Dimension(100, 40));
            okButton.setBorderPainted(false);
            okButton.setContentAreaFilled(false);
            okButton.setFocusPainted(false);
            okButton.addActionListener(e -> blessingDialog.dispose());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.setOpaque(false);
            buttonPanel.add(okButton);
            dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

            blessingDialog.add(dialogPanel);
            blessingDialog.setUndecorated(true); //rimuove la decorazione della finestra
            blessingDialog.setShape(new RoundRectangle2D.Float(0, 0, 400, 200, 20, 20));
            blessingDialog.setVisible(true);

            updateBlessingButton();
        }
    }

    private boolean isBlessingAvailable() {
        LocalDate lastBlessing = gameFrame.getGameData().getBlessingDate();
        LocalDate today = LocalDate.now();

        if (lastBlessing == null) {
            return true;
        }

        long daysSinceLastBlessing = ChronoUnit.DAYS.between(lastBlessing, today);
        return daysSinceLastBlessing >= 7;
    }

    private void updateBlessingButton() {
        if (isBlessingAvailable()) {
            blessingButton.setEnabled(true);
            blessingButton.setText("BENEDIZIONE");
        } else {
            blessingButton.setEnabled(false);

            LocalDate lastBlessing = gameFrame.getGameData().getBlessingDate();
            LocalDate nextAvailable = lastBlessing.plusDays(7);
            long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), nextAvailable);

            blessingButton.setText("DISPONIBILE TRA " + daysLeft + " GIORNI");
        }

        if (gameFrame.getGameData().isBlessingActive()) {
            blessingButton.setText("ATTIVA: " +
                    gameFrame.getGameData().getBlessedCharacter() +
                    " (" + gameFrame.getGameData().getBlessingGamesLeft() + ")");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //sfondo base
        if (backgroundImage != null) {
            g2.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

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
}