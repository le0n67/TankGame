package tankgame;

/**
 * Date：2023/4/19  15:37
 * Description：坦克面板数值
 *
 * @author Leon
 * @version 1.0
 */

public class Tank {

    //坦克横纵坐标
    private int x;
    private int y;
    private int direct;

    private int speed = 1;

    boolean isLive = true;

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //移动方法
    public void moveUp() {
        direct = 0;
        if (y > 0) {
            y -= speed;
        }
    }

    public void moveRight() {
        direct = 1;
        if (x + 60 < 1000) {
            x += speed;
        }
    }

    public void moveDown() {
        direct = 2;

        if (y + 60 < 750) {
            y += speed;
        }
    }

    public void moveLeft() {
        direct = 3;

        if (x > 0) {
            x -= speed;
        }
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Tank(int x, int y) { //初始化位置构造器
        this.x = x;
        this.y = y;
    }
}
