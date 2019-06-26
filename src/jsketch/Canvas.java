package jsketch;

import jsketch.displayable.*;
import jsketch.displayable.Rectangle;
import jsketch.toolpanel.ToolPalette;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class Canvas extends JPanel {
    public final KeyStroke ESCStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0);
    public Controller m_controller = null;
    public ToolPalette.ToolType m_tool = ToolPalette.ToolType.SELECTION;
    public Color m_color = Color.BLUE;
    public float m_thickness = 2;
    public ArrayList<Displayable> m_displayables = new ArrayList<>();
    public Displayable m_background = new Rectangle();
    public Displayable m_drawing_displayable = null;
    public Displayable m_selected_displayable = null;
    public Point m_landingPoint = null;

    public Canvas(Controller controller) {
        this.m_controller = controller;

        //this.setPreferredSize(new Dimension(500, 500));
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(10, 10, 10, 10, Color.WHITE), new LineBorder(Color.BLACK)));

        // Add default background for fill in empty space
        m_background.setColour(Color.WHITE);
        m_background.setStrokeThickness(2);
        m_background.setOne(new Point(0, 0));
        m_background.setOther(new Point(1600, 1200));
        m_background.setErasable(false);
        m_background.setMovable(false);
        m_displayables.add(m_background);

        CanvasMouseAdapter listener = new CanvasMouseAdapter();

        this.addMouseListener(listener);
        this.addMouseMotionListener(listener);

        // Register key binding for ESC key
        // registerKeyboardAction(ActionListener action, KeyStroke keyStroke, int condition);
        this.registerKeyboardAction(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (int i = 0; i < m_displayables.size(); ++i) {
                    m_displayables.get(i).setSelected(false);
                }
                repaint();
                System.out.println("[INFO] Canvas.ESCKeyListener(): ESC key was pressed, cleared all focuses");
            }
        }, ESCStroke, JComponent.WHEN_IN_FOCUSED_WINDOW);

        this.repaint();
    }

    public void clearAll() {
        for (int i = m_displayables.size() - 1; i > 0; --i) {
            m_displayables.remove(i);
        }
        repaint();
        System.out.println("[INFO] Canvas.clearAll(): all displayables removed");
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw the background at the very bottom layer
        //m_background.draw(g2);
        if (m_displayables != null) {
            // Draw all underlying shapes
            for (int i = 0; i < m_displayables.size(); ++i) {
                m_displayables.get(i).draw(g2);
            }

        } else {
            System.out.println("[WARN] Canvas.paintComponent(): m_displayables was deleted for some reason, " +
                    "recreated now");
            m_displayables = new ArrayList<>();
        }

        // Draw current shape
        if (m_drawing_displayable != null) m_drawing_displayable.draw(g2);
    }

    public class CanvasMouseAdapter extends MouseAdapter {
        @Override
        public void mousePressed(MouseEvent e) {

            if (m_tool == ToolPalette.ToolType.SELECTION) {
                // If has previously selected something, should
                // clear that selection then continue
                if (m_selected_displayable != null) {
                    m_selected_displayable.setSelected(false);
                    m_selected_displayable = null;
                }

                m_landingPoint = e.getPoint();

                for (int i = m_displayables.size() - 1; i >= 0; --i) {
                    if (m_displayables.get(i).hitTest(e.getPoint())) {
                        m_selected_displayable = m_displayables.get(i);
                        break;
                    }
                }

                if (m_background == m_selected_displayable) {
                    // If directly click on the background, start group selecting
                    // Before start the selection rectangle, unselect all previously
                    // selected items
                    for (int i = 0; i < m_displayables.size(); ++i) {
                        m_displayables.get(i).setSelected(false);
                    }

                    m_drawing_displayable = new SelectionBox();
                    m_drawing_displayable.setScale(1.0f);
                    m_drawing_displayable.setStrokeThickness(2.0f);
                    m_drawing_displayable.setColour(Color.GRAY);
                    m_drawing_displayable.setOne(e.getPoint());

                } else {
                    if (m_selected_displayable.getSelected()) {
                        // The user is moving a group of items
                        // Do nothing since the items are already high-lighted

                    } else {
                        // The user is moving a single item
                        m_selected_displayable.setSelected(true);
                    }
                }

                // Tool panel has to reflect the selected displayable anyway
                m_controller.getColorPanel().setFocusedColor(m_selected_displayable.getColor());
                m_controller.getLineThicknessPalette().setFocusedThickness(
                        m_selected_displayable.getStrokeThickness());

            } else if (m_tool == ToolPalette.ToolType.ERASE) {
                for (int i = m_displayables.size() - 1; i >= 0; --i) {
                    if (m_displayables.get(i).hitTest(e.getPoint())) {
                        if (m_displayables.get(i).getErasable()) m_displayables.remove(i);
                        break;
                    }
                }

            } else if (m_tool == ToolPalette.ToolType.LINE) {
                m_drawing_displayable = new Line();
                m_drawing_displayable.setScale(1.0f);
                m_drawing_displayable.setStrokeThickness(m_thickness);
                m_drawing_displayable.setColour(m_color);
                m_drawing_displayable.setStartPoint(e.getPoint());

            } else if (m_tool == ToolPalette.ToolType.CIRCLE) {
                m_drawing_displayable = new Circle();
                m_drawing_displayable.setScale(1.0f);
                m_drawing_displayable.setStrokeThickness(m_thickness);
                m_drawing_displayable.setColour(m_color);
                m_drawing_displayable.setOrigin(e.getPoint());

            } else if (m_tool == ToolPalette.ToolType.RECTANGLE) {
                m_drawing_displayable = new Rectangle();
                m_drawing_displayable.setScale(1.0f);
                m_drawing_displayable.setStrokeThickness(m_thickness);
                m_drawing_displayable.setColour(m_color);
                m_drawing_displayable.setOne(e.getPoint());

            } else if (m_tool == ToolPalette.ToolType.FILL) {
                for (int i = m_displayables.size() - 1; i >= 0; --i) {
                    if (m_displayables.get(i).hitTest(e.getPoint())) {
                        m_displayables.get(i).setColour(m_color);
                        break;
                    }
                }
            }

            repaint();
        }

        @Override
        public void mouseDragged(MouseEvent e) {

            if (m_tool == ToolPalette.ToolType.SELECTION) {
                if (m_background == m_selected_displayable) {
                    m_drawing_displayable.setOther(e.getPoint());

                } else {
                    // Move all the selected item to the position
                    for (int i = 0; i < m_displayables.size(); ++i) {
                        if (m_displayables.get(i).getSelected()) {
                            m_displayables.get(i).moveAlongVector(m_landingPoint, e.getPoint());
                        }
                    }
                    // NOTE: m_landingPoint has to be continuously updated
                    // as long as there is a drag
                    m_landingPoint = e.getPoint();
                }

            } else if (m_tool == ToolPalette.ToolType.ERASE) {
                // This enables drag erase, but will lead to a problem:
                // single click will cause erasing of multi-layer displayables
                    /*for (int i = m_displayables.size()-1; i >= 0 ; --i) {
                        if (m_displayables.get(i).hitTest(e.getPoint())) {
                            m_displayables.remove(i);
                            break;
                        }
                    }*/

            } else if (m_tool == ToolPalette.ToolType.LINE) {
                m_drawing_displayable.setEndPoint(e.getPoint());

            } else if (m_tool == ToolPalette.ToolType.CIRCLE) {
                m_drawing_displayable.setRadius(e.getPoint());

            } else if (m_tool == ToolPalette.ToolType.RECTANGLE) {
                m_drawing_displayable.setOther(e.getPoint());

            } else if (m_tool == ToolPalette.ToolType.FILL) {

            }

            repaint();
        }

        @Override
        public void mouseReleased(MouseEvent e) {

            if (m_tool == ToolPalette.ToolType.SELECTION) {

                m_landingPoint = null;

                if (m_background == m_selected_displayable) {
                    for (int i = 0; i < m_displayables.size(); ++i) {
                        if (m_drawing_displayable.contains(m_displayables.get(i))) {
                            m_displayables.get(i).setSelected(true);
                        }
                    }

                    // Once released, the selection box should be gone
                    m_drawing_displayable = null;

                } else {
                    // Enable this code: De-select the object if
                    // the user let the mouse hold go
                    //m_selected_displayable.setSelected(false);
                    //m_selected_displayable = null;
                }

            } else if (m_tool == ToolPalette.ToolType.ERASE) {

            } else if (m_tool == ToolPalette.ToolType.LINE) {
                if (m_drawing_displayable != null) m_displayables.add(m_drawing_displayable);
                m_drawing_displayable = null;

            } else if (m_tool == ToolPalette.ToolType.CIRCLE) {
                if (m_drawing_displayable != null) m_displayables.add(m_drawing_displayable);
                m_drawing_displayable = null;

            } else if (m_tool == ToolPalette.ToolType.RECTANGLE) {
                if (m_drawing_displayable != null) m_displayables.add(m_drawing_displayable);
                m_drawing_displayable = null;

            } else if (m_tool == ToolPalette.ToolType.FILL) {

            }

            repaint();
        }

    }

    ///////////////////////////////////////////////////////////////////////////
    public ToolPalette.ToolType getTool() {
        return m_tool;
    }

    public void setTool(ToolPalette.ToolType m_tool) {
        this.m_tool = m_tool;
    }

    public Color getColor() {
        return m_color;
    }

    public void setColor(Color m_color) {
        this.m_color = m_color;
    }

    public float getThickness() {
        return m_thickness;
    }

    public void setThickness(float thickness) {
        this.m_thickness = thickness;
    }

    public Displayable getSelectedDisplayable() {
        return m_selected_displayable;
    }

    public void setSelectedDisplayable(Displayable m_selected_displayable) {
        this.m_selected_displayable = m_selected_displayable;
    }

    public ArrayList<Displayable> getCurrDisplayables() {
        return m_displayables;
    }

    public void setCurrDisplayables(ArrayList<Displayable> m_displayables) {
        this.m_displayables = m_displayables;
        this.m_background = m_displayables.get(0);
    }
}
