package com.paytm.insider.hackernews.util;



public class PairParentChild {
       
	private Long parent;
	private Long child;

	public PairParentChild(Long child,Long parent) {
		this.child=child;
		this.parent=parent;
	}

	public Long getParent() {
		return parent;
	}

	public void setParent(Long parent) {
		this.parent = parent;
	}

	public Long getChild() {
		return child;
	}

	public void setChild(Long child) {
		this.child = child;
	}
}
