package tankgame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.Path;
import java.util.Vector;

/**
 * Date：2023/4/19  15:38
 * Description：坦克大战绘图区域
 *
 * @author Leon
 * @version 1.0
 */

//将panel实现线程，实现子弹不断重绘
public class Mypanel extends JPanel implements KeyListener, Runnable {
    MyTank myTank = null;
    Vector<EnemyTank> enemyTanks = new Vector<>();
    //定义存放node对象的Vector，用于存放数据
    Vector<Node> nodes = new Vector<>();

    //定义一个炸弹集合
    Vector<Bomb> bombs = new Vector<>();
    int enemyTankSize = 8;//初始化定义敌军数量

    //定义三张炸弹图片用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    public Mypanel(String key) {
        myTank = new MyTank(450, 650);//初始化坦克位置

        myTank.setSpeed(10);//速度设置

        //将Mypanel的敌方坦克集合设置给Record
        Recorder.setEnemyTanks(enemyTanks);
        switch (key) {
            case "1"://新游戏
                nodes.clear();
                //初始化敌方坦克
                for (int i = 0; i < enemyTankSize; i++) {
                    //创建敌方坦克
                    EnemyTank enemyTank = new EnemyTank(100 * (i + 1), 0);
                    //将敌方坦克加入敌方坦克类中的集合
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置类别
                    enemyTank.setDirect(2);
                    enemyTank.setSpeed(5);
                    //启动坦克
                    new Thread(enemyTank).start();
                    //添加到集合中
                    enemyTanks.add(enemyTank);
                }

                //初始化图片对象
                //初始化图片对象
                image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
                image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
                image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
                new AePlayWave("src\\Music.wav").start();
                break;
            case "2"://继续游戏
                nodes = Recorder.getNodes();
                //初始化敌方坦克
                for (int i = 0; i < nodes.size(); i++) {
                    //创建敌方坦克
                    Node node = nodes.get(i);
                    EnemyTank enemyTank = new EnemyTank(node.getX(), node.getY());
                    //将敌方坦克加入敌方坦克类中的集合
                    enemyTank.setEnemyTanks(enemyTanks);
                    //设置类别
                    enemyTank.setDirect(node.getDirect());
                    enemyTank.setSpeed(5);
                    //启动坦克
                    new Thread(enemyTank).start();
                    //添加到集合中
                    enemyTanks.add(enemyTank);

                }
                //初始化图片对象
                //初始化图片对象
                image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_1.gif"));
                image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_2.gif"));
                image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb_3.gif"));
                new AePlayWave("src\\Music.wav").start();
                break;
            default:
                System.out.println("输入有误");
                break;
        }

    }

    @Override
    public void paint(Graphics g) {

        super.paint(g);

        g.fillRect(0, 0, 1000, 750);//画板背景色

        //显示游戏信息
        showInfo(g);

        if (myTank != null && myTank.isLive) {
            drawTank(myTank.getX(), myTank.getY(), g, myTank.getDirect(), 0);//画出自己的坦克
        }

        for (int i = 0; i < myTank.myShots.size(); i++) {
            Shot shot = myTank.myShots.get(i);
            if (shot != null && shot.isExist) {//画出自己的子弹
                g.setColor(Color.WHITE);
                g.fillOval(shot.x, shot.y, 3, 3);
            } else {//如果已不存在，则从集合删除
                myTank.myShots.remove(shot);
            }
        }

        for (int i = 0; i < bombs.size(); i++) {
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前对象的生命画出图片
            if (bomb.life > 6) {
                g.drawImage(image1, bomb.x, bomb.y, 60, 60, this);
            } else if (bomb.life > 3) {
                g.drawImage(image2, bomb.x, bomb.y, 60, 60, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 60, 60, this);
            }
            //炸弹生命值降低
            bomb.lifeDown();
            //如果bomb.life =0删除
            if (bomb.life == 0) {
                bombs.remove(bomb);
            }
        }

        for (int i = 0; i < enemyTanks.size(); i++) {//画出敌方坦克和子弹
            //取出坦克
            EnemyTank enemyTank = enemyTanks.get(i);

            if (enemyTank.isLive) {

                drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);

                for (int j = 0; j < enemyTank.enemyShots.size(); j++) {
                    //取出子弹
                    Shot shot = enemyTank.enemyShots.get(j);
                    if (shot != null && shot.isExist) {//画出子弹
                        g.setColor(Color.pink);
                        g.fillOval(shot.x, shot.y, 3, 3);
                    } else {
                        enemyTank.enemyShots.remove(shot);
                    }
                }

            } else {
                enemyTanks.remove(enemyTank);
                Recorder.addKillNum();
            }
        }


    }

    //显示信息
    public void showInfo(Graphics g) {
        g.setColor(Color.black);
        Font font = new Font("宋体", Font.BOLD, 25);
        g.setFont(font);

        g.drawString("X " + Recorder.getKllNum() + "", 1090, 110);
        g.drawString("Score：", 1020, 40);

        drawTank(1020, 70, g, 2, 1);


    }

    //绘制坦克

    /**
     * @param x      坦克横坐标
     * @param y      纵坐标
     * @param g      画笔
     * @param direct 坦克方向(0123,上右下左)
     * @param type   坦克类型（敌我）
     */

    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        switch (type) {//根据坦克类型设定面板数据
            case 0://己方
                g.setColor(Color.green);
                break;
            case 1://敌方
                g.setColor(Color.RED);
                break;
        }

        switch (direct) {//根据坦克方向绘制坦克
            case 0://向上
                g.fill3DRect(x, y, 10, 60, false);//左轮
                g.fill3DRect(x + 30, y, 10, 60, false);//右轮
                g.fill3DRect(x + 10, y + 10, 20, 40, false);//主舱
                g.fillOval(x + 10, y + 20, 20, 20);//炮台
                g.drawLine(x + 20, y, x + 20, y + 30);//炮管
                break;
            case 1: //向右
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x + 60, y + 20);
                break;
            case 2: //向下
                g.fill3DRect(x, y, 10, 60, false);
                g.fill3DRect(x + 30, y, 10, 60, false);
                g.fill3DRect(x + 10, y + 10, 20, 40, false);
                g.fillOval(x + 10, y + 20, 20, 20);
                g.drawLine(x + 20, y + 30, x + 20, y + 60);
                break;
            case 3: //向左
                g.fill3DRect(x, y, 60, 10, false);
                g.fill3DRect(x, y + 30, 60, 10, false);
                g.fill3DRect(x + 10, y + 10, 40, 20, false);
                g.fillOval(x + 20, y + 10, 20, 20);
                g.drawLine(x + 30, y + 20, x, y + 20);
                break;
        }

    }

    public void hit() {
        //判断是否只因中
        for (int i = 0; i < myTank.myShots.size(); i++) {
            //取出子弹
            Shot shot = myTank.myShots.get(i);
            if (shot.isExist) {
                for (int j = 0; j < enemyTanks.size(); j++) {
                    EnemyTank enemyTank = enemyTanks.get(j);
                    hitTank(shot, enemyTank);
                }
            }
        }

        for (int i = 0; i < enemyTanks.size(); i++) {
            //取出敌人坦克
            EnemyTank enemyTank = enemyTanks.get(i);
            //遍历enemyTank 对象的所有子弹
            for (int j = 0; j < enemyTank.enemyShots.size(); j++) {
                //取出子弹
                Shot shot = enemyTank.enemyShots.get(j);
                //判断 shot 是否击中我的坦克
                if (myTank.isLive && shot.isExist) {
                    hitTank(shot, myTank);
                }
            }
        }


    }


    //判定我方子弹是否击中敌方
    public void hitTank(Shot s, Tank tank) {
        //判断s 击中坦克
        switch (tank.getDirect()) {
            case 0:
            case 2:
                if (s.x > tank.getX() && s.x < tank.getX() + 40
                        && s.y > tank.getY() && s.y < tank.getY() + 60) {
                    s.isExist = false;
                    tank.isLive = false;
                    //创建Bomb对象，加入到bombs集合中
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;

            case 1:
            case 3:
                if (s.x > tank.getX() && s.x < tank.getX() + 60
                        && s.y > tank.getY() && s.y < tank.getY() + 40) {
                    s.isExist = false;
                    tank.isLive = false;
                    Bomb bomb = new Bomb(tank.getX(), tank.getY());
                    bombs.add(bomb);
                }
                break;
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

        //行动监控
        if (e.getKeyCode() == KeyEvent.VK_W) {
            myTank.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            myTank.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            myTank.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            myTank.moveRight();
        }

        this.repaint();//重绘刷新！

        //射击监控 (子弹线程结束，子弹对象不一定为空)

        if (e.getKeyCode() == KeyEvent.VK_SPACE && myTank.isLive) {
            myTank.toShot();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void run() {//自行重绘

        while (true) {

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            hit();

            this.repaint();
        }
    }
}
