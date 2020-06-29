package com.company;

import java.awt.*;

public class Config {
    public static final int SIZE = 20;
    public static final int WIDTH = 20;
    public static final int HEIGHT = 20;
    public static final int SLEEP_MS = 100;

    public static Color getColor(Status status) {
        switch (status) {
            default:
            case FREE:
                return Color.WHITE;
            case BORN:
                return Color.RED;
            case LIVE:
                return Color.GREEN;
            case DIED:
                return Color.BLACK;
        }
    }
}