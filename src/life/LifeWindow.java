package life;

import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

public class LifeWindow extends javax.swing.JFrame {

    private static final String HELPMESSAGE = "Use the slider to modify the speed. Minimal speed will stop the game.\nKeybinds:\nh - show help (this message)\nc - clear all life\nnumbers - move slider to certain percentages\nl - load interesting patterns\ns - save pattern\nr - fill the board with cells randomly\nd - set different dimensions";
    private static final String PATTERNDIRECTORY = "/patterns/";

    public LifeWindow() {
        initComponents();
        this.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                lifeWidget1.requestFocusInWindow();
            }
        });
        try {
            lifeWidget1.setBoard(PatternsIO.load(getPatternsDirectory() + "help"));
        } catch (IOException | ClassNotFoundException ex) {
            try {
                URL url = new URL("https://github.com/wolftxt/Game-Of-Life/raw/refs/heads/master/patterns/help");
                InputStream in = url.openStream();
                File out = new File(getPatternsDirectory(), "help");
                if (!out.exists()) {
                    out.mkdirs();
                }
                Files.copy(in, out.toPath(), StandardCopyOption.REPLACE_EXISTING);
                lifeWidget1.setBoard(PatternsIO.load(getPatternsDirectory() + "help"));
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "The patterns/help file is missing, you probably deleted it (ノಠ益ಠ)ノ", "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void help() {
        JOptionPane.showMessageDialog(null, HELPMESSAGE, "Welcome to Conway's Game of Life", JOptionPane.INFORMATION_MESSAGE);
    }

    private String getPatternsDirectory() {
        File codeDirectory = new File(LifeWindow.class.getProtectionDomain().getCodeSource().getLocation().getFile());
        return codeDirectory.getParent() + PATTERNDIRECTORY;
    }

    private void showCoolPatterns() {
        File folder = new File(getPatternsDirectory());
        String[] options = folder.list();
        JComboBox<String> comboBox = new JComboBox<>(options);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select a pattern:"));
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Choose an Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                lifeWidget1.setBoard(PatternsIO.load(folder.getAbsolutePath() + comboBox.getSelectedItem()));
            } catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Unable to load pattern", "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void savePattern() {
        JTextField input = new JTextField(10);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Name your pattern, name must be at least 3 characters long:"));
        panel.add(input);

        int result = JOptionPane.showConfirmDialog(null, panel, "Save Pattern", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION && input.getText().length() >= 3) {
            if (PatternsIO.save(lifeWidget1.getBoard(), getPatternsDirectory() + input.getText())) {
                JOptionPane.showMessageDialog(null, "Unable to save pattern", "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void generateRandomCells() {
        JSpinner input = new JSpinner(new SpinnerNumberModel(10, 1, 100, 1));
        JPanel panel = new JPanel();
        panel.add(new JLabel("Input a percentage:"));
        panel.add(input);

        int result = JOptionPane.showConfirmDialog(this, panel, "Generate Random Cells", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            lifeWidget1.generateRandomCells((int) input.getValue());
        }
    }

    private void setDimensions() {
        JSpinner input1 = new JSpinner(new SpinnerNumberModel(50, 10, 500, 1));
        JSpinner input2 = new JSpinner(new SpinnerNumberModel(50, 10, 500, 1));
        JPanel panel = new JPanel();
        panel.add(new JLabel("Width:"));
        panel.add(input1);
        panel.add(new JLabel("Height:"));
        panel.add(input2);

        int result = JOptionPane.showConfirmDialog(this, panel, "Input Dimensions", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            lifeWidget1.setBoard((int) input1.getValue(), (int) input2.getValue());
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lifeWidget1 = new life.LifeWidget();
        jSlider1 = new javax.swing.JSlider();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        lifeWidget1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                lifeWidget1MouseReleased(evt);
            }
        });
        lifeWidget1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                lifeWidget1KeyPressed(evt);
            }
        });

        jSlider1.setValue(0);
        jSlider1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jSlider1StateChanged(evt);
            }
        });
        jSlider1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jSlider1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lifeWidget1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jSlider1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jSlider1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(lifeWidget1, javax.swing.GroupLayout.DEFAULT_SIZE, 778, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSlider1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jSlider1StateChanged
        int value = jSlider1.getValue();
        long cycleTime = (100 - value) * 10;
        if (cycleTime == 1000) {
            cycleTime = Long.MAX_VALUE;
        }
        lifeWidget1.setCycleTime(cycleTime);
    }//GEN-LAST:event_jSlider1StateChanged

    private void lifeWidget1MouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lifeWidget1MouseReleased
        lifeWidget1.changeCell(evt.getX(), evt.getY());
    }//GEN-LAST:event_lifeWidget1MouseReleased

    private void lifeWidget1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_lifeWidget1KeyPressed
        if (Character.isDigit(evt.getKeyChar())) {
            int pressedNum = Character.getNumericValue(evt.getKeyChar());
            jSlider1.setValue(pressedNum * 10);
        }
        switch (evt.getKeyCode()) {
            case KeyEvent.VK_H -> {
                help();
            }
            case KeyEvent.VK_C -> {
                lifeWidget1.clearLife();
            }
            case KeyEvent.VK_L -> {
                showCoolPatterns();
            }
            case KeyEvent.VK_S -> {
                savePattern();
            }
            case KeyEvent.VK_R -> {
                generateRandomCells();
            }
            case KeyEvent.VK_D -> {
                setDimensions();
            }
        }
    }//GEN-LAST:event_lifeWidget1KeyPressed

    private void jSlider1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jSlider1KeyPressed
        lifeWidget1KeyPressed(evt);
    }//GEN-LAST:event_jSlider1KeyPressed

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
            java.util.logging.Logger.getLogger(LifeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LifeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LifeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LifeWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LifeWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSlider jSlider1;
    private life.LifeWidget lifeWidget1;
    // End of variables declaration//GEN-END:variables
}
