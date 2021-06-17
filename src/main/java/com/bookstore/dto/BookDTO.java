package com.bookstore.dto;

import lombok.Data;
import lombok.ToString;

@Data
public @ToString class BookDTO {

	public String bookName;
	
	public String bookAuthor;
	
	public String bookDescription;
	
	public String bookLogo;
	
	public double bookPrice;
	
	public long bookQuantity;
}
