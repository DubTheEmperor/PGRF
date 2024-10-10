package org.example;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

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
    private BufferedImage img;

    private int currentX;
    private int currentY;

    public Canvas(int width, int height) {
        frame = new JFrame();

        frame.setLayout(new BorderLayout());
        frame.setTitle("UHK FIM PGRF : " + this.getClass().getName());
        frame.setResizable(false);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        currentX = img.getWidth() / 2;
        currentY = img.getHeight() / 2;

        panel = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                present(g);
            }
        };

        panel.setPreferredSize(new Dimension(width, height));

        frame.addKeyListener(new KeyAdapter()
        {
            @Override
            public void keyPressed(KeyEvent e)
            {
                clear();
                if(e.getKeyCode() == KeyEvent.VK_UP)
                {
                    currentY--;
                    img.setRGB(currentX, currentY,0xffff00);
                    panel.repaint();
                }

                if(e.getKeyCode() == KeyEvent.VK_DOWN)
                {
                    currentY++;
                    img.setRGB(currentX, currentY,0xffff00);
                    panel.repaint();
                }

                if(e.getKeyCode() == KeyEvent.VK_RIGHT)
                {
                    currentX++;
                    img.setRGB(currentX, currentY,0xffff00);
                    panel.repaint();
                }

                if(e.getKeyCode() == KeyEvent.VK_LEFT)
                {
                    currentX--;
                    img.setRGB(currentX, currentY,0xffff00);
                    panel.repaint();
                }
            }
        });

        frame.add(panel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
    }

    public void clear() {
        Graphics gr = img.getGraphics();
        gr.setColor(new Color(0x2f2f2f));
        gr.fillRect(0, 0, img.getWidth(), img.getHeight());
    }

    public void present(Graphics graphics) {
        graphics.drawImage(img, 0, 0, null);
    }

    public void draw() {
        clear();
//        img.setRGB(img.getWidth() / 2, img.getHeight() / 2,0xffff00);

//        for (int i = 0; i < img.getHeight(); i++)
//        {
//            for (int j = 0; j < img.getWidth(); j++)
//            {
//                if(i == j || i + j == img.getWidth())
//                {
//                    img.setRGB(i, j,0xff0000);
//                }
//            }
//        }


    }

    public void start() {
        draw();
        panel.repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Canvas(800, 800).start());
    }
}