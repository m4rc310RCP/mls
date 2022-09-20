package com.m4rc310.rcp.ui.utils.services;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IContextFunction;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;
import org.osgi.service.component.annotations.Component;


@Component(service = IContextFunction.class, property = "service.context.key=com.m4rc310.rcp.ui.utils.services.IInitService")
public class ServiceContextFunction extends ContextFunction {

	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		IInitService action = ContextInjectionFactory.make(IInitServiceImpl.class, context);
		MApplication app = context.get(MApplication.class);
		IEclipseContext appCtx = app.getContext();
		appCtx.set(IInitService.class, action);
		return action;
	}
}
