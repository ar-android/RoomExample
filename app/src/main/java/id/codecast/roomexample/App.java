package id.codecast.roomexample;

import android.app.Application;
import android.arch.persistence.room.Room;

public class App extends Application{

    public static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        database = Room.databaseBuilder(this, AppDatabase.class, "product-db").build();
    }
}
