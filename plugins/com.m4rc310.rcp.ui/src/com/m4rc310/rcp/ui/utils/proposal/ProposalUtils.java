package com.m4rc310.rcp.ui.utils.proposal;

import org.eclipse.jface.viewers.ComboViewer;

public class ProposalUtils {
	public static <T> void enableContentProposal(ComboViewer comboViewer, IProposalController<T> controller){
		MComboViewerContentProposalProvider<T> provider = new MComboViewerContentProposalProvider<T>(comboViewer);
		provider.setProposalController(controller);
		provider.initProposal();
	}
}
