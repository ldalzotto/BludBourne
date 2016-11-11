package com.packtpub.libgdx.bludbourne.profile;

import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;

/**
 * Created by ldalzotto on 10/11/2016.
 */
public class ProfileSubject {

    private Array<ProfileObserver> _observers;

    public ProfileSubject(){
        _observers = new Array<ProfileObserver>();
    }

    public void addObserver(ProfileObserver profileObserver){
        _observers.add(profileObserver);
    }

    public void removeObserver(ProfileObserver profileObserver){
        _observers.removeValue(profileObserver, true);
    }

    protected void notify(final ProfileManager profileManager, ProfileObserver.ProfileEvent profileEvent ){
        for(ProfileObserver observer: _observers){
            observer.onNotify(profileManager, profileEvent);
        }
    }

}
