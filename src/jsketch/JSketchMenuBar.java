package jsketch;

import jsketch.file.JSketchFileManager;

import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class JSketchMenuBar extends MenuBar {
    public Controller m_controller = null;
    private Menu m_menu = new Menu("File");
    private JSketchFileManager m_jsfm = null;

    public JSketchMenuBar(Controller controller) {
        this.m_controller = controller;
        this.m_jsfm = new JSketchFileManager(controller);

        MenuBarActionListener listener = new MenuBarActionListener();
        for (String s: new String[] {"New", "Load", "Save"}) {
            MenuItem mi = new MenuItem(s);
            mi.addActionListener(listener);
            m_menu.add(mi);
        }

        this.add(m_menu);
    }

    public class MenuBarActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == m_menu.getItem(0)) {
                m_controller.getCanvas().clearAll();

            } else if (e.getSource() == m_menu.getItem(1)) {
                // NOTE: The following code are referenced to
                // http://www.java2s.com/Tutorials/Java/Swing_How_to/JFileChooser/Make_JFileChooser_to_save_file.htm
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showOpenDialog(null);

                File selectedFile = null;
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    selectedFile = jfc.getSelectedFile();
                    m_jsfm.loadFile(selectedFile);
                    System.out.println("Loading file "+selectedFile.getAbsolutePath());
                }

            } else if (e.getSource() == m_menu.getItem(2)) {
                // NOTE: The following code are referenced to
                // http://www.java2s.com/Tutorials/Java/Swing_How_to/JFileChooser/Make_JFileChooser_to_save_file.htm
                JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                int returnValue = jfc.showSaveDialog(null);

                File selectedFile = jfc.getSelectedFile();
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    m_jsfm.saveToFile(selectedFile);
                    System.out.println("Saving to file "+selectedFile.getAbsolutePath());
                }

            }
        }
    }
}
