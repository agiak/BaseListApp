package gr.baseapps.baselistapp.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import javax.inject.Inject

class NetworkInterceptor @Inject constructor(): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(getRequest(chain.request()))
    }

    private fun getRequest(request: Request): Request {
        return request.newBuilder()
            .build()
    }
}
