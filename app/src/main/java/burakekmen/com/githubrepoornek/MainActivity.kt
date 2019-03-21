package burakekmen.com.githubrepoornek

import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.View
import burakekmen.com.githubrepoornek.adapters.RcList_Adapter
import burakekmen.com.githubrepoornek.models.GithubUserModel
import burakekmen.com.githubrepoornek.models.Owner
import burakekmen.com.githubrepoornek.network.ApiClient
import burakekmen.com.githubrepoornek.network.ApiInterface
import com.kaopiz.kprogresshud.KProgressHUD
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener, View.OnClickListener, android.widget.SearchView.OnQueryTextListener {

    var requestDialog:KProgressHUD?=null
    var repoListesi:ArrayList<GithubUserModel>? = null
    var repoOwner:Owner?= null
    var myAdapter:RcList_Adapter?= null
    var apiInterface:ApiInterface?=null

    var doubleBackToExitPressedOnce:Boolean=false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setFullScreen()
        setContentView(R.layout.activity_main)

        apiInterface  = ApiClient.client?.create(ApiInterface::class.java)
        activity_main_scView.setOnClickListener(this)
        activity_main_scView.setOnQueryTextListener(this)
        activity_main_scView.onActionViewExpanded()
    }


    override fun onQueryTextSubmit(query: String?): Boolean {
        repoListesi?.clear()

        var aramaMetni = query

        if(!aramaMetni!!.equals("")){
            getRequest(aramaMetni)
        }

        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {

        return false
    }

    override fun onClick(view: View?) {
        when(view?.id){
            R.id.activity_main_scView ->{
                activity_main_scView?.setQuery("", false)
                activity_main_scView?.isIconified = false
               // activity_main_scView?.clearFocus()
            }
        }
    }

    private fun setFullScreen() {
        supportActionBar?.hide()
    }


    private fun snackBarGoster(){

        activity_main_userProfile_imgView.setImageResource(R.drawable.github_user_profile)
        activity_main_txtUsername.visibility = View.INVISIBLE

        val snackBar = Snackbar.make(activity_main_linearlayout, getString(R.string.snackBar_Not_Found_User), Snackbar.LENGTH_SHORT)
        snackBar.show()
    }


    private fun snackBarGosterCikis(){
        val snackBar = Snackbar.make(activity_main_linearlayout, getString(R.string.exit_string), Snackbar.LENGTH_LONG)
        snackBar.show()
    }

    private fun dialogGoster(){

        if(requestDialog == null) {
                requestDialog = KProgressHUD.create(this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel(getString(R.string.dialog_label))
                        .setDetailsLabel(getString(R.string.dialog_detail_label))
                        .setCancellable(true)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show()
        }

    }

    private fun dialogGosterme(){
        if(requestDialog != null) {
            if (requestDialog!!.isShowing) {
                requestDialog!!.dismiss()
                requestDialog = null
            }
        }
    }

    override fun onBackPressed() {

        if (supportFragmentManager.backStackEntryCount > 0) {
            supportFragmentManager.popBackStack()
        } else if (!doubleBackToExitPressedOnce) {
            this.doubleBackToExitPressedOnce = true

            snackBarGosterCikis()

            Handler().postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
        } else {
            android.os.Process.killProcess(android.os.Process.myPid())
            super.onBackPressed()
            return
        }

    }

    fun getRequest(username:String){

        dialogGoster()

        var apiCall = apiInterface?.getUserRepos(username)

        apiCall?.enqueue(object : Callback<ArrayList<GithubUserModel>> {
            override fun onFailure(call: Call<ArrayList<GithubUserModel>>?, t: Throwable?) {

                Log.e("BASARISIZ", t?.message)
                dialogGosterme()
            }

            override fun onResponse(call: Call<ArrayList<GithubUserModel>>?, response: Response<ArrayList<GithubUserModel>>?) {
                Log.e("BASARILI", response?.message())

                if(response!!.isSuccessful) {
                    if (response.body() != null) {


                        repoListesi = response.body()

                        if (repoListesi?.size!! > 0)
                            repoOwner = repoListesi?.get(0)?.getOwner()

                        if (repoOwner != null) {
                            Picasso.get().load(repoOwner?.getAvatarUrl()).into(activity_main_userProfile_imgView)
                            activity_main_txtUsername.text = repoOwner?.getLogin()
                            activity_main_txtUsername.visibility = View.VISIBLE
                        }

                        myAdapter = RcList_Adapter(applicationContext, repoListesi)
                        activity_main_rcView.adapter = myAdapter

                        var myLayoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
                        activity_main_rcView.layoutManager = myLayoutManager

                        dialogGosterme()
                    }else {
                        dialogGosterme()
                        snackBarGoster()
                    }
                }
                else {

                    dialogGosterme()
                    snackBarGoster()
                }
            }

        })
    }

}

