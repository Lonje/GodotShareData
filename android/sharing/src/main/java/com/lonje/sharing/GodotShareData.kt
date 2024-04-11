package com.lonje.sharing

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot
import java.io.File


class GodotShareData(godot: Godot) : GodotPlugin(godot) {
    private val logTag = "godot::GodotShareData"
    private val mimeTypeText = "text/plain"
    private val mimeTypeImage = "image/*"
    private val fileProvider = ".sharefileprovider"

    override fun getPluginName() = "GodotShareData"

    @UsedByGodot
    fun share(text: String, subject: String, title: String, path: String) {
        //Log.d(logTag, "share() called with path $path")
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType(mimeTypeImage)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        if (text.isNotEmpty()) {
            shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        }
        if (subject.isNotEmpty()) {
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        }
        if (title.isNotEmpty()) {
            activity?.startActivity(Intent.createChooser(shareIntent, title))
        }
        if (path.isNotEmpty()) {
            val f = File(path)
            val uri: Uri = try {
                FileProvider.getUriForFile(activity!!, activity!!.packageName+fileProvider, f)
            } catch (e: IllegalArgumentException) {
                Log.e(logTag, "The selected file can't be shared: $path", e)
                return
            }
            shareIntent.clipData = ClipData.newRawUri("", uri)
            shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        }
    }}
