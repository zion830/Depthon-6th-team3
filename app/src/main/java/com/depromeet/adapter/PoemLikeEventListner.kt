package com.depromeet.adapter

interface PoemLikeEventListener {
    fun onLikeBtnClickListener(poemId: Int, userId: Int, isLike: Boolean)
}