# SIW-Movie project
## Portale per organizzare film e relativi artisti
Progetto svolto durante il corso di "Sistemi Informativi sul Web"

## Author
<a href="https://github.com/Chiodoo">
  <img src="https://avatars.githubusercontent.com/u/167012156?v=4" width="80">
</a>

## Modello di dominio

```mermaid
classDiagram
  class Siw-Movie {

  }

  class Movie {
    ID
    Titolo
    Anno
    urlImage
  }

  class Artist {
    ID
    Nome
    Cognome
    Nascita
  }

  class User {
    ID
    Nome
    Cognome
    Email
    Nascita
  }

  class Credentials {
    ID
    Username
    Password
    Ruolo
  }

  Credentials -- "1" User
  Movie "*"--"*" Artist
  User -- "*" Siw-Movie
  Movie -- "*" Siw-Movie
  Artist -- "*" Siw-Movie
```