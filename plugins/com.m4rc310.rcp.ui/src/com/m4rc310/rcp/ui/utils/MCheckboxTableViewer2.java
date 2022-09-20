package com.m4rc310.rcp.ui.utils;

import java.util.List;

import org.eclipse.core.runtime.ListenerList;
import org.eclipse.jface.util.SafeRunnable;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTableViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;

public class MCheckboxTableViewer2 extends CheckboxTableViewer {

	private ListenerList<IMCheckListener> mCheckStateListeners = new ListenerList<>();

	public MCheckboxTableViewer2(Table table) {
		super(table);
		init();
	}

	private void init() {

		addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				fireMCheckStateListener(getCheckedElements());
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
						fireMCheckStateListener(getCheckedElements());
					} catch (Exception e2) {
					}
				}

				super.keyPressed(e);
			}
		});
	}

	public void addMCheckStateListener(IMCheckListener listener) {
		mCheckStateListeners.add(listener);
	}

	@Override
	public void setAllChecked(boolean state) {
		super.setAllChecked(state);
		fireMCheckStateListener(getCheckedElements());
	}

	@Override
	public void setCheckedElements(Object[] elements) {
		super.setCheckedElements(elements);
		fireMCheckStateListener(getCheckedElements());
	}

	private void fireMCheckStateListener(Object[] checkeds) {
		for (IMCheckListener l : mCheckStateListeners) {
			SafeRunnable.run(new SafeRunnable() {
				@Override
				public void run() {
					l.loadCheckedObjects(checkeds);
				}
			});
		}
	}

	public static MCheckboxTableViewer2 newCheckList(Composite parent, int style) {
		Table table = new Table(parent, SWT.CHECK | style);
		return new MCheckboxTableViewer2(table);
	}
	
	public interface MCheckListener {
		void process(MCheckboxTableViewer2 viewer, List<?> selectedItens, List<?> allItens);
	}
	
	

}
