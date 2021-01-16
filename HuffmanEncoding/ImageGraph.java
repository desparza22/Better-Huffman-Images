import java.util.*;
import java.awt.*;
import java.awt.Image.*;
import java.awt.image.*;
import java.awt.Container.*;

import javax.swing.*;
import java.io.*;
import javax.imageio.ImageIO;

import java.lang.Math;

public class ImageGraph {
  String fileName;
  FileWriter fw = null;

  public ImageGraph(String fileName) {
    this.fileName = fileName;
    createFile();
  }

  private void createFile() {
    try {
      File createdFile = new File(fileName);
      createdFile.createNewFile();
      fw = new FileWriter(fileName);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void write(String line) {
    try {
      fw.write(line);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void newLine() {
    try {
      fw.write(System.getProperty("line.separator"));
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public void close() {
    try {
      fw.close();
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  private int blueVal(int ARGBVal) {
    return (ARGBVal)&255;
  }
  private int greenVal(int ARGBVal) {
    return (ARGBVal>>8)&255;
  }
  private int redVal(int ARGBVal) {
    return (ARGBVal>>16)&255;
  }
  private int alphaVal(int ARGBVal) {
    return (ARGBVal>>24)&255;
  }
}
