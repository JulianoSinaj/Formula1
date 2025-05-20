package com.formula1.model;

import java.util.ArrayList;
import java.util.List;

public class Track {
    private final int width;
    private final int height;
    private final boolean[][] grid; // true represents valid track space, false represents walls/off-track
    private final Vector2D startPosition;
    private final Vector2D finishPosition;
    private final List<Vector2D> checkpoints;

    public Track(int width, int height, Vector2D startPosition, Vector2D finishPosition) {
        this.width = width;
        this.height = height;
        this.grid = new boolean[height][width];
        this.startPosition = startPosition;
        this.finishPosition = finishPosition;
        this.checkpoints = new ArrayList<>();
        initializeDefaultTrack();
    }

    private void initializeDefaultTrack() {
        // First, set all cells as walls (false)
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                grid[y][x] = false;
            }
        }

        // Then, create the racing area (true) in the middle section
        int wallThickness = 1; // Thickness of the walls
        int topWall = wallThickness;
        int bottomWall = height - wallThickness - 1;
        int leftWall = wallThickness;
        int rightWall = width - wallThickness - 1;

        // Set the valid racing area
        for (int y = topWall; y <= bottomWall; y++) {
            for (int x = leftWall; x <= rightWall; x++) {
                grid[y][x] = true;
            }
        }

        // Ensure start and finish positions are valid
        if (startPosition!=null && finishPosition!=null) {
            grid[startPosition.getY()][startPosition.getX()] = true;
            grid[finishPosition.getY()][finishPosition.getX()] = true;
            
            // Also make sure the positions next to start are valid for both players
            int startY = startPosition.getY();
            int startX = startPosition.getX();
            if (startY > 0) grid[startY - 1][startX] = true; // Position above
            if (startY < height - 1) grid[startY + 1][startX] = true; // Position below
        }
    }

    public boolean isValidPosition(Vector2D position) {
        int x = position.getX();
        int y = position.getY();
        return x >= 0 && x < width && y >= 0 && y < height && grid[y][x];
    }

    public boolean isFinish(Vector2D position) {
        return position.equals(finishPosition);
    }

    public Vector2D getStartPosition() {
        return startPosition;
    }

    public Vector2D getFinishPosition() {
        return finishPosition;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public boolean[][] getGrid() {
        return grid;
    }

    public List<Vector2D> getCheckpoints() {
        return new ArrayList<>(checkpoints);
    }

    public void addCheckpoint(Vector2D checkpoint) {
        checkpoints.add(checkpoint);
    }
} 