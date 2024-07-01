package com.example.whishlistapp.data

import kotlinx.coroutines.flow.Flow

class Wishrespository(private val wishDao: WishDao) {

    suspend fun addAwish(wish: Wish){
        wishDao.addWish(wish)
    }

    fun getWishes(): Flow<List<Wish>> = wishDao.getAllWishes()

    fun getWishById(id:Long): Flow<Wish>{
        return wishDao.getWishId(id)
    }

    suspend fun updateWish(wish: Wish){
        wishDao.updateWish(wish)
    }

    suspend fun deleteWish(wish: Wish){
        wishDao.deleteWish(wish)
    }
}