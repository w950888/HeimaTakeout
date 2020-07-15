package com.bcd.wy.heimatakeout.ui.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bcd.wy.heimatakeout.R
import com.bcd.wy.heimatakeout.model.beans.Seller
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.squareup.picasso.Picasso
import org.jetbrains.anko.find

/**
 * @author Luke
 * @time 2020/6/15 下午 4:45
 * @version 1.0.0
 * @description
 */
class HomeRvAdapter(
    val context: Context
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * kotlin中用伴生对象定义常量
     */
    companion object {
        val TYPE_TITLE = 0
        val TYPE_SELLER = 1
    }

    var mDatas: ArrayList<Seller> = ArrayList<Seller>()

    //val context:Context

    /**
     * 不同position对应不同的类型
     */
    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_TITLE
        } else {
            return TYPE_SELLER
        }
    }

    fun setData(data: ArrayList<Seller>) {
        this.mDatas = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_TITLE -> return TitleHolder(View.inflate(context, R.layout.item_title, null))
            TYPE_SELLER -> return SellerHolder(View.inflate(context, R.layout.item_seller, null))
            else -> return TitleHolder(View.inflate(context, R.layout.item_home_common, null))
        }
    }

    override fun getItemCount(): Int {
        if (mDatas.size > 0) return mDatas.size + 1
        else return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val viewType = getItemViewType(position)
        when (viewType) {
            TYPE_TITLE -> (holder as TitleHolder).bindData("我是头布局------")
            TYPE_SELLER -> (holder as SellerHolder).bindData(mDatas[position - 1])
        }
    }

    inner class SellerHolder(item: View) : RecyclerView.ViewHolder(item) {
        val tvTitle: TextView
        val ivLogo: ImageView
        val rbScore: RatingBar
        val tvSale: TextView
        val tvSendPrice: TextView
        val tvDistance: TextView

        init {
            tvTitle = item.find(R.id.tv_title)
            ivLogo = item.find(R.id.seller_logo)
            rbScore = item.find(R.id.ratingBar)
            tvSale = item.find(R.id.tv_home_sale)
            tvSendPrice = item.find(R.id.tv_home_send_price)
            tvDistance = item.find(R.id.tv_home_distance)
        }

        fun bindData(seller: Seller) {
            tvTitle.text = seller.name
            // TODO: 2020/6/19 赋值其他字段
            Picasso.with(context)
                .load(seller.icon)
                .into(ivLogo)
            rbScore.rating = seller.score.toFloat()
            tvSale.text = "月售${seller.sale}单"
            tvSendPrice.text = "￥${seller.sendPrice}起送/配送费￥${seller.deliveryFee}"
            tvDistance.text = "${seller.distance}"
        }


    }

    var url_maps: HashMap<String, String> = HashMap()

    inner class TitleHolder(item: View) : RecyclerView.ViewHolder(item) {
        val sliderLayout: SliderLayout

        init {
            sliderLayout = item.findViewById(R.id.slider)
        }

        fun bindData(data: String) {
            if (url_maps.size == 0) {
                url_maps.put(
                    "Hannibal",
                    "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg"
                );
                url_maps.put(
                    "Big Bang Theory",
                    "http://tvfiles.alphacoders.com/100/hdclearart-10.png"
                );
                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                url_maps.put(
                    "Game of Thrones",
                    "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg"
                );
            }

            for ((key, value) in url_maps) {
                var textSliderView: TextSliderView = TextSliderView(context)
                textSliderView.description(key).image(value)
                sliderLayout.addSlider(textSliderView)
            }
        }

    }
}