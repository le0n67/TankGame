package tankgame;

/**
 * Date：2023/4/19  21:03
 * Description：爆炸效果的实现
 *
 * @author Leon
 * @version 1.0
 */

public class Bomb {
    int x, y;//炸弹坐标
    int life = 9;//生命周期
    boolean isLive = true;

    public void lifeDown() {
        if (life > 0) {
            life--;
        } else {
            isLive = false;
        }
    }

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
