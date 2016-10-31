package com.packtpub.libgdx.bludbourne;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.utils.Array;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public class EntityConfig {
    Array<AnimationConfig> animationConfig;
    Entity.State state = Entity.State.IDLE;
    Entity.Direction direction = Entity.Direction.DOWN;
    String entityID;
    TypingBox typingBox;

    public TypingBox getTypingBox() {
        return typingBox;
    }

    public void setTypingBox(TypingBox typingBox) {
        this.typingBox = typingBox;
    }

    EntityConfig(){
        animationConfig = new Array<AnimationConfig>();
        typingBox = new TypingBox();
    }

    public Array<AnimationConfig> getAnimationConfig() {
        return animationConfig;
    }

    public void setAnimationConfig(Array<AnimationConfig> animationConfig) {
        this.animationConfig = animationConfig;
    }

    public Entity.State getState() {
        return state;
    }

    public void setState(Entity.State state) {
        this.state = state;
    }

    public Entity.Direction getDirection() {
        return direction;
    }

    public void setDirection(Entity.Direction direction) {
        this.direction = direction;
    }

    public String getEntityID() {
        return entityID;
    }

    public void setEntityID(String entityID) {
        this.entityID = entityID;
    }

    static public class AnimationConfig{
        private float frameDuration = 1.0f;
        private Entity.AnimationType animationType;
        private Array<String> texturePaths;
        private Array<GridPoint2> gridPoints;

        public AnimationConfig(){
            animationType = Entity.AnimationType.IDLE;
            texturePaths = new Array<String>();
            gridPoints = new Array<GridPoint2>();
        }

        public float getFrameDuration() {
            return frameDuration;
        }

        public void setFrameDuration(float frameDuration) {
            this.frameDuration = frameDuration;
        }

        public Entity.AnimationType getAnimationType() {
            return animationType;
        }

        public void setAnimationType(Entity.AnimationType animationType) {
            this.animationType = animationType;
        }

        public Array<String> getTexturePaths() {
            return texturePaths;
        }

        public void setTexturePaths(Array<String> texturePaths) {
            this.texturePaths = texturePaths;
        }

        public Array<GridPoint2> getGridPoints() {
            return gridPoints;
        }

        public void setGridPoints(Array<GridPoint2> gridPoints) {
            this.gridPoints = gridPoints;
        }
    }

    static public class TypingBox {
        String texturePath;
        GridPoint2 gridPoint;
        Integer textureWidth;
        Integer textureHeight;

        public TypingBox(){
            gridPoint = new GridPoint2();
        }

        public String getTexturePath() {
            return texturePath;
        }

        public void setTexturePath(String texturePath) {
            this.texturePath = texturePath;
        }

        public GridPoint2 getGridPoint() {
            return gridPoint;
        }

        public void setGridPoint(GridPoint2 gridPoint) {
            this.gridPoint = gridPoint;
        }

        public Integer getTextureWidth() {
            return textureWidth;
        }

        public void setTextureWidth(Integer textureWidth) {
            this.textureWidth = textureWidth;
        }

        public Integer getTextureHeight() {
            return textureHeight;
        }

        public void setTextureHeight(Integer textureHeight) {
            this.textureHeight = textureHeight;
        }
    }


}
