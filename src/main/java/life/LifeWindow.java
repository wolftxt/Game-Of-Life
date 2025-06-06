package life;

import com.formdev.flatlaf.FlatDarkLaf;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import javax.swing.*;

public class LifeWindow extends javax.swing.JFrame {

    private static final String HELPMESSAGE = "Use the slider to modify the speed. Minimal speed will stop the game.\nKeybinds:\nh - show help (this message)\nc - clear all life\nnumbers - move slider to certain percentages\nl - load interesting patterns\ns - save pattern\nr - fill the board with cells randomly\nd - set different dimensions";

    public LifeWindow() {
        initComponents();
        this.addWindowFocusListener(new WindowAdapter() {
            public void windowGainedFocus(WindowEvent e) {
                lifeWidget1.requestFocusInWindow();
            }
        });
        Thread.ofVirtual().start(() -> {
            try {
                lifeWidget1.setBoard(PatternsIO.load("help"));
            } catch (IOException | ClassNotFoundException | URISyntaxException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "The patterns/help file is missing, you probably deleted it (ノಠ益ಠ)ノ", "Something went wrong", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void help() {
        JOptionPane.showMessageDialog(null, HELPMESSAGE, "Welcome to Conway's Game of Life", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showCoolPatterns() {
        File folder = PatternsIO.getPatternsDirectory();
        String[] options = folder.list();
        JComboBox<String> comboBox = new JComboBox<>(options);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Select a pattern:"));
        panel.add(comboBox);

        int result = JOptionPane.showConfirmDialog(null, panel, "Choose an Option", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                lifeWidget1.setBoard(PatternsIO.load((String) comboBox.getSelectedItem()));
            } catch (IOException | ClassNotFoundException | URISyntaxException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "The patterns/" + comboBox.getSelectedItem() + " file is missing, you probably deleted it (ノಠ益ಠ)ノ", "Something went wrong", JOptionPane.ERROR_MESSAGE);
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
            if (PatternsIO.save(lifeWidget1.getBoard(), input.getText())) {
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
        FlatDarkLaf.setup();
        FlatDarkLaf.setUseNativeWindowDecorations(true);
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
