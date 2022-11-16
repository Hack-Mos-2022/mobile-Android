package ru.vdnh.android.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import dagger.hilt.android.AndroidEntryPoint
import ru.vdnh.android.presentation.common.SplashViewModel
import ru.vdnh.android.presentation.util.SetupNavigation
import ru.vdnh.android.ui.theme.AppTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
            .setKeepOnScreenCondition {
//            true
            splashViewModel.isLoading.value
        }
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {
                val screen by splashViewModel.startDestination

                Surface(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    SetupNavigation(
                        startDestination = screen
                    )
                }
            }
        }
    }
}


