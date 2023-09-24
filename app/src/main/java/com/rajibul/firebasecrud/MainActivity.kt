package com.rajibul.firebasecrud

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rajibul.firebasecrud.databinding.ActivityMainBinding
import com.rajibul.firebasecrud.databinding.AdditionDialogBinding

class MainActivity : AppCompatActivity(), ListInterface {
    lateinit var binding: ActivityMainBinding
    lateinit var recyclerAdapter: RecyclerAdapter
    lateinit var layoutManager: LinearLayoutManager
    var firestore= Firebase.firestore
    var notes:ArrayList<Notes> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        recyclerAdapter= RecyclerAdapter(notes,this)


        firestore.collection("Users")
            .get()
            .addOnSuccessListener {
              //  System.out.println("in snapshot ${it.documents}")
                for(items in it.documents){
                    System.out.println("items ${items.data}")
                    var firestoreClass= items.toObject(Notes::class.java)?:Notes()
                    firestoreClass.id=items.id
                notes.add(firestoreClass)
                }
                recyclerAdapter.notifyDataSetChanged()
            }
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvNotes.layoutManager = layoutManager
        binding.rvNotes.adapter = recyclerAdapter



        binding.fabAdd.setOnClickListener {
            var dialog=Dialog(this)
            var dialogBinding:AdditionDialogBinding
            dialogBinding= AdditionDialogBinding.inflate(layoutInflater)
            dialog.setContentView(dialogBinding.root)
         //   throw RuntimeException("Test Crash")
            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            dialog.show()
            dialogBinding.textClock.setOnClickListener {
                Toast.makeText(this@MainActivity, "TextClock", Toast.LENGTH_SHORT).show()
            }
            dialogBinding.btnAdd.setOnClickListener{
                firestore.collection("Users")
                .add(Notes(title = dialogBinding.etTitle.text.toString(),
                    description = dialogBinding.etDescription.text.toString(),
                    time= dialogBinding.textClock.text.toString()))
                    .addOnSuccessListener {
                        Toast.makeText(this,"Data Added",Toast.LENGTH_SHORT).show()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this,"Data Addition Failed",Toast.LENGTH_SHORT).show()
                    }
                    .addOnCanceledListener {
                        Toast.makeText(this,"Cancelled",Toast.LENGTH_SHORT).show()
                    }
                notes.clear()
                firestore.collection("Users")
                    .get()
                    .addOnSuccessListener {
                        //  System.out.println("in snapshot ${it.documents}")
                        for(items in it.documents){
                            System.out.println("items ${items.data}")
                            var firestoreClass= items.toObject(Notes::class.java)?:Notes()
                            firestoreClass.id=items.id
                            notes.add(firestoreClass)
                        }
                        recyclerAdapter.notifyDataSetChanged()
                    }

                dialog.dismiss()
            }

        }
    }

    override fun onDeleteClick(notes: Notes, position: Int) {

    }

    override fun onUpdateClick(notes: Notes, position: Int) {
        var dialog=Dialog(this)
        var dialogBinding:AdditionDialogBinding
        dialogBinding= AdditionDialogBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.show()

        dialogBinding.btnAdd.setOnClickListener {
            var updateNotes = Notes(
                title = dialogBinding.etTitle.text.toString(),
                description = dialogBinding.etDescription.text.toString(),
                time = dialogBinding.textClock.text.toString(),
                id = notes.id ?:""
            )
            firestore.collection("Users")
                .document(notes.id ?: "")
                .set(updateNotes)
                .addOnSuccessListener {
                    Toast.makeText(this, "Data Updated", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Data Updation Failed", Toast.LENGTH_SHORT).show()
                }
                .addOnCanceledListener {
                    Toast.makeText(this, "Data Updation Cancelled", Toast.LENGTH_SHORT).show()
                }
            firestore.collection("Users")
                .get()
                .addOnSuccessListener {
                    //  System.out.println("in snapshot ${it.documents}")
                    for(items in it.documents){
                        System.out.println("items ${items.data}")
                        var firestoreClass= items.toObject(Notes::class.java)?:Notes()
                        firestoreClass.id=items.id

                        recyclerAdapter.notifyDataSetChanged()
                    }
            recyclerAdapter.notifyDataSetChanged()
            dialog.dismiss()
        }


    }

}}