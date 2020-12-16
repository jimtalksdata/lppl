package org.thebubbleindex.model;

public class Austria extends BubbleIndexTimeseries {
    
	protected Austria() {}
	
	public Austria(final String name, final String symbol, final String dtype, final String keywords) {
		this.symbol = symbol;
		this.dtype = dtype;		
		this.name = name;
		this.keywords = keywords;
	}
}
