package diolink.library.lili.bd

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface LinkDao {
    @Query("SELECT * FROM test")
    fun getAll(): LiveData<List<Link>>

    @Query("SELECT * FROM test")
    fun getAllData(): List<Link>

    @Update
    fun updateLink(link: Link)

    @Insert
    fun addLink(link: Link)

    @Delete
    fun delete(link: Link)
}