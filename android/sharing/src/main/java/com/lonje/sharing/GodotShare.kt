package com.lonje.sharing

import android.content.ClipData
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.review.ReviewManagerFactory
import org.godotengine.godot.Godot
import org.godotengine.godot.plugin.GodotPlugin
import org.godotengine.godot.plugin.UsedByGodot
import java.io.File


class GodotShare(godot: Godot) : GodotPlugin(godot) {
    private val logTag = "godot::GodotShare"
    private val mimeTypeText = "text/plain"
    private val mimeTypeImage = "image/*"
    private val fileProvider = ".sharefileprovider"

    override fun getPluginName() = "GodotShare"

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
        activity?.startActivity(Intent.createChooser(shareIntent, title))
    }

    @UsedByGodot
    fun rate() {
//        Log.d(logTag, "rate()")
        val appCtx = activity?.applicationContext ?: return
        val reviewManager = ReviewManagerFactory.create(appCtx)
        reviewManager.requestReviewFlow().addOnCompleteListener {
            val localActivity = activity
            if (it.isSuccessful && localActivity != null) {
                reviewManager.launchReviewFlow(localActivity, it.result)
            }
        }
    }

    @UsedByGodot
    fun update() {
//        Log.d(logTag, "update()")
        val appCtx = activity?.applicationContext ?: return
        val appUpdateManager = AppUpdateManagerFactory.create(appCtx)
        // Returns an intent object that you use to check for an update.
        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
            }
        }
    }
}
