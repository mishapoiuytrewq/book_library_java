package com.it.book_library_java.repository;

import com.it.book_library_java.entity.BookEntity;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CatalogRepository extends CrudRepository<BookEntity, String> {
    @Query("""
            select b.*, c.open
            from book b
                     join catalog c on b.id = c.book_id
            where b.id = :id
            """)
    Optional<BookEntity> book(@Param("id") UUID id);

    @Query("""
            select b.*, c.open
            from book b
                     join catalog c on b.id = c.book_id
            where c.open = false
            """)
    List<BookEntity> privateBooks();

    @Query("""
            select b.*, c.open
            from book b
                     join catalog c on b.id = c.book_id
            where c.open = true
            """)
    List<BookEntity> publicBooks();

    @Modifying
    @Query("insert into catalog(book_id, open) values (:id, :open)")
    void saveBook(@Param("id") UUID id, @Param("open") boolean open);

    @Modifying
    @Query("delete from catalog where book_id = :id")
    void deleteBook(@Param("id") UUID id);

    @Modifying
    @Query("update catalog set open = :open where book_id = :id")
    void changeCatalogAtBook(@Param("id") UUID id, @Param("open") Boolean open);
}
