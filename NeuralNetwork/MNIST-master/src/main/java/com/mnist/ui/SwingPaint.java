package com.mnist.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import com.mnist.data.Mnist;

public class SwingPaint {

	JButton clearBtn, trainBtn, testManualBtn;
	DrawPaint drawArea;
	ActionListener actionListener = new ActionListener() {

		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == trainBtn) {
				Mnist.trainAndTest();
			}
			else if (e.getSource() == clearBtn) {
				drawArea.clear();
			} else if (e.getSource() == testManualBtn) {
				try {
					drawArea.convertToPixels();
					int outputLabel = Mnist.testPaintedImage("scaled.png");
					System.out.println(outputLabel);
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	};

	public static void main(String[] args) {
		new SwingPaint().show();
	}

	public void show() {
		JFrame frame = new JFrame("Swing Paint");
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		drawArea = new DrawPaint();
		content.add(drawArea, BorderLayout.CENTER);

		JPanel controls = new JPanel();

		trainBtn = new JButton("Train & Test Bundle");
		trainBtn.addActionListener(actionListener);

		testManualBtn = new JButton("Test");
		testManualBtn.addActionListener(actionListener);

		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(actionListener);

		controls.add(trainBtn);
		controls.add(testManualBtn);
		controls.add(clearBtn);
		content.add(controls, BorderLayout.NORTH);

//    frame.setSize(200, 200);
		frame.setSize(400, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);

	}

}