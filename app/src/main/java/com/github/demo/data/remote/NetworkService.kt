package com.github.demo.data.remote


import com.github.demo.data.remote.response.owner.OwnerDetailsResponse
import com.github.demo.data.remote.response.publicRepo.Owner
import com.github.demo.data.remote.response.publicRepo.PublicRepoResponse
import com.github.demo.data.remote.response.readMe.ReadMeResponse
import com.github.demo.data.remote.response.search.UserSearchResponse
import io.reactivex.Single
import retrofit2.Response
import retrofit2.http.*

import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET(EndPoints.REPOSITORY)
    fun callPublicRepos(@Query("per_page") per_page: String = "20"): Single<Response<List<PublicRepoResponse>>>

    @GET
    fun callNextPublicRepos(@Url url: String): Single<Response<List<PublicRepoResponse>>>

    @GET(EndPoints.USER_REPOS_CONTRIBUTORS)
    fun callContributors(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String
    ): Single<List<Owner>>

    @GET(EndPoints.OWNER_DETAIL)
    fun callOwnerDetails(
        @Path("owner") owner: String
    ): Single<OwnerDetailsResponse>

    @GET(EndPoints.REPo_README)
    fun callReadMe(
        @Path("owner") owner: String,
        @Path("repoName") repoName: String
    ): Single<ReadMeResponse>

    @GET(EndPoints.SEARCH_USERS)
    fun searchUser(
        @Query("q") q: String
    ): Single<UserSearchResponse>

    @GET(EndPoints.USER_REPOS)
    fun callReposByUser(
        @Path("owner") owner: String
    ): Single<List<PublicRepoResponse>>
}