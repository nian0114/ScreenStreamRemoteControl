package info.dvkr.screenstream.mjpeg.ui.settings

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import info.dvkr.screenstream.common.ModuleSettings
import info.dvkr.screenstream.mjpeg.R
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.EnableIPv6
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.EnableLocalhost
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.LocalhostOnly
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.ServerPort
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.WifiOnly
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.BrightnessSU
import info.dvkr.screenstream.mjpeg.ui.settings.advanced.BrightnessFakeScreen

public data object AdvancedGroup : ModuleSettings.Group {
    override val id: String = "ADVANCED"
    override val position: Int = 4
    override val items: List<ModuleSettings.Item> =
        listOf(WifiOnly, EnableIPv6, EnableLocalhost, LocalhostOnly, ServerPort, BrightnessSU, BrightnessFakeScreen)
            .filter { it.available }.sortedBy { it.position }

    @Composable
    override fun TitleUI(modifier: Modifier) {
        Text(
            text = stringResource(id = R.string.mjpeg_pref_settings_advanced),
            modifier = modifier,
            style = MaterialTheme.typography.titleMedium
        )
    }
}
