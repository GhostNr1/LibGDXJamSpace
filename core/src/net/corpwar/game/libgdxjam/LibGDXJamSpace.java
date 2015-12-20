package net.corpwar.game.libgdxjam;

import com.artemis.*;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import net.corpwar.game.libgdxjam.components.*;
import net.corpwar.game.libgdxjam.screens.GameScreen;
import net.corpwar.game.libgdxjam.screens.SplashScreen;
import net.corpwar.game.libgdxjam.systems.GameLoopSystemInvocationStrategy;
import net.corpwar.game.libgdxjam.systems.InputSystem;
import net.corpwar.game.libgdxjam.systems.PhysicsSystem;
import net.corpwar.game.libgdxjam.systems.SortedRenderSystem;

public class LibGDXJamSpace extends Game {

	// One batch
	public SpriteBatch batch;

	// One camera, orthographic to handle 2d, and a viewport
	public Camera camera;
	public Viewport viewport;

	// Size to viewport
	private final float width = 1920, height = 1080;

	// Arthemis gameworld
	public World arthWorld;

	// Screens
	public SplashScreen splashScreen;
	public GameScreen gameScreen;

	// Handle all assets
	public AssetManager assetManager;
	
	@Override
	public void create () {
		assetManager = new AssetManager();

		// create all screens
		splashScreen = new SplashScreen(this);
		gameScreen = new GameScreen(this);

		// Create camera
		camera = new OrthographicCamera();
		((OrthographicCamera)camera).setToOrtho(false);
		viewport = new StretchViewport(width, height, camera);
		camera.position.set(viewport.getWorldWidth() / 2f, viewport.getWorldHeight() / 2f, 0);

		batch = new SpriteBatch();

		WorldConfiguration config = new WorldConfigurationBuilder()
				.with(new SortedRenderSystem(this), new PhysicsSystem(20), new InputSystem(this, 20))
				//.with(new RenderSys(), new EditingSys(), new ScriptSys())
				.register(new GameLoopSystemInvocationStrategy(20))
				.build();
		arthWorld = new World(config);
		setScreen(splashScreen);

		testData();
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		camera.update();
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
		viewport.update(width, height);
	}

	private void testData() {
		Entity defaultSprite = arthWorld.createEntity();
		defaultSprite.edit()
				.add(new Transform2DComp(new Vector2(0,0), 1, new Vector2(1,1), 0))
				.add(new TextureComp(new Sprite(new Texture("badlogic.jpg"))))
				.add(new PhysicComp())
				.add(new InputComp())
				.add(new ShipComp(100, 100, false));
	}
}
