package prove02;

import java.awt.*;

/**
 * Created by Peyton on 4/25/2017.
 */
public class Zombie extends Creature implements Movable, Aggressor {
    public Zombie() {
        _health = 2;
    }

    @Override
    public void move() {
        _location.x++;
    }

    @Override
    public void attack(Creature target) {
        if(target != null && !(target instanceof Plant)) {
            target.takeDamage(10);
        }
    }

    @Override
    Shape getShape() {
        return Shape.Square;
    }

    @Override
    Color getColor() {
        return Color.blue;
    }

    @Override
    Boolean isAlive() {
        return _health > 0;
    }
}
