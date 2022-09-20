package com.m4rc310.rcp.constants.injects;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.model.application.MApplication;
import org.eclipse.e4.ui.model.application.MContribution;
import org.eclipse.e4.ui.model.application.ui.MUIElement;
import org.eclipse.e4.ui.model.application.ui.basic.MTrimmedWindow;
import org.eclipse.e4.ui.workbench.modeling.EModelService;

public class MInject {
	@Inject
	@Optional
	EModelService modelService;

	@Inject
	@Optional
	MApplication app;

	private MTrimmedWindow window;

	public void start(MTrimmedWindow window) {
		this.window = window;
	}

	public void invoke(Class<? extends Annotation> type) {
		invoke(type, null);
	}

	public void invoke(Class<? extends Annotation> type, Object valueDefault) {
		
		app.getAddons().forEach(addon -> {
			
			ContextInjectionFactory.invoke(addon.getObject(), type, window.getContext(), null);
		});

		List<MContribution> elements = modelService.findElements(window, null, MContribution.class, null,
				EModelService.ANYWHERE);
		elements.forEach(element -> {
			Object object = element.getObject();
			if (object != null) {
				ContextInjectionFactory.invoke(element.getObject(), type, window.getContext(), null);
			}
		});
	}
	
	public List<MUIElement> list(String ... ids){
		List<MUIElement> list = new ArrayList<>();
		for (String id : ids) {
			List<MUIElement> elements =  modelService.findElements(app, id, MUIElement.class, Collections.<String>emptyList(),
				EModelService.IN_MAIN_MENU | EModelService.ANYWHERE);
			list.addAll(elements);
		}
		return list;
	}
	
	public MUIElement get(String id) {
		List<MUIElement> elements = modelService.findElements(app, id, MUIElement.class, Collections.<String>emptyList(),
				EModelService.IN_MAIN_MENU | EModelService.ANYWHERE); 
		return elements.isEmpty() ? null : elements.get(0);
	} 

	public <T> T get(Class<T> type, String id) {
		List<T> elements = modelService.findElements(app, id, type, Collections.<String>emptyList(),
				EModelService.IN_MAIN_MENU | EModelService.ANYWHERE); 
		return elements.isEmpty() ? null : elements.get(0);
	}

}
