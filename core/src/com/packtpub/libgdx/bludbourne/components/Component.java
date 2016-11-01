package com.packtpub.libgdx.bludbourne.components;

/**
 * Created by ldalzotto on 30/10/2016.
 */
public interface Component {
    public static final String MESSAGE_TOKEN = ":::::";

    public static enum MESSAGE{
        CURRENT_POSITION,
        INIT_START_POSITION,
        CURRENT_DIRECTION,
        CURRENT_STATE,
        COLLISION_WITH_MAP,
        COLLISION_WITH_ENTITY,
        LOAD_ANIMATIONS,
        INIT_DIRECTION,
        INIT_STATE,
        INIT_SELECT_ENTITY,
        ENTITY_SELECTED,
        ENTITY_DESELECTED,
        LOAD_TYPING_BOX,
        TYPING_WORD_INIT,
        TYPING_LETTER_FOUND;
    }

    void dispose();
    void receiveMessage(String message);

}
