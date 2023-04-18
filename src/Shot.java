/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-06  16:56:07
 */
public class Shot implements Runnable{
    //子弹x，y坐标
    int x;
    int y;
    //子弹方向
    int direct = 0;
    //子弹速度
    int speed;
    //子弹是否存活
    boolean isLive = true;

    public Shot(int x, int y, int direct, int speed) {
        this.x = x;
        this.y = y;
        this.direct = direct;
        this.speed = speed;
    }

    @Override
    public void run() {//射击
        while (true){
            //休眠
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //方向，改变x，y坐标
            switch (direct){
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
            //当子弹移动到面板边缘时，销毁子弹
            if (!(x >= 0 && x <= 1280 && y >= 0 && y <= 720 && isLive)){
                isLive = false;
                break;
            }
        }
    }
}
