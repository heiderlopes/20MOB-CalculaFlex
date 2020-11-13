package br.com.calculaflex.data.remote.utils.firebase

import br.com.calculaflex.BuildConfig
import br.com.calculaflex.R
import com.google.android.gms.tasks.Task
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.tasks.await

object RemoteConfigUtils {

    suspend fun getFirebaseRemoteConfig(): FirebaseRemoteConfig {

        val remoteConfig = Firebase.remoteConfig

        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
            fetchTimeoutInSeconds = 60
        }

        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
        remoteConfig.setConfigSettingsAsync(configSettings)

        val cacheExpiration = if (BuildConfig.DEBUG) 0L else 720L
        remoteConfig.fetch(cacheExpiration).await()
        remoteConfig.activate().await()

        return remoteConfig
    }

    suspend fun fetchAndActivate(): Task<Boolean> {
        return getFirebaseRemoteConfig().activate()
    }
}
