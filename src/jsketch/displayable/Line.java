package jsketch.displayable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class Line implements Displayable {
    public Color m_color = Color.BLACK;
    public float m_scale = 1.0f;
    public float m_strokeThickness = 1.0f;
    public boolean m_isSelected = false;
    public boolean m_erasable = true;
    public boolean m_movable = true;
    public Point m_start = null;
    public Point m_end = null;


    public void setStartPoint(Point p) {
        m_start = p;
    }

    public void setEndPoint(Point p) {
        m_end = p;
    }

    public void moveAlongVector(Point a, Point b) {
        if (!m_movable) return;
        double changeX = b.getX() - a.getX();
        double changeY = b.getY() - a.getY();
        this.m_start.setLocation(m_start.getX()+changeX, m_start.getY()+changeY);
        this.m_end.setLocation(m_end.getX()+changeX, m_end.getY()+changeY);
    }

    public void draw(Graphics2D g2) {
        if (m_start == null || m_end == null) {
            System.out.println("[WARN] Line.draw(): m_start = " + m_start + ", m_end = " + m_end);
            return;
        }

        AffineTransform M = g2.getTransform();
        g2.scale(m_scale, m_scale);
        g2.setStroke(new BasicStroke(m_strokeThickness / m_scale));
        g2.setTransform(M);

        if (m_isSelected) {
            int alpha = 200;
            g2.setColor(new Color(m_color.getRed(), m_color.getGreen(), m_color.getBlue(), alpha));

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(m_strokeThickness / m_scale,
                    BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[] {10.0f}, 0.0f));
            g2.drawLine((int) m_start.getX(), (int) m_start.getY(), (int) m_end.getX(), (int) m_end.getY());

        } else {
            g2.setColor(m_color);
            g2.drawLine((int) m_start.getX(), (int) m_start.getY(), (int) m_end.getX(), (int) m_end.getY());

        }
    }

    public boolean hitTest(Point p) {
        if (p == null) return false;
        if ((m_start.y + m_end.y) / (m_start.x + m_end.x) != p.y / p.x) return false;

        return Math.min(m_start.x, m_end.x) <= p.x
                && p.x <= Math.max(m_start.x, m_end.x)
                && Math.min(m_start.y, m_end.y) <= p.y
                && p.y <= Math.max(m_start.y, m_end.y);
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
        String result = "Line,";
        result += this.m_scale + ",";
        result += this.m_strokeThickness + ",";
        result += this.m_color.getRed() + ",";
        result += this.m_color.getGreen() + ",";
        result += this.m_color.getBlue() + ",";
        result += this.m_erasable + ",";
        result += this.m_movable + ",";

        result += this.m_start.getX() + ",";
        result += this.m_start.getY() + ",";
        result += this.m_end.getX() + ",";
        result += this.m_end.getY();
        return result;
    }

    /////////////////////////////////////////////////////////////////
    public boolean contains(Displayable displayable) {
        return false;
    }

    public void setRadius(Point p) {
    }

    public void setOrigin(Point p) {
    }

    public void setOne(Point p) {
    }

    public void setOther(Point p) {
    }
}
