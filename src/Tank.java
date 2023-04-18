/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-03  16:13:31
 * 坦克类
 */
public class Tank {
    private int x;//坦克的横坐标
    private int y;//坦克的纵坐标
    private int direct;//坦克方向 0123 上右下左
    private int speed;//移动速度
    boolean isLive = true;//是否存活

    //上右下左移动方法
    public void moveUp(){
        y -= getSpeed();
    }
    public void moveRight(){
        x += getSpeed();
    }
    public void moveDown(){
        y += getSpeed();
    }
    public void moveLeft(){
        x -= getSpeed();
    }

    public Tank(int x, int y, int speed) {
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
