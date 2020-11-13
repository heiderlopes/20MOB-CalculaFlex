package br.com.calculaflex.extensions

import br.com.calculaflex.data.remote.utils.firebase.RemoteConfigUtils
import com.google.common.primitives.Primitives
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import com.google.gson.reflect.TypeToken

@Throws(JsonSyntaxException::class)
suspend fun <T> Gson.fromRemoteConfig(keyRemoteConfig: String, classOfT: Class<T>): T? {
    val json = RemoteConfigUtils.getFirebaseRemoteConfig().getString(keyRemoteConfig)
    val `object` = fromJson(json, classOfT)
    return Primitives.wrap(classOfT).cast(`object`)
}

suspend fun <T> Gson.fromListRemoteConfig(
    keyRemoteConfig: String,
    clazz: Class<T>?
): MutableList<T>? {
    val json = RemoteConfigUtils.getFirebaseRemoteConfig().getString(keyRemoteConfig)
    val typeOfT = TypeToken.getParameterized(MutableList::class.java, clazz).type
    return Gson().fromJson(json, typeOfT)
}
