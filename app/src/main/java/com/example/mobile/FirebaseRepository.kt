package com.example.mobile

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

object FirebaseRepository {
    private val database = FirebaseDatabase.getInstance().reference.child("dados")

    fun carregarDados(
        onSuccess: (List<Item>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val lista = mutableListOf<Item>()
                for (data in snapshot.children) {
                    val item = data.getValue(Item::class.java)
                    item?.let {
                        it.id = data.key ?: ""
                        lista.add(it)
                    }
                }

                onSuccess(lista)
            }

            override fun onCancelled(error: DatabaseError) {
                onFailure(error.message)
            }
        })
    }

    fun excluirItem(
        itemId: String,
        onSuccess: () -> Unit,
        onFailure: (String) -> Unit
    ) {
        if (itemId.isNotEmpty()) {
            database.child(itemId).removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    onSuccess()
                } else {
                    onFailure(task.exception?.message ?: "Erro desconhecido ao excluir item")
                }
            }
        } else {
            onFailure("ID do item inválido ou vazio")
        }
    }
}

