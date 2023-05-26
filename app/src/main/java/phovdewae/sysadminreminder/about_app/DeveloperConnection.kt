package phovdewae.sysadminreminder.about_app

import android.content.Context
import android.content.Intent
import android.net.Uri
import phovdewae.sysadminreminder.R
import phovdewae.sysadminreminder.databinding.ActivityAboutAppBinding

class DeveloperConnection {

    fun sendToMail(context: Context, binding: ActivityAboutAppBinding) {
        val email = "v1rus3731@gmail.com"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "SysAdmin Reminder for Android feedback")

        if (intent.resolveActivity(context.packageManager) != null) context.startActivity(intent)
        else binding.tvDevLink.text = email
    }

    fun sendToPage(context: Context, binding: ActivityAboutAppBinding, socialNetwork: String) {
        val vkUrl = "https://vk.com/v1rus"
        val linkedInUrl = "https://linkedin.com/in/vladislav-klimenok-195385253/"

        val intent = Intent(Intent.ACTION_VIEW)
        when (socialNetwork) {
            context.getString(R.string.about_app_dev_vkontakte) ->
                intent.data = Uri.parse(vkUrl)
            context.getString(R.string.about_app_dev_linkedin) ->
                intent.data = Uri.parse(linkedInUrl)
        }

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            when (socialNetwork) {
                context.getString(R.string.about_app_dev_vkontakte) -> binding.tvDevLink.text = vkUrl
                context.getString(R.string.about_app_dev_linkedin) -> binding.tvDevLink.text = linkedInUrl
            }
        }
    }
}