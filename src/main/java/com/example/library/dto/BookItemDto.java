package com.example.library.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ApiModel(value = "BookItemDto", description = "Модель данных описывает книгу")
public class BookItemDto {

    @ApiModelProperty("Идентификатор книги")
    private Long id;

    @NotEmpty(message = "Название книги не может быть пустым")
    @Size(min = 5, max = 10 ,message = "название не должно привышать 10 и не должно быть меньше 5")
    @ApiModelProperty("Название книги")
    private String name;

    @ApiModelProperty("Автор книги")
    private String author;

    @ApiModelProperty("Жанр книги")
    private String genre;

    @ApiModelProperty("Дата выдачи книги читателю")
    private LocalDate startDate;

    @ApiModelProperty("Дата, до которой читателю необходимо сдать книгу")
    private LocalDate finishDate;
}
