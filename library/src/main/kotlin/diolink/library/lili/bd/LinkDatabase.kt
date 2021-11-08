package diolink.library.lili.bd

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Link::class], version = 1, exportSchema = false)
abstract class LinkDatabase : RoomDatabase() {

    abstract fun linkDao(): LinkDao

    companion object {
        @Volatile
        private var INSTANCE: LinkDatabase? = null

        fun getDatabase(context: Context): LinkDatabase {

            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    LinkDatabase::class.java,
                    "link_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}