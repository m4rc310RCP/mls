package com.m4rc310.rcp.ui.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

public class MCheckboxTableViewer extends CheckboxTableViewer {

	private ListenerList<MCheckListener> mCheckStateListeners = new ListenerList<>();
	
	

	public MCheckboxTableViewer(Table table) {
		super(table);
		init();
	}

	private void init() {

		addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				sfire(event.getSource(),getCheckedListElements(), getAllElements());
			}
		});

		getTable().addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {

				if (e.keyCode == SWT.SPACE) {
					try {
						IStructuredSelection selection = getStructuredSelection();
						Object selected = selection.getFirstElement();
						e.data = selected;
						boolean check = getChecked(selected);
						setChecked(selected, !check);
						sfire(MCheckboxTableViewer.this, getCheckedListElements(), getAllElements());
					} catch (Exception e2) {
					}
				}

				super.keyPressed(e);
			}
		});
	}
	
//	@Override
//	protected void inputChanged(Object input, Object oldInput) {
//		super.inputChanged(input, oldInput);
//		if(inputListener != null) {
//			inputListener.process(this, getAllElements());
//		}
//	}
	
	@Override
	protected void inputChanged(Object input, Object oldInput) {
		super.inputChanged(input, oldInput);
	}
	
	
	
	
	public void putMInputListener(MInputListener listener) {
	}

	public void addMCheckStateListener(MCheckListener listener) {
		mCheckStateListeners.add(listener);
	}

	@Override
	public void setAllChecked(boolean state) {
		super.setAllChecked(state);
//		fireMCheckStateListener(getCheckedElements());
		sfire(this, getCheckedListElements(), getAllElements());
	}

	@Override
	public void setCheckedElements(Object[] elements) {
		super.setCheckedElements(elements);
//		fireMCheckStateListener(getCheckedElements());
		sfire(this, getCheckedListElements(), getAllElements());
	}
	
	private List<?> getAllElements(){
		TableItem[] children = getTable().getItems();
		List<Object> v = new ArrayList<>(children.length);
		for (TableItem item : children) {
			Object data = item.getData();
			if (data != null) {
					v.add(data);
			}
		}
		return v;
	}

	private List<?> getCheckedListElements(){
		TableItem[] children = getTable().getItems();
		List<Object> v = new ArrayList<>(children.length);
		for (TableItem item : children) {
			Object data = item.getData();
			if (data != null) {
				if (item.getChecked()) {
					v.add(data);
				}
			}
		}
		return v;
	}
	
	private void sfire(Object source,List<?> checkeds, List<?> all) {
		mCheckStateListeners.forEach(listener->{
//			SafeRunnable.run(()->{
				MCheckboxTableViewer viewer = (MCheckboxTableViewer) source;
				listener.process(viewer, checkeds, all);
//			});
		});
	}
	
	
//	private void fireMCheckStateListener(Object[] checkeds) {
//		for (IMCheckListener l : mCheckStateListeners) {
//			SafeRunnable.run(new SafeRunnable() {
//				@Override
//				public void run() {
//					l.loadCheckedObjects(checkeds);
//				}
//			});
//		}
//	}

	public static MCheckboxTableViewer newCheckList(Composite parent, int style) {
		Table table = new Table(parent, SWT.CHECK | style);
		return new MCheckboxTableViewer(table);
	}
	
	public interface MCheckListener {
		void process(MCheckboxTableViewer viewer, List<?> selectedItens, List<?> allItens);
	}
	
	public interface MInputListener {
//		void eventChange(MEvent event);
		void process(MCheckboxTableViewer viewer, List<?> allItens);
	}
	
	
	
	

}
