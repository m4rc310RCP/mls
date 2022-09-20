package com.m4rc310.rcp.ui.utils.proposal;

import org.eclipse.jface.fieldassist.ContentProposal;

public class MContentProposal<T> extends ContentProposal {
	
	public Object control;
	public T value;

	public MContentProposal(String content) {
		super(content);
	}

}
