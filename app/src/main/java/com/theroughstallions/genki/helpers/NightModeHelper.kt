package com.theroughstallions.genki.helpers

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources

/**
 * Provides helper functions to make night mode an easier process to implement.
 */
object NightModeHelper {
    /**
     * Changes the given [ImageView] drawable based on whether the user is in light mode
     * or night mode.
     * @param imageView The image view with drawable to change.
     * @param context The context required for getting new drawable.
     * @param resources Resources for getting UI mode.
     * @param resIDLightMode The id of the light mode drawable to be used.
     * @param resIDNightMode The id of the night mode drawable to be used.
     */
    fun changeDrawableOnNightMode(
        imageView: ImageView,
        context: Context,
        resources: Resources,
        @DrawableRes resIDLightMode: Int,
        @DrawableRes resIDNightMode: Int
    ) {
        when (resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
            Configuration.UI_MODE_NIGHT_YES -> imageView.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    resIDNightMode
                )
            )
            Configuration.UI_MODE_NIGHT_NO -> imageView.setImageDrawable(
                AppCompatResources.getDrawable(
                    context,
                    resIDLightMode
                )
            )
        }
    }
}