package jsketch.toolpanel;

import jsketch.Controller;
import jsketch.permitive.GridOfButtons;
import jsketch.permitive.ImageIconManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ToolPalette extends GridOfButtons {
    public Controller m_controller = null;
    private final String[] m_icon_paths = {
            "/res/selection.png",
            "/res/erase.png",
            "/res/line.png",
            "/res/circle.png",
            "/res/rectangle.png",
            "/res/fill.png",
    };
    public enum ToolType {SELECTION, ERASE, LINE, CIRCLE, RECTANGLE, FILL};
    public ToolType m_focused_tool = null;

    public ToolPalette(int width, int height, Controller controller) {
        this.m_controller = controller;

        this.setPreferredSize(new Dimension(width, height));
        this.setBorder(BorderFactory.createMatteBorder(10,10,0,0, Color.WHITE));

        ToolPaletteMouseListener listener = new ToolPaletteMouseListener();

        super.drawButtons();
        for (int i = 0; i < m_num_col * m_num_row; ++i) {
            JButton btn = m_btns.get(i);

            btn.setOpaque(true);
            btn.setBackground(Color.WHITE);
            btn.setIcon(ImageIconManager.resizeIcon(
                    ImageIconManager.getImageIcon(m_icon_paths[i]), btn.getWidth(), btn.getHeight()));
            btn.setName(ToolType.values()[i].name());

            btn.addMouseListener(listener);
        }
        //this.setMaximumSize(new Dimension(width, height));
        // Layout has been set at parent class
    }

    public class ToolPaletteMouseListener extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            if (m_focused_btn == btn) return;
            setFocusedBtn(btn);

            m_focused_tool = ToolType.valueOf(m_focused_btn.getName());
            m_controller.getCanvas().m_tool = m_focused_tool;
            System.out.println("Canvas tool changed to "+m_controller.getCanvas().m_tool);

            if (m_focused_tool == ToolType.ERASE) {
                m_controller.getColorPalette().setEnablePalette(false);
                m_controller.getChooser().m_chooser_btn.setVisible(false);
                m_controller.getLineThicknessPalette().setEnablePalette(false);

            } else if (m_focused_tool == ToolType.FILL) {
                m_controller.getColorPalette().setEnablePalette(true);
                m_controller.getChooser().m_chooser_btn.setVisible(true);
                m_controller.getLineThicknessPalette().setEnablePalette(false);

            } else {
                m_controller.getColorPalette().setEnablePalette(true);
                m_controller.getChooser().m_chooser_btn.setVisible(true);
                m_controller.getLineThicknessPalette().setEnablePalette(true);
            }
        }
    }

}
