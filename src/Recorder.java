import java.io.*;
import java.util.Vector;

/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-11  16:25:57
 * 该类用于记录信息,和文件交互
 */
public class Recorder {
    //定义一个变量记录得分
    private static int score;

    //定义IO对象
    private static BufferedReader br;
    private static BufferedWriter bw;

    //定义一个文件路径, 记录临时存档，用于恢复上局游戏
    private static String recordFile = "src/myRecord.txt";

    //定义一个文件路径, 记录历史分数排行榜
    private static String scoreFile = "src/myScore.txt";

    //定义一个Vector，指向 MyPanel 对象的 敌人坦克
    private static Vector<Enemy> enemies = null;

    //定义一个hero，指向 Hero对象
    private static Hero hero = null;

    //定义一个Node 的Vector,用于保存敌人的信息
    private static Vector<Node> nodes = new Vector<>();

    //定义一个集合 储存从文件读取的 分数记录
    public static Vector<String> scores = new Vector<>();

    public static void setHero(Hero hero) {
        Recorder.hero = hero;
    }

    public static void setEnemies(Vector<Enemy> enemies) {
        Recorder.enemies = enemies;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Recorder.score = score;
    }

    //当游戏退出时，将 score 保存到 src\\tankgame02\\myRecord.txt"
    //保存敌人坦克的坐标和方向
    public static void keepRecord() {
        //如果 hero 没有存活不进行保存
        if (hero.isLive) {
            try {
                bw = new BufferedWriter(new FileWriter(recordFile));
                bw.write(score + "\r\n");
                //遍历敌人坦克的Vector， 然后根据情况保存
                for (int i = 0; i < enemies.size(); i++) {
                    //取出坦克
                    Enemy enemy = enemies.get(i);
                    if (enemy.isLive) {
                        //保存该enemy的信息
                        String record = enemy.getX() + " " + enemy.getY() + " " + enemy.getDirect();
                        //写入到文件
                        bw.write(record + "\r\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            //hero 没有存活后，清空上次保存的记录
            try {
                bw = new BufferedWriter(new FileWriter(recordFile));
                bw.write("");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (bw != null) {
                        bw.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //编写方法，保存历史分数排行榜
    public static void keepScore() {
        //需要 hero 没有存活时 才进行保存
        if (!hero.isLive) {
            try {
                bw = new BufferedWriter(new FileWriter(scoreFile, true));
                bw.write(score + "\r\n");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (bw != null) {
                    try {
                        bw.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    //用于读取 recordFile 文件，恢复相关信息
    public static Vector<Node> getNodesAndScore() {
        try {
            br = new BufferedReader(new FileReader(recordFile));
            //读取分数
            score = Integer.parseInt(br.readLine());

            //循环读取文件，生成 nodes 集合
            String line = "";
            while ((line = br.readLine()) != null) {
                String[] xyd = line.split(" ");
                Node node = new Node(Integer.parseInt(xyd[0]),
                        Integer.parseInt(xyd[1]),
                        Integer.parseInt(xyd[2]));
                nodes.add(node); //把node 放入集合
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //关闭流
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return nodes;
    }

    //编写方法 读取 历史分数排行榜 并按降序排序返回
    public static void getScoreRecord() {
        scores.clear();
        try {
            br = new BufferedReader(new FileReader(scoreFile));
            String lien = "";

            while ((lien = br.readLine()) != null) {
                scores.add(lien);
            }

            for (int i = 0; i < scores.size(); i++) {
                System.out.println(scores.get(i));
            }

            //匿名内部类排序
            scores.sort((o1, o2) -> {
                if (null != o1 && null != o2) {
                    int a = Integer.parseInt(o1);
                    int b = Integer.parseInt(o2);
                    return b - a;
                }
                return 0;
            });

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //当hero击毁敌方坦克就要增加 score
    public static void addScore() {
        Recorder.score++;
    }
}
