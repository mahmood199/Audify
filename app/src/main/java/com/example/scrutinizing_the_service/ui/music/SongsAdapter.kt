package com.example.scrutinizing_the_service.ui.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ItemMusicBinding

class SongsAdapter : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    private val songs = mutableListOf<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindData(songs[position])
    }

    override fun getItemCount() = songs.size

    fun addNewItems(songs: List<Song>) {
        val size = this.songs.size
        this.songs.addAll(songs)
        notifyItemChanged(size, songs.size)
    }

    inner class SongViewHolder(
        private val binding: ItemMusicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(song: Song) {
            with(binding) {
                tvSong.text = song.name
                tvArtist.text = song.artist
            }
        }

    }

}