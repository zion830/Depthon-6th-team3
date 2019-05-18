package com.depromeet.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.depromeet.R
import com.depromeet.data.Poem

class PoemListAdapter : RecyclerView.Adapter<PoemListAdapter.PoemListViewHolder>() {
    var poems: ArrayList<Poem> = ArrayList()

    fun addPoem(poem: Poem) {
        poems.add(poem)
    }

    fun getItem(index: Int): Poem = poems[index]

    fun clear() {
        poems.clear()
    }

    override fun onCreateViewHolder(parent: ViewGroup, type: Int): PoemListViewHolder {
        val viewGroup = LayoutInflater.from(parent.context).inflate(R.layout.item_poem, parent, false)
        return PoemListViewHolder(viewGroup)
    }

    override fun getItemCount(): Int = poems.size

    override fun onBindViewHolder(holder: PoemListViewHolder, position: Int) {
        holder.bind(poems[position])
    }

    inner class PoemListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userNameText = view.findViewById<View>(R.id.tv_poem_name) as TextView
        val titleText1 = view.findViewById<View>(R.id.tv_poem_title1) as TextView
        val titleText2 = view.findViewById<View>(R.id.tv_poem_title2) as TextView
        val titleText3 = view.findViewById<View>(R.id.tv_poem_title3) as TextView
        val contentText1 = view.findViewById<View>(R.id.tv_poem_content1) as TextView
        val contentText2 = view.findViewById<View>(R.id.tv_poem_content2) as TextView
        val contentText3 = view.findViewById<View>(R.id.tv_poem_content3) as TextView

        fun bind(data: Poem) {
            userNameText.text = data.userName
            titleText1.text = data.wordFirst.substring(0, 1)
            titleText2.text = data.wordSecond.substring(0, 1)
            titleText3.text = data.wordThird.substring(0, 1)

            contentText1.text = data.wordFirst
            contentText2.text = data.wordSecond
            contentText3.text = data.wordThird
        }
    }
}
