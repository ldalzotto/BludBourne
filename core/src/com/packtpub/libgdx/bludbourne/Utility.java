package com.packtpub.libgdx.bludbourne;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.TextureLoader;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * Created by ldalzotto on 29/10/2016.
 */
public class Utility {

    public static final AssetManager _assetManager = new AssetManager();
    private static final String TAG = Utility.class.getSimpleName();
    private static InternalFileHandleResolver _filePathResolver = new InternalFileHandleResolver();

    private final static String STATUSUI_TEXTURE_ATLAS_PATH = "skins/statusui.atlas";
    private final static String STATUSUI_SKIN_PATH = "skins/statusui.json";

    private final static String ITEMS_TEXTURE_ATLAS_PATH = "skins/items.atlas";

    private final static String MAIN_MENU_TEXTURE_ATLAS_PATH = "skins/main_menu_screen.atlas";
    private final static String MAIN_MENU_SKIN_PATH = "skins/main_menu_screen.json";

    public static TextureAtlas STATUSUI_TEXTUREATLAS = new TextureAtlas(Gdx.files.internal(STATUSUI_TEXTURE_ATLAS_PATH));
    public static Skin STATUSUI_SKIN = new Skin(Gdx.files.internal(STATUSUI_SKIN_PATH), STATUSUI_TEXTUREATLAS);
    public static TextureAtlas ITEMS_TEXTUREATLAS = new TextureAtlas(Gdx.files.internal(ITEMS_TEXTURE_ATLAS_PATH));
    public static TextureAtlas MAIN_MENU_TEXTUREATLAS = new TextureAtlas(Gdx.files.internal(MAIN_MENU_TEXTURE_ATLAS_PATH));
    public static Skin MAINMENU_SKIN = new Skin(Gdx.files.internal(MAIN_MENU_SKIN_PATH), MAIN_MENU_TEXTUREATLAS);

    //Once the asset manager is done loading
    public static void unloadAsset (String assetFilenamePath) {
        if(_assetManager.isLoaded(assetFilenamePath)) {
            _assetManager.unload(assetFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Asset is not loaded. Nothing to unload:" + assetFilenamePath);
        }
    }

    public static float loadCompleted(){
        return _assetManager.getProgress();
    }

    public static int numberAssetsQueued(){
        return _assetManager.getQueuedAssets();
    }

    public static boolean updateAssetLoading(){
        return _assetManager.update();
    }

    public static boolean isAssetLoaded(String filename){
        return _assetManager.isLoaded(filename);
    }

    public static void loadMapAsset(String mapFileNamePath){
        if(mapFileNamePath == null || mapFileNamePath.isEmpty()){
            return;
        }

        //load asset
        if(_filePathResolver.resolve(mapFileNamePath).exists()){
            _assetManager.setLoader(TiledMap.class, new TmxMapLoader(_filePathResolver));
            _assetManager.load(mapFileNamePath, TiledMap.class);

            //Until we add loading screen, just block until we load the map
            _assetManager.finishLoadingAsset(mapFileNamePath);
            Gdx.app.debug(TAG, "Map loaded!: " + mapFileNamePath);
        } else {
            Gdx.app.debug(TAG, "Map doesn't exist!: " + mapFileNamePath);
        }
    }

    public static TiledMap getMapAsset(String mapFilenamePath){
        TiledMap map = null;

        // once the asset manager is done loading
        if(isAssetLoaded(mapFilenamePath)) {
            map = _assetManager.get(mapFilenamePath, TiledMap.class);
        } else {
            Gdx.app.debug(TAG, "Map is not loaded: " + mapFilenamePath);
        }

        return map;
    }

    public static void loadTextureAsset(String textureFilenamePath){
        if(textureFilenamePath == null || textureFilenamePath.isEmpty()){
            return;
        }

        //load asset
        if(_filePathResolver.resolve(textureFilenamePath).exists()){
            _assetManager.setLoader(Texture.class, new TextureLoader(_filePathResolver));
            _assetManager.load(textureFilenamePath, Texture.class);

            // until we add loading screen, just block until we load the texture
            _assetManager.finishLoadingAsset(textureFilenamePath);
        } else {
            Gdx.app.debug(TAG, "Texture doesn't exists!: " + textureFilenamePath);
        }
    }

    public static Texture getTextureAsset(String textureFilenamePath){
        Texture texture = null;

        // once the asset manager is done loading
        if(_assetManager.isLoaded(textureFilenamePath)){
            texture = _assetManager.get(textureFilenamePath, Texture.class);
        } else {
            Gdx.app.debug(TAG, "Texture is not loaded: " + textureFilenamePath);
        }

        return texture;
    }

}
