package com.it.book_library_java.repository;

import com.it.book_library_java.entity.BookEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface BookRepository extends CrudRepository<BookEntity, String> {
    @Query("""
            select count(b.id) = 1 as exists
            from book b
            where b.id = :id
            """)
    boolean bookExists(@Param("id") UUID id);

    default boolean bookNotExists(UUID id) {
        return !bookExists(id);
    }

    @Modifying
    @Query("insert into book(id, title, author, release_date) values (:id, :title, :author, :releaseDate)")
    void saveBook(@Param("id") UUID id, @Param("title") String title,
                  @Param("author") String author, @Param("releaseDate") LocalDate releaseDate);

    @Modifying
    @Query("delete from book where id = :id")
    void deleteBook(@Param("id") UUID id);
}
