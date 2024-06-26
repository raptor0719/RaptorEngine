package raptor.engine.game.entity;

import raptor.engine.collision.api.ICollisionShape;
import raptor.engine.display.render.IDrawable;
import raptor.engine.display.render.RenderPlane;

public interface IEntity extends IDrawable {
	long getId();
	String getName();
	void update(long millisSinceLastFrame);
	int getX();
	int getY();
	void setX(int newX);
	void setY(int newY);
	RenderPlane getRenderPlane();
	int getWidth();
	int getHeight();
	boolean hasCollision(long planeId);
	ICollisionShape getCollision(long planeId);
}
