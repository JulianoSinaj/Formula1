package com.formula1.player;

import com.formula1.model.Vector2D;
import com.formula1.model.Car;
import com.formula1.model.Track;
import java.util.Random;

public class BotDriver implements Player {
    private final String name;
    private final Random random;

    public BotDriver(String name) {
        this.name = name;
        this.random = new Random();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Vector2D getNextMove(Car car, Track track) {
        Vector2D currentPos = car.getPosition();
        Vector2D finish = track.getFinishPosition();
        Vector2D currentVel = car.getVelocity();

        // 30% chance to make a random move
        if (random.nextDouble() < 0.3) {
            int randomX = random.nextInt(3) - 1; // -1, 0, or 1
            int randomY = random.nextInt(3) - 1; // -1, 0, or 1
            Vector2D randomMove = new Vector2D(randomX, randomY);
            
            // Check if the random move is valid
            Vector2D newVel = currentVel.add(randomMove);
            Vector2D newPos = currentPos.add(newVel);
            if (track.isValidPosition(newPos)) {
                return randomMove;
            }
        }

        // Otherwise, try to move towards the finish line but with some variation
        int dx = Integer.compare(finish.getX() - currentPos.getX(), 0);
        int dy = Integer.compare(finish.getY() - currentPos.getY(), 0);

        // Add small random variations to make movement more interesting
        if (random.nextDouble() < 0.2) {
            dx += (random.nextInt(3) - 1);
            dy += (random.nextInt(3) - 1);
        }

        // Limit acceleration to -1, 0, or 1
        dx = Math.max(-1, Math.min(1, dx));
        dy = Math.max(-1, Math.min(1, dy));

        // Check if the move is valid
        Vector2D proposedMove = new Vector2D(dx, dy);
        Vector2D newVel = currentVel.add(proposedMove);
        Vector2D newPos = currentPos.add(newVel);

        if (track.isValidPosition(newPos)) {
            return proposedMove;
        }

        // If the move is not valid, try to slow down
        return new Vector2D(-Integer.compare(currentVel.getX(), 0), 
                          -Integer.compare(currentVel.getY(), 0));
    }
}
