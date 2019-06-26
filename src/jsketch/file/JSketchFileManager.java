package jsketch.file;

import jsketch.Controller;
import jsketch.displayable.Circle;
import jsketch.displayable.Displayable;
import jsketch.displayable.Line;
import jsketch.displayable.Rectangle;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;

public class JSketchFileManager {
    public final String SEPARATOR = "&";
    public final String EXTENSION = ".jsketch";
    public Controller m_controller = null;


    public JSketchFileManager(Controller controller) {
        this.m_controller = controller;
    }

    public String stateToString(ArrayList<Displayable> displayables) {
        if (displayables == null) {
            System.out.println("[WARN] JSketchFile.stateToString(): displayables is null");
            return null;
        }
        String result = "";
        for (int i = 0; i < displayables.size(); ++i) {
            result += displayables.get(i).toString() + SEPARATOR;
        }
        return result;
    }

    public Displayable stringToDisplayable(String s) {
        String[] data = s.split(",");

        String type = data[0];
        float scale = Float.parseFloat(data[1]);
        float thickness = Float.parseFloat(data[2]);
        int colorR = Integer.parseInt(data[3]);
        int colorG = Integer.parseInt(data[4]);
        int colorB = Integer.parseInt(data[5]);
        Color color = new Color(colorR, colorG, colorB);

        Displayable result = null;

        if (type.equals("Line")) {
            Line line = new Line();
            line.setScale(scale);
            line.setStrokeThickness(thickness);
            line.setColour(color);
            line.setErasable(Boolean.parseBoolean(data[6]));
            line.setMovable(Boolean.parseBoolean(data[7]));
            line.setStartPoint(new Point((int) Double.parseDouble(data[8]), (int) Double.parseDouble(data[9])));
            line.setEndPoint(new Point((int) Double.parseDouble(data[10]), (int) Double.parseDouble(data[11])));
            result = line;

        } else if (type.equals("Circle")) {
            Circle circle = new Circle();
            circle.setScale(scale);
            circle.setStrokeThickness(thickness);
            circle.setColour(color);
            circle.setErasable(Boolean.parseBoolean(data[6]));
            circle.setMovable(Boolean.parseBoolean(data[7]));
            circle.setOrigin(new Point((int) Double.parseDouble(data[8]), (int) Double.parseDouble(data[9])));
            circle.setRadius(Double.parseDouble(data[10]));
            result = circle;

        } else if (type.equals("Rectangle")) {
            Rectangle rectangle = new Rectangle();
            rectangle.setScale(scale);
            rectangle.setStrokeThickness(thickness);
            rectangle.setColour(color);
            rectangle.setErasable(Boolean.parseBoolean(data[6]));
            rectangle.setMovable(Boolean.parseBoolean(data[7]));
            rectangle.setOne(new Point((int) Double.parseDouble(data[8]), (int) Double.parseDouble(data[9])));
            rectangle.setOther(new Point((int) Double.parseDouble(data[10]), (int) Double.parseDouble(data[11])));
            rectangle.setWidth(Double.parseDouble(data[12]));
            rectangle.setHeight(Double.parseDouble(data[13]));
            rectangle.setMinX(Double.parseDouble(data[14]));
            rectangle.setMinY(Double.parseDouble(data[15]));
            result = rectangle;

        }

        System.out.println(result.toString());
        return result;
    }

    public ArrayList<Displayable> stringToState(String s) {
        ArrayList<Displayable> result = new ArrayList<>();
        String lines[] = s.split(SEPARATOR);

        System.out.println("[INFO] JSketchFileManager.stringToState(): Displayables created:");
        for (int i = 0; i < lines.length; ++i) {
            result.add(stringToDisplayable(lines[i]));
        }

        // Don't forget to set the base layer to be un-erasable
        result.get(0).setErasable(false);

        return result;
    }

    public void saveToFile(File selectedFile) {
        if (selectedFile == null) {
            System.out.println("[EROR] JSketchFileManager.saveToFile(): trying to save to null");
            return;
        }

        if (!selectedFile.getName().toLowerCase().endsWith(EXTENSION)) {
            selectedFile = new File(selectedFile.getParentFile(), selectedFile.getName()+EXTENSION);
        }
        String data = this.stateToString(m_controller.getCanvas().m_displayables);

        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile.getAbsoluteFile()));
            writer.write(data);
            writer.close();
            Desktop.getDesktop().open(selectedFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadFile(File selectedFile) {
        if (selectedFile == null) {
            System.out.println("[EROR] JSketchFileManager.loadFile(): trying to load null");
            return;
        }

        String data = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(selectedFile.getAbsolutePath()));
            String temp;
            while ((temp = br.readLine()) != null) data += temp;
        } catch (Exception e) {
            e.printStackTrace();
        }

        m_controller.getCanvas().setCurrDisplayables(this.stringToState(data));
        m_controller.getCanvas().repaint();
    }

}
