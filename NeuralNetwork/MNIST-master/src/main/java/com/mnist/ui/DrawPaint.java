package com.mnist.ui;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.RenderedImage;
import javax.swing.JComponent;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import javax.imageio.ImageIO;
import com.mortennobel.imagescaling.ResampleFilters;
import com.mortennobel.imagescaling.ResampleOp;

public class DrawPaint extends JComponent {

	private Image image;
	private Graphics2D g2;
	private int currentX, currentY, oldX, oldY;

	public DrawPaint() {
		setDoubleBuffered(false);
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				oldX = e.getX();
				oldY = e.getY();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				currentX = e.getX();
				currentY = e.getY();

				if (g2 != null) {
					g2.drawLine(oldX, oldY, currentX, currentY);
					repaint();
					oldX = currentX;
					oldY = currentY;
				}
			}
		});
	}

	protected void paintComponent(Graphics g) {
		if (image == null) {
			image = createImage(getSize().width, getSize().height);
			g2 = (Graphics2D) image.getGraphics();
//			g2.setStroke(new BasicStroke(12));
			g2.setStroke(new BasicStroke(40));
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			clear();
		}

		g.drawImage(image, 0, 0, null);
	}

	// now we create exposed methods
	public void clear() {
		g2.setPaint(Color.white);
		// draw white on entire draw area to clear
		g2.fillRect(0, 0, getSize().width, getSize().height);
		g2.setPaint(Color.black);
		repaint();
		
        String path = new File("").getAbsolutePath();
		String imgFilePath = path + "/saved.png";
		String imgFilePath1 = path + "/scaled.png";
        try
        { 
            Files.deleteIfExists(Paths.get(imgFilePath)); 
            Files.deleteIfExists(Paths.get(imgFilePath1)); 
        } 
        catch(NoSuchFileException e) 
        { 
            System.out.println("No such file/directory exists"); 
        } 
        catch(DirectoryNotEmptyException e) 
        { 
            System.out.println("Directory is not empty."); 
        } 
        catch(IOException e) 
        { 
            System.out.println("Invalid permissions."); 
        } 
          
        System.out.println("Cleared Image"); 
	}

	public void convertToPixels() throws IOException, InterruptedException {

		BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null),
				BufferedImage.TYPE_INT_RGB);
		File outputfile = new File("saved.png");
		try {
			ImageIO.write((RenderedImage) image, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		bufferedImage.flush();
		
		BufferedImage img1 = null;
		try {
		    img1 = ImageIO.read(new File("saved.png"));
		} catch (IOException e) {
		}
		Image scaled = scale(img1);
		BufferedImage scaledBuffered = toBufferedImage(scaled);
		ImageIO.write(scaledBuffered, "png", new File("scaled.png"));
	}

	private static BufferedImage scale(BufferedImage imageToScale) {
		ResampleOp resizeOp = new ResampleOp(28, 28);
		resizeOp.setFilter(ResampleFilters.getLanczos3Filter());
		BufferedImage filter = resizeOp.filter(imageToScale, null);
		return filter;
	}

	private static BufferedImage toBufferedImage(Image img) {
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();
		return bimage;

	}

}