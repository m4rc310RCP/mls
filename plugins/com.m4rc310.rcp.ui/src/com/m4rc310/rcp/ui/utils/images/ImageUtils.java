package com.m4rc310.rcp.ui.utils.images;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;

public class ImageUtils {

	private static int DRAW_FLAGS = SWT.DRAW_MNEMONIC | SWT.DRAW_TAB | SWT.DRAW_TRANSPARENT | SWT.DRAW_DELIMITER;

	public static Image makeMockImage(Device device, String text, int width, int height) {
		Rectangle rect = new Rectangle(0, 0, width, height);
		
		Font deviceFont = device.getSystemFont();
		Font font = new Font(device, deviceFont.getFontData()[0].getName(), 10, SWT.NONE);

		final Image bufferImage = new Image(device, Math.max(1, width), Math.max(1, height));
		final GC gc = new GC(bufferImage);
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		gc.setFont(font);
		

		gc.setForeground(device.getSystemColor(SWT.COLOR_GRAY));
		gc.setBackground(device.getSystemColor(SWT.COLOR_DARK_GRAY));
		gc.fillRectangle(rect);
		gc.drawLine(0, 0, width, height);
		gc.drawLine(width, 0, 0, height);
		
		gc.setBackground(device.getSystemColor(SWT.COLOR_YELLOW));
		gc.setForeground(device.getSystemColor(SWT.COLOR_DARK_RED));
		text = formatText(gc, text, rect);
		Point extent = gc.textExtent(text);
		
		int x = (rect.width/2)  - (extent.x/2);
		int y = (rect.height/2) - (extent.y/2);
		gc.drawString(text, x, y, false);

		return bufferImage;
	}

	private static String formatText(GC gc, String text, Rectangle rect) {
		String stext = String.format("%s [%dx%d]", text, rect.width, rect.height);
		Point extent = gc.textExtent(stext, DRAW_FLAGS);
		boolean overflow = extent.x > rect.width || extent.y > rect.height;
		if (overflow) {
			stext = String.format("[%dx%d]", rect.width, rect.height);
			extent = gc.textExtent(stext, DRAW_FLAGS);
			
			overflow = extent.x > rect.width || extent.y > rect.height;
			if (overflow) {
				stext = "";
			}
		}
		return stext;
	}
	
	
	
	
}
