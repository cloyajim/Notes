package com.example.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notes.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var notesFinishedAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        noteAdapter = NoteAdapter(mutableListOf(), this)
        binding.rvNotes.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = noteAdapter
        }

        notesFinishedAdapter = NoteAdapter(mutableListOf(), this)
        binding.rvNotesFinished.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = notesFinishedAdapter
        }

        binding.btnAdd.setOnClickListener {
            if(binding.etDescription.text.toString().isNotBlank()){
                val note = Note((noteAdapter.itemCount + 1).toLong(),
                    binding.etDescription.text.toString().trim())

                addNoteAuto(note)
                binding.etDescription.text?.clear()

            } else {
                binding.etDescription.error = getString(R.string.validation_field_required)
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    private fun getData(){
        val data = mutableListOf(
            Note(1,"Estudiar"),
            Note(2, "Ver serie favorita"),
            Note(3, "Comer"),
            Note(4, "Caminar", true))

        data.forEach { note ->
            addNoteAuto(note)
        }
    }

    private fun addNoteAuto(note: Note) {
        if(note.isFinish){
            notesFinishedAdapter.add(note)
        } else {
            noteAdapter.add(note)
        }
    }

    private fun deleteNoteAuto(note: Note) {
        if(note.isFinish){
            noteAdapter.remove(note)
        }else {
            notesFinishedAdapter.remove(note)
        }

    }

    override fun onChecked(note: Note) {
        deleteNoteAuto(note)
        addNoteAuto(note)
    }

    override fun onLongClick(note: Note, currentAdapter:NoteAdapter) {
        val builder = AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setPositiveButton(getString(R.string.dialog_ok), { dialogInterface, i ->
                currentAdapter.remove(note)
            })
            .setNegativeButton(getString(R.string.dialog_cancel), null)
        builder.create().show()


        //deleteNoteAuto(note)
        //binding.etDescription.text?.delete()

    }


}