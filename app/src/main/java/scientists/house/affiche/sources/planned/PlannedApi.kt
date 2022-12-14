package scientists.house.affiche.sources.planned

import scientists.house.affiche.sources.affiche.entitites.AffichePost

interface PlannedApi {
    suspend fun getPlannedAffichePosts(): List<AffichePost>

    suspend fun removeAffichePost(postId: Long)
}
