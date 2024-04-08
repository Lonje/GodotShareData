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

    override fun getPluginName() = "GodotShareData"

    @UsedByGodot
    fun share_text(title: String, subject: String, text: String) {
        Log.d(logTag, "shareText() called")
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType(mimeTypeText)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        activity?.startActivity(Intent.createChooser(shareIntent, title))
    }

    @UsedByGodot
    fun share_image(path: String, title: String, subject: String, text: String?) {
        Log.d(logTag, "shareImage() called with path $path")
        val f = File(path)
        val uri: Uri = try {
            FileProvider.getUriForFile(activity!!, activity!!.packageName, f)
        } catch (e: IllegalArgumentException) {
            Log.e(logTag, "The selected file can't be shared: $path", e)
            return
        }
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType(mimeTypeImage)
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
        shareIntent.putExtra(Intent.EXTRA_TEXT, text)
        shareIntent.clipData = ClipData.newRawUri("", uri)
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri)
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        activity?.startActivity(Intent.createChooser(shareIntent, title))
    }}
