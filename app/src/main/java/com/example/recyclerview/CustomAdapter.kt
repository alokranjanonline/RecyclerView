package com.example.recyclerview

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback

class CustomAdapter(private val mList: List<ItemsViewModel>, var context: Context) :
    RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    private var mInterstitialAd: InterstitialAd? = null
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        holder.imageView.setImageResource(ItemsViewModel.image)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text
        holder.itemView.setOnClickListener(){
            var adRequest = AdRequest.Builder().build()
            InterstitialAd.load(context,"ca-app-pub-3940256099942544/1033173712", adRequest, object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                    holder.textView.text="Not Loaded"
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                    holder.textView.text="Ad Loaded"
                }
            })
            mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
                override fun onAdClicked() {
                    // Called when a click is recorded for an ad.
                    holder.textView.text="Ad was clicked."
                    Log.d(ContentValues.TAG, "Ad was clicked.")
                }

                override fun onAdDismissedFullScreenContent() {
                    // Called when ad is dismissed.
                    Log.d(ContentValues.TAG, "Ad dismissed fullscreen content.")
                    holder.textView.text="Ad dismissed fullscreen content."
                    mInterstitialAd = null
                    val intent = Intent(context, MainActivity2::class.java)
                    intent.putExtra("message_key", ItemsViewModel.text)
                    context.startActivity(intent)
                }

                /*override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                    // Called when ad fails to show.
                    textActivity.text="Ad failed to show fullscreen content."
                    mInterstitialAd = null
                }*/

                override fun onAdImpression() {
                    // Called when an impression is recorded for an ad.
                    Log.d(ContentValues.TAG, "Ad recorded an impression.")
                    holder.textView.text="Ad recorded an impression."
                }

                override fun onAdShowedFullScreenContent() {
                    // Called when ad is shown.
                    holder.textView.text="Ad showed fullscreen content."
                    Log.d(ContentValues.TAG, "Ad showed fullscreen content.")
                }
            }
            if (mInterstitialAd != null) {
                mInterstitialAd?.show( context as Activity )

            } else {
                holder.textView.text="The interstitial ad wasn't ready yet."
            }
            Toast.makeText(context, ItemsViewModel.text, Toast.LENGTH_SHORT).show()

        }

    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageview)
        val textView: TextView = itemView.findViewById(R.id.textView)
    }
}
