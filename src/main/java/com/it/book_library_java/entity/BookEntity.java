package com.it.book_library_java.entity;

import org.springframework.data.annotation.Id;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

public record BookEntity(@Id @NotNull UUID id,
                         @NotBlank String title,
                         @NotBlank String author,
                         @NotNull LocalDate releaseDate,
                         @NotNull Boolean open) {
}
