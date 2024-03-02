package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.dto.filter.ReaderDtoFilter;
import com.example.library.entity.ReaderEntity;
import com.example.library.exception.NotFoundException;
import com.example.library.mapper.ReaderMapper;
import com.example.library.repository.ReaderEntityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReaderServiceImpl {
    private final ReaderEntityRepository readerEntityRepository;
    private final ReaderMapper readerMapper;

    public void save(ReaderRegisterDto readerRegisterDto) {
        ReaderEntity readerEntity = readerMapper.toEntity(readerRegisterDto);
        readerEntityRepository.save(readerEntity);
    }

    public void deleteById(long id) {
        ReaderEntity readerEntity = readerEntityRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format("Reader is not found id = %s", id)));
        readerEntityRepository.delete(readerEntity);
    }

    public ReaderRegisterDto update(ReaderRegisterDto readerRegisterDto) {
        ReaderEntity readerEntity = readerEntityRepository.findById(readerRegisterDto.getId())
                .orElseThrow(() -> new NotFoundException(String.format("Reader is not found id = %s", readerRegisterDto)));
        readerEntity.setName(readerRegisterDto.getName());
        readerEntity.setSurname(readerRegisterDto.getSurname());
        readerEntity.setMiddleName(readerRegisterDto.getMiddleName());
        readerEntity.setPhoneNumber(readerRegisterDto.getPhoneNumber());
        return readerMapper.toRegisterDto(readerEntityRepository.save(readerEntity));
    }

    public ReaderDto getById(Long readerId) {
        return readerEntityRepository.findById(readerId)
                .map(readerMapper::toDto)
                .orElseThrow(() -> new NotFoundException(String.format("Reader is not found id = %s", readerId)));
    }

    public List<ReaderDto> getReaderEntityByFirstNameAndSurname(String firstName, String surname) {
        return readerEntityRepository.findReaderEntitiesByAndNameAndSurname(firstName, surname).stream()
                .map(readerMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<ReaderDtoInfo> getReaderByFilter(ReaderDtoFilter readerDtoFilter) {
        return readerEntityRepository.findReaderEntitiesBySurnameAndNameAndMiddleNameAndDateOfBirthAndReaderTicket(
                readerDtoFilter.getSurname(),
                readerDtoFilter.getName(),
                readerDtoFilter.getMiddleName(),
                readerDtoFilter.getDateOfBirth(),
                readerDtoFilter.getTicket()
        ).stream()
                .map(readerMapper::toInfoDto)
                .collect(Collectors.toList());
    }
}
