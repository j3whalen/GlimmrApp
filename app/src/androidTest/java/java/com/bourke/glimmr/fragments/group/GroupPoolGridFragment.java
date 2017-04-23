package java.com.bourke.glimmr.fragments.group;

import com.bourke.glimmr.BuildConfig;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.bourke.glimmr.R;
import com.bourke.glimmr.common.Constants;
import com.bourke.glimmr.fragments.base.PhotoGridFragment;
import com.bourke.glimmr.fragments.group.AddToGroupDialogFragment;
import com.bourke.glimmr.tasks.LoadGroupPoolTask;
import com.googlecode.flickrjandroid.groups.Group;
import com.googlecode.flickrjandroid.photos.Photo;

public class GroupPoolGridFragment extends PhotoGridFragment {

    private static final String TAG = "Glimmr/GroupPoolGridFragment";

    private static final String KEY_NEWEST_GROUPPOOL_PHOTO_ID =
        "glimmr_newest_grouppool_photo_id";
    private static final String KEY_GROUP_FRAGMENT_GROUP_ID =
        "glimmr_grouppool_group_id";

    private Group mGroup;

    public static GroupPoolGridFragment newInstance(Group group) {
        GroupPoolGridFragment newFragment = new GroupPoolGridFragment();
        newFragment.mGroup = group;
        return newFragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.groupviewer_fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_add_photos:
                FragmentTransaction ft =
                    mActivity.getSupportFragmentManager().beginTransaction();
                ft.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                Fragment prev = mActivity.getSupportFragmentManager()
                    .findFragmentByTag(AddToGroupDialogFragment.TAG);
                if (prev != null) {
                    ft.remove(prev);
                }
                ft.addToBackStack(null);

                DialogFragment newFragment =
                    AddToGroupDialogFragment.newInstance(mGroup);
                newFragment.show(ft, AddToGroupDialogFragment.TAG);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Once the parent binds the adapter it will trigger cacheInBackground
     * for us as it will be empty when first bound.  So we don't need to
     * override startTask().
     */
    @Override
    protected boolean cacheInBackground() {
        startTask(mPage++);
        return mMorePages;
    }

    private void startTask(int page) {
        super.startTask();
        mActivity.setProgressBarIndeterminateVisibility(Boolean.TRUE);
        new LoadGroupPoolTask(this, mGroup, page).execute(mOAuth);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_GROUP_FRAGMENT_GROUP_ID, mGroup.getId());
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null && mGroup == null) {
            String groupId = savedInstanceState.getString(
                    KEY_GROUP_FRAGMENT_GROUP_ID);
            if (groupId != null) {
                mGroup = new Group();
                mGroup.setId(groupId);
            } else {
                Log.e(TAG, "No stored group id in savedInstanceState");
            }
        }
    }

    @Override
    public String getNewestPhotoId() {
        SharedPreferences prefs = mActivity.getSharedPreferences(Constants
                .PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getString(KEY_NEWEST_GROUPPOOL_PHOTO_ID, null);
    }

    @Override
    public void storeNewestPhotoId(Photo photo) {
        SharedPreferences prefs = mActivity.getSharedPreferences(Constants
                .PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(KEY_NEWEST_GROUPPOOL_PHOTO_ID, photo.getId());
        editor.commit();
        if (BuildConfig.DEBUG)
            Log.d(getLogTag(), "Updated most recent grouppool photo id to " +
                photo.getId());
    }

    @Override
    protected String getLogTag() {
        return TAG;
    }
}
