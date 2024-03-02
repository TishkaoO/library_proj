package com.example.library.mapper;

import com.example.library.dto.ReaderDto;
import com.example.library.dto.ReaderDtoInfo;
import com.example.library.dto.ReaderRegisterDto;
import com.example.library.entity.BookEntity;
import com.example.library.entity.ReaderEntity;
import org.mapstruct.*;

import java.util.List;

//@Mapper(componentModel = "spring")
public interface ReaderMapper {

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "fullName", expression = "java(mapToFullName(readerEntity.name, readerEntity.surname, readerEntity.middleName))")
    ReaderDto toDto(ReaderEntity readerEntity);

    ReaderEntity toEntity(ReaderRegisterDto readerRegisterDto); // тут всё совпадает

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
            nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
    @Mapping(target = "countBook", expression = "java(convertCountBook(readerEntity.getBooks()))")
    ReaderDtoInfo toInfoDto(ReaderEntity readerEntity);

    ReaderRegisterDto toRegisterDto(ReaderEntity readerEntity);

    default String mapToFullName(String name, String surname, String middle) {
       return name + " " + surname + " " + middle;
    }

    default int convertCountBook(List<BookEntity> books) {
        return books.size();
    }
}
