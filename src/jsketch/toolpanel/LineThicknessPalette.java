package jsketch.toolpanel;

import jsketch.Controller;
import jsketch.permitive.GridOfButtons;
import jsketch.permitive.ImageIconManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class LineThicknessPalette extends GridOfButtons {
    public Controller m_controller = null;
    private final String[] m_icon_paths = {
            "/res/thickness1.jpeg",
            "/res/thickness2.jpeg",
            "/res/thickness3.jpeg",
            "/res/thickness4.jpeg"
    };
    public float m_focused_thickness = 2.0f;

    public LineThicknessPalette(int width, int height, Controller controller) {
        this.m_controller = controller;

        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createMatteBorder(10, 10, 10, 0, Color.WHITE));
        this.m_num_row = 4;
        this.m_num_col = 1;

        LineThicknessPaletteMouseListener listener = new LineThicknessPaletteMouseListener();

        this.drawButtons();
        for (int i = 0; i < m_num_col * m_num_row; ++i) {
            JButton btn = m_btns.get(i);

            btn.setOpaque(true);

            btn.setName(Float.toString((float)(i+1)*2));
            btn.setIcon(ImageIconManager.resizeIcon(
                    ImageIconManager.getImageIcon(m_icon_paths[i]), width, btn.getHeight()));

            btn.addMouseListener(listener);
        }

        // Layout has been set at parent class
    }

    private JButton findBtnByThickness(float thickness) {
        for (int i = 0; i < m_btns.size(); ++i) {
            if (Float.parseFloat(m_btns.get(i).getName()) == thickness) return m_btns.get(i);
        }
        return null;
    }

    public void setFocusedThickness(float thickness) {
        m_focused_thickness = thickness;
        setFocusedBtn(findBtnByThickness(thickness));
    }

    public class LineThicknessPaletteMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            // If same button reference, no need to change
            if (m_focused_btn == btn) return;

            m_focused_thickness = Float.parseFloat(btn.getName());
            setFocusedBtn(btn);

            m_controller.getCanvas().m_thickness = m_focused_thickness;
            System.out.println("Canvas thickness changed to " + m_controller.getCanvas().m_thickness);
        }
    }

}
