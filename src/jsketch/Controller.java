package jsketch;

import jsketch.toolpanel.*;

public class Controller {
    public JSketchMenuBar menuBar = null;
    public Canvas canvas = null;
    public ToolPanel toolPanel = null;

    public ToolPalette toolPalette = null;
    public LineThicknessPalette lineThicknessPalette = null;

    public ColorPanel colorPanel = null;
    public ColorPalette colorPalette = null;
    public Chooser chooser = null;

    public Controller() {}

    /////////////////////////////////////////////////////////////////

    public JSketchMenuBar getMenuBar() {
        return menuBar;
    }

    public void setMenuBar(JSketchMenuBar menuBar) {
        this.menuBar = menuBar;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public ToolPanel getToolPanel() {
        return toolPanel;
    }

    public void setToolPanel(ToolPanel toolPanel) {
        this.toolPanel = toolPanel;
    }

    public ToolPalette getToolPalette() {
        return toolPalette;
    }

    public void setToolPalette(ToolPalette toolPalette) {
        this.toolPalette = toolPalette;
    }

    public ColorPanel getColorPanel() {
        return colorPanel;
    }

    public void setColorPanel(ColorPanel colorPanel) {
        this.colorPanel = colorPanel;
    }

    public ColorPalette getColorPalette() {
        return colorPalette;
    }

    public void setColorPalette(ColorPalette colorPalette) {
        this.colorPalette = colorPalette;
    }

    public Chooser getChooser() {
        return chooser;
    }

    public void setChooser(Chooser chooser) {
        this.chooser = chooser;
    }

    public LineThicknessPalette getLineThicknessPalette() {
        return lineThicknessPalette;
    }

    public void setLineThicknessPalette(LineThicknessPalette lineThicknessPalette) {
        this.lineThicknessPalette = lineThicknessPalette;
    }
}
