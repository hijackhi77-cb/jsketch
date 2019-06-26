package jsketch.toolpanel;

import jsketch.Controller;
import jsketch.permitive.GridOfButtons;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ColorPalette extends GridOfButtons {
    public Controller m_controller = null;
    public final Color[] m_colors = {Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.RED, Color.PINK};
    public Color m_focused_color = null;

    public ColorPalette(int width, int height, Controller controller) {
        this.m_controller = controller;

        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createMatteBorder(10, 10, 0, 0, Color.WHITE));

        ColorPaletteMouseListener listener = new ColorPaletteMouseListener();

        super.drawButtons();
        for (int i = 0; i < m_num_col * m_num_row; ++i) {
            JButton btn = m_btns.get(i);

            btn.setOpaque(true);
            btn.setBackground(m_colors[i]);
            // Use RGB as btn names
            btn.setName(Integer.toString(m_colors[i].getRGB()));

            btn.addMouseListener(listener);
        }

        // Layout has been set at parent class
    }

    public void setFocusedColor(Color color) {
        if (color == null) {
            System.out.println("[WARN] ColorPalette.setFocusedColor() trying to assign null");
            return;
        }
        if (m_focused_color == color) return;

        m_focused_color = color;
        setFocusedBtn(findBtnByColor(color));
    }

    private JButton findBtnByColor(Color color) {
        for (int i = 0; i < m_btns.size(); ++i) {
            if (Integer.parseInt(m_btns.get(i).getName()) == color.getRGB())
                return m_btns.get(i);
        }
        return null;
    }

    public class ColorPaletteMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                JButton btn = (JButton) e.getSource();
                // If same button reference, no need to change
                if (m_focused_btn == btn) return;
                m_controller.getColorPanel().setFocusedColor(new Color(Integer.parseInt(btn.getName())));

            } else if (SwingUtilities.isRightMouseButton(e)) {
                // Right click: set custom color to color button
                JButton btn = (JButton) e.getSource();
                Color colorChosen = JColorChooser.showDialog(null, "Pick a color", Color.BLACK);
                // User clicked cancel in the chooser
                if (colorChosen == null) return;
                btn.setName(Integer.toString(colorChosen.getRGB()));
                btn.setBackground(colorChosen);
                // Select to the new changed color
                m_controller.getColorPanel().setFocusedColor(new Color(Integer.parseInt(btn.getName())));

            }
        }
    }

}
