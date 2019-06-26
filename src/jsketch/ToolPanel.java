package jsketch;

import jsketch.toolpanel.*;

import javax.swing.*;
import java.awt.*;

public class ToolPanel extends JPanel {
    public Controller m_controller = null;

    public ToolPanel(int width, int height, Controller controller) {
        this.m_controller = controller;

        //this.setMaximumSize(new Dimension(400, 1200));
        //this.setMinimumSize(new Dimension(200, 480));
        this.setPreferredSize(new Dimension(width, height));

        controller.setToolPalette(new ToolPalette(width, height/19*6, controller));
        controller.setColorPanel(new ColorPanel(width, height/19*9, controller));
        controller.setLineThicknessPalette(new LineThicknessPalette(width, height/19*4, controller));

        this.add(controller.getToolPalette());
        this.add(controller.getColorPanel());
        this.add(controller.getLineThicknessPalette());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }
}
