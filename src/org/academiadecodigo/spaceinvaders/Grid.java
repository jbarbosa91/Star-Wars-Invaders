package org.academiadecodigo.spaceinvaders;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public interface Grid {
    int BORDER = 10;
    int cellSize = 16;
    int cols = 85;
    int rows = 48;
    int x = cols * cellSize;
    int y = rows * cellSize;

    int getCellSize();

    int getX();

    int getY();
}


