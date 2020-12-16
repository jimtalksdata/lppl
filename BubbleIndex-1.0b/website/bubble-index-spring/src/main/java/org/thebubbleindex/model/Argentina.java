package org.thebubbleindex.model;

public class Argentina extends BubbleIndexTimeseries {
    
	protected Argentina() {}
	
	public Argentina(final String name, final String symbol, final String dtype, final String keywords) {
		this.symbol = symbol;
		this.dtype = dtype;		
		this.name = name;
		this.keywords = keywords;
	}
}
