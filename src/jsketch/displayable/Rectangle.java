package jsketch.displayable;

import java.awt.*;
import java.awt.geom.AffineTransform;

import static java.lang.Math.*;

public class Rectangle implements Displayable {
    public Color m_color = Color.BLACK;
    public float m_scale = 1.0f;
    public float m_strokeThickness = 1.0f;
    public boolean m_isSelected = false;
    public boolean m_erasable = true;
    public boolean m_movable = true;
    public Point m_one = null;
    public Point m_other = null;
    public double m_width = 0;
    public double m_height = 0;
    public double m_minX = 0;
    public double m_minY = 0;
    public double m_maxX = 0;
    public double m_maxY = 0;


    public void setOne(Point p) {
        this.m_one = p;
    }

    public void setOther(Point p) {
        this.m_other = p;
        m_minX = min(m_one.x, m_other.x);
        m_minY = min(m_one.y, m_other.y);
        m_maxX = max(m_one.x, m_other.x);
        m_maxY = max(m_one.y, m_other.y);
        m_width = abs(m_one.x - m_other.x);
        m_height = abs(m_one.y - m_other.y);
    }

    public void moveAlongVector(Point a, Point b) {
        if (!m_movable) return;
        double changeX = b.getX() - a.getX();
        double changeY = b.getY() - a.getY();
        this.m_one.setLocation(m_one.getX() + changeX, m_one.getY() + changeY);
        this.m_other.setLocation(m_other.getX() + changeX, m_other.getY() + changeY);
        m_minX = min(m_one.x, m_other.x);
        m_minY = min(m_one.y, m_other.y);
        m_maxX = max(m_one.x, m_other.x);
        m_maxY = max(m_one.y, m_other.y);
    }

    public void draw(Graphics2D g2) {
        if (m_one == null || m_other == null) {
            System.out.println("[WARN] Rectangle.draw(): one = " + m_one + ", other = " + m_other);
            return;
        }

        AffineTransform M = g2.getTransform();
        g2.scale(m_scale, m_scale);
        g2.setStroke(new BasicStroke(m_strokeThickness / m_scale));
        g2.setTransform(M);

        if (m_isSelected) {
            int alpha = 200;
            g2.setColor(new Color(m_color.getRed(), m_color.getGreen(), m_color.getBlue(), alpha));
            g2.fillRect((int) m_minX, (int) m_minY, (int) m_width, (int) m_height);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(m_strokeThickness / m_scale,
                    BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[] {10.0f}, 0.0f));
            g2.drawRect((int) m_minX, (int) m_minY, (int) m_width, (int) m_height);

        } else {
            g2.setColor(m_color);
            g2.fillRect((int) m_minX, (int) m_minY, (int) m_width, (int) m_height);
            g2.setColor(Color.BLACK);
            g2.drawRect((int) m_minX, (int) m_minY, (int) m_width, (int) m_height);
        }
    }

    public boolean hitTest(Point p) {
        return m_minX <= p.x && p.x <= m_minX + m_width
                && m_minY <= p.y && p.y <= m_minY + m_height;
    }

    /////////////////////////////////////////////////////////////////
    public void setStrokeThickness(float strokeThickness) {
        this.m_strokeThickness = strokeThickness;
    }

    public float getStrokeThickness() {
        return this.m_strokeThickness;
    }

    public void setColour(Color color) {
        this.m_color = color;
    }

    public Color getColor() {
        return this.m_color;
    }

    public void setScale(float scale) {
        this.m_scale = scale;
    }

    public float getScale() {
        return this.m_scale;
    }

    public void setErasable(boolean bool) {
        this.m_erasable = bool;
    }

    public boolean getErasable() {
        return this.m_erasable;
    }

    public void setMovable(boolean bool) {
        this.m_movable = bool;
    }

    public boolean getMovable() {
        return m_movable;
    }

    public void setSelected(boolean bool) {
        this.m_isSelected = bool;
    }

    public boolean getSelected() {
        return this.m_isSelected;
    }

    public String toString() {
        String result = "Rectangle,";
        result += this.m_scale + ",";
        result += this.m_strokeThickness + ",";
        result += this.m_color.getRed() + ",";
        result += this.m_color.getGreen() + ",";
        result += this.m_color.getBlue() + ",";
        result += this.m_erasable + ",";
        result += this.m_movable + ",";

        result += this.m_one.getX() + ",";
        result += this.m_one.getY() + ",";
        result += this.m_other.getX() + ",";
        result += this.m_other.getY() + ",";
        result += this.m_width + ",";
        result += this.m_height + ",";
        result += this.m_minX + ",";
        result += this.m_minY;
        return result;
    }

    public void setWidth(double width) {
        this.m_width = width;
    }

    public void setHeight(double height) {
        this.m_height = height;
    }

    public void setMinX(double x) {
        this.m_minX = x;
    }

    public void setMinY(double y) {
        this.m_minY = y;
    }

    /////////////////////////////////////////////////////////////////
    public boolean contains(Displayable displayable) {
        return false;
    }

    public void setStartPoint(Point p) {
    }

    public void setEndPoint(Point p) {
    }

    public void setRadius(Point p) {
    }

    public void setOrigin(Point p) {
    }

}
