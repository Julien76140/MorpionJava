/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package morpion.morpionjava;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author julie
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    private int pion = 0;
    private int joueur = 0;
    private int colone = -1;
    private int ligne = -1;
    private URL pathImage;
    private Clip clip;
    private Clip clip_2;
    private Boolean draw;
    private int stack = 0;
    private Boolean image;
    private Boolean victoire;
    private int tour = 0;
    private final int[][] map = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};//Terrain de jeu
    private String NameJ1;
    private String NameJ2;
    private  URL pion1Url;
    private  URL pion2Url;
    private  URL songURL;
    private  URL songEffectMenuURL=getClass().getResource("effectMenu.wav");
    private  URL pionEffectMenuURL=getClass().getResource("pion.wav");
    private  URL victoryEffectMenuURL=getClass().getResource("victory.wav");

    public MainFrame() {

        initComponents();
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        this.setSize((int) screen.getWidth(), (int) screen.getHeight());
        this.setTitle("Morpion en Java !");

        jButton1.setPreferredSize(new Dimension(300, 50));

        jPanel5.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        jPanel3.setBorder(new EmptyBorder(-4, -4, -4, -4));
        jPanel4.setBorder(new EmptyBorder(100, 100, 100, 100));
        jPanel1.setBackground(Color.BLACK);
        jPanel2.setBackground(Color.BLACK);
        jPanel3.setBackground(Color.WHITE);
        jPanel4.setBackground(Color.BLACK);
        jPanel5.setBackground(Color.BLACK);

        jPanel2.setBorder(new EmptyBorder(100, 100, 100, 100));

        jButton12.setText("Recommencez la partie !");
        jButton13.setText("Retourner au menu");

    }

    private void gameInit() {
    	
    	
        pion1Url = getClass().getResource("x.png");
        pion2Url = getClass().getResource("o.png");
        songURL = getClass().getResource("song.wav");

        if(clip !=null){ //reset musique
        clip.close();
        clip.stop();
        }
        PlayMusic(songURL);

        tour = 0;
        draw = false;

        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        jButton7.setEnabled(true);
        jButton8.setEnabled(true);
        jButton9.setEnabled(true);
        jButton10.setEnabled(true);
        jButton11.setEnabled(true);

        for (int k = 0; k <= 2; k++) {
            for (int j = 0; j <= 2; j++) {
                map[k][j] = 0;//initialise la map

            }
        }

            Random number = new Random();
            joueur = number.nextInt(2) + 1;//Joueur qui commence de maniere aletoire

            jLabel3.setText("");
            //reset text button
            jButton3.setText("");
            jButton3.setText("");
            jButton4.setText("");
            jButton5.setText("");
            jButton6.setText("");
            jButton7.setText("");
            jButton8.setText("");
            jButton9.setText("");
            jButton10.setText("");
            jButton11.setText("");
            jLabel1.setText("");
            jLabel2.setText("");
            jLabel3.setText("");
            jLabel4.setText("");

            //reset jbutton
            jButton3.setIcon(null);
            jButton4.setIcon(null);
            jButton5.setIcon(null);
            jButton6.setIcon(null);
            jButton7.setIcon(null);
            jButton8.setIcon(null);
            jButton9.setIcon(null);
            jButton10.setIcon(null);
            jButton11.setIcon(null);

            pion = getJoueur();// valeur du pion en fonction du joueur

            getNamePlayer();

            jLabel1.setText("Au tour du joueur " + getNamePlayer());

        

    }

    private String getNamePlayer() {

        return switch (joueur % 2) {
            case 0 ->
                NameJ2;
            case 1 ->
                NameJ1;
            default ->
                null;
        };
    }

    private int getJoueur() {

        return switch (joueur % 2) {
            case 0 ->
                2;
            case 1 ->
                1;
            default ->
                0;
        };

    }

    void PlayMusic(URL musicLocation) {

        try {
            AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicLocation);
            clip = AudioSystem.getClip();
            clip.open(audioInput);
            clip.start();
            clip.loop(Clip.LOOP_CONTINUOUSLY);

        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {

            System.err.println(e);
        }
    }
    
    void PlaySoundEffect(URL effect) {

        try {
            AudioInputStream audioEffectInput = AudioSystem.getAudioInputStream(effect);
            clip_2 = AudioSystem.getClip();
            clip_2.open(audioEffectInput);
            clip_2.start();
        } catch (IOException | LineUnavailableException | UnsupportedAudioFileException e) {

            System.err.println(e);
        }
    }
    
    private int JoueurSuivant() {

        joueur++;
        PlaySoundEffect(pionEffectMenuURL);

        return joueur;
    }
    private void game(int ligne, int colone) {

        tour++;
        jLabel4.setText("Nombre de tour : " + tour);

        victoire = false;

        if (map[ligne][colone] == 0) {

            jLabel2.setText("");
            getJoueur();
            pion = getJoueur();
            map[ligne][colone] = pion;

            if (pion == 1) {

                pathImage = pion1Url;

            } else if (pion == 2) {

                pathImage =pion2Url;

            }

            image = true;
            stack = 0;
            win();
            JoueurSuivant();

        } else if (map[ligne][colone] != 0) {
            jLabel2.setText("Cette case est pleine choisissez en une autre !");
            image = false;

        }

        getNamePlayer();

        jLabel1.setText("Au tour du joueur " + getNamePlayer());

        System.out.println("\nEtat du terrain :\n[" + map[0][0] + "|" + map[0][1] + "|" + map[0][2] + "]");
        System.out.println("[" + map[1][0] + "|" + map[1][1] + "|" + map[1][2] + "]");
        System.out.println("[" + map[2][0] + "|" + map[2][1] + "|" + map[2][2] + "]\n");

    }

    private void win() { //condition de victoire

        for (int k = 0; k <= 2; k++) {
            if (map[k][0] == pion && map[k][1] == pion && map[k][2] == pion) {

                System.out.println("Victoire colone");
                victoire = true;

            }

        }
        for (int k = 0; k <= 2; k++) {
            if (map[0][k] == pion && map[1][k] == pion && map[2][k] == pion) {

                System.out.println("Victoire ligne");
                victoire = true;

            }

        }

        if (map[0][0] == pion && map[1][1] == pion && map[2][2] == pion) {

            System.out.println("Victoire diagonale");

            victoire = true;

        }
        
        if (map[0][2] == pion && map[1][1] == pion && map[2][0] == pion) {

            System.out.println("Victoire diagonale");

            victoire = true;

        }

        for (int y = 0; y <= 2; y++) {
            for (int t = 0; t <= 2; t++) {

                if (map[y][t] != 0 && !victoire) {

                    stack++;

                    if (stack == 9) {

                        jLabel3.setText("Égalité !");
                        draw = true;

                    }
                }

            }
        }

        if (victoire) {
            jLabel3.setText("Victoire du joueur " + getJoueur());
            clip.close();
            clip.stop();
            PlayMusic(victoryEffectMenuURL);

        }
        if (victoire || draw) {

            jButton3.setEnabled(false);
            jButton4.setEnabled(false);
            jButton5.setEnabled(false);
            jButton6.setEnabled(false);
            jButton7.setEnabled(false);
            jButton8.setEnabled(false);
            jButton9.setEnabled(false);
            jButton10.setEnabled(false);
            jButton11.setEnabled(false);

        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel1 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jButton14 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButton1.setBackground(new java.awt.Color(204, 0, 204));
        jButton1.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setText("Jouer");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 100);
        jPanel1.add(jButton1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridLayout(1, 0));

        jPanel3.setLayout(new java.awt.GridLayout(3, 2, 15, 15));

        jButton3.setBackground(new java.awt.Color(0, 0, 0));
        jButton3.setText("jButton3");
        jButton3.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        jButton3.setBorderPainted(false);
        jButton3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jButton3.setDefaultCapable(false);
        jButton3.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton3);

        jButton4.setBackground(new java.awt.Color(0, 0, 0));
        jButton4.setText("jButton4");
        jButton4.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton4);

        jButton5.setBackground(new java.awt.Color(0, 0, 0));
        jButton5.setText("jButton5");
        jButton5.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);

        jButton6.setBackground(new java.awt.Color(0, 0, 0));
        jButton6.setText("jButton6");
        jButton6.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6);

        jButton7.setBackground(new java.awt.Color(0, 0, 0));
        jButton7.setText("jButton7");
        jButton7.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton7);

        jButton8.setBackground(new java.awt.Color(0, 0, 0));
        jButton8.setText("jButton8");
        jButton8.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8);

        jButton9.setBackground(new java.awt.Color(0, 0, 0));
        jButton9.setText("jButton9");
        jButton9.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton9);

        jButton10.setBackground(new java.awt.Color(0, 0, 0));
        jButton10.setText("jButton10");
        jButton10.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10);

        jButton11.setBackground(new java.awt.Color(0, 0, 0));
        jButton11.setText("jButton11");
        jButton11.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton11);

        jPanel2.add(jPanel3);

        jPanel4.setLayout(new java.awt.GridLayout(3, 2, 15, 15));

        jLabel1.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("jLabel1");
        jPanel4.add(jLabel1);

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Calibri Light", 1, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 91, 91));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel2.setText("jLabel2");
        jPanel4.add(jLabel2);

        jLabel4.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(51, 153, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("jLabel4");
        jPanel4.add(jLabel4);

        jLabel3.setBackground(new java.awt.Color(255, 255, 255));
        jLabel3.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(102, 255, 153));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("jLabel3");
        jPanel4.add(jLabel3);

        jButton12.setBackground(new java.awt.Color(70, 102, 255));
        jButton12.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jButton12.setForeground(new java.awt.Color(255, 255, 255));
        jButton12.setText("jButton12");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton12);

        jButton13.setBackground(new java.awt.Color(255, 91, 91));
        jButton13.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("jButton13");
        jButton13.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jButton13.setMaximumSize(new java.awt.Dimension(135, 10));
        jButton13.setMinimumSize(new java.awt.Dimension(135, 10));
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton13);

        jPanel2.add(jPanel4);

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setLayout(new java.awt.GridLayout(5, 1, 0, 30));

        jLabel5.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Choisissez les noms des joueurs :");
        jPanel5.add(jLabel5);

        jTextField1.setBackground(new java.awt.Color(0, 0, 0));
        jTextField1.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(255, 255, 255));
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField1.setText("Joueur 1");
        jTextField1.setCaretColor(new java.awt.Color(255, 255, 255));
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField1);

        jTextField2.setBackground(new java.awt.Color(0, 0, 0));
        jTextField2.setFont(new java.awt.Font("Calibri Light", 1, 18)); // NOI18N
        jTextField2.setForeground(new java.awt.Color(255, 255, 255));
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        jTextField2.setText("Joueur 2");
        jTextField2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField2ActionPerformed(evt);
            }
        });
        jPanel5.add(jTextField2);

        jButton14.setBackground(new java.awt.Color(255, 153, 51));
        jButton14.setFont(new java.awt.Font("Calibri Light", 1, 24)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Commencer la partie !");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton14);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1113, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        jPanel5.setVisible(true);
        jButton1.setVisible(false);
        jPanel5.repaint();
        jPanel5.revalidate();
        PlaySoundEffect(songEffectMenuURL);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ligne = 0;
        colone = 0;

        game(ligne, colone);

        if (image) {

            jButton3.setIcon(new ImageIcon(pathImage));
        }

    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        ligne = 0;
        colone = 1;

        game(ligne, colone);

        if (image) {
            jButton4.setIcon(new ImageIcon(pathImage));
        }

    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        ligne = 0;
        colone = 2;

        game(ligne, colone);
        if (image) {
            jButton5.setIcon(new ImageIcon(pathImage));
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        ligne = 1;
        colone = 0;

        game(ligne, colone);
        if (image) {
            jButton6.setIcon(new ImageIcon(pathImage));
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        ligne = 1;
        colone = 1;

        game(ligne, colone);
        if (image) {
            jButton7.setIcon(new ImageIcon(pathImage));
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        ligne = 1;
        colone = 2;

        game(ligne, colone);
        if (image) {
            jButton8.setIcon(new ImageIcon(pathImage));
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        ligne = 2;
        colone = 0;

        game(ligne, colone);
        if (image) {
            jButton9.setIcon(new ImageIcon(pathImage));
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        ligne = 2;
        colone = 1;

        game(ligne, colone);
        if (image) {
            jButton10.setIcon(new ImageIcon(pathImage));
        }
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        ligne = 2;
        colone = 2;

        game(ligne, colone);

        if (image) {
            jButton11.setIcon(new ImageIcon(pathImage));
        }

    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        gameInit();
        PlaySoundEffect(songEffectMenuURL);

    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        jPanel2.setVisible(false);
        jPanel1.setVisible(true);
        jButton1.setVisible(true);
        jPanel1.repaint();
        jPanel1.revalidate();
        clip.close();
        clip.stop();
        PlaySoundEffect(songEffectMenuURL);

    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        // TODO add your handling code here:
        PlaySoundEffect(songEffectMenuURL);
        jPanel5.setVisible(false);
        jPanel1.setVisible(false);
        jPanel2.setVisible(true);
        jPanel3.setVisible(true);
        jPanel4.setVisible(true);
        NameJ1 = jTextField1.getText();
        NameJ2 = jTextField2.getText();
        gameInit();
        jPanel2.repaint();
        jPanel2.revalidate();
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {

            MainFrame main = new MainFrame();
            main.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables
}
