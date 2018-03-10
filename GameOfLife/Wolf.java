package prove02;

import java.awt.*;
import java.util.Random;

/**
 * Created by Peyton on 4/28/2017.
 */
public class Wolf extends Creature implements Movable, Aggressor, Aware, Spawner {
    Random _rand;
    private int preferredDirection;
    private boolean canSpawn;

    public Wolf() {
        _rand = new Random();
        _health = 1;
        preferredDirection = _rand.nextInt(4);
        canSpawn = false;
    }

    @Override
    public void move() {
        switch(preferredDirection) {
            case 0:
                _location.x++;
                break;
            case 1:
                _location.x--;
                break;
            case 2:
                _location.y++;
                break;
            case 3:
                _location.y--;
                break;
            default:
                break;
        }
    }

    @Override
    public void attack(Creature target) {
        if(target instanceof Animal) {
            target.takeDamage(5);
            canSpawn = true;
        }
    }

    @Override
    public void senseNeighbors(Creature above, Creature below, Creature left, Creature right) {
        if (preferredDirection == 0) {
            if (right instanceof Animal) {
                preferredDirection = 0;
            } else if (below instanceof Animal) {
                preferredDirection = 2;
            } else if (left instanceof Animal) {
                preferredDirection = 1;
            } else if (above instanceof Animal) {
                preferredDirection = 3;
            }
        } else if (preferredDirection == 1) {
            if (left instanceof Animal) {
                preferredDirection = 1;
            } else if (above instanceof Animal) {
                preferredDirection = 3;
            } else if (right instanceof Animal) {
                preferredDirection = 0;
            } else if (below instanceof Animal) {
                preferredDirection = 2;
            }
        } else if (preferredDirection == 2) {
            if (below instanceof Animal) {
                preferredDirection = 2;
            } else if (left instanceof Animal) {
                preferredDirection = 1;
            } else if (above instanceof Animal) {
                preferredDirection = 3;
            } else if (right instanceof Animal) {
                preferredDirection = 0;
            }
        } else if (preferredDirection == 3) {
            if (above instanceof Animal) {
                preferredDirection = 3;
            } else if (right instanceof Animal) {
                preferredDirection = 0;
            } else if (below instanceof Animal) {
                preferredDirection = 2;
            } else if (left instanceof Animal) {
                preferredDirection = 1;
            }
        }
    }

    public Creature spawnNewCreature() {
        if (!canSpawn)
            return null;
        Wolf baby = new Wolf();
        Point newPoint = (Point)_location.clone();
        newPoint.x--;
        baby.setLocation(newPoint);
        canSpawn = false;
        return baby;
    }

    @Override
    public Shape getShape() {
        return Shape.Square;
    }

    @Override
    public Color getColor() {
        return Color.gray;
    }

    @Override
    public Boolean isAlive() {
        return _health > 0;
    }
}
