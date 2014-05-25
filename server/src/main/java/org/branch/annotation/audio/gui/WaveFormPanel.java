package org.branch.annotation.audio.gui;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import org.branch.annotation.audio.model.Sample;


public class WaveFormPanel extends JPanel {
	private long big;
	private Sample[] data;

	protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        int lineHeight = getHeight() / 2;
        g.setColor(Color.BLACK );
        g.drawLine(0, lineHeight, (int) getWidth(), lineHeight);

        drawWaveform(g);
    }
	
	public void setSamples(Sample[] sampleData, long big) {
		this.data = sampleData;
		this.big = big;
		
		this.repaint();
	}
	
	public void drawWaveform(Graphics gs) {
//		GradientPaint top = new GradientPaint(0, 0, Color.RED, 0, getHeight() / 2, Color.GREEN);
//		GradientPaint bottom = new GradientPaint(0, getHeight() / 2, Color.GREEN, 0, getHeight(), Color.RED);
		
		Graphics2D g = (Graphics2D) gs;

		g.setColor( Color.GREEN );
		
		int step = data.length / getWidth();
		
		int scale = (int) (big / (double) getHeight());
		scale = Math.max( scale , 1 );
		int x = 0;
		int remainder = getWidth() / step;
		int center = getHeight() / 2;
		
		for ( int i = 0; i < data.length; i++, x++) {
//			long sum = 0;
			long max = 0;
			long min = 0;
			for ( int s = 0; s < step && i < data.length; s++, i++) {
//				sum += Math.abs( data.get(i).getSamples()[ channel ] );
				max = Math.max( max, data[i].getValue() );
				min = Math.min( min, data[i].getValue() );
			}
			
			//int value = (int) ((sum / step) / scale);
			int value = (int) ((max + (min * -1) ) / scale);
			
//			int y1 = (getHeight() - (value / 2) )  / 2;
//			int y2 = (getHeight() / 2) + (value / 2);
//			g.drawLine(x, y1, x, y2)/;			
			int y = value / 2;
			g.drawLine(x, center - y, x, center + y );
			
//			g.setPaint(top);
//			g.drawLine(x, y1, x, getHeight() / 2);
			
//			g.setPaint(bottom);
//			g.drawLine(x, getHeight() / 2, x, y2);
		}
	}
}
