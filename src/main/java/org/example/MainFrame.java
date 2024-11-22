package org.example;

import toolBarData.ToolBar;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame
{
	private Canvas canvas;
	private ToolBar toolBar;

	public MainFrame(int width, int height)
	{
		this.setSize(width, height);
		this.setVisible(true);
		this.setLayout(new BorderLayout());
		this.setTitle("Canvas");
		this.setResizable(false);
		this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		toolBar = new ToolBar();
		canvas = new Canvas(width, height - toolBar.getHeight(), toolBar);
		toolBar.setCanvas(canvas);

		this.add(canvas, BorderLayout.CENTER);
		this.add(toolBar, BorderLayout.SOUTH);
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> new MainFrame(800,800));
	}
}
