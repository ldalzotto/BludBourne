package com.packtpub.libgdx.bludbourne.components.comTypeable;

import java.util.HashMap;

/**
 * Created by ldalzotto on 01/11/2016.
 */
//TODO effectuer un refactoring sur les classes utilisant cette classe "Typeable"
public class WrapperWordAndLetterToType {

    private HashMap<String, HashMap<String, Boolean>> _wordAndLetterToType;

    public WrapperWordAndLetterToType() {
        _wordAndLetterToType = new HashMap<String, HashMap<String, Boolean>>();
    }

    public HashMap<String, HashMap<String, Boolean>> getWordAndLetterToType() {
        return _wordAndLetterToType;
    }

    public void setWordAndLetterToType(HashMap<String, HashMap<String, Boolean>> _wordAndLetterToType) {
        this._wordAndLetterToType = _wordAndLetterToType;
    }
}
