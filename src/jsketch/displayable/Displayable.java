package jsketch.displayable;

import java.awt.*;

public interface Displayable {
    /* Line */
    void setStartPoint(Point p);
    void setEndPoint(Point p);

    /* Circle */
    void setRadius(Point p);
    void setOrigin(Point p);

    /* Rectangle */
    void setOne(Point p);
    void setOther(Point p);

    /* Universal - Draw */
    void setColour(Color colour);
    Color getColor();
    void setScale(float scale);
    float getScale();
    void setStrokeThickness(float thickness);
    float getStrokeThickness();

    /* Universal - Modify */
    void setSelected(boolean bool);
    boolean getSelected();
    void setErasable(boolean bool);
    boolean getErasable();
    void setMovable(boolean bool);
    boolean getMovable();

    /* Group Selection */
    boolean contains(Displayable displayable);

    /* File */
    String toString();

    /* Important */
    void draw(Graphics2D g2);
    boolean hitTest(Point p);
    void moveAlongVector(Point a, Point b);
}