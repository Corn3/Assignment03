# Assignment03

The data used for testing can be found in the file "Dummy_data.txt".

Postman was used in order to test the endpoints, some of those can be found in the file CRUD.postman_collection.json or as URLs seen below.

PostgreSQL was used as a database to store the different model objects.

The following project supports all the base REST operations including:

* GET
* DELETE
* PUT
* POST

These operations were tested using the following endpoints in Postman:
* GET:
  * localhost:8080/api/v1/movies
  * localhost:8080/api/v1/movies/6/characters
  * localhost:8080/api/v1/franchises/2/characters
  * localhost:8080/api/v1/franchises
  * localhost:8080/api/v1/franchises/1
  * localhost:8080/api/v1/characters
  * localhost:8080/api/v1/characters/1
* DELETE:
  * localhost:8080/api/v1/movies/1
  * localhost:8080/api/v1/characters/1
  * localhost:8080/api/v1/franchises/1
* POST:
  * localhost:8080/api/v1/movies
* PUT:
  * localhost:8080/api/v1/movies/1
  * localhost:8080/api/v1/movies/4/characters/4
  * localhost:8080/api/v1/movies/4/franchise/2

Project was made by: Tyson Mcleod and Robin Eliasson.
