package com.example.scrutinizing_the_service.services

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.os.IInterface
import android.os.Parcel
import java.io.FileDescriptor

class MyService : Service() {

    override fun onBind(intent: Intent): IBinder {
        return object : IBinder {
            override fun getInterfaceDescriptor(): String? {
                TODO("Not yet implemented")
            }

            override fun pingBinder(): Boolean {
                TODO("Not yet implemented")
            }

            override fun isBinderAlive(): Boolean {
                TODO("Not yet implemented")
            }

            override fun queryLocalInterface(p0: String): IInterface? {
                TODO("Not yet implemented")
            }

            override fun dump(p0: FileDescriptor, p1: Array<out String>?) {
                TODO("Not yet implemented")
            }

            override fun dumpAsync(p0: FileDescriptor, p1: Array<out String>?) {
                TODO("Not yet implemented")
            }

            override fun transact(p0: Int, p1: Parcel, p2: Parcel?, p3: Int): Boolean {
                TODO("Not yet implemented")
            }

            override fun linkToDeath(p0: IBinder.DeathRecipient, p1: Int) {
                TODO("Not yet implemented")
            }

            override fun unlinkToDeath(p0: IBinder.DeathRecipient, p1: Int): Boolean {
                TODO("Not yet implemented")
            }

        }
    }
}