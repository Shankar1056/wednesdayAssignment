package com.wednesdayassignment.data.model

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions


class ArtistModel(
    var resultCount: Int? = null,
    var results: ArrayList<ResultItems>? = null

)
@Entity(tableName = "ResultItems")
class ResultItems(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var wrapperType: String? = null,
    var kind: String? = null,
    var artistId: String? = null,
    var collectionId: String? = null,
    var trackId: String? = null,
    var artistName: String? = null,
    var collectionName: String? = null,
    var trackName: String? = null,
    var collectionCensoredName: String? = null,
    var trackCensoredName: String? = null,
    var artistViewUrl: String? = null,
    var collectionViewUrl: String? = null,
    var trackViewUrl: String? = null,
    var previewUrl: String? = null,
    var artworkUrl30: String? = null,
    var artworkUrl60: String? = null,
    var artworkUrl100: String? = null,
    var collectionPrice: String? = null,
    var trackPrice: String? = null,
    var releaseDate: String? = null,
    var collectionExplicitness: String? = null,
    var trackExplicitness: String? = null,
    var discCount: String? = null,
    var discNumber: String? = null,
    var trackCount: String? = null,
    var trackNumber: String? = null,
    var trackTimeMillis: String? = null,
    var country: String? = null,
    var currency: String? = null,
    var primaryGenreName: String? = null,
    var contentAdvisoryRating: String? = null,
    var isStreamable: String? = null
)


@BindingAdapter("imageUrl")
fun loadImage(view: ImageView, imageUrl: String?) {
    Glide.with(view.context)
        .load(imageUrl).apply(RequestOptions().circleCrop())
        .into(view)
}



