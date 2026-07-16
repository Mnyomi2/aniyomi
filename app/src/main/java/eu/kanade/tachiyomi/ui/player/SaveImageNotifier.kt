package eu.kanade.tachiyomi.ui.player

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.app.NotificationCompat
import eu.kanade.tachiyomi.data.notification.Notifications
import eu.kanade.tachiyomi.util.system.cancelNotification
import eu.kanade.tachiyomi.util.system.notify
import tachiyomi.core.common.i18n.stringResource
import tachiyomi.i18n.MR

/**
 * Notifier for image (screenshot) saving progress and results in the player.
 */
class SaveImageNotifier(private val context: Context) {

    private val progressNotificationBuilder by lazy {
        NotificationCompat.Builder(context, Notifications.CHANNEL_COMMON)
    }

    private val completeNotificationBuilder by lazy {
        NotificationCompat.Builder(context, Notifications.CHANNEL_COMMON)
    }

    fun onClear() {
        context.cancelNotification(Notifications.ID_DOWNLOAD_IMAGE)
    }

    fun onComplete(uri: Uri) {
        val shareIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(uri, "image/*")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        val sharePendingIntent = PendingIntent.getActivity(
            context,
            0,
            shareIntent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )

        completeNotificationBuilder
            .setContentTitle(context.stringResource(MR.strings.picture_saved))
            .setSmallIcon(android.R.drawable.stat_sys_download_done)
            .setAutoCancel(true)
            .addAction(
                android.R.drawable.ic_menu_share,
                context.stringResource(MR.strings.action_share),
                sharePendingIntent,
            )

        context.notify(Notifications.ID_DOWNLOAD_IMAGE, completeNotificationBuilder.build())
    }

    fun onError(error: String?) {
        completeNotificationBuilder
            .setContentTitle(context.stringResource(MR.strings.error_saving_picture))
            .setContentText(error)
            .setSmallIcon(android.R.drawable.stat_notify_error)
            .setAutoCancel(true)

        context.notify(Notifications.ID_DOWNLOAD_IMAGE, completeNotificationBuilder.build())
    }
}
