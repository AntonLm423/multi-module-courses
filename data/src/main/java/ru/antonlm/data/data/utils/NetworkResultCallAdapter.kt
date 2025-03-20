package ru.antonlm.data.data.utils

import retrofit2.Call
import retrofit2.CallAdapter
import ru.antonlm.data.domain.NetworkResult
import java.lang.reflect.Type

internal class NetworkResultCallAdapter(
    private val resultType: Type
) : CallAdapter<Type, Call<NetworkResult<Type>>> {

    override fun responseType(): Type = resultType

    override fun adapt(call: Call<Type>): Call<NetworkResult<Type>> {
        return NetworkResultCall(call)
    }
}
