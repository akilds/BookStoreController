package com.bookstore.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bookstore.dto.BookDTO;
import com.bookstore.model.BookData;
import com.bookstore.service.IBookService;
import com.bookstore.util.Response;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/book")
@Slf4j
public class BookController {

	@Autowired
	private IBookService bookService;
	
	@GetMapping("getallbooks")
	public ResponseEntity<List<?>> getAllBooks(@RequestHeader String token) {
		log.info("Get All Book Data");
		List<BookData> response = bookService.getAllBooks(token);
		return new ResponseEntity<List<?>>(response, HttpStatus.OK);
	}
	
	@PostMapping("/create")
	public ResponseEntity<Response> addBookData(@Valid @RequestBody BookDTO bookDTO) {
		log.info("Create Book Data : " + bookDTO);
		Response response  = bookService.addBook(bookDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public ResponseEntity<Response> updateBookData(@RequestHeader String token,
			                                       @Valid @RequestBody BookDTO userDTO) {
		log.info("Update Book Data : " + userDTO);
		Response response  = bookService.updateBook(token, userDTO);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<Response> deleteBookData(@RequestHeader String token) {
		log.info("Book Data Deleted");
		Response response  = bookService.deleteBook(token);
		return new ResponseEntity<Response>(response, HttpStatus.OK);
	}
}
