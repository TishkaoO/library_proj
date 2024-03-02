package com.example.library.mapper;

import com.example.library.dto.BookDto;
import com.example.library.dto.BookItemDto;
import com.example.library.entity.BookEntity;
import org.mapstruct.*;

import java.time.temporal.ChronoUnit;

//@Mapper(componentModel = "spring")
public interface BookMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "fullName", expression = "java(mapToFullName(bookEntity))")
    @Mapping(target = "amountOfDays", expression = "java(convertAmountOfDays(bookEntity))")
    BookDto toDto(BookEntity bookEntity);

    BookEntity toEntity(BookItemDto bookItemDto);

    BookItemDto toItemDto(BookEntity bookEntity);


    default String mapToFullName(BookEntity bookEntity) {
        return bookEntity.getName() + " " + bookEntity.getNameAuthor()  + " " + bookEntity.getGenre();
    }

    default long convertAmountOfDays(BookEntity bookEntity) {
        return ChronoUnit.DAYS.between(bookEntity.getStartDate(), bookEntity.getFinishDate());
    }
}
