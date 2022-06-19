create table book
(
    id           uuid primary key,
    title        text not null,
    author       text not null,
    release_date date not null
);

create table catalog
(
    id      int primary key generated always as identity,
    book_id uuid not null unique references book (id),
    open    bool not null
);