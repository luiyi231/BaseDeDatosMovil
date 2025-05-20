package com.example.androidproyectounivalle.data.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.ForeignKey;
import com.example.androidproyectounivalle.data.dao.BookDao;
import com.example.androidproyectounivalle.data.dao.AuthorDao;
import com.example.androidproyectounivalle.data.dao.PublisherDao;
import com.example.androidproyectounivalle.data.dao.NoteDao;
import com.example.androidproyectounivalle.data.entity.Book;
import com.example.androidproyectounivalle.data.entity.Author;
import com.example.androidproyectounivalle.data.entity.Publisher;
import com.example.androidproyectounivalle.data.entity.Note;
import static androidx.room.ForeignKey.CASCADE;

@Database(entities = {Book.class, Author.class, Publisher.class, Note.class}, version = 2, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract BookDao bookDao();
    public abstract AuthorDao authorDao();
    public abstract PublisherDao publisherDao();
    public abstract NoteDao noteDao();

    private static volatile AppDatabase INSTANCE;

    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    "library_db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}