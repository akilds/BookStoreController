package com.bookstore.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.bookstore.dto.BookDTO;

import lombok.Data;

@Entity
@Table(name = "bookTable")
public @Data class BookData {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int bookId;
	
	@Column
	private String bookName;
	private String bookAuthor;
	private String bookDescription;
	private String bookLogo;
	private double bookPrice;
	private long bookQuantity;

	public BookData() {}
	
	public void updateBook(BookDTO bookDTO) {
		this.bookName = bookDTO.bookName;
		this.bookAuthor = bookDTO.bookAuthor;
		this.bookDescription = bookDTO.bookDescription;
		this.bookLogo = bookDTO.bookLogo;
		this.bookPrice = bookDTO.bookPrice;
		this.bookQuantity = bookDTO.bookQuantity;
	}
}
