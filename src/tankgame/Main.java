package tankgame;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * Date：2023/4/19  15:43
 * Description：绘图框架（主方法）
 *
 * @author Leon
 * @version 1.0
 */

public class Main extends JFrame {
    Mypanel mp = null; //定义一个面板
    Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        new Main();
    }

    public Main() {

        System.out.println("请输入选择： 1.新游戏 2.继续游戏");
        String choice = scanner.next();
        mp = new Mypanel(choice);//创建画板
        new Thread(mp).start();
        this.add(mp);
        this.addKeyListener(mp);
        this.setSize(1306, 789);//画板大小
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//关闭
        this.setVisible(true);//可见

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.storeRecord();
                System.exit(0);
            }
        });
    }


}
