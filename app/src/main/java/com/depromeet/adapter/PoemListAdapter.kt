package com.depromeet.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.depromeet.R
import com.depromeet.data.Poem


class PoemListAdapter(context: Context) : RecyclerView.Adapter<PoemListAdapter.PoemListViewHolder>() {
    var poems: ArrayList<Poem> = ArrayList()
    private val context = context

    fun addPoem(poem: Poem) {
        poems.add(poem)
    }

    fun addPoems(poem: ArrayList<Poem>) {
        poems.addAll(poem)
    }

    fun setPoem(index: Int, poem: Poem) {
        poems[index] = poem
        notifyItemChanged(index)
    }

    fun getItem(index: Int) = poems[index]

    fun clear() {
        poems.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): PoemListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(
                R.layout.item_poem, parent, false)


        return PoemListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int = poems.size

    override fun onBindViewHolder(holder: PoemListViewHolder, position: Int) {
        holder.bind(poems[position])
    }

    inner class PoemListViewHolder(private var view: View) : RecyclerView.ViewHolder(view) {
        fun bind(data: Poem) {
            val userNameText = view.findViewById<View>(R.id.tv_poem_name) as TextView
            val titleText1 = view.findViewById<View>(R.id.tv_main_title1) as TextView
            val titleText2 = view.findViewById<View>(R.id.tv_main_title2) as TextView
            val titleText3 = view.findViewById<View>(R.id.tv_main_title3) as TextView
            val contentText1 = view.findViewById<View>(R.id.tv_main_content1) as TextView
            val contentText2 = view.findViewById<View>(R.id.tv_main_content2) as TextView
            val contentText3 = view.findViewById<View>(R.id.tv_main_content3) as TextView
            val likeCountText = view.findViewById<View>(R.id.tv_poem_like) as TextView
            val likeImageButton = view.findViewById<View>(R.id.ib_poem_like) as? ImageButton

            userNameText.text = data.userName
            titleText1.text = data.wordFirst.substring(0, 1)
            titleText2.text = data.wordSecond.substring(0, 1)
            titleText3.text = data.wordThird.substring(0, 1)

            contentText1.text = data.wordFirst
            contentText2.text = data.wordSecond
            contentText3.text = data.wordThird

            likeCountText.text = data.likeCount.toString()
            likeImageButton?.setImageDrawable(
                    context.getDrawable(if (data.isLike) R.drawable.ic_fill_heart else R.drawable.ic_heart_white))
        }
    }
}
