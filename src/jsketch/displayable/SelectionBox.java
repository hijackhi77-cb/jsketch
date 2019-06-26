package jsketch.displayable;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class SelectionBox extends Rectangle {

    @Override
    public void draw(Graphics2D g2) {
        if (m_one == null || m_other == null) {
            System.out.println("[WARN] Rectangle.draw(): one = " + m_one + ", other = " + m_other);
            return;
        }

        AffineTransform M = g2.getTransform();
        g2.scale(m_scale, m_scale);
        g2.setTransform(M);

        g2.setColor(m_color);
        /* width - the width of this BasicStroke. The width must be
         * greater than or equal to 0.0f. If width is set to 0.0f,
         * the stroke is rendered as the thinnest possible line for
         * the target device and the antialias hint setting.
         * cap - the decoration of the ends of a BasicStroke
         * join - the decoration applied where path segments meet
         * miterlimit - the limit to trim the miter join. The
         * miterlimit must be greater than or equal to 1.0f.
         * dash - the array representing the dashing pattern
         * dash_phase - the offset to start the dashing pattern
         */
        g2.setStroke(new BasicStroke(m_strokeThickness / m_scale,
                BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER,
                10.0f, new float[]{10.0f}, 10.0f));

        g2.drawRect((int) m_minX, (int) m_minY, (int) m_width, (int) m_height);
    }

    @Override
    public boolean contains(Displayable displayable) {

        boolean result = false;
        if (displayable instanceof Line) {
            // True if both start and end points in the rectangle
            if (this.hitTest(((Line) displayable).m_start)
                    && this.hitTest(((Line) displayable).m_start)
            ) result = true;

        } else if (displayable instanceof Circle) {
            // True if both the upper-left and bottom-right points
            // are in the rectangle
            double x = ((Circle) displayable).m_origin.getX();
            double y = ((Circle) displayable).m_origin.getY();
            double r = ((Circle) displayable).m_radius;
            Point upperLeft = new Point((int) (x-r), (int) (y-r));
            Point bottomRight = new Point((int) (x+r), (int) (y+r));
            if (this.hitTest(upperLeft) && this.hitTest(bottomRight)
            ) result = true;

        } else if (displayable instanceof Rectangle) {
            // True if both the upper-left and bottom-right points
            // are in the rectangle
            if (this.hitTest(((Rectangle) displayable).m_one)
                    && this.hitTest(((Rectangle) displayable).m_other)
            ) result = true;
        }

        return result;
    }
}
