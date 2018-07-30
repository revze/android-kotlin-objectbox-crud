package id.revze.myfriendapp

import android.app.Application
import id.revze.myfriendapp.model.MyObjectBox
import io.objectbox.BoxStore

class CoreApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        ObjectBox.build(this)
    }
}