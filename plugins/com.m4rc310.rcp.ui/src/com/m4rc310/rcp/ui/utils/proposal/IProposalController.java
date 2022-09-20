package com.m4rc310.rcp.ui.utils.proposal;

import java.util.List;

public interface IProposalController<T> {

	public List<T> filter(String contents);

	public String toText(T t);
}
