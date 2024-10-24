package com.example.listviewobjects

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.listviewobjects.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var indexClicked: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userList = ArrayList<User>()
        userList.add(User("Vagner", "123"))

        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        binding.listUsers.adapter = adapter

        disableButtonEdit()
        disableButtonRemove()

        binding.listUsers.setOnItemClickListener { parent, view, position, id ->
            val user = userList.get(position)
            binding.editUsername.setText(
                user.username
            )
            binding.editPassword.setText(user.password)
            indexClicked = position
            disableButtonInsert()
            enableButtonEdit()
            enableButtonRemove()
        }

        binding.buttonEdit.setOnClickListener({
            if (indexClicked != -1) {
                val username = binding.editUsername.text.toString().trim()
                val password = binding.editPassword.text.toString().trim()

                if (!username.isEmpty() && !password.isEmpty()) {
                    val user = userList.get(indexClicked)
                    user.username = username
                    user.password = password

                    clearFields()
                    reloadArray(adapter)
                    enableButtonInsert()
                } else {
                    Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        binding.buttonInsert.setOnClickListener({
            if (indexClicked == -1) {
                val user =
                    User(
                        binding.editUsername.text.toString().trim(),
                        binding.editPassword.text.toString().trim()
                    )

                if (user.password.isEmpty() || user.username.isEmpty()) {
                    Toast.makeText(this, "Username and password are required", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    userList.add(user)
                    reloadArray(adapter)
                    clearFields()
                }
            }
        })

        binding.buttonRemove.setOnClickListener({
            if (indexClicked != -1) {
                userList.removeAt(indexClicked)

                clearFields()
                reloadArray(adapter)
                enableButtonInsert()
                disableButtonRemove()
                disableButtonEdit()
            }
        })

        binding.buttonClear.setOnClickListener({
            userList.clear()
            clearFields()
            reloadArray(adapter)
            enableButtonInsert()
            disableButtonRemove()
            disableButtonEdit()
        })
    }

    private fun clearFields() {
        binding.editUsername.setText("")
        binding.editPassword.setText("")
        indexClicked = -1
        enableButtonInsert()
        disableButtonRemove()
        disableButtonEdit()
    }

    private fun reloadArray(adapter: ArrayAdapter<User>) {
        adapter.notifyDataSetChanged()
    }

    private fun disableButtonInsert() {
        binding.buttonInsert.isEnabled = false

    }

    private fun enableButtonInsert() {
        binding.buttonInsert.isEnabled = true
    }

    private fun enableButtonEdit() {
        binding.buttonEdit.isEnabled = true
    }

    private fun disableButtonEdit() {
        binding.buttonEdit.isEnabled = false
    }

    private fun enableButtonRemove() {
        binding.buttonRemove.isEnabled = true
    }

    private fun disableButtonRemove() {
        binding.buttonRemove.isEnabled = false
    }
}