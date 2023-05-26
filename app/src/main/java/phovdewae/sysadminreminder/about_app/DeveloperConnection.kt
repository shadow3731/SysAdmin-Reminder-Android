package phovdewae.sysadminreminder.about_app

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import phovdewae.sysadminreminder.R

class DeveloperConnection {

    fun sendToMail(context: Context) {
        val email = "v1rus3731@gmail.com"

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:")
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        intent.putExtra(Intent.EXTRA_SUBJECT, "SysAdmin Reminder for Android feedback")

        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(intent)
        } else {
            Toast.makeText(
                context,
                "${context.getString(R.string.developer_email_title)} $email",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun sendToPage(context: Context, socialNetwork: String) {
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
                context.getString(R.string.about_app_dev_vkontakte) -> Toast.makeText(
                    context,
                    "${context.getString(R.string.developer_vkontakte_title)} $vkUrl",
                    Toast.LENGTH_LONG
                ).show()

                context.getString(R.string.about_app_dev_linkedin) -> Toast.makeText(
                    context,
                    "${context.getString(R.string.developer_linkedin_title)} $linkedInUrl",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}