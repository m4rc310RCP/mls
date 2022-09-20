package com.m4rc310.rcp.ui.controls.toolcontrols;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import com.m4rc310.rcp.ui.controls.statusbar.MStatusBarConstants;
import com.m4rc310.rcp.ui.utils.PartControl;

public class ToolcontrolStatusBarStreaming implements MStatusBarConstants{
	
	@Inject PartControl pc; 
	@Inject @Optional @Named(NAMED$statusbar_streaming_icon) String imageIconUri;
	@Inject @Optional @Named(NAMED$statusbar_streaming_message) String message;
	@Inject @Optional @Named(NAMED$statusbar_streaming_message_color) int messageForegroud;
	
	@PostConstruct
	public void createGui(Composite parent) {
		parent.setLayout(new GridLayout(2, false));
		pc.clearMargins(parent);
		
		Label labelIcon = pc.getLabel(parent, "");
		labelIcon.setImage(pc.getImage(imageIconUri));
		
		GridData gd = new GridData(SWT.NONE, SWT.CENTER, true, true );
		labelIcon.setLayoutData(gd);
		
		Label labelMessage = pc.getLabel(parent, message);
		labelMessage.setForeground(pc.getColor(messageForegroud));
		gd = new GridData(SWT.NONE, SWT.CENTER, true, true );
		gd.minimumWidth = 330;
		labelMessage.setLayoutData(gd);
	}
}