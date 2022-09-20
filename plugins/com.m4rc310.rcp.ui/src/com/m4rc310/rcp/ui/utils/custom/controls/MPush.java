package com.m4rc310.rcp.ui.utils.custom.controls;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

public class MPush extends Composite {

	public MPush(Composite parent, int style) {
		super(parent, style | SWT.BORDER);
//		setBackground(getDisplay().getSystemColor(SWT.COLOR_WHITE));
		addListener(SWT.Resize, e->{
			System.out.println("Resize");
		});
	}
	

}
