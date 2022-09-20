package com.m4rc310.rcp.ui.utils;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.EclipseContextFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

import com.m4rc310.rcp.ui.utils.streaming.MEvent;
import com.m4rc310.rcp.ui.utils.streaming.MListener;
import com.m4rc310.rcp.ui.utils.streaming.MStreamLocal;

public class MAction implements IMAction {
	
	private final Map<String, IMAction> mapInstances = new HashMap<>();
	
	protected final MStreamLocal stream;
	
	protected final IEclipseContext context;

	private boolean isRaised = false;
	
	
	
	public MAction() {
		this.stream = new MStreamLocal();
		context = EclipseContextFactory.create();
	}
	
	
	
	@Override
	public IEclipseContext getContext() {
		return context;
	}
	
	@Override
	public MStreamLocal getStream() {
		return stream;
	}
	
	@Override
	public void removeListeners() {
		removeListeners(this);
	}
	
	@Override
	public void removeListeners(Object target) {
		stream.removeListenerFromTarget(target);
	}
	
	@Override
	public void addListener(String property, MListener listener) {
        addListener(this, property, listener);
    }
	
	@Override
	public void addListener(Object target,String property, MListener listener) {
		stream.addListener(target, property, listener);
	}
	
	@Override
	public void putListener(String property, MListener listener) {
		stream.putListener(this, property, listener);
	}
	
	
	@Override
	public void fireInCache(String ref, Object ... args) {
		stream.fireListenerInCache(MEvent.event(this, ref, args));
	}
	@Override
	public void fire(String ref, Object ... args) {
//		Runnable runnable = ()->{
			stream.fireListener(MEvent.event(this, ref, args));
//		};
//		runnable.run();
	}

	@Override
	public void remove(IMAction action) {
		mapInstances.values().removeIf(thisAction->thisAction.equals(action));
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T extends IMAction> T getInstance(String ref, Class<T> type) {
		if (mapInstances.containsKey(ref)) {
			T instance = (T) mapInstances.get(ref);
			instance.setRaised(false);
			return instance;
		}
		final T instance = ContextInjectionFactory.make(type, context);
		instance.setRaised(true);
		
		MApplication app = context.get(MApplication.class);
		IEclipseContext appCtx = app.getContext();
		appCtx.set(IMAction.class, instance);
		
		removeListeners(instance);
		mapInstances.put(ref, instance);
		return instance;
	}
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void setRaised(boolean raised) {
		this.isRaised = raised;
	}


	@Override
	public boolean wasRaised() {
		return isRaised ;
	}
	
}
