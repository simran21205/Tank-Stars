package com.mygdx.game.Screens;

import com.mygdx.game.TankStars;

import java.io.*;

public class SaveLoad implements Serializable {
    TankStars app;

    public SaveLoad(TankStars app) {
        this.app = app;
    }

    public static Tank load1() throws IOException, ClassNotFoundException {
        ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(new File("out1.txt")));
        Tank playerTank = (PlayerTank) outputStream.readObject();
        return playerTank;
    }
    public static Tank load2() throws IOException, ClassNotFoundException {
        ObjectInputStream outputStream = new ObjectInputStream(new FileInputStream(new File("out2.txt")));
        Tank EnemyTank = (EnemyTank) outputStream.readObject();
        return EnemyTank;
    }
}
