package com.assignment.paytm.module.adapters

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.recyclerview.widget.RecyclerView
import com.assignment.paytm.R
import com.assignment.paytm.model.response.PopularItem
import com.assignment.paytm.module.activity.DetailActivity
import com.assignment.paytm.utiles.ImageUtils
import kotlinx.android.synthetic.main.content_user_header.view.*


class RecycleViewAdadpter(private var pPopularEventList: ArrayList<PopularItem>) :
    RecyclerView.Adapter<UserViewHolder>() {

    override fun onCreateViewHolder(pParent: ViewGroup, pViewType: Int): UserViewHolder {
        return UserViewHolder(
            pItemView = LayoutInflater.from(pParent.context).inflate(
                R.layout.item_view_users,
                pParent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return pPopularEventList.size
    }

    override fun onBindViewHolder(pHolder: UserViewHolder, pPosition: Int) {
        pHolder.mItemView.tv_title.text = pPopularEventList[pPosition].name
        pHolder.mItemView.tv_desc.text = pPopularEventList[pPosition].city
        ImageUtils.newInstance().downloadImage(
            pUrl = pPopularEventList[pPosition].horizontalCoverImage!!,
            pView = pHolder.mItemView.iv_users
        )
        pHolder.mItemView.setOnClickListener {
            onClickItems(pHolder = pHolder, pPosition = pPosition)
        }
    }

    private fun onClickItems(pHolder: UserViewHolder, pPosition: Int) {
        when (pHolder.mItemView.context != null && pPosition < pPopularEventList.size) {
            true -> {
                val intent = Intent(pHolder.mItemView.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity::class.java.simpleName, pPopularEventList[pPosition])
                val transitionName: String = pHolder.mItemView.context.getString(R.string.transition)
                val options =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        pHolder.mItemView.context as Activity,
                        pHolder.mItemView,
                        transitionName
                    )
                ActivityCompat.startActivity(pHolder.mItemView.context, intent, options.toBundle())
            }
        }
    }
}

class UserViewHolder(var pItemView: View) : RecyclerView.ViewHolder(pItemView) {
    var mItemView = pItemView
}