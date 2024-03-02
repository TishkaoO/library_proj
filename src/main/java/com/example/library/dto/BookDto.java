package com.example.library.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "BookDto", description = "Модель данных описывает книгу")
public class BookDto {

    @ApiModelProperty("Идентификатор книги")
    private Long id;

    @ApiModelProperty("Полное название книги")
    private String fullName;

    @ApiModelProperty("Дни до сдачи кники в библиотеку")
    private Long amountOfDays;
}
