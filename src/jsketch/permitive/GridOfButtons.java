package jsketch.permitive;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class GridOfButtons extends JPanel {
    protected int m_num_col = 2;
    protected int m_num_row = 3;
    protected ArrayList<JButton> m_btns = new ArrayList<>();
    protected int m_btn_width = 25;
    protected JButton m_focused_btn = null;

    public GridOfButtons() {
    }

    public void drawButtons() {
        this.setBackground(Color.DARK_GRAY);

        for (int i = 0; i < m_num_col * m_num_row; ++i) {
            JButton temp_btn = new JButton();
            temp_btn.setBorder(new LineBorder(Color.BLACK));
            if (i == 0) m_focused_btn = temp_btn;

            temp_btn.setMaximumSize(new Dimension(m_btn_width, m_btn_width));
            //temp_btn.setMinimumSize(new Dimension(20, 20));
            temp_btn.setPreferredSize(new Dimension(m_btn_width, m_btn_width));

            temp_btn.setBounds(0, 0, m_btn_width, m_btn_width);

            m_btns.add(temp_btn);
            this.add(temp_btn);
        }
        this.setLayout(new GridLayout(m_num_row, m_num_col));

        setFocusedBtn(m_focused_btn);
    }

    public void setFocusedBtn(JButton btn) {
        // No need to null check here, if null is passed in, simply
        // set all buttons to non-focused view
        m_focused_btn = btn;
        for (int i = 0; i < m_num_col * m_num_row; ++i) {
            if (m_btns.get(i) == btn) {
                m_btns.get(i).setBorder(new LineBorder(Color.BLACK, 4));
            } else {
                m_btns.get(i).setBorder(new LineBorder(Color.BLACK));
            }
        }
    }

    public void setEnablePalette(boolean isEnable) {
        for (int i=0; i<m_btns.size(); ++i) {
            m_btns.get(i).setVisible(isEnable);
        }
    }
}
