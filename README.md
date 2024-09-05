# NotesApp
This is a simple Notes App built using MVVM (Model-View-ViewModel) architecture, Room database with SQLite, and Java. The app allows users to add, edit, delete, and view notes. Each note consists of a title and a description.

## Features

- Add Notes: Users can add new notes with a title and description.
- Edit Notes: Existing notes can be modified.
- Delete Notes: Users can delete notes using an icon on each list item.
- View All Notes: Notes are displayed in a list format on the home screen.

## App Structure
The app is structured as follows:
```
├── database
│   ├── Note.java                 # Data model class for Note
│   ├── NoteDao.java              # DAO (Data Access Object) interface for database operations
│   └── NoteDatabase.java         # Singleton Room database class
│
├── repository
│   └── NoteRepository.java       # Repository layer for abstracting data operations
│
└── ui
    ├── NoteViewModel.java        # ViewModel for Notes, manages UI-related data
    ├── FirstFragment.java        # Fragment displaying the list of notes
    ├── MainActivity.java         # Main activity hosting the fragments
    ├── NoteAdapter.java          # RecyclerView Adapter for displaying list of notes
    └── SecondFragment.java       # Fragment for adding or editing a note
```



https://github.com/user-attachments/assets/b7038ada-b0d2-474a-91f5-36f82ff36ede



[Download Here](https://drive.google.com/file/d/1SBs3sK5G-ZYQRsCDQJGiyI5sA3egOBvG/view?usp=drive_link)


