package com.packtpub.libgdx.bludbourne.components.comAbstract;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;
import com.packtpub.libgdx.bludbourne.map.MapManager;
import com.packtpub.libgdx.bludbourne.Utility;

import java.util.Hashtable;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public abstract class GraphicsComponent implements Component {
    protected TextureRegion _currentFrame = null;
    protected Entity.State _currentState;
    protected float _frameTime = 0f;
    protected Entity.Direction _currentDirection;
    protected Json _json;
    protected Vector2 _currentPosition;
    protected Hashtable<Entity.AnimationType, Animation> _animations;
    protected ShapeRenderer _shapeRenderer;

    protected GraphicsComponent(){
        _currentPosition = new Vector2(0,0);
        _currentState = Entity.State.WALKING;
        _currentDirection = Entity.Direction.DOWN;
        _json = new Json();
        _animations = new Hashtable<Entity.AnimationType, Animation>();
        _shapeRenderer = new ShapeRenderer();
    }

    public abstract void update(Entity entity, MapManager mapManager, Batch batch, float delta);

    protected void updateAnimations(float delta){
        //Want to avoid overflow
        _frameTime = (_frameTime + delta)%5;

        //Look into the appropriate variable when changin position
        switch (_currentDirection){
            case DOWN:
                if(_currentState == Entity.State.WALKING){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_DOWN);
                    if (animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_DOWN);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                }
                break;
            case LEFT:
                if(_currentState == Entity.State.WALKING){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_LEFT);
                    if (animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_LEFT);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                }
                break;
            case UP:
                if(_currentState == Entity.State.WALKING){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_UP);
                    if (animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_UP);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                }
                break;
            case RIGHT:
                if(_currentState == Entity.State.WALKING){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_RIGHT);
                    if (animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                } else if(_currentState == Entity.State.IDLE){
                    Animation animation = _animations.get(Entity.AnimationType.WALK_RIGHT);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrames()[0];
                } else if(_currentState == Entity.State.IMMOBILE) {
                    Animation animation = _animations.get(Entity.AnimationType.IMMOBILE);
                    if(animation == null){
                        return;
                    }
                    _currentFrame = animation.getKeyFrame(_frameTime);
                }
                break;
            default:
                break;
        }
    }

    //Specific two frame animations where each frame is stored in separate texture
    protected Animation loadAnimation(String firstTexture, String secondTexture, Array<GridPoint2> points,
                                      float frameDuration){
        Utility.loadTextureAsset(firstTexture);
        Texture texture1 = Utility.getTextureAsset(firstTexture);

        Utility.loadTextureAsset(secondTexture);
        Texture texture2 = Utility.getTextureAsset(secondTexture);

        TextureRegion[][] texture1Frames = TextureRegion.split(texture1, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT);
        TextureRegion[][] texture2Frames = TextureRegion.split(texture2, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT);

        Array<TextureRegion> animationKeyFrames = new Array<TextureRegion>(2);
        GridPoint2 point = points.first();

        animationKeyFrames.add(texture1Frames[point.x][point.y]);
        animationKeyFrames.add(texture2Frames[point.x][point.y]);

        return new Animation(frameDuration, animationKeyFrames, Animation.PlayMode.LOOP);
    }
    
    protected Animation loadAnimation(String textureName, Array<GridPoint2> points, float frameDuration){
        Utility.loadTextureAsset(textureName);
        Texture texture = Utility.getTextureAsset(textureName);
        
        TextureRegion[][] textureFrames = TextureRegion.split(texture, Entity.FRAME_WIDTH, Entity.FRAME_HEIGHT);
        
        Array<TextureRegion> animationKeyFrames = new Array<TextureRegion>(points.size);

        for (GridPoint2 point :
                points) {
            animationKeyFrames.add(textureFrames[point.x][point.y]);
        }

        return new Animation(frameDuration, animationKeyFrames, Animation.PlayMode.LOOP);
        
    }

    protected TextureRegion extractTextureRegion(TextureRegion textureRegions, GridPoint2 point, int textureHeight,
                                                 int textureWidth){
        TextureRegion[][] textures = textureRegions.split(textureWidth, textureHeight);
        return textures[point.x][point.y];
    }

}
