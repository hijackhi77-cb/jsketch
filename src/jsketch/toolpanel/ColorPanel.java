package jsketch.toolpanel;

import jsketch.Controller;

import javax.swing.*;
import java.awt.*;

public class ColorPanel extends JPanel {
    public Controller m_controller = null;
    public Color m_focused_color = null;

    public ColorPanel(int width, int height, Controller controller) {
        this.m_controller = controller;

        this.setPreferredSize(new Dimension(width, height));

        controller.setColorPalette(new ColorPalette(width, height / 4 * 3, controller));
        controller.setChooser(new Chooser(width, height / 4, controller));

        this.add(controller.getColorPalette());
        this.add(controller.getChooser());
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void setFocusedColor(Color color) {
        m_focused_color = color;
        m_controller.getColorPalette().setFocusedColor(color);
        m_controller.getChooser().setFocusedColor(color);
        m_controller.getCanvas().setColor(color);
        System.out.println("ColorPanel has set to " + color);
    }

    public Color getFocusedColor() {
        return this.m_focused_color;
    }

}
