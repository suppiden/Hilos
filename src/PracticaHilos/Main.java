package PracticaHilos;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Rectangle2D;



class Marco extends JFrame {
    public Marco(){
        setVisible(true);
        setBounds(600,0,600,600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.BLUE);
        Lamina lamina = new Lamina();
        add(lamina);
        Thread t = new Thread(lamina);
        t.start();
    }
}

class Lamina extends JPanel implements Runnable {
    private int x = 300;
    private int y = 300;
    private int dirX = 0;
    private int dirY = 0;

    @Override
    public void run() {
        while (true) {
            x += dirX;
            y += dirY;
            repaint();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        Rectangle2D rect = new Rectangle2D.Double(x, y, 30, 30);
        g2.setPaint(Color.BLUE);
        g2.fill(rect);
    }

    public Lamina() {
        setBindings();
    }

    private void setBindings() {
        int condition = JComponent.WHEN_IN_FOCUSED_WINDOW;
        InputMap inputMap = getInputMap(condition);
        ActionMap actionMap = getActionMap();

        String vkLeft = "VK_LEFT";
        String vkRight = "VK_RIGHT";
        String vkUp = "VK_UP";
        String vkDown = "VK_DOWN";

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), vkLeft);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), vkRight);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), vkUp);
        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), vkDown);

        actionMap.put(vkLeft, new DirAction(-10, 0));
        actionMap.put(vkRight, new DirAction(10, 0));
        actionMap.put(vkUp, new DirAction(0, -10));
        actionMap.put(vkDown, new DirAction(0, 10));
    }

    private class DirAction extends AbstractAction {
        private int deltaX;
        private int deltaY;

        public DirAction(int deltaX, int deltaY) {
            this.deltaX = deltaX;
            this.deltaY = deltaY;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            dirX = deltaX;
            dirY = deltaY;
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Marco marco = new Marco();
    }
}

