package jsketch.displayable;

import java.awt.*;
import java.awt.geom.AffineTransform;


public class Circle implements Displayable {
    public float m_scale = 1.0f;
    public float m_strokeThickness = 1.0f;
    public Color m_color = Color.BLACK;
    public boolean m_isSelected = false;
    public boolean m_movable = true;
    public boolean m_erasable = true;
    public Point m_origin = null;
    public double m_radius = 0;


    public void setRadius(Point p) {
        this.m_radius = p.distance(m_origin);
    }

    public void setRadius(Double r) {
        this.m_radius = r;
    }

    public void setOrigin(Point p) {
        this.m_origin = p;
    }

    public void moveAlongVector(Point a, Point b) {
        if (!m_movable) return;
        double changeX = b.getX() - a.getX();
        double changeY = b.getY() - a.getY();
        this.m_origin.setLocation(m_origin.getX()+changeX, m_origin.getY()+changeY);
    }

    public void draw(Graphics2D g2) {
        if (m_origin == null) {
            System.out.println("[WARN] Circle.draw(): origin = null");
            return;
        }

        AffineTransform M = g2.getTransform();
        g2.scale(m_scale, m_scale);
        g2.setStroke(new BasicStroke(m_strokeThickness / m_scale));
        g2.setTransform(M);

        if (m_isSelected) {
            int alpha = 200;
            g2.setColor(new Color(m_color.getRed(), m_color.getGreen(), m_color.getBlue(), alpha));
            g2.fillOval((int) (m_origin.getX() - m_radius), (int) (m_origin.getY() - m_radius),
                    (int) (2 * m_radius), (int) (2 * m_radius));

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(m_strokeThickness / m_scale,
                    BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                    10.0f, new float[] {10.0f}, 0.0f));
            g2.drawOval((int) (m_origin.getX() - m_radius), (int) (m_origin.getY() - m_radius),
                    (int) (2 * m_radius), (int) (2 * m_radius));

        } else {
            g2.setColor(m_color);
            g2.fillOval((int) (m_origin.getX() - m_radius), (int) (m_origin.getY() - m_radius),
                    (int) (2 * m_radius), (int) (2 * m_radius));
            g2.setColor(Color.BLACK);
            g2.drawOval((int) (m_origin.getX() - m_radius), (int) (m_origin.getY() - m_radius),
                    (int) (2 * m_radius), (int) (2 * m_radius));
        }
    }

    public boolean hitTest(Point p) {
        return m_origin.distance(p) <= m_radius;
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
        String result = "Circle,";
        result += this.m_scale + ",";
        result += this.m_strokeThickness + ",";
        result += this.m_color.getRed() + ",";
        result += this.m_color.getGreen() + ",";
        result += this.m_color.getBlue() + ",";
        result += this.m_erasable + ",";
        result += this.m_movable + ",";

        result += this.m_origin.getX() + ",";
        result += this.m_origin.getY() + ",";
        result += this.m_radius;
        return result;
    }

    /////////////////////////////////////////////////////////////////
    public boolean contains(Displayable displayable) {
        return false;
    }

    public void setStartPoint(Point p) {
    }

    public void setEndPoint(Point p) {
    }

    public void setOne(Point p) {
    }

    public void setOther(Point p) {
    }

}
