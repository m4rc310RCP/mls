
package com.m4rc310.rcp.ui.addons;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.Active;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.core.services.events.IEventBroker;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.di.UISynchronize;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.advanced.MPerspective;
import org.eclipse.e4.ui.workbench.IWorkbench;
import org.eclipse.e4.ui.workbench.UIEvents;
import org.eclipse.e4.ui.workbench.UIEvents.EventTags;
import org.eclipse.e4.ui.workbench.modeling.EModelService;
import org.eclipse.swt.widgets.Display;
import org.osgi.service.event.Event;

import com.m4rc310.rcp.ui.annotations.MActivatePerspective;
import com.m4rc310.rcp.ui.utils.PartControl;

public class AddonStartup {

	@Inject
	@Optional
	private IWorkbench workbench;
	
	@Inject
	@Optional
	private UISynchronize sync;
	
	@Inject @Optional
	Display display;
	
	@Inject
	@Optional
	private IEventBroker eventBroker;
	
//	@Inject
//	@Optional
	public void start(@UIEventTopic(UIEvents.UILifeCycle.APP_STARTUP_COMPLETE) Event event,
			EModelService modelService, MApplication app) {
		
		new Timer().schedule(new TimerTask() {
			@Override
			public void run() {
				if (workbench != null && display != null) {
					cancel();
					sync.asyncExec(() -> {
						eventBroker.subscribe(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT, e->{
							addPerspectiveListener(e, modelService, app);
						});
					});
				}
			}
		}, 0, 200);
	}
	
//	@MConstruct
	public void init(@Optional @Active PartControl pc) {
		System.out.println("gui utils");
		System.out.println(pc);
	}
	
//	@Inject
//	@Optional
	public void addPerspectiveListener(
			@UIEventTopic(UIEvents.ElementContainer.TOPIC_SELECTEDELEMENT) org.osgi.service.event.Event event,
			EModelService modelService, MApplication app) {

		Object value = event.getProperty(EventTags.NEW_VALUE);
		if (value instanceof MPerspective) {
			MPerspective perspective = (MPerspective) value;

			List<MUIElement> elements = modelService.findElements(app, null, MUIElement.class,
					Collections.emptyList(), EModelService.IN_MAIN_MENU | EModelService.IN_TRIM);

			elements.forEach(item -> {
				String sid1 = perspective.getElementId().trim();
				item.getTags().forEach(tag -> {

					if (tag.toLowerCase().contains("perspective:")) {
						item.setToBeRendered(false);
						String sid2 = tag.replace("perspective:", "").trim();
						if (sid1.equalsIgnoreCase(sid2)) {
							item.setToBeRendered(true);
						}
					}
				});
			});
			
			app.getAddons().forEach(addon -> {
				Class<?> type = addon.getObject().getClass();
				
				List<Method> methods = new ArrayList<Method>();
				methods.addAll(Arrays.asList(type.getMethods()));
//				methods.addAll(Arrays.asList(type.getDeclaredMethods()));
				
				methods.forEach(method -> {
					if (method.isAnnotationPresent(MActivatePerspective.class)) {
						if (method.isAnnotationPresent(MActivatePerspective.class)) {
							MActivatePerspective mActivatePerspective = method.getAnnotation(MActivatePerspective.class);
							String perspectiveId = mActivatePerspective.perspectiveId();
							
							if (perspectiveId.equalsIgnoreCase(perspective.getElementId())) {
								IEclipseContext context = app.getContext();
								context.set(MPerspective.class,(MPerspective)value);
								ContextInjectionFactory.invoke(addon.getObject(), MActivatePerspective.class, context, null);
							}
						}
					}
				});
				
			});

		}
	}

}
