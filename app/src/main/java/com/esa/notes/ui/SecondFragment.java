package com.esa.notes.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.esa.notes.R;
import com.esa.notes.database.Note;
import com.esa.notes.databinding.FragmentSecondBinding;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;
    private NoteViewModel noteViewModel;
    private int noteId = -1;
    private boolean isEditMode = false;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentSecondBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        noteViewModel = new ViewModelProvider(requireActivity()).get(NoteViewModel.class);

        Bundle arguments = getArguments();
        if (arguments != null && arguments.containsKey("noteId")) {
            isEditMode = true;
            updateToolbarTitle("Edit Note");
            noteId = arguments.getInt("noteId");
            noteViewModel.getNoteById(noteId).observe(getViewLifecycleOwner(), note -> {
                if (note != null) {
                    binding.edtTitle.setText(note.getTitle());
                    binding.edtDescription.setText(note.getDescription());
                }
            });
        }

        // Handle Button
        binding.btnSave.setOnClickListener(v -> {
            String title = binding.edtTitle.getText().toString().trim();
            String description = binding.edtDescription.getText().toString().trim();

            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(getContext(), "Please insert a title and description", Toast.LENGTH_SHORT).show();
                return;
            }

            // Insert or update the note
            if (arguments != null && arguments.containsKey("noteId")) {
                int noteId = arguments.getInt("noteId");
                Note note = new Note(title, description);
                note.setId(noteId);
                noteViewModel.update(note);
            } else {
                Note note = new Note(title, description);
                noteViewModel.insert(note);
            }

            // Navigate back
            NavHostFragment.findNavController(SecondFragment.this)
                    .navigate(R.id.action_SecondFragment_to_FirstFragment);
        });
    }

    private void updateToolbarTitle(String title) {
        if (getActivity() != null) {
            // Set the title of the top bar (Toolbar)
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(title);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}