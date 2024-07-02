package com.example.whishlistapp

import android.content.Context
import androidx.room.Room
import com.example.whishlistapp.data.WishDatabase
import com.example.whishlistapp.data.Wishrespository

object Graph {
    lateinit var database: WishDatabase

    val wishRespository by lazy{
        Wishrespository(wishDao= database.wishDao())
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, WishDatabase:: class.java,"wishlist.db").build()

    }
}