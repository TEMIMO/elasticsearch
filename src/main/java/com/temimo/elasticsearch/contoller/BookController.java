package com.temimo.elasticsearch.contoller;

import com.temimo.elasticsearch.document.Book;
import com.temimo.elasticsearch.dto.BookPaginationRequest;
import com.temimo.elasticsearch.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {
    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public void saveBook(@RequestBody Book book) {
        bookService.save(book);
    }

    @PostMapping("/find")
    public List<Book> findBooks(@RequestBody BookPaginationRequest r) {
        String author = r.getAuthor();
        Integer size = r.getSize();
        Integer number = r.getNumber();
        return bookService.searchByAuthor(author, number, size).getContent();
    }
}
