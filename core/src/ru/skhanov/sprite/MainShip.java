package ru.skhanov.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.skhanov.base.MovingFont;
import ru.skhanov.math.Rect;
import ru.skhanov.pool.BulletPool;
import ru.skhanov.pool.ExplosionPool;

public class MainShip extends Ship {


    private static Vector2 mainShipBulletV = new Vector2(0, 0.3f);
    private static float mainShipBulletHeight = 0.01f;

    private Vector2 v0 = new Vector2(0.5f, 0);


    private boolean pressedLeft;
    private boolean pressedRight;



    public MainShip(TextureRegion regions, TextureRegion bulletRegion, BulletPool bulletPool, ExplosionPool explosionPool, Sound shootSound, int hp, int damage, MovingFont hpMoveFont) {
        super(regions, bulletPool, shootSound, hp, damage, explosionPool, hpMoveFont);
        setHeightProportion(0.15f);
        this.bulletPool = bulletPool;
        this.bulletRegion = bulletRegion;
        this.shipType = ShipType.MAIN_SHIP;

    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setBottom(worldBounds.getBottom());
    }

    @Override
    public void update(float delta) {
        super.update(delta);
        reachWorldBoundsCheck();
        pos.mulAdd(v, delta);

    }

    private void reachWorldBoundsCheck() {
        if(getLeft() < worldBounds.getLeft()) {
            stopMove();
            setLeft(worldBounds.getLeft());
        } else if(getRight() > worldBounds.getRight()) {
            stopMove();
            setRight(worldBounds.getRight());
        }
    }

    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = true;
                moveLeft();
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = true;
                moveRight();
                break;
            case Input.Keys.UP:
            case Input.Keys.SPACE:
                shoot(bulletRegion, mainShipBulletV, mainShipBulletHeight, damage);
                break;
        }
        return false;
    }


    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A:
            case Input.Keys.LEFT:
                pressedLeft = false;
                if(pressedRight) {
                    moveRight();
                } else {
                    stopMove();
                }
                break;
            case Input.Keys.D:
            case Input.Keys.RIGHT:
                pressedRight = false;
                if(pressedLeft) {
                    moveLeft();
                } else  {
                    stopMove();
                }
                stopMove();
                break;
        }
        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if(touch.x < 0) {
            moveLeft();
        } else {
            moveRight();
        }
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        stopMove();
        return false;
    }

    @Override
    public boolean isBulletCollision(Rect bullet) {
        return super.isBulletCollision(bullet) && pos.y > bullet.getBottom();
    }

    public void moveRight() {
        System.out.println( "shipRight:" + getRight() + "/worldRight" + worldBounds.getRight());
            v.set(v0);
    }

    public void moveLeft() {
        System.out.println( "shipLef:" + getLeft() + "/worldLeft" + worldBounds.getLeft());
        v.set(v0).rotate(180);
    }

    private void stopMove() {
        v.setZero();
    }

    @Override
    public void destroy() {
        boom();
        super.destroy();
    }

    public void addMainShipBulletHeight(float mainShipBulletHeight) {
        MainShip.mainShipBulletHeight += mainShipBulletHeight;
    }

    public void addDamage(int damage) {
        this.damage += damage;
    }

    public void accelerateBullet(float v) {
        mainShipBulletV.set(0, mainShipBulletV.y + v);
    }
}


