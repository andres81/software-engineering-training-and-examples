# Google Books API restful Application

The application can retrieve books from the [Google Books API](https://developers.google.com/books)
by giving a search string and optionally a language code.
The results are maximum 10 books, ordered on newest publication date, and contain the following data:

- ISBN 10 and 13
- Title
- Author(s)
- Publication date

# Building the application

This project is a Maven project. Therefore building it is as simple as:

```Bash
mvn clean install
```

Or using the wrapper:

```Bash
./mvnw clean install
```

# Running the application

You can run the application by running the following command on the command line:

```Bash
./mvnw spring-boot:run
```

After startup, you can easily test if it works by opening a browser, and use the following
url:

```
http://localhost:8080/books?searchText=l'être et le néant&language=fr
```

# Technical information

The application is a RESTFul API application. The API is described in a swagger.yml file located under:

    src/main/resources

The API is contract first. More on this [here](https://docs.spring.io/spring-ws/docs/current/reference/html/#why-contract-first).


