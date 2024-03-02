package com.example.library.service;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookItemDto;
import com.example.library.entity.BookEntity;
import com.example.library.entity.ReaderEntity;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.BookMapper;
import com.example.library.repository.BookEntityRepository;
import com.example.library.repository.ReaderEntityRepository;
import liquibase.repackaged.net.sf.jsqlparser.util.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl {
    private final BookEntityRepository bookEntityRepository;
    private final ReaderEntityRepository readerEntityRepository;
    private final BookMapper bookMapper;

    public void save(BookItemDto book) {
        bookEntityRepository.save(bookMapper.toEntity(book));
    }

    // Удалить из списка книги, если читатель их нам вернул.
    public void removeBookFromReadersList(List<Long> bookIds) {
        bookIds.forEach(bookId -> {
            BookEntity bookEntity = bookEntityRepository.findById(bookId)
                    .orElseThrow(() -> new NotFoundException(String.format("Book is not found id = %s", bookId)));
            bookEntity.setReaderEntity(null);
            bookEntityRepository.save(bookEntity);
        });
    }

    // Из списка книг, нужно выбрать книгу и назначить читателю, если у него нет ее на руках
    public void assignBookToReader(Long bookId, Long readerId) {
        BookEntity bookEntity = bookEntityRepository.findById(bookId) // достаем книгу
                .orElseThrow(() -> new NotFoundException(String.format("Book is not found id = %s", bookId)));
        ReaderEntity readerEntity = readerEntityRepository.findById(readerId) // достаем читателя
                .orElseThrow(() -> new NotFoundException(String.format("Reader is not found id = %s", readerId)));
        if (bookEntity.getReaderEntity() == null) { // проверяем, что книга не имеет читателя
            List<BookEntity> readerBooks = readerEntity.getBooks();// получаем список книг, которые есть у читателя
            boolean bookAlreadyAssigned = readerBooks.stream() // проверяем, назначена ли книга читателю
                    .anyMatch(book -> book.getId().equals(bookId)); // anyMatch(есть ли в с стриме хоть один подходящий
            // элемент проверяет, есть ли в триме книга с нужным id
            if (!bookAlreadyAssigned) {
                bookEntity.setReaderEntity(readerEntity); // назначаем книгу
                bookEntityRepository.save(bookEntity); // сохраняем
            }
        }
    }

    public BookItemDto update(BookItemDto book) {
        BookEntity bookEntity = bookEntityRepository.findById(book.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Book is not found id = %s", book)));
        bookEntity.setName(book.getName());
        bookEntity.setNameAuthor(book.getAuthor());
        bookEntity.setGenre(book.getGenre());
        bookEntity.setStartDate(book.getStartDate());
        bookEntity.setFinishDate(book.getFinishDate());
        return bookMapper.toItemDto(bookEntityRepository.save(bookEntity));
    }

    public List<BookItemDto> getAll() {
        return bookEntityRepository.findAll().stream()
                .map(bookMapper::toItemDto)
                .collect(Collectors.toList());
    }

    public BookDto getBookDtoById(Long id) {
        return bookEntityRepository.findById(id)
                .map(bookMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format("Book is not found id = %s", id)));
    }
}





