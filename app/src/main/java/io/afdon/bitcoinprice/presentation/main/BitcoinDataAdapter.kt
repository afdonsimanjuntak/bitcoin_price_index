package io.afdon.bitcoinprice.presentation.main

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.afdon.bitcoinprice.databinding.ItemBitcoinDataBinding

class BitcoinDataAdapter : RecyclerView.Adapter<BitcoinDataAdapter.Holder>() {

    val listDiffer = AsyncListDiffer(this, object : DiffUtil.ItemCallback<Item>() {
        override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
            Log.d("afdon", "areItemsTheSame: ")
            return oldItem.time == newItem.time
        }

        override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
            return oldItem == newItem
        }
    })

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(ItemBitcoinDataBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        ))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) =
        holder.bind(listDiffer.currentList[position])

    override fun getItemCount(): Int = listDiffer.currentList.size

    class Holder(
        private val binding: ItemBitcoinDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Item) {
            binding.item = item
        }
    }

    data class Item(
        val time: String,
        val rate: String,
        val latitude: String,
        val longitude: String
    )
}