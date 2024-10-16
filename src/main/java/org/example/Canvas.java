package org.example;

import rasterData.RasterBI;
import rasterOps.TrivialLiner;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * trida pro kresleni na platno: zobrazeni pixelu
 *
 * @author PGRF FIM UHK
 * @version 2020
 */

public class Canvas
{
	private JFrame frame;
	private JPanel panel;
	private RasterBI img;

	private int currentX;
	private int currentY;

	private TrivialLiner liner;
	private int r1;
	private int c1;
	private int r2;
	private int c2;

	private List<int[]> lines;

	public Canvas(int width, int height)
	{
		frame = new JFrame();
		img = new RasterBI(width, height);
		liner = new TrivialLiner();
		lines = new ArrayList<>();

		frame.setLayout(new BorderLayout());
		frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
		frame.setResizable(false);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		currentX = img.getWidth() / 2;
		currentY = img.getHeight() / 2;

		panel = new JPanel()
		{
			private static final long serialVersionUID = 1L;

			@Override
			public void paintComponent(Graphics g)
			{
				super.paintComponent(g);
				img.present(g);
			}
		};

		panel.setPreferredSize(new Dimension(width, height));

		frame.addMouseListener(new MouseAdapter()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				r1 = e.getY();
				c1 = e.getX();
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				lines.add(new int[]{c1, r1, e.getX(), e.getY()});
			}
		});

		frame.addMouseMotionListener(new MouseMotionAdapter()
		{
			@Override
			public void mouseDragged(MouseEvent e)
			{
				img.clear();

				for (int[] line : lines)
				{
					liner.draw(img, line[0], line[1], line[2], line[3], 0xffff00);
				}

				liner.draw(img, c1, r1, e.getX(), e.getY(), 0xffff00);

				panel.repaint();
			}
		});

		frame.add(panel, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}

	public void start()
	{
		img.clear();
		panel.repaint();
	}

	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(() -> new Canvas(800, 800).start());
	}
}