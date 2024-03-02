package com.example.library.repository;

import com.example.library.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookEntityRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findBookEntitiesByNameAuthor(String authorName);

    List<BookEntity> findBookEntitiesByNameAndAndNameAuthorAndGenre(
                    String name,
                    String author,
                    String genre
            );
}
