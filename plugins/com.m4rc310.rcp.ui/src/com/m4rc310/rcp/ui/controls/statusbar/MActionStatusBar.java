package com.m4rc310.rcp.ui.controls.statusbar;

import java.text.MessageFormat;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.eclipse.e4.core.di.annotations.Creatable;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.SWT;

import com.m4rc310.rcp.ui.constants.MConsts;
import com.m4rc310.rcp.ui.utils.MAction;

@Creatable
@Singleton
public class MActionStatusBar extends MAction implements MStatusBarConstants, MConsts {

	@Inject
	IEventBroker eventBroker;

	@Inject
	EModelService model;

	@Inject
	MApplication app;

	@Inject
	UISynchronize sync;
	
	@Inject
	public void startStatusBar() {
		eventBroker.subscribe(LOG$print_ln, e->{
			String text = (String) e.getProperty(IEventBroker.DATA);
			message(text);
		});
	}
	
	
	public void alert(String message) {
		eventBroker.send(EVENT_TOPIC$statusbar_alert_icon, "platform:/plugin/com.m4rc310.rcp.ui/icons/16x16/others/comments.png");
	}

	public void message(String message, Object... args) {
		String smessage = MessageFormat.format(message, args);
		sendMessage(TypeMessage.NONE, smessage);
	}

	boolean isClearStatusbar = true;
	int idelay = 0;

	public void clearStatusbar(int delay) {
		idelay = delay;
		if (isClearStatusbar) {
			new Timer().schedule(new TimerTask() {
				@Override
				public void run() {
					if (idelay <= 0) {

						sendMessage(TypeMessage.NONE, "");
						isClearStatusbar = true;
						cancel();
					} else {
						idelay--;
					}
				}
			}, 0, 1000);

			isClearStatusbar = false;
		}
	}

	public void info(String message, Object... args) {
		message = MessageFormat.format(message, args);
		sendMessage(TypeMessage.INFO, message);
	}

	public void error(String message, Object... args) {
		message = MessageFormat.format(message, args);
		sendMessage(TypeMessage.ERROR, message);
	}

	private void sendMessage(TypeMessage type, String message) {
		MUIElement toolcontrol = model.find(ID$toolcontrol_streaming, app);
		sync.asyncExec(()-> toolcontrol.setToBeRendered(false));

		int icolor;

		switch (type) {
		case ERROR:
			icolor = SWT.COLOR_RED;
			break;
		case INFO:
			icolor = SWT.COLOR_BLACK;
			break;
		default:
			icolor = SWT.COLOR_BLACK;
		}

		app.getContext().set(NAMED$statusbar_streaming_icon, type.getIconUri());
		app.getContext().set(NAMED$statusbar_streaming_message_color, icolor);
		app.getContext().set(NAMED$statusbar_streaming_message, message);

		sync.asyncExec(()-> toolcontrol.setToBeRendered(true));
	}
}
