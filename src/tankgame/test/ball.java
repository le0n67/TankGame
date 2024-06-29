package tankgame.test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Date：2023/4/19  16:07
 * Description：事件处理机制实例
 *
 * @author Leon
 * @version 1.0
 */

public class ball extends JFrame {
    MyBallPanel mp = null;
    public static void main(String[] args) {
        new ball();
    }

    public ball() {
        mp = new MyBallPanel();
        this.add(mp);
        this.addKeyListener(mp);//监听到面板上的事件
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}

class MyBallPanel extends JPanel implements KeyListener {
    int x=10;
    int y=10;
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(x, y, 30, 30);
        g.fillOval(x+8,y+5,5,5);
        g.fillOval(x+16,y+5,5,5);
        g.drawLine(x+14,y+12,x+14,y+20);
        g.drawLine(x+10,y+25,x+18,y+25);
        g.drawString("李东星",x+30,y+30);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            y--;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            y++;
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            x--;
        }else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            x++;
        }

        this.repaint();

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
