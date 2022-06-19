import React, {useEffect, useState} from "react"
import {AutoComplete, Button, Col, DatePicker, Form, Input, message, Row, Switch} from "antd"
import {Book, BookEntity, BookForm} from "../entity/BookEntity";
import {useForm} from "antd/es/form/Form";
import moment from "moment";
import {v4 as uuidv4} from 'uuid';

async function fetchBooks(open: boolean): Promise<Book[]> {
    let modifier: string
    if (open) {
        modifier = "public"
    } else {
        modifier = "private"
    }

    const response = await fetch(`/api/v1/book/${modifier}`)
    const books = await response.json()
    if (books instanceof Array) {
        const result = []
        for (const rawBook of books) {
            const tempBook = {
                ...rawBook,
                value: rawBook.title
            }
            const book = BookEntity.safeParse(tempBook)
            if (book.success) {
                result.push(book.data)
            } else {
                message.error(JSON.stringify(book.error))
            }
        }
        return result
    } else {
        message.error("/api/v1/book/private return not array")
    }
    return []
}

async function saveBook(book: BookForm) {
    if (book.open == null) {
        book.open = false
    }
    await fetch("/api/v1/book", {
        method: "PUT",
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(book)
    })
}

async function updateBook(book: BookForm) {
    await fetch(`/api/v1/book/id/${book.id}/open/${book.open}`, {method: "PATCH"})
}

async function deleteBook(bookId: string) {
    await fetch(`/api/v1/book/id/${bookId}`, {method: "DELETE"})
}


export default function Main() {
    const [publicBooks, setPublicBooks] = useState<Book[]>([])
    useEffect(() => {
        fetchBooks(true)
            .then(result => setPublicBooks(result))
    }, [])

    const [privateBooks, setPrivateBooks] = useState<Book[]>([])
    useEffect(() => {
        fetchBooks(false)
            .then(result => setPrivateBooks(result))
    }, [])

    const [selectedBook, setSelectedBook] = useState<boolean>(false)

    const [bookId, setBookId] = useState<string | undefined>(undefined)

    if (!selectedBook && bookId == null) {
        setBookId(uuidv4())
    }

    const autoCompleteOnChange = (_: any, option: any) => {
        if (option == null) {
            return
        }
        const book = option as Book
        setSelectedBook(true)
        setBookId(book.id)
        form.setFieldsValue({
            ...book,
            releaseDate: moment(book.releaseDate)
        })
    }

    const autoCompleteOnSearch = (value: string | undefined | null) => {
        if (value === "" || value == null) {
            resetState()
        }
    }

    const onFinish = (book: BookForm) => {
        book.id = bookId
        if (!selectedBook) {
            saveBook(book).then(_ => resetState())
        } else {
            updateBook(book).then(_ => resetState())
        }
    }

    const [form] = useForm()

    const resetState = () => {
        form.resetFields()
        fetchBooks(false)
            .then(result => setPrivateBooks(result))
        fetchBooks(true)
            .then(result => setPublicBooks(result))
        setBookId(uuidv4())
        setSelectedBook(false)
    }

    return (
        <Row justify={"center"}>
            <Col>
                <Row style={{padding: "12px"}}>
                    <AutoComplete
                        style={{width: "30vw"}}
                        placeholder="Публичная библиотека"
                        options={publicBooks}
                        onChange={autoCompleteOnChange}
                        onSearch={autoCompleteOnSearch}
                        allowClear={true}
                    />
                </Row>
                <Row style={{padding: "12px"}}>
                    <AutoComplete
                        style={{width: "30vw"}}
                        placeholder="Приватная библиотека"
                        options={privateBooks}
                        onChange={autoCompleteOnChange}
                        onSearch={autoCompleteOnSearch}
                        allowClear={true}
                    />
                </Row>
                <Form
                    form={form}
                    layout="vertical"
                    onFinish={onFinish}
                >
                    <Form.Item name="title" label="Название"
                               rules={[{required: true, message: 'Поле не заполненно!'}]}
                    >
                        <Input disabled={selectedBook}/>
                    </Form.Item>
                    <Form.Item name="author" label="Автор"
                               rules={[{required: true, message: 'Поле не заполненно!'}]}
                    >
                        <Input disabled={selectedBook}/>
                    </Form.Item>
                    <Form.Item name="releaseDate" label="Дата публикации"
                               rules={[{required: true, message: 'Поле не заполненно!'}]}
                    >
                        <DatePicker disabled={selectedBook}/>
                    </Form.Item>
                    <Form.Item name="open" label="Публичная" valuePropName="checked">
                        <Switch/>
                    </Form.Item>
                    <Row>
                        <Button type="primary" htmlType="submit">Сохранить</Button>
                        <Button
                            onClick={() => bookId ? deleteBook(bookId).then(() => resetState()) : null}>
                            Удалить
                        </Button>
                    </Row>
                </Form>
            </Col>
        </Row>

    )
}