package com.kt.na_social.feed;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kt.na_social.R;
import com.kt.na_social.databinding.FragmentCommentBinding;

public class CommentFragment extends Fragment {

    FragmentCommentBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCommentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}