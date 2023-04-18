import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

/**
 * @author 千叶零
 * @version 1.0
 * creats 2023-01-03  16:22:03
 */
public class MyTankGame extends JFrame {
    //定义MyPanel
    MyPanel myPanel;

    public static void main(String[] args) {
        MyTankGame myTankGame = new MyTankGame();
    }

    public MyTankGame() {
        myPanel = new MyPanel();
        //启动线程
        Thread thread = new Thread(myPanel);
        thread.start();
        this.add(myPanel);//把面板加入
        this.setSize(1280, 750);//窗口大小
        this.addKeyListener(myPanel);//实现监听
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//点击 X 时，关闭程序
        this.setVisible(true);//设置可见

        //在 JFrame 中增加响应关闭窗口的处理
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("监听到窗口关闭");
                System.out.println("已保存");
                try {
                    //窗口关闭时，调用方法 临时保存
                    Recorder.keepRecord();
                } catch (Exception ex) {
                    ex.printStackTrace();
                } finally {
                    //关闭程序
                    System.exit(0);
                }
            }
        });
    }
}
