package com.example.library.controller;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookItemDto;
import com.example.library.service.BookServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RequestMapping("/books")
@RestController
@RequiredArgsConstructor
@Api(tags = "Контроллер для работы с книгами.")
public class BookController {

    private final BookServiceImpl bookService;

    @PostMapping("/save")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Создать книгу")
    public void save(@Validated @RequestBody BookItemDto book) {
      bookService.save(book);
    }

    // не поняла как писать контроллеры с мапперами немного. Через репозиторий? К нему, а не к сервисам обращаться?
    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Удалить из списка книгу, если читатель ее нам вернул")
    public void removeBookFromReadersList(@RequestParam List<Long> bookId) {
        bookService.removeBookFromReadersList(bookId);
    }

    @GetMapping("/assign-book-to-reader")
    @Operation(summary = "Получить книгу и добавить ее читателю")
    public void assignBookToReader(@RequestParam Long bookId, @RequestParam Long readerId){
        bookService.assignBookToReader(bookId, readerId);
    }

    @PutMapping("/update")
    @Operation(summary = "Обновить книги")
    public BookItemDto update(@RequestBody BookItemDto book) {
        return bookService.update(book);
    }

    @GetMapping("/getAll")
    @Operation(summary = "Получить все книги")
    public List<BookItemDto> getAll() {
        return bookService.getAll();
    }

    @GetMapping("/find/{id}")
    @Operation(summary = "Получить книгу по id")
    public BookDto getBookDtoById(@PathVariable Long id) {
        return bookService.getBookDtoById(id);
    }
}
