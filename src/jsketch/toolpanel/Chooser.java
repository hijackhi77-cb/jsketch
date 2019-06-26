package jsketch.toolpanel;

import jsketch.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Chooser extends JPanel {
    public Controller m_controller = null;
    public JButton m_chooser_btn = new JButton("Chooser");
    public Color m_focused_color = null;

    public Chooser(int width, int height, Controller controller) {
        this.m_controller = controller;

        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createMatteBorder(10, 10, 0, 0, Color.WHITE));
        this.setBackground(Color.BLUE);

        ChooserMouseListener listener = new ChooserMouseListener();
        m_chooser_btn.addMouseListener(listener);

        this.add(m_chooser_btn);
        this.setLayout(new GridBagLayout());
    }

    public void setFocusedColor(Color color) {
        if (color == null) {
            System.out.println("[WARN] ColorPalette.setFocusedColor() trying to assign null");
            return;
        }
        m_focused_color = color;
        this.setBackground(color);
    }

    public class ChooserMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            Color colorChosen = JColorChooser.showDialog(null, "Pick a color", Color.BLACK);
            m_controller.getColorPanel().setFocusedColor(colorChosen);
        }
    }
}
