package ru.skhanov.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.math.MatrixUtils;
import ru.skhanov.math.Rect;

public class Base2DScreen implements Screen, InputProcessor {

    protected SpriteBatch batch;

    private Rect screenBounds; //границы области рисования в пикселях
    protected Rect worldBounds; // границы проекции наших мировых координат
    private Rect glBounds; // границы проэкции OpenGL координат

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    private Vector2 touch;

    @Override
    public void show() {
        System.out.println("show");
        this.batch = new SpriteBatch();
        Gdx.input.setInputProcessor(this);
        this.screenBounds = new Rect();
        this.worldBounds = new Rect();
        this.glBounds = new Rect(0, 0, 1f, 1f);
        this.worldToGl = new Matrix4();
        this.screenToWorld = new Matrix3();
        this.touch = new Vector2();

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize w = " + width + "h=" + height);
        Gdx.input.setInputProcessor(this);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);
        float aspect = width / (float) height;
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        batch.setProjectionMatrix(worldToGl);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);
        resize(worldBounds);
    }

    public void resize(Rect worldBounds) {

    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
    }

    @Override
    public void dispose() {
//        System.out.println("dispose");
        batch.dispose();
    }




    @Override
    public boolean keyDown(int keycode) {
//        System.out.println("key down:" + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
//        System.out.println("key up:" + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
//        System.out.println("key taped:" + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
//        System.out.println(Base2DScreen.class.getSimpleName() + "touchDown x:" + screenX + "y:" + screenY );
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchDown(touch, pointer);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer) {
//        System.out.println(Base2DScreen.class.getSimpleName() +"touchDown touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }


    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println(Base2DScreen.class.getSimpleName()  + "touchDown x:" + screenX + "y:" + screenY );
        touch.set(screenX, screenBounds.getHeight() - screenY).mul(screenToWorld);
        touchUp(touch, pointer);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        System.out.println(Base2DScreen.class.getSimpleName() + "touchUp touch.x = " + touch.x + " touch.y = " + touch.y);
        return false;
    }


    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDown x:" + screenX + "y:" + screenY );
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
