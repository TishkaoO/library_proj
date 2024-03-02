package com.example.library.repository;

import com.example.library.entity.ReaderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface ReaderEntityRepository extends JpaRepository<ReaderEntity, Long> {

    List<ReaderEntity> findReaderEntitiesByAndNameAndSurname(String firstName, String surname);

    @Query(value = "select * from book_reader\n" +
            "         join public.book b on b.book_id_pk = book_reader.book_id_fk\n" +
            "         where surname ilike :surname%", nativeQuery = true)
    List<ReaderEntity> findReaderByLastName(@Param("surname") String surname);

    List<ReaderEntity> findReaderEntitiesBySurnameAndNameAndMiddleNameAndDateOfBirthAndReaderTicket(
            String surname,
            String name,
            String middleName,
            LocalDate birthday,
            String ticket
    );
}
