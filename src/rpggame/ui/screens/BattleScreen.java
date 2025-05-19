package rpggame.ui.screens;

import rpggame.characters.Character;
import rpggame.combat.Attack;
import rpggame.combat.CombatManager;
import rpggame.mobs.Boss;
import rpggame.mobs.Gloomling;
import rpggame.mobs.Mob;
import rpggame.mobs.Pyrewrath;
import rpggame.mobs.Rotcap;
import rpggame.ui.GameFrame;
import rpggame.ui.components.AnimationPanel;
import rpggame.ui.components.PixelButton;
import rpggame.ui.components.PixelPanel;
import rpggame.utils.ImageLoader;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.plaf.basic.BasicProgressBarUI;
import javax.swing.plaf.basic.BasicScrollBarUI;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public class BattleScreen extends PixelPanel {
    private GameFrame gameFrame;
    private String worldName;
    private Character player;
    private Mob enemy;
    private CombatManager combatManager;

    private Image backgroundImage;
    private Image playerImage;
    private Image enemyImage;

    private AnimationPanel animationPanel;
    private JProgressBar playerHealthBar;
    private JProgressBar enemyHealthBar;
    private JTextArea battleLog;
    private JPanel actionPanel;

    private List<String> completedWorlds;
    private boolean isBossFight;

    private SecureRandom random;

    //colori moderni (stessi della HomeScreen)
    private static final Color PRIMARY_COLOR = new Color(34, 139, 34);
    private static final Color SECONDARY_COLOR = new Color(139, 69, 19);
    private static final Color ACCENT_COLOR = new Color(210, 180, 140);

    private static final Color BG_COLOR = new Color(25, 25, 40);
    private static final Color TEXT_COLOR = new Color(240, 240, 255);

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

    //UI personalizzata per JProgressBar con stile moderno
    private static class ModernProgressBarUI extends BasicProgressBarUI {
        private Color barColor;

        public ModernProgressBarUI(Color barColor) {
            this.barColor = barColor;
        }

        @Override
        protected void paintDeterminate(Graphics g, JComponent c) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            //dimensioni e coordinate
            int width = progressBar.getWidth();
            int height = progressBar.getHeight();
            int barRectWidth = (int) (progressBar.getPercentComplete() * width);

            //disegna lo sfondo
            g2.setColor(new Color(45, 45, 65));
            g2.fillRoundRect(0, 0, width, height, 10, 10);

            //disegna il bordo
            g2.setColor(new Color(SECONDARY_COLOR.getRed(), SECONDARY_COLOR.getGreen(),
                    SECONDARY_COLOR.getBlue(), 150));
            g2.drawRoundRect(0, 0, width - 1, height - 1, 10, 10);

            //disegna la barra di progresso
            if (barRectWidth > 0) {
                //crea un gradiente per la barra
                GradientPaint gradient = new GradientPaint(
                        0, 0, barColor,
                        0, height, barColor.darker()
                );
                g2.setPaint(gradient);

                //disegna la barra con bordi arrotondati solo se non è piena
                if (barRectWidth < width) {
                    g2.fillRoundRect(0, 0, barRectWidth, height, 10, 10);
                } else {
                    g2.fillRoundRect(0, 0, width, height, 10, 10);
                }

                //effetto lucido sulla barra
                g2.setColor(new Color(255, 255, 255, 75));
                g2.fillRoundRect(2, 2, barRectWidth - 4, height / 3, 5, 5);
            }

            //disegna il testo
            if (progressBar.isStringPainted()) {
                paintString(g2, 0, 0, width, height, barRectWidth, getSelectionBackground());
            }

            g2.dispose();
        }

        protected void paintString(Graphics g, int x, int y, int width, int height, int fillStart, Color selectionForeground) {
            if (progressBar.isStringPainted()) {
                String progressString = progressBar.getString();
                g.setFont(progressBar.getFont());
                FontMetrics fm = g.getFontMetrics();
                int stringWidth = fm.stringWidth(progressString);
                int stringHeight = fm.getHeight();

                int stringX = x + (width - stringWidth) / 2;
                int stringY = y + ((height - stringHeight) / 2) + fm.getAscent();

                //disegna il contorno del testo
                g.setColor(Color.BLACK);
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        if (dx != 0 || dy != 0) {
                            g.drawString(progressString, stringX + dx, stringY + dy);
                        }
                    }
                }

                //disegna il testo
                g.setColor(Color.WHITE);
                g.drawString(progressString, stringX, stringY);
            }
        }
    }

    public BattleScreen(GameFrame gameFrame, String worldName) {
        this.gameFrame = gameFrame;
        this.worldName = worldName;
        this.player = gameFrame.getGameData().getPlayerCharacter();
        this.completedWorlds = gameFrame.getGameData().getCompletedWorlds();
        this.isBossFight = completedWorlds.size() >= 3;
        this.random = new SecureRandom();

        //crea il nemico giusto
        createEnemy();

        //inizializza il gestore del combattimento
        combatManager = new CombatManager(player, enemy, random);

        //carica le immagini
        loadImages();

        //configura il layout
        setupLayout();

        //inizia il combattimento
        startBattle();
    }

    private void createEnemy() {
        if (isBossFight) {
            enemy = new Boss();
            player.restoreFullHealth(); //ripristina la salute del giocatore per il boss
        } else {
            switch (worldName) {
                case "Sporecradle Woods":
                    enemy = new Rotcap();
                    break;
                case "Embervault Depths":
                    enemy = new Pyrewrath();
                    break;
                case "Duskveil Reach":
                    enemy = new Gloomling();
                    break;
                default:
                    enemy = new Rotcap(); //default fallback
            }
        }
    }

    private void loadImages() {
        //carica lo sfondo in base al mondo
        if (isBossFight) {
            backgroundImage = ImageLoader.loadImage("/images/boss_background.png");
        } else {
            switch (worldName) {
                case "Sporecradle Woods":
                    backgroundImage = ImageLoader.loadImage("/images/forest_background.png");
                    break;
                case "Embervault Depths":
                    backgroundImage = ImageLoader.loadImage("/images/cave_background.png");
                    break;
                case "Duskveil Reach":
                    backgroundImage = ImageLoader.loadImage("/images/dark_background.png");
                    break;
                default:
                    backgroundImage = ImageLoader.loadImage("/images/default_background.png");
            }
        }

        //carica l'immagine del giocatore in base al personaggio
        playerImage = ImageLoader.loadImage("/images/characters/" + player.getName().toLowerCase() + ".png");

        //carica l'immagine del nemico
        enemyImage = ImageLoader.loadImage("/images/enemies/" + enemy.getName().toLowerCase() + ".png");
    }

    private void setupLayout() {
        setLayout(new BorderLayout());

        //titolo della battaglia con effetto glow
        ModernLabel titleLabel = new ModernLabel(
                isBossFight ? "BATTAGLIA FINALE" : "BATTAGLIA IN " + worldName.toUpperCase(),
                new Font("Monospaced", Font.BOLD, 24),
                ACCENT_COLOR, true);
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        add(titleLabel, BorderLayout.NORTH);

        //pannello centrale per l'animazione
        ModernPanel centerPanel = new ModernPanel(new Color(0, 0, 0), 0.3f, 20);
        centerPanel.setLayout(new BorderLayout());

        //pannello per l'animazione
        animationPanel = new AnimationPanel(playerImage, enemyImage) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                //aggiungi effetti visivi moderni all'animazione
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                //disegna piattaforme per i personaggi
                int platformWidth = 120;
                int platformHeight = 30;
                int platformY = getHeight() - 50;

                //piattaforma giocatore (sinistra)
                GradientPaint playerPlatform = new GradientPaint(
                        50, platformY, SECONDARY_COLOR,
                        50 + platformWidth, platformY + platformHeight, SECONDARY_COLOR.darker()
                );
                g2.setPaint(playerPlatform);
                g2.fillRoundRect(50, platformY, platformWidth, platformHeight, 15, 15);
                g2.setColor(new Color(255, 255, 255, 100));
                g2.drawRoundRect(50, platformY, platformWidth, platformHeight, 15, 15);

                //piattaforma nemico (destra)
                GradientPaint enemyPlatform = new GradientPaint(
                        getWidth() - 170, platformY, ACCENT_COLOR,
                        getWidth() - 50, platformY + platformHeight, ACCENT_COLOR.darker()
                );
                g2.setPaint(enemyPlatform);
                g2.fillRoundRect(getWidth() - 170, platformY, platformWidth, platformHeight, 15, 15);
                g2.setColor(new Color(255, 255, 255, 100));
                g2.drawRoundRect(getWidth() - 170, platformY, platformWidth, platformHeight, 15, 15);

                g2.dispose();
            }
        };
        centerPanel.add(animationPanel, BorderLayout.CENTER);

        //pannello superiore per le barre della vita
        ModernPanel statsPanel = new ModernPanel(PRIMARY_COLOR, 0.7f, 15);
        statsPanel.setLayout(new GridLayout(2, 2, 10, 5));
        statsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //label per i nomi
        ModernLabel playerLabel = new ModernLabel(player.getName(),
                new Font("Monospaced", Font.BOLD, 14),
                SECONDARY_COLOR, false);

        ModernLabel enemyLabel = new ModernLabel(enemy.getName(),
                new Font("Monospaced", Font.BOLD, 14),
                ACCENT_COLOR, false);
        enemyLabel.setHorizontalAlignment(JLabel.RIGHT);

        //barre della vita moderne
        playerHealthBar = new JProgressBar(0, player.getMaxHealth());
        playerHealthBar.setValue(player.getCurrentHealth());
        playerHealthBar.setStringPainted(true);
        playerHealthBar.setString(player.getCurrentHealth() + "/" + player.getMaxHealth());
        playerHealthBar.setFont(new Font("Monospaced", Font.BOLD, 12));
        playerHealthBar.setUI(new ModernProgressBarUI(new Color(0, 200, 100)));

        enemyHealthBar = new JProgressBar(0, enemy.getMaxHealth());
        enemyHealthBar.setValue(enemy.getCurrentHealth());
        enemyHealthBar.setStringPainted(true);
        enemyHealthBar.setString(enemy.getCurrentHealth() + "/" + enemy.getMaxHealth());
        enemyHealthBar.setFont(new Font("Monospaced", Font.BOLD, 12));
        enemyHealthBar.setUI(new ModernProgressBarUI(new Color(200, 50, 50)));

        statsPanel.add(playerLabel);
        statsPanel.add(enemyLabel);
        statsPanel.add(playerHealthBar);
        statsPanel.add(enemyHealthBar);

        centerPanel.add(statsPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        //pannello inferiore per il log di battaglia e le azioni
        ModernPanel bottomPanel = new ModernPanel(PRIMARY_COLOR, 0.8f, 20);
        bottomPanel.setLayout(new BorderLayout(10, 10));
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        //log di battaglia
        battleLog = new JTextArea(5, 40);
        battleLog.setEditable(false);
        battleLog.setFont(new Font("Monospaced", Font.PLAIN, 12));
        battleLog.setLineWrap(true);
        battleLog.setWrapStyleWord(true);
        battleLog.setForeground(TEXT_COLOR);
        battleLog.setBackground(new Color(35, 35, 55));
        battleLog.setBorder(new CompoundBorder(
                new LineBorder(SECONDARY_COLOR, 1),
                new EmptyBorder(8, 8, 8, 8)
        ));

        JScrollPane scrollPane = new JScrollPane(battleLog);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                this.thumbColor = SECONDARY_COLOR;
                this.trackColor = new Color(35, 35, 55);
            }
        });
        bottomPanel.add(scrollPane, BorderLayout.CENTER);

        //pannello per i pulsanti delle azioni
        actionPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        actionPanel.setOpaque(false);

        //pulsanti moderni per le azioni
        PixelButton attackButton = createModernButton("ATTACCO BASE", SECONDARY_COLOR);
        attackButton.addActionListener(e -> performPlayerAction(player.getBasicAttack()));

        PixelButton specialAttackButton = createModernButton("ATTACCO SPECIALE", new Color(0, 150, 200));
        specialAttackButton.addActionListener(e -> performPlayerAction(player.getSpecialAttack()));

        PixelButton abilityButton = createModernButton("ABILITÀ", new Color(200, 150, 0));
        abilityButton.addActionListener(e -> performPlayerAction(player.getAbility()));

        PixelButton fleeButton = createModernButton("FUGGI", ACCENT_COLOR);
        fleeButton.addActionListener(e -> {
            battleLog.append("Hai deciso di fuggire dalla battaglia!\n");
            gameFrame.showWorldMapScreen();
        });

        actionPanel.add(attackButton);
        actionPanel.add(specialAttackButton);
        actionPanel.add(abilityButton);
        actionPanel.add(fleeButton);

        bottomPanel.add(actionPanel, BorderLayout.SOUTH);

        add(bottomPanel, BorderLayout.SOUTH);
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
        button.setFont(new Font("Monospaced", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setPreferredSize(new Dimension(150, 50));
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        return button;
    }

    private void startBattle() {
        battleLog.append("Inizio battaglia contro " + enemy.getName() + " in " + worldName + "!\n");

        //determina chi inizia in base all'iniziativa
        if (player.getInitiative() > enemy.getInitiative()) {
            battleLog.append(player.getName() + " ha l'iniziativa e attacca per primo!\n");
            enableActionButtons(true);
        } else if (enemy.getInitiative() > player.getInitiative()) {
            battleLog.append(enemy.getName() + " ha l'iniziativa e attacca per primo!\n");
            performEnemyAction();
        } else {
            //in caso di parità, estrai casualmente
            if (random.nextBoolean()) {
                battleLog.append(player.getName() + " ha l'iniziativa e attacca per primo!\n");
                enableActionButtons(true);
            } else {
                battleLog.append(enemy.getName() + " ha l'iniziativa e attacca per primo!\n");
                performEnemyAction();
            }
        }
    }

    private void performPlayerAction(Attack attack) {
        //disabilita i pulsanti durante l'azione
        enableActionButtons(false);

        //esegui l'attacco
        int damage = combatManager.calculateDamage(attack);

        //animazione dell'attacco
        animationPanel.playAttackAnimation(true, attack.getColor());

        //aggiorna il log di battaglia
        if (damage == 0) {
            battleLog.append(player.getName() + " usa " + attack.getName() + ": Miss!\n");
        } else if (damage == 40) {
            battleLog.append(player.getName() + " usa " + attack.getName() + ": Critical Hit! " + damage + " danni!\n");
        } else {
            battleLog.append(player.getName() + " usa " + attack.getName() + ": " + damage + " danni!\n");
        }

        if(attack.getName().equals("Cura")){
            System.out.println("Vita corrente: " + player.getCurrentHealth());
            System.out.println("Vita: " + (player.getCurrentHealth() + damage));
            System.out.println("Vita massima: " + player.getMaxHealth());
            System.out.println("Confronto: " + ((player.getCurrentHealth() + damage) >= player.getMaxHealth()));
            if((player.getCurrentHealth() + damage) >= player.getMaxHealth()){
                player.restoreFullHealth();
            }else{
                damage *= -1;
                player.takeDamage(damage);
            }
            updatePlayerHealthBar();
        }else{
            //applica il danno al nemico
            enemy.takeDamage(damage);
            updateEnemyHealthBar();
        }

        //controlla se il nemico è stato sconfitto
        if (enemy.getCurrentHealth() <= 0) {
            handleEnemyDefeated();
        } else {
            //turno del nemico
            Timer timer = new Timer(1000, e -> performEnemyAction());
            timer.setRepeats(false);
            timer.start();
        }
    }

    private void performEnemyAction() {
        //scegli casualmente un attacco
        Attack attack = enemy.getRandomAttack(random);

        //esegui l'attacco
        int damage = combatManager.calculateDamage(attack);

        //animazione dell'attacco
        animationPanel.playAttackAnimation(false, attack.getColor());

        //aggiorna il log di battaglia
        if (damage == 0) {
            battleLog.append(enemy.getName() + " usa " + attack.getName() + ": Miss!\n");
        } else if (damage == 40) {
            battleLog.append(enemy.getName() + " usa " + attack.getName() + ": Critical Hit! " + damage + " danni!\n");
        } else {
            battleLog.append(enemy.getName() + " usa " + attack.getName() + ": " + damage + " danni!\n");
        }

        //applica il danno al giocatore
        player.takeDamage(damage);
        updatePlayerHealthBar();

        //controlla se il giocatore è stato sconfitto
        if (player.getCurrentHealth() <= 0) {
            handlePlayerDefeated();
        } else {
            //riabilita i pulsanti per il turno del giocatore
            enableActionButtons(true);
        }
    }

    private void updatePlayerHealthBar() {
        playerHealthBar.setValue(player.getCurrentHealth());
        playerHealthBar.setString(player.getCurrentHealth() + "/" + player.getMaxHealth());

        //cambia il colore della barra in base alla salute rimanente
        if (player.getCurrentHealth() < player.getMaxHealth() * 0.25) {
            playerHealthBar.setUI(new ModernProgressBarUI(new Color(200, 50, 50))); //rosso per salute bassa
        } else if (player.getCurrentHealth() < player.getMaxHealth() * 0.5) {
            playerHealthBar.setUI(new ModernProgressBarUI(new Color(200, 150, 0))); //giallo per salute media
        } else {
            playerHealthBar.setUI(new ModernProgressBarUI(new Color(0, 200, 100))); //verde per salute alta
        }
    }

    private void updateEnemyHealthBar() {
        enemyHealthBar.setValue(enemy.getCurrentHealth());
        enemyHealthBar.setString(enemy.getCurrentHealth() + "/" + enemy.getMaxHealth());

        //cambia il colore della barra in base alla salute rimanente
        if (enemy.getCurrentHealth() < enemy.getMaxHealth() * 0.25) {
            enemyHealthBar.setUI(new ModernProgressBarUI(new Color(100, 0, 0))); //rosso scuro per salute bassa
        } else if (enemy.getCurrentHealth() < enemy.getMaxHealth() * 0.5) {
            enemyHealthBar.setUI(new ModernProgressBarUI(new Color(150, 0, 0))); //rosso medio per salute media
        } else {
            enemyHealthBar.setUI(new ModernProgressBarUI(new Color(200, 50, 50))); //rosso per salute alta
        }
    }

    private void enableActionButtons(boolean enable) {
        for (Component component : actionPanel.getComponents()) {
            component.setEnabled(enable);
        }
    }

    private void handleEnemyDefeated() {
        battleLog.append("Hai sconfitto " + enemy.getName() + "!\n");

        if (isBossFight) {

            //reset dei mondi completati dopo aver sconfitto il boss
            gameFrame.getGameData().setCompletedWorlds(new ArrayList<>());
            gameFrame.getGameData().saveData();

            //vittoria finale
            Timer timer = new Timer(2000, e -> gameFrame.showVictoryScreen());
            timer.setRepeats(false);
            timer.start();
        } else {
            //aggiungi il mondo completato
            if (!completedWorlds.contains(worldName)) {
                completedWorlds.add(worldName);
                gameFrame.getGameData().setCompletedWorlds(completedWorlds);
            }

            //controlla se tutti i mondi sono stati completati
            if (completedWorlds.size() >= 3) {
                battleLog.append("Hai completato tutti i mondi! È ora di affrontare il boss finale!\n");

                Timer timer = new Timer(3000, e -> gameFrame.showBattleScreen("Boss"));
                timer.setRepeats(false);
                timer.start();
            } else {
                Timer timer = new Timer(2000, e -> gameFrame.showWorldMapScreen());
                timer.setRepeats(false);
                timer.start();
            }

            //aggiorna i dati di gioco
            gameFrame.getGameData().saveData();

            //aggiorna il contatore della benedizione se attiva
            if (gameFrame.getGameData().isBlessingActive()) {
                int gamesLeft = gameFrame.getGameData().getBlessingGamesLeft() - 1;
                gameFrame.getGameData().setBlessingGamesLeft(gamesLeft);

                if (gamesLeft <= 0) {
                    gameFrame.getGameData().setBlessingActive(false);
                }

                gameFrame.getGameData().saveData();
            }
        }
    }

    private void handlePlayerDefeated() {
        battleLog.append("Sei stato sconfitto da " + enemy.getName() + "!\n");

        //disabilita i pulsanti
        enableActionButtons(false);

        //mostra la schermata di sconfitta dopo un breve ritardo
        Timer timer = new Timer(2000, e -> gameFrame.showDefeatScreen());
        timer.setRepeats(false);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        //disegna lo sfondo
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

            //effetto particelle/stelle
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
                getWidth() / 2, 0, new Color(ACCENT_COLOR.getRed(), ACCENT_COLOR.getGreen(),
                ACCENT_COLOR.getBlue(), 100),
                getWidth() / 2, 200, new Color(0, 0, 0, 0)
        );
        g2.setPaint(topGlow);
        g2.fillRect(0, 0, getWidth(), 200);

        g2.dispose();
    }
}