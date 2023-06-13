# card-deck-api
This API serve to retrieve a winner of a game of cards. 
### Game rules
- All players receive the same amount of cards retrieved from a shuffled deck;
- All players have to sum its cards' values, following these rules:
  - All numeric cards have its value, e.g, a two of clubs have 2 as its value;
  - The Ace have value equal to 1;
  - The Jack have value equal to 11;
  - The Queen have value equal to 12;
  - The King have value equal to 13;
- The game has one winner if the sum of one player's cards is the biggest among the players, otherwise the game ends in a draw if two or more players have the biggest sum among them.
## Table of Contents
- [Installation](#installation)
- [Usage](#usage)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Error Handling](#error-handling)
- [Testing](#testing)
- [Deployment](#deployment)

## Installation
To run the API locally, follow these steps:

1. Ensure that Java 17+ and Maven are installed on your system.
2. Clone this repository: `git clone https://github.com/gustavooquinteiro/card-deck-api.git`
3. Navigate to the project directory: `cd card-deck-api`
4. Build the project: `mvn clean install`
5. Run the API: `mvn spring-boot:run`
6. The API will now be running on `http://localhost:8080`

## Usage
See [API Dcoumentation](#api-documentation) to further API's details.
The main funcionalities are reached with the following endpoints:

- `GET /game/new`: Retrieve a new game with 4 players with 5 cards of a random shuffled deck from [Deck of Cards API](https://deckofcardsapi.com/) each.
- `GET /game/{gameId}/winner`: Retrieve a winner of given game ID. This endpoint call will return the winner player or a list of all players if the game endend in a draw. 

## API Documentation
The API documentation is generated using Swagger. You can access it at http://localhost:8080/swagger-ui/. The documentation provides detailed information about each endpoint, request/response formats, and allows you to interact with the API.

## Configuration
The API can be configured using the `application.properties` file. You can modify properties such as the database connection, logging levels, and server port.

## Error Handling
If an error occurs while processing a request, the API will return an appropriate error response. The response will include an error code, an user friendly message, and details about the error. Here's an example of an error response:

```json
{
	"status": 404,
	"title": "Resource not found",
	"type": "https://my-card-deck.api.com/resource-not-found",
	"detail": "Specific detail about the error",
	"uiMessage": "An user friendly message",
	"timestamp": "2023-06-09T20:00:00.9159768-03:00"
}
```


## Testing
The API includes automated tests to ensure its functionality. You can run the tests using the following command:

```bash
mvn test
```
## Deployment
To deploy the API to a production environment, follow these steps:

1. Set up a server with Java and a MySQL database.
2. Build the project: `mvn clean install`
3. Copy the generated JAR file (card-deck-api.jar) to the server.
4. Run the API: `java -jar card-deck-api.jar`
