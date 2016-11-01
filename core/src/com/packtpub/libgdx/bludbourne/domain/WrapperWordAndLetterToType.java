package com.packtpub.libgdx.bludbourne.domain;

import java.util.HashMap;
import java.util.Map;

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

    public enum EXPECTED_TYPING_INFO {
        EXPECTED_LETTER, EXPECTED_KEY;
    }

    public Map<EXPECTED_TYPING_INFO, String> getExpectedLetterAndKey(){

        HashMap<EXPECTED_TYPING_INFO, String> resultMap = new HashMap<EXPECTED_TYPING_INFO, String>();

        Integer wordLength = _wordAndLetterToType.get(String.valueOf(0)).entrySet().iterator().next().getKey().length();
        String letterThatPlayerHaveToType = null;
        String keyOfLetterThatPlayerHaveToType = null;

        for(int i = 1; i <= wordLength ; i++){
            if(!_wordAndLetterToType.get(String.valueOf(i)).entrySet().iterator().next().getValue()){
                letterThatPlayerHaveToType = _wordAndLetterToType.get(String.valueOf(i)).entrySet().iterator().next().getKey();
                keyOfLetterThatPlayerHaveToType = String.valueOf(i);
                break;
            }
        }

        resultMap.put(EXPECTED_TYPING_INFO.EXPECTED_KEY, keyOfLetterThatPlayerHaveToType);
        resultMap.put(EXPECTED_TYPING_INFO.EXPECTED_LETTER, letterThatPlayerHaveToType);

        return resultMap;
    }

    public void setValueToTrue(String keyOrder, String value){
        HashMap<String, Boolean> setValueToTrue = new HashMap<String, Boolean>();
        setValueToTrue.put(value, Boolean.TRUE);
        _wordAndLetterToType.put(String.valueOf(keyOrder),
                setValueToTrue);
    }

    public boolean isEmpty(){
        return _wordAndLetterToType.isEmpty();
    }

    public void initMap(String wordToType){
        _wordAndLetterToType.clear();
        HashMap<String, Boolean> wordToTypeValue = new HashMap<String, Boolean>();
        wordToTypeValue.put(wordToType, Boolean.FALSE);

        _wordAndLetterToType.put(String.valueOf(0), wordToTypeValue);

        for (int i = 0; i < wordToType.length(); i++){
            HashMap<String, Boolean> letterToTypeValue = new HashMap<String, Boolean>();
            letterToTypeValue.put(String.valueOf(wordToType.charAt(i)), Boolean.FALSE);
            _wordAndLetterToType.put(String.valueOf(i+1), letterToTypeValue);
        }
    }
}
