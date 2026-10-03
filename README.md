

# Fruit Machine

A Spring Boot REST service implementing a configurable fruit machine game.

The machine supports:

- A configurable number of slots and colours.
- Random slot generation for each play.
- Jackpot, full-house, and small-prize outcomes.
- Configurable adjacent matching (`k`) for small prizes.
- A machine float and fixed play cost.
- Free plays when the machine cannot fully cover a non-jackpot prize.
- Runtime configuration and machine state through REST endpoints.

The implementation separates the game rules and state management from the REST/controller layer and includes automated tests for the core game logic and API behaviour.

## Running the Application

### Prerequisites

- Java 21 or later

### Start the application

On Windows:

```bash
.\mvnw.cmd spring-boot:run
```

On macOS/Linux:

```bash
./mvnw spring-boot:run
```

The application starts on the default port:

```text
http://localhost:8080
```

The REST API is available under:

```text
/api/machine
```

## Running the Tests

Run the complete automated test suite using:

### Windows

```bash
.\mvnw.cmd test
```

### macOS/Linux

```bash
./mvnw test
```

The test suite covers the core game rules, prize calculation, configurable slot generation, machine state, service orchestration, configuration validation, and REST API behaviour.

## REST API

### Play

```http
POST /api/machine/play
```

Plays one round of the fruit machine. A paid play adds the configured play cost to the machine float. If free plays are available, one free play is consumed instead.

Example response:

```json
{
  "slots": ["BLACK", "GREEN", "WHITE", "YELLOW"],
  "prizeType": "FULL_HOUSE",
  "payout": 51.00,
  "freePlaysCredited": 0,
  "currentFloat": 51.00
}
```

### Get Machine State

```http
GET /api/machine
```

Returns the current configuration and runtime state of the machine.

Example response:

```json
{
  "fruitMachineConfiguration": {
    "slots": 4,
    "colors": ["BLACK", "WHITE", "GREEN", "YELLOW"],
    "adjacentMatchCount": 2,
    "playCost": 2.00,
    "initialFloat": 100.00
  },
  "currentFloat": 100.00,
  "freePlays": 0
}
```

### Configure Machine

```http
PUT /api/machine/configuration
```

Updates the machine configuration. Reconfiguration resets the current float to the supplied `initialFloat` and clears any existing free plays.

Example request:

```json
{
  "slots": 6,
  "colors": ["RED", "GREEN", "BLUE"],
  "adjacentMatchCount": 3,
  "playCost": 3.00,
  "initialFloat": 200.00
}
```

Configuration is validated before it is applied. For example, the slot count and play cost must be positive, colours must not be empty, and `adjacentMatchCount` must be between `2` and the configured number of slots.


## API Documentation

OpenAPI documentation is generated using Springdoc.

With the application running, Swagger UI is available at:

`http://localhost:8080/swagger-ui.html`

The OpenAPI specification is available at:

`http://localhost:8080/v3/api-docs`

## Key Engineering Decisions and Assumptions

### Prize Priority

If an outcome could satisfy more than one prize rule, prizes are evaluated in the following order:

1. Jackpot
2. Full house
3. Small prize
4. No prize

Only one prize is awarded per play.

### Machine Float and Play Cost

For a paid play, the configured play cost is added to the machine float before the prize is calculated.

For example, with a float of `100.00` and a play cost of `2.00`, the available float becomes `102.00` before evaluating the payout.

A free play does not add the play cost to the float.

### Small Prize

A small prize is awarded when there is a contiguous sequence of at least `k` matching slots.

Only one small prize is awarded per play, even if multiple or overlapping matching sequences exist.

The small prize is five times the configured play cost.

### Insufficient Float

If the machine cannot fully cover a non-jackpot prize, the remaining float is paid and free plays are credited for the unpaid shortfall.

For example, if the prize is `10`, but only `4` is available, `4` is paid and `6` free plays are credited.

The jackpot is excluded from the free-play shortfall rule.

### Configuration

The number of colours does not need to match the number of slots because colours may occur multiple times in a spin.

The adjacent match count (`k`) must be between `2` and the configured number of slots.

### Reconfiguration

Updating the configuration resets the runtime state. The current float is set to the new `initialFloat`, and existing free plays are cleared.

## Deliberate Omissions and Future Work
The implementation focuses on the core requirements of the exercise.

Potential future improvements include:

- Persisting machine configuration and runtime state instead of keeping them only in memory.
- Defining a concurrency strategy for simultaneous plays and configuration changes.
- Adding CI for automated build and test execution.
- Adding application metrics and observability.
- Defining an explicit policy for fractional monetary shortfalls when converting a shortfall into free plays.

## AI Usage

I used AI mainly as a sounding board when I found parts of the requirements open to interpretation. I used it to discuss possible approaches and suggest test cases and edge cases to help cover the main scenarios.

Some of the areas I discussed were:

- Discussing when the play cost should be added to the machine float.
- Discussing how free plays should work when there is not enough float to cover a prize.
- Discussing the order in which prize conditions should be evaluated.
- Discussing what should happen to the existing state when the machine is reconfigured.
- Suggesting test scenarios for prize evaluation, payouts, insufficient float, free plays, configuration validation, and REST API behaviour.

I reviewed the suggested test scenarios and selected the cases that were relevant to the requirements.

## Docker

The application can also be built and run using Docker.

First, package the application:

### Windows

```bash
.\mvnw.cmd clean package

