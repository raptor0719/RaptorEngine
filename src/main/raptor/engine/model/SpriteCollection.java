package raptor.engine.model;

import java.util.Map;

public class SpriteCollection {
	private final Map<String, DirectionalSprite> phases;
	private final String name;

	public SpriteCollection(final String name, final Map<String, DirectionalSprite> phases) {
		this.phases = phases;
		this.name = name;
	}

	public DirectionalSprite getSprite(final String phase) {
		return phases.get(phase);
	}

	public String getName() {
		return name;
	}
}
