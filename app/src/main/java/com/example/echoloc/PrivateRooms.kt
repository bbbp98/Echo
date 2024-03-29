package com.example.echoloc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.echoloc.adapter.PrivateAdapter
import com.example.echoloc.database.Pref
import com.example.echoloc.model.RoomModel
import com.example.echoloc.model.Usermodel
import com.example.echoloc.util.showToast
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_private_rooms.view.*

class PrivateRooms : Fragment(),PrivateAdapter.onClick {

    lateinit var adapter: PrivateAdapter

    lateinit var databaseReference1: DatabaseReference

    lateinit var list:ArrayList<RoomModel>
    lateinit var recyclerView: RecyclerView
    lateinit var pref: Pref

    lateinit var database: FirebaseDatabase
    lateinit var databaseReference: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       var view =inflater.inflate(R.layout.fragment_private_rooms, container, false)
        database=FirebaseDatabase.getInstance()
        pref = Pref(requireContext())

        databaseReference = database.getReference("Echoloc").child("private")
        databaseReference1=database.getReference("Echoloc").child("private")
        recyclerView = view.recyclerview_private
        list = ArrayList()
        adapter = PrivateAdapter(requireContext(), list, pref.getData("id"),this)

        recyclerView.adapter = adapter
        getPrivateRooms()
        return view
    }

    private fun getPrivateRooms() {
        databaseReference.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                list.clear()

                for (postsnapshot in snapshot.children) {
                    var value = postsnapshot.getValue(RoomModel::class.java)
                    var isAdmin = false
                    if (pref.getData("id") == value!!.admin_id) {
                        value.isGroupjoined(true)
                    }
                    list.add(value!!)
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                showToast(context!!, error.message)
            }

        })
    }

    override fun onGroupJoined(roomModel: RoomModel) {

    var userModel=
        Usermodel(pref.getData("id"), pref.getData("name"), pref.getData("email"), "", pref.getData("call"),pref.getData("profileImageUrl"))

        databaseReference1.child(roomModel.group_id).child("requests").child(pref.getData("id"))
            .setValue(userModel)

    }
}