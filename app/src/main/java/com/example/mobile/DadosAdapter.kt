package com.example.mobile

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.mobile.databinding.ItemDadosBinding

class DadosAdapter(
    private val onItemAction: (Item, String) -> Unit
) : ListAdapter<Item, DadosAdapter.DadosViewHolder>(ItemDiffCallback()) {

    class DadosViewHolder(val binding: ItemDadosBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DadosViewHolder {
        val binding = ItemDadosBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DadosViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DadosViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            tvNomeItem.text = item.nome

            btnEditarItem.setOnClickListener { onItemAction(item, "editar") }
            btnExcluirItem.setOnClickListener { onItemAction(item, "excluir") }
        }
    }
}
