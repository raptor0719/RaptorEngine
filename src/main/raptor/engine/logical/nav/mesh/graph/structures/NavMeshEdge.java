package raptor.engine.logical.nav.mesh.graph.structures;

import raptor.engine.logical.nav.api.graph.structures.IGraphEdge;
import raptor.engine.util.geometry.LineSegment;
import raptor.engine.util.structures.ValuePair;

public class NavMeshEdge implements IGraphEdge {
	private final int cost;
	private final NavMeshNode destination;
	private final ValuePair<LineSegment, LineSegment> adjacentEdges;

	public NavMeshEdge(final int cost, final NavMeshNode destination, final ValuePair<LineSegment, LineSegment> adjacentEdges) {
		this.cost = cost;
		this.destination = destination;
		this.adjacentEdges = adjacentEdges;
	}

	@Override
	public int getCost() {
		return cost;
	}

	@Override
	public NavMeshNode getDestination() {
		return destination;
	}

	// Corresponds to the line segment of the start node, and the bordering line segment of the destination node
	public ValuePair<LineSegment, LineSegment> getAdjacentEdges() {
		return adjacentEdges;
	}
}