package raptor.engine.game.entity;

import raptor.engine.event.IEventSource;
import raptor.engine.logical.collision.api.ICollideable;
import raptor.engine.util.geometry.Point;

public abstract class BasicEntity implements IEntity {
	private final int id;
	private final IEventSource eventSource;
	private final ICollideable collision;

	private final Point position;

	public BasicEntity(final int id, final IEventSource eventSource, final ICollideable collision) {
		this.id = id;
		this.eventSource = eventSource;
		this.collision = collision;

		this.position = new Point(0, 0);
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public int getX() {
		return position.getX();
	}

	@Override
	public int getY() {
		return position.getY();
	}

	@Override
	public ICollideable getCollision() {
		return collision;
	}

	protected IEventSource getEventSource() {
		return eventSource;
	}

	protected void setX(final int x) {
		position.setX(x);
	}

	protected void setY(final int y) {
		position.setY(y);
	}
}
