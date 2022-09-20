package com.m4rc310.rcp.ui.utils.proposal;

import java.util.ArrayList;

import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.jface.fieldassist.ComboContentAdapter;
import org.eclipse.jface.fieldassist.ContentProposalAdapter;
import org.eclipse.jface.fieldassist.IContentProposal;
import org.eclipse.jface.fieldassist.IContentProposalProvider;
import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;

public class MComboViewerContentProposalProvider<T> {

//	implements IContentProposalProvider

	private static final String LCL = "abcdefghijklmnopqrstuvwxyz";
	private static final String UCL = LCL.toUpperCase();
	private static final String NUMS = "0123456789";

	private final ComboViewer comboViewer;
	private IProposalController<T> controller;

	public MComboViewerContentProposalProvider(ComboViewer comboViewer) {
		this.comboViewer = comboViewer;
	}

	public void initProposal() {
		IContentProposalProvider provider = new IContentProposalProvider() {
			@Override
			public IContentProposal[] getProposals(String contents, int position) {
				ArrayList<MContentProposal<T>> list = new ArrayList<>();

				controller.filter(contents).forEach(value -> {
					MContentProposal<T> proposal = new MContentProposal<T>(controller.toText(value));
					proposal.control = comboViewer;
					proposal.value = value;
					list.add(proposal);
				});

				return list.toArray(new IContentProposal[list.size()]);
			}
		};

		ContentProposalAdapter proposalAdapter = new ContentProposalAdapter(comboViewer.getCombo(),
				new ComboContentAdapter(), provider, getActivationKeystroke(), getAutoactivationChars());

		proposalAdapter.setPropagateKeys(true);
		proposalAdapter.setProposalAcceptanceStyle(ContentProposalAdapter.PROPOSAL_REPLACE);
		proposalAdapter.addContentProposalListener(e -> {
			@SuppressWarnings("unchecked")
			MContentProposal<T> proposal = (MContentProposal<T>) e;
			T value = proposal.value;
			ComboViewer viewer = (ComboViewer)proposal.control;
			viewer.setSelection(new StructuredSelection(value));
		});
		
	}
	
	public static <T> T cast(Object o, Class<T> clazz) {
	    return clazz.isInstance(o) ? clazz.cast(o) : null;
	}

	public void setProposalController(IProposalController<T> controller) {
		this.controller = controller;
	}

	static char[] getAutoactivationChars() {
		String delete = new String(new char[] { 8 });
		String allChars = LCL + UCL + NUMS + delete;
		return allChars.toCharArray();
	}

	static KeyStroke getActivationKeystroke() {
		KeyStroke instance = KeyStroke.getInstance(Integer.valueOf(SWT.CTRL).intValue(), Integer.valueOf(' ').intValue());
		return instance;
	}

}
