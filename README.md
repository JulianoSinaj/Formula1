# Formula 1 Racing Game

A simple 2D turn-based Formula 1 racing game implemented in Java using JavaFX.

## Description

This is a grid-based racing game where players take turns moving their cars around the track. The game features:
- A human player controlled by arrow keys
- A simple AI bot opponent
- Grid-based movement with velocity and acceleration
- Collision detection with track boundaries
- Simple graphics using JavaFX

## Requirements

- Java 17 or higher
- Gradle (build tool)
- JavaFX (handled by Gradle dependencies)

## How to Build and Run

1. Clone the repository
2. Navigate to the project directory
3. Build the project using Gradle: gradle build
4. Run the game: gradle run

## How to Play

- Use the arrow keys to control your car's acceleration:
  - Up Arrow: Accelerate upward
  - Down Arrow: Accelerate downward
  - Left Arrow: Accelerate left
  - Right Arrow: Accelerate right
- Your car's velocity persists between turns (simulating momentum)
- Avoid hitting the walls (gray cells)
- Reach the finish line (green cell) to win
- If you crash into a wall, your car is out of the race

## Game Rules

1. Players take turns moving their cars
2. Each turn, a player can adjust their acceleration in one of four directions
3. The new position is calculated based on:
   - Current position
   - Current velocity
   - Applied acceleration
4. A player loses if they hit a wall
5. The first player to reach the finish line wins

## Project Structure

- `src/main/java/com/formula1/`
  - `Formula1Game.java` - Main JavaFX application
  - `GameController.java` - Game logic and UI control
  - `GameRenderer.java` - Renders the game grid and cars
  - `GameUIBuilder.java` - Builds and styles the JavaFX UI
  - `model/`
    - `Vector2D.java` - 2D vector for position and velocity
    - `Car.java` - Car entity with position and velocity
    - `Track.java` - Race track grid
    - `GameModel.java` - Game state and logic
  - `player/`
    - `Player.java` - Player interface
    - `HumanPlayer.java` - Human player implementation
    - `BotDriver.java` - AI player implementation
