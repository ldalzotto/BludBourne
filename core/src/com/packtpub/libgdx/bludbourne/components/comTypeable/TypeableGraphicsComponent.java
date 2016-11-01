package com.packtpub.libgdx.bludbourne.components.comTypeable;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.packtpub.libgdx.bludbourne.Entity;
import com.packtpub.libgdx.bludbourne.EntityConfig;
import com.packtpub.libgdx.bludbourne.Utility;
import com.packtpub.libgdx.bludbourne.components.comAbstract.GraphicsComponent;
import com.packtpub.libgdx.bludbourne.components.comInterface.Component;
import com.packtpub.libgdx.bludbourne.domain.WrapperWordAndLetterToType;
import com.packtpub.libgdx.bludbourne.map.Map;
import com.packtpub.libgdx.bludbourne.map.MapManager;

import java.util.HashMap;

/**
 * Created by ldalzotto on 31/10/2016.
 */
public class TypeableGraphicsComponent extends GraphicsComponent {

    private static final String TAG = TypeableGraphicsComponent.class.getSimpleName();

    private boolean _isSelected = false;

    private TextureRegion _typingBoxTexture;

    private String _wordToType;
    private float _letterWidth;

    BitmapFont _typeFont;

    private float _typingBoxWidth;
    private WrapperWordAndLetterToType _wrapperWordAndLetterToType;

    public enum TYPE_SUBJECT {
        FOOD, NATURE
    }

    public WrapperWordAndLetterToType getWrapperWordAndLetterToType() {
        return _wrapperWordAndLetterToType;
    }

    public void setWrapperWordAndLetterToType(WrapperWordAndLetterToType _wrapperWordAndLetterToType) {
        this._wrapperWordAndLetterToType = _wrapperWordAndLetterToType;
    }

    public TypeableGraphicsComponent(){
        _typeFont = new BitmapFont();
        _typeFont.getData().setScale(0.1f, 0.1f);
        _wrapperWordAndLetterToType = new WrapperWordAndLetterToType();
    }

    @Override
    public void dispose() {

    }

    @Override
    public void receiveMessage(String message) {
        String[] string = message.split(Component.MESSAGE_TOKEN);

        if(string.length == 0){
            return;
        }

        //Specifically for messages with 1 object payload
        if(string.length == 1){
            if(string[0].equalsIgnoreCase(MESSAGE.ENTITY_DESELECTED.toString())){
                _isSelected = false;
            } else if(string[0].equalsIgnoreCase(MESSAGE.ENTITY_SELECTED.toString())){
                _isSelected = true;
            }
        }

        //Specifically for messages with 1 object payload
        if( string.length == 2 ) {
            if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_POSITION.toString())) {
                _currentPosition = _json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.INIT_START_POSITION.toString())) {
                _currentPosition = _json.fromJson(Vector2.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_STATE.toString())) {
                _currentState = _json.fromJson(Entity.State.class, string[1]);
            } else if (string[0].equalsIgnoreCase(MESSAGE.CURRENT_DIRECTION.toString())) {
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            }else if (string[0].equalsIgnoreCase(MESSAGE.LOAD_ANIMATIONS.toString())) {
                EntityConfig entityConfig = _json.fromJson(EntityConfig.class, string[1]);
                Array<EntityConfig.AnimationConfig> animationConfigs = entityConfig.getAnimationConfig();

                for( EntityConfig.AnimationConfig animationConfig : animationConfigs ){
                    Array<String> textureNames = animationConfig.getTexturePaths();
                    Array<GridPoint2> points = animationConfig.getGridPoints();
                    Entity.AnimationType animationType = animationConfig.getAnimationType();
                    float frameDuration = animationConfig.getFrameDuration();
                    Animation animation = null;

                    if( textureNames.size == 1) {
                        animation = loadAnimation(textureNames.get(0), points, frameDuration);
                    }else if( textureNames.size == 2){
                        animation = loadAnimation(textureNames.get(0), textureNames.get(1), points, frameDuration);
                    }

                    _animations.put(animationType, animation);
                }
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_DIRECTION.toString())){
                _currentDirection = _json.fromJson(Entity.Direction.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.INIT_STATE.toString())){
                _currentState = _json.fromJson(Entity.State.class, string[1]);
            } else if(string[0].equalsIgnoreCase(MESSAGE.LOAD_TYPING_BOX.toString())){
                EntityConfig entityConfig = _json.fromJson(EntityConfig.class, string[1]);
                EntityConfig.TypingBox typingBox = entityConfig.getTypingBox();
                Utility.loadTextureAsset(typingBox.getTexturePath());
                if(Utility.isAssetLoaded(typingBox.getTexturePath())){
                    Texture texture = Utility.getTextureAsset(typingBox.getTexturePath());
                    _typingBoxTexture = extractTextureRegion(new TextureRegion(texture), typingBox.getGridPoint(),
                            typingBox.getTextureWidth(), typingBox.getTextureHeight());
                }
            } else if(string[0].equalsIgnoreCase(MESSAGE.TYPING_LETTER_FOUND.toString())){
                _wrapperWordAndLetterToType = _json.fromJson(WrapperWordAndLetterToType.class, string[1]);
            }
        }
    }

    @Override
    public void update(Entity entity, MapManager mapManager, Batch batch, float delta) {
        updateAnimations(delta);
        if(_isSelected){
            batch.begin();
            _wordToType = getRandomWord(TYPE_SUBJECT.NATURE);
            drawTypingBox(batch, entity);
            if(_wrapperWordAndLetterToType.getWordAndLetterToType().isEmpty()) {
                initTypingWordLogic(entity);
            }
            batch.end();
        }

        batch.begin();
        batch.draw(_currentFrame, _currentPosition.x, _currentPosition.y, 1, 1);
        batch.end();

        //Used to graphically debug boundingboxes

        Rectangle rect = entity.getCurrentBoundingBox();
        Camera camera = mapManager.getCamera();
        _shapeRenderer.setProjectionMatrix(camera.combined);
        _shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        _shapeRenderer.setColor(Color.BLACK);
        _shapeRenderer.rect(rect.getX() * Map.UNIT_SCALE, rect.getY() * Map.UNIT_SCALE, rect.getWidth() * Map.UNIT_SCALE, rect.getHeight() * Map.UNIT_SCALE);
        _shapeRenderer.end();

    }

    private void drawTypingBox(Batch batch, Entity entity){
        _typingBoxWidth = 2;
        batch.draw(_typingBoxTexture, _currentPosition.x,
                _currentPosition.y + entity.getCurrentBoundingBox().getHeight()*Map.UNIT_SCALE, 2, 1);
        _typeFont.draw(batch, _wordToType, _currentPosition.x,
                _currentPosition.y + 2*entity.getCurrentBoundingBox().getHeight()*Map.UNIT_SCALE);
        _typeFont.draw(batch, "f", _currentPosition.x,
                _currentPosition.y);
    }

    private String getRandomWord(TYPE_SUBJECT subject){
        if(subject.equals(TYPE_SUBJECT.FOOD)){
            return "Cake";
        } else if (subject.equals(TYPE_SUBJECT.NATURE)){
            return "Tree";
        } else {
            return "Debug";
        }
    }

    private void initTypingWordLogic(Entity entity){
        _wrapperWordAndLetterToType.initMap(_wordToType);
        entity.sendMessage(MESSAGE.TYPING_WORD_INIT, _json.toJson(_wrapperWordAndLetterToType));
    }

}
