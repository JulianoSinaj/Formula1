package com.formula1;

import com.formula1.model.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.List;

public class GameRenderer {
    public static final int CELL_SIZE = 30;
    public static final int GRID_WIDTH = 20;
    public static final int GRID_HEIGHT = 15;

    private static final Color WALL_COLOR = Color.rgb(70, 70, 70); // Darker gray walls
    private static final Color TRACK_COLOR = Color.rgb(250, 250, 250); // Almost white track
    private static final Color PLAYER_COLOR = Color.rgb(30, 144, 255); // Dodger blue for player
    private static final Color BOT_COLOR = Color.rgb(220, 20, 60); // Crimson red for bot
    private static final Color FINISH_COLOR = Color.rgb(50, 205, 50); // Lime green finish

    private final Canvas canvas;

    public GameRenderer(Canvas canvas) {
        this.canvas = canvas;
    }

    public void render(GameModel gameModel) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        
        // Fill background with gradient
        gc.setFill(TRACK_COLOR);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        // Draw track with improved visuals
        Track track = gameModel.getTrack();
        boolean[][] grid = track.getGrid();
        for (int y = 0; y < GRID_HEIGHT; y++) {
            for (int x = 0; x < GRID_WIDTH; x++) {
                if (!grid[y][x]) {
                    gc.setFill(WALL_COLOR);
                    // Add slight padding for better wall appearance
                    gc.fillRoundRect(
                        x * CELL_SIZE + 1, 
                        y * CELL_SIZE + 1, 
                        CELL_SIZE - 2, 
                        CELL_SIZE - 2,
                        5, 5 // Rounded corners
                    );
                }
            }
        }

        // Draw finish line with gradient
        Vector2D finish = track.getFinishPosition();
        gc.setFill(FINISH_COLOR);
        gc.fillRoundRect(
            finish.getX() * CELL_SIZE + 1,
            finish.getY() * CELL_SIZE + 1,
            CELL_SIZE - 2,
            CELL_SIZE - 2,
            5, 5
        );

        // Draw cars with improved visuals
        List<Car> cars = gameModel.getCars();
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            Vector2D pos = car.getPosition();
            
            // Draw car with shadow effect
            double centerX = pos.getX() * CELL_SIZE + CELL_SIZE / 2;
            double centerY = pos.getY() * CELL_SIZE + CELL_SIZE / 2;
            double radius = (CELL_SIZE - 12) / 2.0;
            
            // Draw shadow
            gc.setFill(Color.rgb(0, 0, 0, 0.2));
            gc.fillOval(centerX - radius + 2, centerY - radius + 2, radius * 2, radius * 2);
            
            // Draw car
            gc.setFill(car.isCrashed() ? Color.BLACK : (i == 0 ? PLAYER_COLOR : BOT_COLOR));
            gc.fillOval(centerX - radius, centerY - radius, radius * 2, radius * 2);
        }

        // Draw grid lines with improved style
        gc.setStroke(Color.rgb(200, 200, 200, 0.5));
        gc.setLineWidth(0.5);
        for (int x = 0; x <= GRID_WIDTH; x++) {
            gc.strokeLine(x * CELL_SIZE, 0, x * CELL_SIZE, GRID_HEIGHT * CELL_SIZE);
        }
        for (int y = 0; y <= GRID_HEIGHT; y++) {
            gc.strokeLine(0, y * CELL_SIZE, GRID_WIDTH * CELL_SIZE, y * CELL_SIZE);
        }
    }
} 