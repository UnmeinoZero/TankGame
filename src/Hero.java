import java.util.Vector;

/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-03  16:14:48
 * 自己的坦克
 */
public class Hero extends Tank {
    //创建子弹集合
    Vector<Shot> shots = new Vector<>();

    Vector<Enemy> enemies = new Vector<>();

    public Hero(int x, int y, int speed) {
        super(x, y, speed);
    }

    //提供一个方法，可以将 MyPanel 的成员 Vector<Enemy> enemies = new Vector<>();
    //设置到 Enemy 的成员 enemies
    public void setEnemies(Vector<Enemy> enemies) {
        this.enemies = enemies;
    }

    //射击方法
    public void shotEnemy() {
        //子弹数量大于等于 一定数量 ，hero 没有存活时，无法继续发射子弹
        if (shots.size() >= 3 || !(isLive)) {
            return;
        }

        //定义一个 Shot 对象, 表示一个射击
        Shot shot = null;

        //根据当前hero对象的方向和位置，创建Shot对象
        switch (getDirect()) {//根据hero的位置，确定子弹初始位置，然后启动线程，自动移动
            case 0:
                shot = new Shot(getX() + 28, getY(), 0, 30);
                break;
            case 1:
                shot = new Shot(getX() + 60, getY() + 28, 1, 30);
                break;
            case 2:
                shot = new Shot(getX() + 28, getY() + 60, 2, 30);
                break;
            case 3:
                shot = new Shot(getX(), getY() + 28, 3, 30);
                break;
        }
        //把子弹加入集合
        shots.add(shot);
        //启动Shot线程
        new Thread(shot).start();
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
                break;
            case 1://方向为右时，敌人坦克的右上角和右下角进入hero范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
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
                break;
            case 2://方向为下时，敌人坦克的左下角和右下角进入hero范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
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
                break;
            case 3://方向为左时，敌人坦克的左上角和左下角进入hero范围时，判断重叠
                for (int i = 0; i < enemies.size(); i++) {
                    //遍历集合取出坦克
                    Enemy enemy = enemies.get(i);
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
                break;
        }
        return false;
    }
}
