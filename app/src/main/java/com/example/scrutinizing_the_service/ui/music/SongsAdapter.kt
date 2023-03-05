package com.example.scrutinizing_the_service.ui.music

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scrutinizing_the_service.data.Song
import com.example.scrutinizing_the_service.databinding.ItemMusicBinding

class SongsAdapter(
    val itemClickListener: (ItemClickListener) -> Unit
) : RecyclerView.Adapter<SongsAdapter.SongViewHolder>() {

    private val songs = mutableListOf<Song>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        return SongViewHolder(
            ItemMusicBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        holder.bindData(songs[position], position)
    }

    override fun getItemCount() = songs.size

    fun addNewItems(songs: List<Song>) {
        val size = this.songs.size
        this.songs.addAll(songs)
        notifyItemChanged(size, songs.size)
    }

    fun getItemAtPosition(songPosition: Int) =  songs[songPosition]

    inner class SongViewHolder(
        private val binding: ItemMusicBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindData(song: Song, position: Int) {
            with(binding) {
                tvSong.text = song.name
                tvArtist.text = song.artist
                tvPath.text = song.path

                root.setOnClickListener {
                    itemClickListener(
                        ItemClickListener.ItemClicked(
                            song,
                            position,
                            itemCount
                        )
                    )
                }
            }
        }

    }

}