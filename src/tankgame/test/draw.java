package tankgame.test;

import javax.swing.*;
import java.awt.*;

/**
 * Date：2023/4/18  21:04
 * Description：TODO
 *
 * @author Leon
 * @version 1.0
 */

public class draw extends JFrame {

    private Mypanel mp = null;

    public static void main(String[] args) {
        draw draw = new draw();
    }

    public draw() {//构造器
        //初始化面板
        mp = new Mypanel();
        //把面板放入到窗口
        this.add(mp);
        //设置窗口大小
        this.setSize(400, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

}

//定义面板Mypanel
class Mypanel extends JPanel {

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        g.drawOval(10, 10, 100, 100);
    }
}