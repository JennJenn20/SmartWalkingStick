package com.example.smartwalkingstick

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.example.smartwalkingstick.databinding.FragmentEmergencyContactsBinding

class EmergencyContactsDialogFragment : DialogFragment() {
    private var _binding: FragmentEmergencyContactsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEmergencyContactsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Example of adding emergency contacts
        val contactsListView = binding.contactsListView
        val contacts = listOf("John Doe: 123-456-7890", "Jane Smith: 098-765-4321")

        val adapter = android.widget.ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            contacts
        )
        contactsListView.adapter = adapter

        // Handle the Add Contact button
        binding.addContactButton.setOnClickListener {
            // Add logic to open a contact picker or add a new contact
            Log.d("EmergencyContacts", "Add contact clicked")
        }

        // Handle the Close button
        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}