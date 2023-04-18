import java.util.Vector;

/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-03  17:54:31
 * 敌人坦克
 */
public class Enemy extends Tank implements Runnable {
    //创建敌人坦克子弹合集
    Vector<Shot> shots = new Vector<>();

    Vector<Enemy> enemies = new Vector<>();

    Hero hero = null;

    public Enemy(int x, int y, int speed) {
        super(x, y, speed);
    }

    //提供一个方法，可以将 MyPanel 的成员 Vector<Enemy> enemies = new Vector<>();
    //设置到 Enemy 的成员 enemies
    public void setEnemies(Vector<Enemy> enemies) {
        this.enemies = enemies;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }


    //编写方法，判断当前坦克， 是否和其他坦克重叠或者碰撞
    public boolean isTouchEnemy() {
        //体积修正
        int a = 60;
        //通过方向确认两个点来判断是否重叠或碰撞
        switch (getDirect()) {
            case 0://方向为上时，敌人坦克的左上角和右上角进入其他敌人坦克范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己判断
                    if (enemy != this) {
                        //左上角
                        if (this.getX() >= enemy.getX() &&
                                this.getX() <= enemy.getX() + a &&
                                this.getY() >= enemy.getY() &&
                                this.getY() <= enemy.getY() + a) {
                            return true;
                        }
                        //右上角
                        if (this.getX() + a >= enemy.getX() &&
                                this.getX() + a <= enemy.getX() + a &&
                                this.getY() >= enemy.getY() &&
                                this.getY() <= enemy.getY() + a) {
                            return true;
                        }
                    }
                }
                break;
            case 1://方向为右时，敌人坦克的右上角和右下角进入hero范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己判断
                    if (enemy != this) {
                        //右上角
                        if (this.getX() + a >= enemy.getX() &&
                                this.getX() + a <= enemy.getX() + a &&
                                this.getY() >= enemy.getY() &&
                                this.getY() <= enemy.getY() + a) {
                            return true;
                        }
                        //右下角
                        if (this.getX() + a >= enemy.getX() &&
                                this.getX() + a <= enemy.getX() + a &&
                                this.getY() + a >= enemy.getY() &&
                                this.getY() + a <= enemy.getY() + a) {
                            return true;
                        }
                    }
                }
                break;
            case 2://方向为下时，敌人坦克的左下角和右下角进入hero范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己判断
                    if (enemy != this) {
                        //左下角
                        if (this.getX() >= enemy.getX() &&
                                this.getX() <= enemy.getX() + a &&
                                this.getY() + a >= enemy.getY() &&
                                this.getY() + a <= enemy.getY() + a) {
                            return true;
                        }
                        //右下角
                        if (this.getX() + a >= enemy.getX() &&
                                this.getX() + a <= enemy.getX() + a &&
                                this.getY() + a >= enemy.getY() &&
                                this.getY() + a <= enemy.getY() + a) {
                            return true;
                        }
                    }
                }
                break;
            case 3://方向为左时，敌人坦克的左上角和左下角进入hero范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
                    //不和自己判断
                    if (enemy != this) {
                        //左上角
                        if (this.getX() >= enemy.getX() &&
                                this.getX() <= enemy.getX() + a &&
                                this.getY() >= enemy.getY() &&
                                this.getY() <= enemy.getY() + a) {
                            return true;
                        }
                        //左下角
                        if (this.getX() >= enemy.getX() &&
                                this.getX() <= enemy.getX() + a &&
                                this.getY() + a >= enemy.getY() &&
                                this.getY() + a <= enemy.getY() + a) {
                            return true;
                        }
                    }
                }
                break;
        }
        return false;
    }


    //编写方法，判断当前坦克， 是否和hero重叠或者碰撞
    public boolean isTouchHero() {
        //体积修正
        int a = 60;
        //通过方向确认两个点来判断是否重叠或碰撞
        switch (getDirect()) {
            case 0://方向为上时，敌人坦克的左上角和右上角进入hero范围时，判断重叠
                //左上角
                if (this.getX() >= hero.getX() &&
                        this.getX() <= hero.getX() + a &&
                        this.getY() >= hero.getY() &&
                        this.getY() <= hero.getY() + a) {
                    return true;
                }
                //右上角
                if (this.getX() + a >= hero.getX() &&
                        this.getX() + a <= hero.getX() + a &&
                        this.getY() >= hero.getY() &&
                        this.getY() <= hero.getY() + a) {
                    return true;
                }
                break;
            case 1://方向为右时，敌人坦克的右上角和右下角进入hero范围时，判断重叠
                //右上角
                if (this.getX() + a >= hero.getX() &&
                        this.getX() + a <= hero.getX() + a &&
                        this.getY() >= hero.getY() &&
                        this.getY() <= hero.getY() + a) {
                    return true;
                }
                //右下角
                if (this.getX() + a >= hero.getX() &&
                        this.getX() + a <= hero.getX() + a &&
                        this.getY() + a >= hero.getY() &&
                        this.getY() + a <= hero.getY() + a) {
                    return true;
                }
                break;
            case 2://方向为下时，敌人坦克的左下角和右下角进入hero范围时，判断重叠
                //左下角
                if (this.getX() >= hero.getX() &&
                        this.getX() <= hero.getX() + a &&
                        this.getY() + a >= hero.getY() &&
                        this.getY() + a <= hero.getY() + a) {
                    return true;
                }
                //右下角
                if (this.getX() + a >= hero.getX() &&
                        this.getX() + a <= hero.getX() + a &&
                        this.getY() + a >= hero.getY() &&
                        this.getY() + a <= hero.getY() + a) {
                    return true;
                }
                break;
            case 3://方向为左时，敌人坦克的左上角和左下角进入hero范围时，判断重叠
                //左上角
                if (this.getX() >= hero.getX() &&
                        this.getX() <= hero.getX() + a &&
                        this.getY() >= hero.getY() &&
                        this.getY() <= hero.getY() + a) {
                    return true;
                }
                //左下角
                if (this.getX() >= hero.getX() &&
                        this.getX() <= hero.getX() + a &&
                        this.getY() + a >= hero.getY() &&
                        this.getY() + a <= hero.getY() + a) {
                    return true;
                }
                break;
        }
        return false;
    }

    //敌人坦克线程
    @Override
    public void run() {
        while (true) {
            //判断敌人坦克是否存活
            if (isLive) {
                //生成一个随机数，使坦克发射子弹时间间隔随机(1 ~ 5)
                int random = (int) (1 + Math.random() * 5);

                //判断如果敌人坦克还活着 shots小于等于 一定数量 ，随机数为 1或者2 创建子弹放入集合，并启动
                if (isLive && shots.size() <= 10 && (random == 1 || random == 2 || random == 3)) {

                    //定义一个 Shot 对象, 表示一个射击
                    Shot shot = null;

                    //根据当前敌人坦克对象的方向和位置，创建Shot对象
                    switch (getDirect()) {//根据敌人坦克的位置，确定子弹初始位置，然后启动线程，自动移动
                        case 0:
                            shot = new Shot(getX() + 28, getY(), 0, 10);
                            break;
                        case 1:
                            shot = new Shot(getX() + 60, getY() + 28, 1, 10);
                            break;
                        case 2:
                            shot = new Shot(getX() + 28, getY() + 60, 2, 10);
                            break;
                        case 3:
                            shot = new Shot(getX(), getY() + 28, 3, 10);
                            break;
                    }

                    //把子弹加入集合
                    shots.add(shot);

                    //启动Shot线程
                    new Thread(shot).start();
                }


                //根据坦克的方向来继续移动
                switch (getDirect()) {
                    case 0://向上移动
                        //增加随机数判定，使敌人坦克每次移动时，移动随机次数(10 ~ 50 次)
                        for (int i = 0; i < (int) (10 + Math.random() * (50 - 10 + 1)); i++) {
                            if (getDirect() == 0 && getY() >= 20 && !isTouchEnemy() && !isTouchHero()) {
                                moveUp();
                            } else {
                                setDirect(0);
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case 1://向右移动
                        for (int i = 0; i < (int) (10 + Math.random() * (50 - 10 + 1)); i++) {
                            if (getDirect() == 1 && getX() <= 1150 && !isTouchEnemy() && !isTouchHero()) {
                                moveRight();
                            } else {
                                setDirect(1);
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case 2://向下移动
                        for (int i = 0; i < (int) (10 + Math.random() * (50 - 10 + 1)); i++) {
                            if (getDirect() == 2 && getY() <= 600 && !isTouchEnemy() && !isTouchHero()) {
                                moveDown();
                            } else {
                                setDirect(2);
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;

                    case 3://向左移动
                        for (int i = 0; i < (int) (10 + Math.random() * (50 - 10 + 1)); i++) {
                            if (getDirect() == 3 && getX() >= 20 && !isTouchEnemy() && !isTouchHero()) {
                                moveLeft();
                            } else {
                                setDirect(3);
                            }
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        break;
                }

                //休眠
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                //然后随机改变方向 0-3
                setDirect((int) (Math.random() * 4));

            } else {
                //敌人坦克没有存活时，结束线程
                break;
            }
        }
    }
}
