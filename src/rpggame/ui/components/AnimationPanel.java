package rpggame.ui.components;

import javax.swing.*;
import java.awt.*;

public class AnimationPanel extends JPanel {
    private Image playerImage;
    private Image enemyImage;
    
    private boolean isPlayerAttacking = false;
    private boolean isEnemyAttacking = false;
    private Color attackColor = Color.WHITE;
    
    private int playerX = 100;
    private int playerY = 200;
    private int enemyX = 500;
    private int enemyY = 200;
    
    private Timer animationTimer;
    private int animationFrame = 0;
    
    public AnimationPanel(Image playerImage, Image enemyImage) {
        this.playerImage = playerImage;
        this.enemyImage = enemyImage;
        
        setPreferredSize(new Dimension(800, 300));
        setOpaque(false);
        
        //inizializza il timer per l'animazione
        animationTimer = new Timer(50, e -> {
            animationFrame++;

            if (animationFrame > 10) {
                animationFrame = 0;
                isPlayerAttacking = false;
                isEnemyAttacking = false;
                animationTimer.stop();
            }

            repaint();
        });
    }
    
    public void playAttackAnimation(boolean isPlayer, Color color) {
        if (isPlayer) {
            isPlayerAttacking = true;
            isEnemyAttacking = false;
        } else {
            isPlayerAttacking = false;
            isEnemyAttacking = true;
        }
        
        this.attackColor = color;
        animationFrame = 0;
        animationTimer.start();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        //disegna il giocatore
        if (playerImage != null) {
            int drawX = playerX;
            int drawY = playerY;
            
            //animazione di attacco del giocatore
            if (isPlayerAttacking) {
                if (animationFrame < 5) {
                    drawX += animationFrame * 10;
                } else {
                    drawX += (10 - animationFrame) * 10;
                }
                
                //disegna l'effetto dell'attacco
                drawAttackEffect(g, drawX + 150, drawY, true);
            }
            
            g.drawImage(playerImage, drawX, drawY, 100, 100, this);
        } else {
            g.setColor(Color.BLUE);
            g.fillRect(playerX, playerY, 50, 100);
        }
        
        //disegna il nemico
        if (enemyImage != null) {
            int drawX = enemyX;
            int drawY = enemyY;
            
            //animazione di attacco del nemico
            if (isEnemyAttacking) {
                if (animationFrame < 5) {
                    drawX -= animationFrame * 10;
                } else {
                    drawX -= (10 - animationFrame) * 10;
                }
                
                //disegna l'effetto dell'attacco
                drawAttackEffect(g, drawX - 50, drawY, false);
            }
            
            g.drawImage(enemyImage, drawX, drawY, 100, 100, this);
        } else {
            g.setColor(Color.RED);
            g.fillRect(enemyX, enemyY, 50, 100);
        }
    }
    
    private void drawAttackEffect(Graphics g, int x, int y, boolean isPlayerAttack) {
        Graphics2D g2d = (Graphics2D) g.create();
        
        g2d.setColor(attackColor);
        
        //disegna un effetto di attacco in stile pixel art
        int size = 20 + animationFrame * 5;
        
        if (isPlayerAttack) {
            for (int i = 0; i < 5; i++) {
                int offsetX = (int) (Math.cos(Math.toRadians(i * 72)) * size);
                int offsetY = (int) (Math.sin(Math.toRadians(i * 72)) * size);
                g2d.drawLine(x, y, x + offsetX, y + offsetY);
            }
        } else {
            for (int i = 0; i < 5; i++) {
                int offsetX = (int) (Math.cos(Math.toRadians(i * 72 + 180)) * size);
                int offsetY = (int) (Math.sin(Math.toRadians(i * 72 + 180)) * size);
                g2d.drawLine(x, y, x + offsetX, y + offsetY);
            }
        }
        
        g2d.dispose();
    }
}