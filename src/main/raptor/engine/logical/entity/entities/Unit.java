package raptor.engine.logical.entity.entities;

import raptor.engine.display.api.IDrawer;
import raptor.engine.logical.entity.api.IOrderable;
import raptor.engine.logical.entity.order.api.IOrder;
import raptor.engine.logical.entity.order.api.IOrderHandler;
import raptor.engine.logical.entity.order.api.IOrderQueue;
import raptor.engine.logical.entity.order.orders.MoveOrder;
import raptor.engine.logical.entity.statblock.api.IStatBlock;
import raptor.engine.logical.event.api.IEvent;
import raptor.engine.logical.nav.api.INavAgent;
import raptor.engine.util.geometry.Point;

public class Unit implements IOrderable {
	private final IStatBlock statblock;
	private int currentHp;

	private final INavAgent navAgent;

	private final IOrderQueue orderQueue;
	private final IOrderHandler orderHandler;

	public Unit(final IStatBlock statblock, final INavAgent navAgent, final IOrderQueue orderQueue, final IOrderHandler orderHandler) {
		this.statblock = statblock;
		this.currentHp = this.statblock.getMaxHealth();

		this.navAgent = navAgent;

		this.orderQueue = orderQueue;
		this.orderHandler = orderHandler;
	}

	@Override
	public boolean colliding(final Point p) {
		return false;
	}

	@Override
	public void affect(final IEvent e) {
	}

	@Override
	public Point getPosition() {
		return navAgent.getPosition();
	}

	@Override
	public void act() {
		// Handle orders
		if (orderQueue.readyForOrder())
			applyOrder(orderQueue.getNewOrder());

		// Move
		navAgent.move();
	}

	@Override
	public void draw(final IDrawer drawer) {
		drawer.drawOval(getPosition().getX()-5, getPosition().getY()-5, 10, 10);
	}

	@Override
	public void order(final IOrder o) {
		orderQueue.addOrder(o);
	}

	private void applyOrder(final IOrder o) {
		switch (o.getOrderType()) {
			case MoveOrder:
				orderHandler.handleMoveOrder((MoveOrder)o, navAgent);
				break;
			case StopOrder:
				orderHandler.handleStopOrder(navAgent);
				break;
			default:
		}
	}
}
