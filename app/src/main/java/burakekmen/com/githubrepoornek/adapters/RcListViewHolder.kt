package burakekmen.com.githubrepoornek.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.TextView
import burakekmen.com.githubrepoornek.R

/****************************
 * Created by Burak EKMEN   |
 * 10.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
class RcListViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

    var repo_Name = itemView?.findViewById<TextView>(R.id.list_item_cdView_txtRepoName)
    var repo_Language = itemView?.findViewById<TextView>(R.id.list_item_cdView_txtRepoLanguage)
    var repo_Watch_Count = itemView?.findViewById<TextView>(R.id.list_item_txtEye_count)
    var repo_Star_Count = itemView?.findViewById<TextView>(R.id.list_item_txtStar_count)
    var repo_Fork_Count = itemView?.findViewById<TextView>(R.id.list_item_txtFork_count)
}