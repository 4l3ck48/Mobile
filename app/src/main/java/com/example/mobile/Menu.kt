package com.example.mobile

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class Menu : AppCompatActivity() {

    private lateinit var adapter: DadosAdapter
    private val database = FirebaseDatabase.getInstance().reference.child("item") //

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu)

        val fabEditar: FloatingActionButton = findViewById(R.id.redirectEdit)
        fabEditar.setOnClickListener {
            val intent = Intent(this, EditarMenuActivity::class.java)
            startActivity(intent)
        }

        val recyclerView: RecyclerView = findViewById(R.id.rv_lista_dados)
        recyclerView.layoutManager = LinearLayoutManager(this)

        adapter = DadosAdapter { item, action ->
            when (action) {
                "editar" -> {
                    Toast.makeText(this, "Para editar: ${item.nome} vá para a pagina de edição", Toast.LENGTH_SHORT).show()
                }
                "excluir" -> {
                    FirebaseRepository.excluirItem(
                        itemId = item.id,
                        onSuccess = {
                            Toast.makeText(this, "Item excluído com sucesso!", Toast.LENGTH_SHORT).show()
                            carregarDados()
                        },
                        onFailure = { errorMessage ->
                            Toast.makeText(this, "Erro ao excluir: $errorMessage", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }


        recyclerView.adapter = adapter

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        carregarDados()
    }

    private fun carregarDados() {
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val listaItens = mutableListOf<Item>()
                for (dataSnapshot in snapshot.children) {
                    val item = dataSnapshot.getValue(Item::class.java)
                    item?.let { listaItens.add(it) }
                }
                // Atualiza o RecyclerView com os itens carregados
                adapter.submitList(listaItens)
            }

            override fun onCancelled(error: DatabaseError) {
                // Exibe uma mensagem de erro caso algo dê errado ao carregar os dados
                Toast.makeText(this@Menu, "Erro ao carregar os dados", Toast.LENGTH_SHORT).show()
            }
        })
    }




}
