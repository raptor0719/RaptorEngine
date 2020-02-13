package raptor.engine.logical.collision.geometry;

import raptor.engine.logical.collision.api.ICollideable;
import raptor.engine.util.geometry.Point;
import raptor.engine.util.geometry.Triangle;

public class CollisionTriangle implements ICollideable {
	private Triangle collisionTriangle;

	public CollisionTriangle(final Triangle collisionTriangle) {
		this.collisionTriangle = collisionTriangle;
	}

	@Override
	public boolean collidesWithTriangle(final CollisionTriangle t) {
		for (final Point p : t.getCollision().getPoints())
			if (collisionTriangle.containsPoint(p))
				return true;
		for (final Point p : collisionTriangle.getPoints())
			if (t.getCollision().containsPoint(p))
				return true;
		return false;
	}

	public void setCollision(final Triangle collisionTriangle) {
		this.collisionTriangle = collisionTriangle;
	}

	public Triangle getCollision() {
		return collisionTriangle;
	}
}