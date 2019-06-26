package jsketch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class JSketch {

    public static void main(String[] args) {
        try {

            System.setProperty("apple.laf.useScreenMenuBar", "true");
            System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Chuan Liu");
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

        } catch (Exception e) {
            e.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new JSketch();
            }
        });
    }

    public int m_frame_width = 1120;
    public int m_frame_height = 840;

    public JSketch() {
        JFrame mainFrame = new JFrame("JSketch");
        mainFrame.setMaximumSize(new Dimension(1600, 1200));
        mainFrame.setMinimumSize(new Dimension(640, 480));
        mainFrame.setPreferredSize(new Dimension(m_frame_width, m_frame_height));
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        mainFrame.setLayout(new BorderLayout());

        Controller controller = new Controller();
        controller.setMenuBar(new JSketchMenuBar(controller));
        controller.setCanvas(new Canvas(controller));
        controller.setToolPanel(new ToolPanel(150, m_frame_height, controller));

        // Add the components
        mainFrame.setMenuBar(controller.getMenuBar());
        mainFrame.add(controller.getCanvas(), BorderLayout.CENTER);
        mainFrame.add(controller.getToolPanel(), BorderLayout.WEST);

        mainFrame.setVisible(true);
        mainFrame.pack();
    }
}
