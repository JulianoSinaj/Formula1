package com.formula1.player;

import com.formula1.model.Vector2D;
import com.formula1.model.Car;
import com.formula1.model.Track;

public interface Player {
    String getName();
    Vector2D getNextMove(Car car, Track track);
} 