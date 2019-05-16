package raptor.engine.test.main;

import java.awt.Image;
import java.io.File;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;

import raptor.engine.model.HardpointPosition;
import raptor.engine.model.Model;
import raptor.engine.model.Sprite;
import raptor.engine.model.SpriteModel;
import raptor.engine.model.SpriteModelDescriptor;
import raptor.engine.model.WireModel;
import raptor.engine.model.WireModelAnimationDescriptor;
import raptor.engine.model.WireModelFrame;

public class TestModelFactory {
	public static Model getModel() {
		final HardpointPosition pos00 = point(0, 0);
		final HardpointPosition pos01 = point(0, 2000);
		final HardpointPosition pos02 = point(2000, 0);
		final HardpointPosition pos03 = point(2000, 2000);
		final WireModelFrame frame0 = new WireModelFrame(new HardpointPosition[] {pos00, pos01, pos02, pos03});

		final HardpointPosition pos10 = point(0, 0);
		final HardpointPosition pos11 = point(0, 1500);
		final HardpointPosition pos12 = point(1500, 0);
		final HardpointPosition pos13 = point(1500, 1500);
		final WireModelFrame frame1 = new WireModelFrame(new HardpointPosition[] {pos10, pos11, pos12, pos13});

		final HardpointPosition pos20 = point(0, 0);
		final HardpointPosition pos21 = point(0, 1000);
		final HardpointPosition pos22 = point(1000, 0);
		final HardpointPosition pos23 = point(1000, 1000);
		final WireModelFrame frame2 = new WireModelFrame(new HardpointPosition[] {pos20, pos21, pos22, pos23});

		final HardpointPosition pos30 = point(0, 0);
		final HardpointPosition pos31 = point(0, 500);
		final HardpointPosition pos32 = point(500, 0);
		final HardpointPosition pos33 = point(500, 500);
		final WireModelFrame frame3 = new WireModelFrame(new HardpointPosition[] {pos30, pos31, pos32, pos33});

		final HardpointPosition pos40 = point(0, 0);
		final HardpointPosition pos41 = point(0, 0);
		final HardpointPosition pos42 = point(0, 0);
		final HardpointPosition pos43 = point(0, 0);
		final WireModelFrame frame4 = new WireModelFrame(new HardpointPosition[] {pos40, pos41, pos42, pos43});

		final List<WireModelFrame> frameList = Arrays.asList(new WireModelFrame[] {frame0, frame1, frame2, frame3, frame4});

		final int[][] directionMapping = new int[][] { {0, 1, 2, 3, 4} };

		final int[] anim0_frameMappings = new int[] {0, 1, 2, 3, 4};
		final int[] anim0_framePortions = new int[] {2, 1, 1, 1, 2};
		final WireModelAnimationDescriptor anim0 = new WireModelAnimationDescriptor(0, anim0_frameMappings, anim0_framePortions);

		final List<WireModelAnimationDescriptor> animations = Arrays.asList(new WireModelAnimationDescriptor[] {anim0});

		final WireModel wireModel = new WireModel(frameList, directionMapping, animations, frame0);

		final List<Sprite> sprites = Arrays.asList(new Sprite[] {getRectangle(10, 10)});
		final SpriteModel spriteModel = new SpriteModel(sprites);
		final SpriteModelDescriptor point0_visual = new SpriteModelDescriptor(0, spriteModel);
		final SpriteModelDescriptor point1_visual = new SpriteModelDescriptor(1, spriteModel);
		final SpriteModelDescriptor point2_visual = new SpriteModelDescriptor(2, spriteModel);
		final SpriteModelDescriptor point3_visual = new SpriteModelDescriptor(3, spriteModel);
		final List<SpriteModelDescriptor> initialVisuals = Arrays.asList(new SpriteModelDescriptor[] {point0_visual, point1_visual, point2_visual, point3_visual});

		return new Model(wireModel, initialVisuals);
	}

	public static Sprite getRectangle(final int width, final int height) {
		Image newImage;
		try {
			newImage = ImageIO.read(new File("box.png"));
			newImage = newImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		} catch (Throwable t) {
			throw new RuntimeException(t);
		}
		return new Sprite(newImage, width/2, height/2);
	}

	private static HardpointPosition point(int x, int y) {
		return new ConcreteHardpointPosition(x, y);
	}

	private static class ConcreteHardpointPosition implements HardpointPosition {
		int x, y, z, rot;

		public ConcreteHardpointPosition(int x, int y) {
			this.x = x;
			this.y = y;
			this.z = 0;
			this.rot = 0;
		}

		@Override
		public int getX() {
			return x;
		}

		@Override
		public int getY() {
			return y;
		}

		@Override
		public int getZ() {
			return z;
		}

		@Override
		public int getRotation() {
			return rot;
		}

	}
}
