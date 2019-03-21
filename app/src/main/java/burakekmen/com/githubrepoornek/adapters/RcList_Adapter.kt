package burakekmen.com.githubrepoornek.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import burakekmen.com.githubrepoornek.R
import burakekmen.com.githubrepoornek.models.GithubUserModel

/****************************
 * Created by Burak EKMEN   |
 * 10.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
class RcList_Adapter(context: Context, repoList:ArrayList<GithubUserModel>?): RecyclerView.Adapter<RcListViewHolder>() {

    private var repoListesi = repoList
    private var context = context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RcListViewHolder {
        var inflater = LayoutInflater.from(parent?.context).inflate(R.layout.list_item, parent, false)

        return RcListViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: RcListViewHolder, position: Int) {

        var repoItem = getItem(position)

        holder.repo_Name?.text = repoItem?.getName()
        holder.repo_Language?.text = repoItem?.getLanguage()
        holder.repo_Star_Count?.text = repoItem?.getStargazersCount().toString()
        holder.repo_Watch_Count?.text = repoItem?.getWatchersCount().toString()
        holder.repo_Fork_Count?.text = repoItem?.getForksCount().toString()

    }

    fun getItem(position:Int):GithubUserModel?{
        return repoListesi?.get(position)
    }

    override fun getItemCount(): Int {
        return repoListesi!!.size
    }

}