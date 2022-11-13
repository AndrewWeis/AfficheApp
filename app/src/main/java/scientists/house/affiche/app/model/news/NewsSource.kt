package scientists.house.affiche.app.model.news

import scientists.house.affiche.sources.news.entities.NewsItem

interface NewsSource {
    suspend fun getNews(): List<NewsItem>
}
