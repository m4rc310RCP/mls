package com.m4rc310.rcp.ui.utils.custom.controls;

import java.util.Objects;

import org.eclipse.swt.graphics.Resource;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Widget;

public class MCanvas extends Canvas {

	public MCanvas(Composite parent, int style) {
		super(parent, style);
	}

	protected void addDisposer(final Widget widget, final Resource... resources) {
		widget.addDisposeListener(event->{
			if (Objects.isNull(resources)) {
				return;
			}
			for (Resource resource : resources) {
				if (resource != null && !resource.isDisposed()) {
					resource.dispose();
				}
			}
		});
	}
}
