package org.thebubbleindex.model;

public class CompositeFifty extends BubbleIndexTimeseries {
	    
	protected CompositeFifty() {}
	
	public CompositeFifty(final String name, final String symbol, final String dtype, final String keywords) {
		this.symbol = symbol;
		this.dtype = dtype;		
		this.name = name;
		this.keywords = keywords;
	}
}
