package com.example.testapp.common.images

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import coil.fetch.VideoFrameUriFetcher
import coil.load
import coil.loadAny
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import coil.transform.RoundedCornersTransformation
import com.example.testapp.core.internal.coroutines.DispatcherProvider
import com.example.testapp.common.coil.TinuiCoil.streamImageLoader
import kotlinx.coroutines.withContext
import okhttp3.Headers.Companion.toHeaders

internal object CoilImageLoader : TinuiImageLoader {

    override var imageHeadersProvider: ImageHeadersProvider = DefaultImageHeadersProvider

    override suspend fun loadAsBitmap(
        context: Context,
        url: String,
        transformation: TinuiImageLoader.ImageTransformation,
    ): Bitmap? = withContext(DispatcherProvider.IO) {
        url.takeUnless(String::isBlank)
            ?.let { url ->
                val imageResult = context.streamImageLoader.execute(
                    ImageRequest.Builder(context)
                        .headers(imageHeadersProvider.getImageRequestHeaders().toHeaders())
                        .data(url)
                        .applyTransformation(transformation)
                        .build()
                )
                (imageResult.drawable as? BitmapDrawable)?.bitmap
            }
    }

    override fun load(
        target: ImageView,
        data: Any?,
        placeholderResId: Int?,
        transformation: TinuiImageLoader.ImageTransformation,
        onStart: () -> Unit,
        onComplete: () -> Unit,
    ) {
        val context = target.context
        target.loadAny(data, context.streamImageLoader) {
            headers(imageHeadersProvider.getImageRequestHeaders().toHeaders())
            placeholderResId?.let(::placeholder)
            listener(
                onStart = { onStart() },
                onCancel = { onComplete() },
                onError = { _, _ -> onComplete() },
                onSuccess = { _, _ -> onComplete() },
            )
            applyTransformation(transformation)
        }
    }

    override fun load(
        target: ImageView,
        data: Any?,
        placeholderDrawable: Drawable?,
        transformation: TinuiImageLoader.ImageTransformation,
        onStart: () -> Unit,
        onComplete: () -> Unit,
    ) {
        val context = target.context
        target.loadAny(data, context.streamImageLoader) {
            headers(imageHeadersProvider.getImageRequestHeaders().toHeaders())
            placeholderDrawable?.let(::placeholder)
            listener(
                onStart = { onStart() },
                onCancel = { onComplete() },
                onError = { _, _ -> onComplete() },
                onSuccess = { _, _ -> onComplete() },
            )
            applyTransformation(transformation)
        }
    }

    override fun loadVideoThumbnail(
        target: ImageView,
        uri: Uri?,
        placeholderResId: Int?,
        transformation: TinuiImageLoader.ImageTransformation,
        onStart: () -> Unit,
        onComplete: () -> Unit,
    ) {
        val context = target.context
        target.loadAny(uri, context.streamImageLoader) {
            headers(imageHeadersProvider.getImageRequestHeaders().toHeaders())
            placeholderResId?.let(::placeholder)
            listener(
                onStart = { onStart() },
                onCancel = { onComplete() },
                onError = { _, _ -> onComplete() },
                onSuccess = { _, _ -> onComplete() },
            )
            fetcher(VideoFrameUriFetcher(context))
            applyTransformation(transformation)
        }
    }

    private fun ImageRequest.Builder.applyTransformation(
        transformation: TinuiImageLoader.ImageTransformation,
    ): ImageRequest.Builder =
        when (transformation) {
            is TinuiImageLoader.ImageTransformation.None -> this
            is TinuiImageLoader.ImageTransformation.Circle -> transformations(
                CircleCropTransformation()
            )
            is TinuiImageLoader.ImageTransformation.RoundedCorners -> transformations(
                RoundedCornersTransformation(
                    transformation.radius
                )
            )
        }
}
