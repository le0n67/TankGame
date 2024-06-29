package tankgame;

/**
 * Date：2023/4/19  17:41
 * Description：子弹
 *
 * @author Leon
 * @version 1.0
 */

public class Shot implements Runnable {

    int x;  //子弹坐标
    int y;
    int direct; //子弹方向
    int speed = 10; //子弹速度
    boolean isExist = true;//子弹是否存在

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //根据方向改变x,y
            switch (direct) {
                case 0://上
                    y -= speed;
                    break;
                case 1://右
                    x += speed;
                    break;
                case 2://下
                    y += speed;
                    break;
                case 3://左
                    x -= speed;
                    break;
            }


            if (!(x >= 0 && x <= 1000 && y >= 0 && y <= 750 && isExist)) {//子弹触界或碰撞到坦克后isExist=false
                isExist = false;
                break;
            }
        }

    }

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    public Shot creatShot(Shot shot, int direct, int x, int y) {

        switch (direct) {//确定方向
            case 0:
                shot = new Shot(x + 20, y, direct);
                break;
            case 1:
                shot = new Shot(x + 60, y + 20, direct);
                break;
            case 2:
                shot = new Shot(x + 20, y + 60, direct);
                break;
            case 3:
                shot = new Shot(x, y + 20, direct);
                break;
        }

        return shot;

    }


}
