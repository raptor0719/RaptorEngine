		package raptor.engine.nav.mesh;

import java.util.ArrayList;
import java.util.List;

import raptor.engine.nav.mesh.graph.NavMeshNode;

// TODO: This object should be read only once created, create a builder for this
// 		and remove the addNode method
public class NavMeshGridCell {
	private final List<NavMeshNode> nodes;
	private final int xPos;
	private final int yPos;

	public NavMeshGridCell(final int xPos, final int yPos) {
		nodes = new ArrayList<NavMeshNode>(5);
		this.xPos = xPos;
		this.yPos = yPos;
	}

	public void addNode(final NavMeshNode node) {
		if (node != null)
			nodes.add(node);
	}

	public NavMeshNode[] getNodes() {
		return nodes.toArray(new NavMeshNode[nodes.size()]);
	}

	public int getX() {
		return xPos;
	}

	public int getY() {
		return yPos;
	}

	@Override
	public boolean equals(final Object o) {
		if (o == null)
			return false;
		if (o == this)
			return true;
		if (!(o instanceof NavMeshGridCell))
			return false;

		final NavMeshGridCell o1 = (NavMeshGridCell)o;

		if (xPos != o1.getX())
			return false;
		if (yPos != o1.getY())
			return false;

		return true;
	}

	@Override
	public String toString() {
		return "NavGridCell:[xPos=" + xPos + ",yPos=" + yPos + "]";
	}
}
