package com.m4rc310.rcp.ui.controls.toolcontrols;

import javax.annotation.PostConstruct;

import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import com.m4rc310.rcp.ui.controls.statusbar.MStatusBarConstants;
import com.m4rc310.rcp.ui.utils.PartControl;


public class ToolcontrolStatusBarAlertIcon implements MStatusBarConstants {
	
//	@Inject @Optional @Named() String uriIcon;
	
	@PostConstruct
	public void createGui(Composite parent, PartControl pc, IEventBroker eventBroker) {
		parent.setLayout(new GridLayout());
		pc.clearMargins(parent);
		
		Label labelIcon = pc.getLabel(parent, "");
		
		GridData gd = new GridData(SWT.CENTER, SWT.CENTER, true, true);
		gd.widthHint = 16;
		gd.heightHint = 16;
		labelIcon.setLayoutData(gd);
		
		eventBroker.subscribe(EVENT_TOPIC$statusbar_alert_icon, new EventHandler() {
			
			@Override
			public void handleEvent(Event event) {
				String uri = (String) event.getProperty(IEventBroker.DATA);
				labelIcon.setImage(pc.getImage(uri));
				eventBroker.unsubscribe(this);
			}
		});
		
		
	}
}