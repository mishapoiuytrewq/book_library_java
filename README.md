# How to launch?

```bash
docker-compose up --build
```
```
default launch: localhost:8080
```

# Requests

```
description: frontend
endpoint: /
method: GET
return: react app
```

```
description: get public books
endpoint: /api/v1/book/public
method: GET
return: json {id: string(uuid), title: string, author: string, releaseDate: string(date), open: boolean}[]
```

```
description: get private books
endpoint: /api/v1/book/private
method: GET
return: json {id: string(uuid), title: string, author: string, releaseDate: string(date), open: boolean}[]
```

```
description: get book by id
endpoint: /api/v1/book/id/{id: string(uuid)}
method: GET
return: json {id: string(uuid), title: string, author: string, releaseDate: string(date), open: boolean}
```

```
description: save book
endpoint: /api/v1/book
method: PUT
body: json {id: string(uuid), title: string, author: string, releaseDate: string(date), open: boolean}
```

```
description: change catalog at the book
endpoint: /api/v1/book/id/{id: string(uuid)}/open/{open: boolean}
method: PATCH
```

```
description: delete book
endpoint: /api/v1/book/id/{id: string(uuid)}
method: DELETE
```

# License

[MIT](https://choosealicense.com/licenses/mit/)