package tankgame;

import java.util.Vector;

/**
 * Date：2023/4/19  15:38
 * Description：坦克初始
 *
 * @author Leon
 * @version 1.0
 */

public class MyTank extends Tank {
    public MyTank(int x, int y) {
        super(x, y);
    }

    Shot shot = new Shot(0, 0, 0);
    Vector<Shot> myShots = new Vector<>();

    public void toShot() {
        if (myShots.size() < 5) {//限制同时存在子弹数目
            shot = shot.creatShot(shot, getDirect(), getX(), getY());
            //添加子弹到集合
            myShots.add(shot);
            //启动射击线程
            new Thread(shot).start();
        }
    }

}
