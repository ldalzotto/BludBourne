package com.packtpub.libgdx.bludbourne.profile;

/**
 * Created by ldalzotto on 10/11/2016.
 */
public interface ProfileObserver {

    public static enum ProfileEvent{
        PROFILE_LOADED,
        SAVING_PROFILE
    }

    void onNotify(final ProfileManager profileManager, ProfileEvent profileEvent);

}
