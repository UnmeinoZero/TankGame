import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-03  16:16:30
 * 坦克大战的绘图区域
 */
public class MyPanel extends JPanel implements KeyListener, Runnable {
    //定义我的坦克
    Hero hero = null;

    //定义一个Vector，放敌人坦克
    Vector<Enemy> enemies = new Vector<>();

    //定义一个Vector，放爆炸
    //当子弹击中坦克时，加入一个Bomb对象bombs
    Vector<Bomb> bombs = new Vector<>();

    //定义一个存放 Node 对象的 Vector，用于恢复敌人坦克
    Vector<Node> nodes = new Vector<>();

    //定义三张图片，用于显示爆炸效果显示
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;

    //定义一个标识，让一个方法在线程中只能被执行一次
    boolean flag = true;

    String key = "";

    int startFlag = 1;

    boolean keyFlag = true;

    //构造器
    public MyPanel() {

    }

    //初始化方法
    public void start() {
        if (startFlag == 2) {
            //如果希望继续上局游戏，再进行文件读取
            if (key.equals("2")) {
                try {
                    nodes = Recorder.getNodesAndScore();
                } catch (Exception e) {
                    System.out.println("没有上局游戏记录，请开始新游戏");
                    key = "1";
                    start();
                }
            }
            //将MyPanel 对象的 enemies 设置给 Reorder
            Recorder.setEnemies(enemies);

            hero = new Hero(600, 600, 10); //初始化自己的坦克

            //将Hero对象 设置给 Reorder
            Recorder.setHero(hero);

            //判断是否继续上局游戏来生成敌人坦克
            switch (key) {
                case "1":
                    //初始化敌人坦克
                    newEnemy(5);
                    break;
                case "2":
                    //恢复上局坦克
                    //初始化敌人的坦克
                    for (Node node : nodes) {
                        //取出一个Node
                        //根据 node 的数据 恢复坦克
                        Enemy enemy = new Enemy(node.getX(), node.getY(), 5);
                        enemy.setDirect(node.getDirect());

                        enemy.setEnemies(enemies);
                        enemy.setHero(hero);
                        hero.setEnemies(enemies);
                        //启动敌人线程
                        new Thread(enemy).start();

                        //加入集合
                        enemies.add(enemy);
                    }
                    break;
            }

            startFlag = 3;

        }

        //初始化图片
        image1 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(Panel.class.getResource("/bomb3.png"));
    }


    /**
     * 创建敌人坦克方法
     *
     * @param num 坦克数量
     */
    public void newEnemy(int num) {

        //初始化敌人的坦克
        for (int i = 0; i < num; i++) {
            //生成两个个随机数，使敌人坦克诞生的位置随机
            int randomX = (int) (100 + Math.random() * (1000 - 100 + 1));
            int randomY = (int) (100 + Math.random() * (600 - 100 + 1));
            //定义一个敌人
            Enemy enemy = null;

            //根据随机数，生成不同位置的坦克
            if (randomX > 100 && randomX < 250) {
                enemy = new Enemy(randomX, 10, 5);
                //生成在上方，初始化默认方向为下
                enemy.setDirect(2);

            } else if (randomX > 250 && randomX < 500) {
                enemy = new Enemy(1100, randomY - 150, 5);
                //生成在右方，初始化默认方向为左
                enemy.setDirect(3);

            } else if (randomX > 500 && randomX < 750) {
                enemy = new Enemy(randomX, 450, 5);
                //生成在下方，初始化默认方向为上
                enemy.setDirect(0);

            } else {
                enemy = new Enemy(10, randomY - 150, 5);
                //生成在左方，初始化默认方向为右
                enemy.setDirect(1);

            }
            enemy.setEnemies(enemies);
            enemy.setHero(hero);
            hero.setEnemies(enemies);
            //启动敌人线程
            new Thread(enemy).start();

            //加入集合
            enemies.add(enemy);
        }
    }

    //编写方法，显示我方击毁敌方坦克的信息
    public void showInfo(Graphics g) {
        //画出玩家的总成绩
        g.setColor(Color.yellow);
        Font font = new Font("微弱雅黑", Font.BOLD, 25);
        g.setFont(font);

        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth("分数：" + Recorder.getScore())) / 2;
        g.drawString("分数：" + Recorder.getScore(), x, 30);

    }

    //画板方法，显示图像
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        g.fillRect(0, 0, 1280, 720);//填充矩形背景，默认黑色

        //画出开始
        if (startFlag != 3) {
            drawGameStart(g);
        }

        //画出分数记录
        if (startFlag == 3) {
            showInfo(g);
        }

        if (null != hero) {
            //判断 hero 是否存活，存活时画出坦克
            if (hero.isLive) {
                //画出自己的坦克
                drawTank(hero.getX(), hero.getY(), g, hero.getDirect(), 0);
            } else {
                //如果 hero 没有存活，显示 GameOver 和 历史分数记录排行榜
                // hero 没有存活时， 存入分数，并读取历史分数排行榜
                if (flag) {
                    Recorder.keepScore();
                    Recorder.getScoreRecord();
                    flag = false;
                }

                drawGameOver(g);
                drawScoreRecord(g);
            }


            //画出子弹，遍历 hero 子弹集合
            for (int i = 0; i < hero.shots.size(); i++) {
                Shot shot = hero.shots.get(i);
                if (shot.isLive) {
                    drawShot(shot.x, shot.y, g, Color.WHITE);
                } else {
                    hero.shots.remove(shot);
                }
            }
        }


        //画出敌人的坦克，遍历敌人坦克集合
        for (int i = 0; i < enemies.size(); i++) {
            //取出坦克
            Enemy enemy = enemies.get(i);
            //判断坦克是否存活，存活时再画出坦克
            if (enemy.isLive) {
                //画出敌人坦克
                drawTank(enemy.getX(), enemy.getY(), g, enemy.getDirect(), 1);
            } else {
                //坦克没有存活时，从集合中删除
                enemies.remove(enemy);
                //敌人坦克死亡时，增加分数
                Recorder.addScore();
            }

            //遍历坦克的子弹集合
            for (int j = 0; j < enemy.shots.size(); j++) {
                //取出子弹
                Shot shot = enemy.shots.get(j);
                //判断子弹是否存活
                if (shot.isLive) {
                    //绘制子弹
                    drawShot(shot.x, shot.y, g, Color.RED);
                } else {
                    //子弹没有存活时，从集合中删除
                    enemy.shots.remove(shot);
                }
            }
        }


        //如果bombs 集合中有对象，就画出
        for (int i = 0; i < bombs.size(); i++) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //取出炸弹
            Bomb bomb = bombs.get(i);
            //根据当前这个bomb对象的life值去画出对应的图片
            if (bomb.life > 10) {
                g.drawImage(image1, bomb.x, bomb.y, 70, 70, this);
            } else if (bomb.life > 5) {
                g.drawImage(image2, bomb.x, bomb.y, 70, 70, this);
            } else {
                g.drawImage(image3, bomb.x, bomb.y, 70, 70, this);
            }
            //让爆炸生命减少
            bomb.lifeDown();
            //如果bomb life为 0， 就从bombs集合中删除
            if (bomb.life <= 0) {
                bombs.remove(bomb);
            }
        }
    }

    //编写方法，画出坦克

    /**
     * @param x      坦克的x坐标
     * @param y      坦克的y坐标
     * @param g      画笔
     * @param direct 坦克的方向（上下左右）
     * @param type   坦克的类型
     */
    public void drawTank(int x, int y, Graphics g, int direct, int type) {

        //根据不同类型的坦克设置不同的颜色
        switch (type) {
            case 0: //hero
                g.setColor(Color.cyan);
                break;
            case 1: //敌人的坦克01
                g.setColor(Color.white);
                break;
            case 2: //敌人的坦克02
                g.setColor(Color.red);
                break;
            case 3: //敌人的坦克03
                g.setColor(Color.YELLOW);
                break;
        }

        //根据不同的方向，绘制对应的坦克
        switch (direct) {
            case 0://表示向上
                //履带
                g.fill3DRect(x, y, 18, 60, false);
                g.fill3DRect(x + 42, y, 18, 60, false);
                //主体
                g.fill3DRect(x + 18, y + 10, 24, 40, false);
                //驾驶舱
                g.fillOval(x + 17, y + 22, 24, 24);
                //炮管
                g.fill3DRect(x + 29, y, 2, 24, false);
                break;
            case 1://表示向右
                //履带
                g.fill3DRect(x, y, 60, 18, false);
                g.fill3DRect(x, y + 42, 60, 18, false);
                //主体
                g.fill3DRect(x + 10, y + 18, 40, 24, false);
                //驾驶舱
                g.fillOval(x + 14, y + 17, 24, 24);
                //炮管
                g.fill3DRect(x + 36, y + 29, 24, 2, false);
                break;
            case 2:///表示向下
                //履带
                g.fill3DRect(x, y, 18, 60, false);
                g.fill3DRect(x + 42, y, 18, 60, false);
                //主体
                g.fill3DRect(x + 18, y + 10, 24, 40, false);
                //驾驶舱
                g.fillOval(x + 17, y + 14, 24, 24);
                //炮管
                g.fill3DRect(x + 29, y + 36, 2, 24, false);
                break;
            case 3://表示向左
                //履带
                g.fill3DRect(x, y, 60, 18, false);
                g.fill3DRect(x, y + 42, 60, 18, false);
                //主体
                g.fill3DRect(x + 10, y + 18, 40, 24, false);
                //驾驶舱
                g.fillOval(x + 22, y + 17, 24, 24);
                //炮管
                g.fill3DRect(x, y + 29, 24, 2, false);
                break;
            default:
                System.out.println("暂时没有处理");
        }
    }


    /**
     * 绘制子弹
     *
     * @param x     子弹X
     * @param y     子弹Y
     * @param g     画笔
     * @param color 子弹颜色
     */
    public void drawShot(int x, int y, Graphics g, Color color) {
        g.setColor(color);
        g.fillRect(x, y, 4, 4);
    }

    //绘制 GameOver
    public void drawGameOver(Graphics g) {
        g.setColor(Color.red);
        Font font = new Font("unispace", Font.BOLD, 60);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth("Game Over")) / 2;
        g.drawString("Game Over", x, 150);
        Font font2 = new Font("微软雅黑", Font.BOLD, 30);
        g.setFont(font2);
        fm = g.getFontMetrics();
        x = (getWidth() - fm.stringWidth("按下\"R\"重新开始游戏")) / 2;
        g.drawString("按下\"R\"重新开始游戏", x, 600);
    }


    //绘制 GameStart
    public void drawGameStart(Graphics g) {

        g.setColor(Color.red);
        Font font = new Font("unispace", Font.BOLD, 100);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int x = (getWidth() - fm.stringWidth("Tank Game")) / 2;
        g.drawString("Tank Game", x, 280);

        g.setColor(Color.green);
        font = new Font("unispace", Font.BOLD, 30);
        g.setFont(font);

        fm = g.getFontMetrics();
        x = (getWidth() - fm.stringWidth("New Game")) / 2;
        g.drawString("1.New Game", x, 500);
        g.drawString("2.Continue", x, 575);
    }

    //绘制历史分数记录排行榜
    public void drawScoreRecord(Graphics g) {
        //排行榜背景
        g.setColor(Color.gray);
        g.fill3DRect(520, 200, 200, 340, false);

        //设置排行榜字体
        g.setColor(Color.WHITE);
        Font font = new Font("unispace", Font.BOLD, 20);
        g.setFont(font);

        //定义第一行 坐标
        int x = 550;
        int y = 230;
        //循环遍历前10个 画出
        for (int i = 0; i < Recorder.scores.size(); i++) {
            if (i == 9) {
                String score = Recorder.scores.get(i);
                g.drawString(i + 1 + ".        " + score, x, y);
                y += 31;
            } else if (i < 10) {
                String score = Recorder.scores.get(i);
                g.drawString(i + 1 + ".         " + score, x, y);
                y += 31;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    //监听键盘按键方法
    //处理 wdsa空格 键按下处理
    @Override
    public void keyPressed(KeyEvent e) {
        if (null != hero) {
            //判断 W 键 向上走，D 键 向右走，S 键 向下走，A 键 向左走
            // j 键 发射子弹
            if (e.getKeyCode() == KeyEvent.VK_W) {//KeyEvent.VK_W表示 W 键
                //如果向上走时，方向不是上，则先改变方向
                //判断x,y的最大最小值，使其只能在窗口内移动
                if (hero.getDirect() == 0 && hero.getY() >= 20 && !hero.isTouchEnemy()) {
                    //调用moveUp()，坦克向上走
                    hero.moveUp();
                } else {
                    //向上走时，坦克方向朝上
                    hero.setDirect(0);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_D) {
                if (hero.getDirect() == 1 && hero.getX() <= 1150 && !hero.isTouchEnemy()) {
                    hero.moveRight();
                } else {
                    hero.setDirect(1);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_S) {
                if (hero.getDirect() == 2 && hero.getY() <= 600 && !hero.isTouchEnemy()) {
                    hero.moveDown();
                } else {
                    hero.setDirect(2);
                }
            } else if (e.getKeyCode() == KeyEvent.VK_A) {
                if (hero.getDirect() == 3 && hero.getX() >= 20 && !hero.isTouchEnemy()) {
                    hero.moveLeft();
                } else {
                    hero.setDirect(3);
                }
            }
            //如果用户按下”空格“，就会发射子弹
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                //调用发射子弹方法
                hero.shotEnemy();
            }

            //当我方tank死亡时，按下 R 重启游戏
            if (!hero.isLive && e.getKeyCode() == KeyEvent.VK_R) {
                System.out.println("重启游戏");
                hero = null;  //删除原始对象
                enemies.clear(); //删除敌人对象
                Recorder.setScore(0); //分数清零
                flag = true;
                hero = new Hero(600, 600, 10); //初始化自己的坦克
            }
        }

        //初始化游戏方法
        if (keyFlag) {
            if (e.getKeyCode() == KeyEvent.VK_1) {
                System.out.println("按下1, 新游戏");
                key = "1";
                keyFlag = false;
                startFlag = 2;
            } else if (e.getKeyCode() == KeyEvent.VK_2) {
                System.out.println("按下2, 继续游戏");
                key = "2";
                keyFlag = false;
                startFlag = 2;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    /**
     * 编写方法，判断子弹是否击中坦克
     *
     * @param shot 子弹对象
     * @param tank 坦克对象
     */
    public void hitTank(Shot shot, Tank tank) {
        if (shot.x > tank.getX() && shot.x < tank.getX() + 60 &&
                shot.y > tank.getY() && shot.y < tank.getY() + 60) {
            //如果子弹进入坦克范围，坦克死亡，子弹死亡
            shot.isLive = false;
            tank.isLive = false;
            //创建一个Bomb对象，加入bombs集合
            Bomb bomb = new Bomb(tank.getX(), tank.getY());
            bombs.add(bomb);
        }
    }


    //判断 Hero 是否中了敌人坦克
    public void hitEnemy() {
        //从子弹集合取出子弹
        for (int i = 0; i < hero.shots.size(); i++) {
            Shot shot = hero.shots.get(i);
            //从敌人集合中取出敌人坦克
            for (Enemy enemy : enemies) {
                //判断子弹是否存活
                if (shot.isLive && enemy.isLive) {
                    //调用 hitTank方法判断
                    hitTank(shot, enemy);
                }
            }
        }
    }


    //判定敌方子弹是否击中 hero
    public void hitHero() {
        //遍历所有敌人坦克
        for (Enemy enemy : enemies) {
            //取出坦克
            //遍历enemy 对象的所有子弹
            for (int j = 0; j < enemy.shots.size(); j++) {
                //取出子弹
                Shot shot = enemy.shots.get(j);
                //判断 shot 是否击中 hero
                if (hero.isLive && shot.isLive) {
                    hitTank(shot, hero);
                }
            }
        }
    }

    //线程
    @Override
    public void run() {//每个一定时间，自动重绘区域
        while (true) {

            start();

            if (startFlag == 3) {
                //生成一个随机数，使敌人坦克诞生时机随机
                int random = (int) (1 + Math.random() * 1000);

                //随机数等于 1 ，敌人坦克数量小于等于 2 时，生成坦克
                if (random == 1 && enemies.size() <= 10) {
                    newEnemy(1);
                }
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //判断我方子弹是否击中敌方坦克
                hitEnemy();
                //判断敌人坦克是否击中我方坦克
                hitHero();
                //重绘
                this.repaint();
            }
        }
    }
}
