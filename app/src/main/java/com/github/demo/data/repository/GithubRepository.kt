package com.github.demo.data.repository

import com.github.demo.data.local.db.DataBaseService
import com.github.demo.data.models.Contributor
import com.github.demo.data.models.ReadMeDetails
import com.github.demo.data.models.Repository
import com.github.demo.data.models.RepositoryModel
import com.github.demo.data.remote.NetworkService
import com.github.demo.data.remote.response.owner.OwnerDetailsResponse
import com.github.demo.data.remote.response.publicRepo.PublicRepoResponse
import com.github.demo.utils.network.PageLinks
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.Response
import javax.inject.Inject


class GithubRepository @Inject constructor(
    private val networkService: NetworkService,
    private val dbService: DataBaseService
) {

    fun doFetchPublicRepo(): Single<RepositoryModel> {
        return networkService.callPublicRepos()
            .map {
                return@map parsePublicRepoResponse(it)
            }
    }

    fun doFetchNextPublicRepo(url: String): Single<RepositoryModel> {
        return networkService.callNextPublicRepos(url)
            .map {
                return@map parsePublicRepoResponse(it)
            }
    }

    fun doFetchContributors(
        owner: String,
        repoName: String
    ): Single<List<Contributor>> {
        val contributorList = ArrayList<Contributor>()
        return networkService.callContributors(owner, repoName)
            .map { data ->
                data.forEach {
                    contributorList.add(
                        Contributor(
                            it.id,
                            it.login,
                            it.avatarUrl,
                            "$owner/$repoName"
                        )
                    )
                }
                return@map contributorList
            }
    }

    fun doFetchReadMe(
        owner: String,
        repoName: String
    ): Single<ReadMeDetails> {
        return networkService.callReadMe(owner, repoName)
            .map {
                return@map ReadMeDetails("$owner/$repoName", it.downloadUrl)
            }
    }


    fun doFetchUserDetails(
        owner: String
    ): Single<OwnerDetailsResponse> = networkService.callOwnerDetails(owner)

    fun getUserFromDb(
        owner: String
    ) = dbService.userDetails().getUser(owner)

    fun saveUserToDb(ownerDetailsResponse: OwnerDetailsResponse) =
        dbService.userDetails().insert(ownerDetailsResponse)

    private fun parsePublicRepoResponse(response: Response<List<PublicRepoResponse>>): RepositoryModel {
        val model = RepositoryModel()
        val repoList = ArrayList<Repository>()

        model.next = PageLinks(response.headers().get("Link")).next!!

        response.body()?.forEach { data ->
            repoList.add(
                Repository(
                    data.id,
                    data.name,
                    data.description,
                    data.owner.login,
                    data.owner.avatarUrl
                )
            )
        }
        model.repository = repoList
        return model
    }

    fun saveRepos(repository: Repository) =
        dbService.repoDao().insert(repository)

    fun getSavedRepos() =
        dbService.repoDao().getAllRepos()

    fun saveReadMeDetails(readMeDetails: ReadMeDetails) =
        dbService.readMeDao().insert(readMeDetails)

    fun getReadMe(key: String) =
        dbService.readMeDao().getReadme(key)

    fun saveContributorsList(list: List<Contributor>) =
        dbService.contributorsDao().insert(list)

    fun getContributorsList(key: String): Flowable<List<Contributor>> =
        dbService.contributorsDao().getContributors(key).toFlowable()

    fun getReadMeByAbstract(
        owner: String,
        repoName: String
    ) =
        Observable.concat(
            getReadMe("$owner/$repoName").toObservable(),
            doFetchReadMe(owner, repoName).toObservable()
        )

    fun getContributorsListByAbstract(
        owner: String,
        repoName: String
    ) = Observable.concat(
        getContributorsList("$owner/$repoName").toObservable(),
        doFetchContributors(owner, repoName).toObservable()
    )

    fun getUserDetails(owner: String) = Observable.concat(
        getUserFromDb(owner).toObservable(),
        doFetchUserDetails(owner).toObservable()
    )

    fun getSearchedUsers(query: String) =
        networkService.searchUser(query).map { return@map it.items }.toObservable()

    fun getRepositoriesByUserName(user: String) = networkService.callReposByUser(user)
        .map {
            val repoList = ArrayList<Repository>()
            it.forEach { data ->
                repoList.add(
                    Repository(
                        data.id,
                        data.name,
                        data.description,
                        data.owner.login,
                        data.owner.avatarUrl
                    )
                )
            }
            return@map repoList
        }

    fun deleteRepo(repository: Repository) = dbService.repoDao().delete(repository)
}