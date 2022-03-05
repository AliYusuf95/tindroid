package co.tinode.tinui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class InvalidTopicFragment extends Fragment {
    public InvalidTopicFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(false);
        return inflater.inflate(R.layout.tinui_fragment_invalid_topic, container, false);
    }
}
