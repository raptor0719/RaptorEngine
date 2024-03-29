package raptor.engine.util.geometry;

import raptor.engine.util.ValuePair;
import raptor.engine.util.geometry.api.ILineSegment;
import raptor.engine.util.geometry.api.IPoint;

public class LineSegment implements ILineSegment {
	private IPoint a;
	private IPoint b;
	private float length;
	private float slope;

	private Vector aToB;

	public LineSegment(final int aX, final int aY, final int bX, final int bY) {
		this.a = new Point(aX, aY);
		this.b = new Point(bX, bY);
		calculateInternalMeasurements();
	}

	public LineSegment(final IPoint a, final IPoint b) {
		this.a = a;
		this.b = b;
		calculateInternalMeasurements();
	}

	public ValuePair<Point, Point> getPoints() {
		return new ValuePair<Point, Point>(new Point(a.getX(), a.getY()), new Point(b.getX(), b.getY()));
	}

	@Override
	public IPoint getStart() {
		return a;
	}

	public void setStart(final int x, final int y) {
		a = new Point(x, y);
		calculateInternalMeasurements();
	}

	public void setStart(final IPoint start) {
		a = start;
		calculateInternalMeasurements();
	}

	@Override
	public IPoint getEnd() {
		return b;
	}

	public void setEnd(final int x, final int y) {
		b = new Point(x, y);
		calculateInternalMeasurements();
	}

	public void setEnd(final IPoint end) {
		b = end;
		calculateInternalMeasurements();
	}

	public void setPoints(final int ax, final int ay, final int bx, final int by) {
		a = new Point(ax, ay);
		b = new Point(bx, by);
		calculateInternalMeasurements();
	}

	public void setPoints(final IPoint a, final IPoint b) {
		this.a = a;
		this.b = b;
	}

	@Override
	public float getLength() {
		return length;
	}

	public float getSlope() {
		return slope;
	}

	/**
	 * @param ls - the intersecting line segment
	 * @return The single point of intersection between the calling object and parameter ls.
	 * Will return null if either the segments do not intersect at all or have multiple points
	 * of intersection.
	 */
	public Point getIntersectionPoint(final LineSegment ls) {
		if (this.equals(ls))
			return null;

		{// Finish fast if the two segments have a common endpoint
		// NOTE: this also covers the case that the lines are collinear and overlap only at ONE point
		// FIXME: I THINK this is wrong because they could share an endpoint, NOT be equal, but STILL overlap
			final Point commonEndpoint = getCommonEndpoint(this, ls);
			if (commonEndpoint != null)
				return commonEndpoint;
		}
		/*
		 * p = this starting point
		 * q = ls starting point
		 *
		 * r = p.aToB
		 * s = q.aToB
		 *
		 * t = scalar of r
		 * u = scalar of s
		 */
		final Vector p = Vector.toVector(new Point(a.getX(), a.getY()));
		final Vector q = Vector.toVector(new Point(ls.a.getX(), ls.a.getY()));
		final Vector r = aToB;
		final Vector s = ls.aToB;

		final int rCrossS = r.cross(s);
		final Vector qMinusP = q.minus(p);
		final int qMinPCrossR = (qMinusP).cross(r);

		if (rCrossS == 0 && qMinPCrossR == 0) { // is collinear
			return null;
		}
		else if (rCrossS == 0 && qMinPCrossR != 0) { // is parallel
			return null;
		}
		else if (rCrossS != 0) { // POSSIBLY intersects
			final float t = (float)qMinusP.cross(s) / rCrossS;
			final float u = (float)qMinPCrossR / rCrossS;

			if ((t >= 0 && t <= 1) && (u >= 0 && u <= 1)) { // intersects!
				final int intersectX = p.getX() + round(t * r.getX());
				final int intersectY = p.getY() + round(t * r.getY());

				return new Point(intersectX, intersectY);
			}
		}

		// Non-collinear, non-parallel, and does not intersect
		return null;
	}

	public boolean sharesEndpoint(final LineSegment compare) {
		return getCommonEndpoint(this, compare) != null;
	}

	public boolean overlaps(final LineSegment compare) {
		return this.a.isOnLineSegment(compare) || this.b.isOnLineSegment(compare);
	}

	/**
	 * @param ls - the line segment to compare
	 * @return Returns if this line segment and the given parameter are connected ONLY
	 * at end-points and share no other common points.
	 */
	public boolean isConnected(final LineSegment compare) {
		final Point commonEndpoint = getCommonEndpoint(this, compare);

		if (commonEndpoint == null)
			return false;

		final boolean thisStartCommonEndpoint = this.getStart().equals(commonEndpoint);
		final boolean compareStartCommonEndpoint = compare.getStart().equals(commonEndpoint);

		final IPoint thisCheckPoint = (thisStartCommonEndpoint) ? this.getEnd() : this.getStart();
		final IPoint compareCheckPoint = (compareStartCommonEndpoint) ? compare.getEnd() : compare.getStart();

		return !thisCheckPoint.isOnLineSegment(compare) && !compareCheckPoint.isOnLineSegment(this);
	}

	public boolean intersectsWithExcludingEndpoints(final LineSegment compare) {
		return this.intersectsWith(compare) && !this.sharesEndpoint(compare);
	}

	/**
	 * This is the same as calling {@link #getIntersectionPoint(LineSegment)} != null
	 *
	 * @param ls - the line segment with which to check intersection
	 * @return True if the calling object and the parameter ls have a point of intersection,
	 * false if they do not have a point of intersection.
	 */
	public boolean intersectsWith(final LineSegment ls) {
		return getIntersectionPoint(ls) != null;
	}

	public double getAngleBetween(final LineSegment ls) {
		return aToB.getAngleBetween(ls.aToB);
	}

	@Override
	public boolean equals(final Object o) {
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (!(o instanceof LineSegment))
			return false;

		final LineSegment ls = (LineSegment)o;

		// case where points match up
		if (a.equals(ls.a)) {
			if (b.equals(ls.b))
				return true;
			return false;
		}

		// case where points are reversed
		if (a.equals(ls.b)) {
			if (b.equals(ls.a))
				return true;
			return false;
		}

		return false;
	}

	@Override
	public String toString() {
		return "LineSegment:[a=" + a + ",b=" + b + "]";
	}

	/* STATIC */

	public static float length(final int ax, final int ay, final int bx, final int by) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)

		final int xDiff = bx - ax;
		final int yDiff = by - ay;

		final int xDiffSquared = xDiff * xDiff;
		final int yDiffSquared = yDiff * yDiff;

		final float distance = (float)Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	public static double length(final double ax, final double ay, final double bx, final double by) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)

		final double xDiff = bx - ax;
		final double yDiff = by - ay;

		final double xDiffSquared = xDiff * xDiff;
		final double yDiffSquared = yDiff * yDiff;

		final double distance = Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	/* INTERNALS */

	private void calculateInternalMeasurements() {
		length = calculateLength(a, b);
		slope = Math.abs(calculateSlope(a, b));
		aToB = calculateAToB(a, b);
	}

	private float calculateLength(final IPoint a, final IPoint b) {
		// length = sqrt((xb-xa)^2+(yb-ya)^2)

		final int xDiff = b.getX() - a.getX();
		final int yDiff = b.getY() - a.getY();

		final int xDiffSquared = xDiff * xDiff;
		final int yDiffSquared = yDiff * yDiff;

		final float distance = (float)Math.sqrt(xDiffSquared + yDiffSquared);

		return distance;
	}

	private Vector calculateAToB(final IPoint a, final IPoint b) {
		return new Vector(b.getX() - a.getX(), b.getY() - a.getY());
	}

	private int round(final float f) {
		return Math.round(f);
	}

	private Point getCommonEndpoint(final LineSegment ls1, final LineSegment ls2) {
		final ValuePair<Point, Point> points1 = ls1.getPoints();
		final ValuePair<Point, Point> points2 = ls2.getPoints();

		if (points1.getValue1().equals(points2.getValue1()) || points1.getValue1().equals(points2.getValue2()))
			return points1.getValue1();
		else if (points1.getValue2().equals(points2.getValue1()) || points1.getValue2().equals(points2.getValue2()))
			return points1.getValue2();

		return null;
	}

	private float calculateSlope(final IPoint a, final IPoint b) {
		if (b.getX() - a.getX() == 0)
			return 0;
		return ((b.getY() - a.getY())/(b.getX() - a.getX()));
	}
}
