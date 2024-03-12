// https://stackoverflow.com/questions/12209801/how-to-change-file-extension-at-runtime-in-java

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.UIManager;

public class Main {

    public static GOL gol;
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        gol = new GOL(2, 3, 3);
        addButtonActions();
    }

    public static void addButtonActions() {
        gol.gameFrame.stepButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gol.step(gol.x, gol.y, gol.z);
            }
        });

        gol.gameFrame.save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    gol.saveGame();
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                    System.out.println("Main.addActions(...).new ActionListener() {...}.actionPerformed()");
                }
            }

        });

        gol.gameFrame.runButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (gol.runningState == false) {
                    gol.runningState = true;
                    gol.gameFrame.runButton.setText("Stop");
                } else {
                    gol.runningState = false;
                    gol.gameFrame.runButton.setText("Run");
                }

                Thread task = new Thread(new ReasourceIntensiveTask());
                task.start();

            }
        });

        gol.gameFrame.load.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {
                    gol.loadSave();
                } catch (Exception a) {
                    System.out.println(a.getMessage());
                }

            }

        });

        gol.gameFrame.clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < gol.arrCells.length; i++) {
                    for (int j = 0; j < gol.arrCells[i].length; j++) {
                        gol.arrCells[i][j].setLive(false);
                    }
                }
            }

        });

        gol.gameFrame.xField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gol.setX(Integer.parseInt(gol.gameFrame.xField.getText()));
            }

        });

        gol.gameFrame.yField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gol.setY(Integer.parseInt(gol.gameFrame.yField.getText()));
            }

        });

        gol.gameFrame.zField.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                gol.setZ(Integer.parseInt(gol.gameFrame.zField.getText()));
            }

        });
    }

    public static class ReasourceIntensiveTask implements Runnable {

        @Override
        public void run() {
            while (gol.runningState) {
                gol.step(gol.x, gol.y, gol.z);
                try {
                    Thread.sleep(gol.frameRate);
                } catch (InterruptedException e) {
                    System.out.println("Interrupted!");
                }
            }
        }

    }
}

