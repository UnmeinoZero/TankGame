/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-06  21:06:36
 * 爆炸类
 */
public class Bomb {
    int x, y; //爆炸坐标
    int life = 15; //爆炸的生命周期
    boolean isLive = true; //是否存活

    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //减少生命
    public void lifeDown(){
        if (life > 0){
            life--;
        }else {
            isLive = false;
        }
    }
}
