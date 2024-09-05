package com.esa.notes.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.esa.notes.R;
import com.esa.notes.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;
    private NoteViewModel noteViewModel;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.rvNote.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvNote.setHasFixedSize(true);

        final NoteAdapter adapter = new NoteAdapter();
        binding.rvNote.setAdapter(adapter);

        noteViewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(getViewLifecycleOwner(), notes -> {
            // Update RecyclerView
            adapter.setNotes(notes);
        });

        adapter.setOnItemClickListener(note -> {
            Bundle bundle = new Bundle();
            bundle.putInt("noteId", note.getId());
            bundle.putString("noteTitle", note.getTitle());
            bundle.putString("noteDescription", note.getDescription());
            NavHostFragment.findNavController(FirstFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment, bundle);
        });

        // Handle delete button click
        adapter.setOnDeleteClickListener(note -> {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Delete Note")
                    .setMessage("Are you sure you want to delete this note ?")
                    .setPositiveButton("Yes", (dialog, which) -> {
                        noteViewModel.delete(note);
                        Toast.makeText(requireContext(), "Note deleted", Toast.LENGTH_SHORT).show();
                    })
                    .setNegativeButton("No", null)
                    .show();
        });

        // Handle navigate to add
        binding.fab.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}