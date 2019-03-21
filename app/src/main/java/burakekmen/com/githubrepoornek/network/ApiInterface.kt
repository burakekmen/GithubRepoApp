package burakekmen.com.githubrepoornek.network

import burakekmen.com.githubrepoornek.models.GithubUserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/****************************
 * Created by Burak EKMEN   |
 * 10.08.2018               |
 * ekmen.burak@hotmail.com  |
 ***************************/
interface ApiInterface {

    @GET("users/{username}/repos")
    fun getUserRepos(@Path("username") userName:String):Call<ArrayList<GithubUserModel>>?
}