package ru.fw_tm;

/**
 * @author : Ragnarok
 * @date : 02.07.12  15:10
 */
public class Location implements Cloneable {
    private float x, y;

    public Location(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void updateX(float newX) {
        x = newX;
    }

    public void updateY(float newY) {
        y = newY;
    }

    public Location update(float newX, float newY) {
        updateX(newX);
        updateY(newY);
        return this;
    }

    @Override
    public Location clone() {
        return new Location(x, y);
    }
}
