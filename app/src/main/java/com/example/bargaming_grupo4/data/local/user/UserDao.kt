package com.example.bargaming_grupo4.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UsersEntity)

    @Query("SELECT * FROM BG_USERS WHERE email = :email AND password = :password")
    suspend fun login(email: String, password: String): UsersEntity?
}