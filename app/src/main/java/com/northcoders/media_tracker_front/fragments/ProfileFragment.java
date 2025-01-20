package com.northcoders.media_tracker_front.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.northcoders.media_tracker_front.R;
import com.northcoders.media_tracker_front.databinding.FragmentProfileBinding;
import com.northcoders.media_tracker_front.viewmodel.ProfileViewModel;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding binding;

    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // These should be in a clickHandler class
        // No time at this point
        loadProfilePicture();
        loadLogoutButton();
        loadDeleteButton();
        binding.profileFragmentUsername.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());


    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile,container,false);
        return binding.getRoot();
    }

    private void loadProfilePicture(){
        // Load in the user's profile picture from the google account
        // As a circular image
        ShapeableImageView profilePicture = binding.profileFragmentAvatar;
        Glide.with(profilePicture)
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl().toString())
                .circleCrop()
                .error(R.drawable.circularcustombutton)
                .into(profilePicture);

    }

    private void loadLogoutButton(){
        // Logic for log out button
        binding.profileFragmentLogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 AuthUI.getInstance()
                        .signOut(getContext())
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            // On complete-> Place the app in the login page
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Intent intent = new Intent(getContext(),com.northcoders.media_tracker_front.LoginActivity.class);
                                getActivity().startActivity(intent);
                                // finishing the activity prevents the back button opening the app again
                                getActivity().finish();
                                Toast.makeText(getContext(), "Log out successful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

    }

    private void loadDeleteButton() {
        ProfileViewModel profileViewModel = new ProfileViewModel(this.requireActivity().getApplication());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    binding.profileFragmentDeleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder quit = new AlertDialog.Builder(getContext())
                    .setTitle("Delete Account")
                    .setMessage("Are you sure you want to delete your account " +user.getDisplayName() +"\"?")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("Profile Fragment","USER PRESSED YES");
                            profileViewModel.deleteUser();
                            // For now the user will just be locally signed out
                            // Their data on the backend will be deleted but not their firebase account
                            // When they log in again another user entry will be made on the backend
                            FirebaseAuth.getInstance().signOut();
                            //deleteUserFirebase();
                            Toast.makeText(getContext(), "You have successfully deleted your account", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getContext(),com.northcoders.media_tracker_front.LoginActivity.class);
                            getActivity().startActivity(intent);
                            // finishing the activity prevents the back button opening the app again
                            getActivity().finish();
                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            quit.show();
        }

    });
    }


    /**
     * This is the method to delete the user from Firebase
     * Will be done in the future but can't be done at this time
     * For now the user will be deleted off the backend database
     */

   /* private void deleteUserFirebase(){

        FirebaseAuth.getInstance().getCurrentUser().delete().addOnSuccessListener(
                new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Log.d("ProfileFragment", "User account deleted.");

                        Toast.makeText(getContext(), "You have successfully deleted your account", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getContext(),com.northcoders.media_tracker_front.LoginActivity.class);
                        getActivity().startActivity(intent);
                        // finishing the activity prevents the back button opening the app again
                        getActivity().finish();

                    }
                }
        );

    }*/



}