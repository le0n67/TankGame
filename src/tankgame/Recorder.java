package tankgame;

import javax.imageio.IIOException;
import java.io.*;
import java.util.Vector;

/**
 * Date：2023/4/21  16:33
 * Description：Game information
 *
 * @author Leon
 * @version 1.0
 */

public class Recorder {
    public static int killNum = 0;

    //定义IO对象,用于写入数据
    public static BufferedWriter bufferedWriter = null;
    public static BufferedReader bufferedReader = null;
    private static final String recordFile = "src\\myRecord.txt";
    public static Vector<EnemyTank> enemyTanks = null;

    //定义Node Vector，用于保存敌人信息
    private static Vector<Node> nodes = new Vector<>();

    public static void setEnemyTanks(Vector<EnemyTank> enemyTanks) {
        Recorder.enemyTanks = enemyTanks;
    }

    public static int getKllNum() {
        return killNum;
    }

    //读取RecordFile恢复相关信息 //启动游戏时启动
    public static Vector<Node> getNodes() {
        try {
            bufferedReader = new BufferedReader(new FileReader(recordFile));
            killNum = Integer.parseInt(bufferedReader.readLine());//读取分数
            //读取文件生成nodes
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]), Integer.parseInt(xyd[1]), Integer.parseInt(xyd[2]));
                nodes.add(node);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return nodes;
    }

    //当游戏退出时保存数据到文件中
    //当游戏退出时记录敌人坦克的坐标和方向
    public static void storeRecord() {

        try {
            bufferedWriter = new BufferedWriter(new FileWriter(recordFile));
            bufferedWriter.write(killNum + "\r\n");

            //遍历敌方坦克集合，根据情况保存
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出坦克保存
                EnemyTank enemyTank = enemyTanks.get(i);
                if (enemyTank.isLive) {//保险判断
                    //保存信息
                    String record = enemyTank.getX() + " " + enemyTank.getY() + " " + enemyTank.getDirect();
                    //写入到文件
                    bufferedWriter.write(record + "\r\n");
                }
            }

            //可添加己方坦克数据
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //我方坦克得分
    public static void addKillNum() {
        Recorder.killNum++;
    }

}
