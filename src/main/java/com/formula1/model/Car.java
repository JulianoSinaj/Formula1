package com.formula1.model;

public class Car {
    private Vector2D position;
    private Vector2D velocity;
    private final String playerName;
    private boolean crashed;
    private static final int MAX_VELOCITY = 1;

    public Car(String playerName, Vector2D startPosition) {
        this.playerName = playerName;
        this.position = startPosition;
        this.velocity = new Vector2D(0, 0);
        this.crashed = false;
    }

    public void move(Vector2D acceleration) {
        // New velocity is current velocity plus acceleration
        Vector2D newVelocity = velocity.add(acceleration);
        
        // Limit velocity components
        int vx = Math.max(-MAX_VELOCITY, Math.min(MAX_VELOCITY, newVelocity.getX()));
        int vy = Math.max(-MAX_VELOCITY, Math.min(MAX_VELOCITY, newVelocity.getY()));
        velocity = new Vector2D(vx, vy);
        
        // New position is current position plus velocity
        position = position.add(velocity);
    }

    public Vector2D getPosition() {
        return position;
    }

    public Vector2D getVelocity() {
        return velocity;
    }

    public String getPlayerName() {
        return playerName;
    }

    public boolean isCrashed() {
        return crashed;
    }

    public void setCrashed(boolean crashed) {
        this.crashed = crashed;
    }

    @Override
    public String toString() {
        return "Car{" +
                "player='" + playerName + '\'' +
                ", position=" + position +
                ", velocity=" + velocity +
                ", crashed=" + crashed +
                '}';
    }
} 