package com.m4rc310.rcp.ui.utils.custom.controls;

import java.util.Objects;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Composite;

public class MPushLabel extends MCanvas {

	private static final int GAP = 12;
	private static int DRAW_FLAGS = SWT.DRAW_MNEMONIC | SWT.DRAW_TAB | SWT.DRAW_TRANSPARENT | SWT.DRAW_DELIMITER;
	private static final int MAX_NUMBER_OF_STEPS = 10;

	private String text;

	private Point textSize;

	private int stepMotion = 0;

	private Font font;

	private Image image;

	private int minHeight = 80;

	private int minWidth = 80;

	public MPushLabel(Composite parent, int style) {
		super(parent, style);
//		super(parent, style | SWT.BORDER | SWT.DOUBLE_BUFFERED);
		this.init();
	}

	private void init() {
		Font defaultFont = new Font(getDisplay(), super.getFont().getFontData()[0].getName(), 10, SWT.BOLD);
		font = defaultFont;

		addDisposer(this, defaultFont);
		addClickListener();
		addPaintListener(e -> onPaint(e));
	}

	private void onPaint(final PaintEvent event) {
		final Rectangle rect = getClientArea();
		if (rect.width == 0 || rect.height == 0) {
			return;
		}
		
		Point size = getTotalSize2();
		rect.width = Math.max(size.x, rect.width);
		rect.height = Math.max(size.y, rect.height);

		final Image bufferImage = new Image(getDisplay(), Math.max(1, rect.width), Math.max(1, rect.height));

		final GC gc = new GC(bufferImage);
		gc.setAdvanced(true);
		gc.setAntialias(SWT.ON);
		gc.setForeground(getDisplay().getSystemColor(SWT.COLOR_DARK_GRAY));
		gc.setBackground(getDisplay().getSystemColor(SWT.COLOR_CYAN));

//		gc.fillRectangle(rect);
		gc.fillRoundRectangle(0, 0, rect.width, rect.height, 50, 50);

		final Point extent = getTotalSize(0, 0);

		gc.setFont(font);
		textSize = gc.textExtent(text, DRAW_FLAGS);
		final int xText = (rect.width - textSize.x) / 2;
		final int yText = (rect.height - textSize.y) / 2;
		gc.drawString(text, xText, yText);

		if (stepMotion != 0) {
			final float zoom = 1f + stepMotion * (Math.max(extent.x, extent.y)) / MAX_NUMBER_OF_STEPS / 100f;
			System.out.println(zoom);
		}

		gc.dispose();
		event.gc.drawImage(bufferImage, 0, 0);
		bufferImage.dispose();
	}

	private Point getTotalSize2() {
		final Point size = new Point(0, 0);
		size.x = Math.max(getTextSize().x, getImageSize().x);
		size.y = Math.addExact(getTextSize().y, getImageSize().y);
		size.y = Math.addExact(size.y, GAP);
		return size;
	}

	private Point getTextSize() {
		final GC gc = new GC(this);
		Point size = new Point(0, 0);
		if (Objects.nonNull(text)) {
			size = gc.textExtent(text, DRAW_FLAGS);
		}
		gc.dispose();
		return size;
	}

	private Point getImageSize() {
		final GC gc = new GC(this);
		gc.setFont(font);
		Point size = new Point(0, 0);
		if (Objects.nonNull(image)) {
			size = getTotalSize(image.getBounds().width, image.getBounds().height);
		}
		gc.dispose();
		return size;
	}

	private void addClickListener() {
		addListener(SWT.MouseUp, e -> {
			System.out.println("Click");
		});
	}

	private Point getTotalSize(final int imgWidth, final int imgHeight) {
		final Point size = new Point(0, 0);

		int textWidth = 0;
		int textHeight = 0;

		if (textSize == null) {
			final GC gc = new GC(this);
			gc.setFont(font);

			textSize = gc.textExtent(text, DRAW_FLAGS);
			gc.dispose();
		}

		textWidth = textSize.x;
		textHeight = textSize.y;

		size.x = Math.max(imgWidth, textWidth);
		size.y = imgHeight + GAP + textHeight;

		return size;
	}

	boolean incrementAnimation() {
		stepMotion++;
		final boolean stopAnimation = stepMotion > MAX_NUMBER_OF_STEPS;

		if (stopAnimation) {
			stepMotion = 0;
		}
		if (!isDisposed()) {
			redraw();
		}
		return !stopAnimation;
	}

	@Override
	public Point computeSize(int wHint, int hHint, boolean changed) {
		checkWidget();
		final Point e = new Point(80, 60);
		return e;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public int getMinHeight() {
		return minHeight;
	}

	public void setMinHeight(int minHeight) {
		this.minHeight = minHeight;
	}

	public int getMinWidth() {
		return minWidth;
	}

	public void setMinWidth(int minWidth) {
		this.minWidth = minWidth;
	}

}
