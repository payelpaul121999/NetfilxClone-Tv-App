package com.droid.netfilxclone.data.repository



import com.droid.netfilxclone.data.model.ContentRow
import com.droid.netfilxclone.data.model.Movie
import com.droid.netfilxclone.data.model.UserProfile
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NetflixRepository {

    // Using TMDB-style placeholder image URLs (picsum for demo)
    private val sampleMovies = listOf(
        Movie(
            id = 1,
            title = "Stranger Things",
            description = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces, and one strange little girl.",
            thumbnailUrl = "https://picsum.photos/seed/st1/300/450",
            backdropUrl = "https://picsum.photos/seed/st1/1280/720",
            rating = 8.7f,
            year = 2016,
            duration = "51m",
            genre = listOf("Sci-Fi", "Horror", "Drama"),
            isNew = false,
            isTopTen = true,
            cast = listOf("Millie Bobby Brown", "Finn Wolfhard", "Winona Ryder")
        ),
        Movie(
            id = 2,
            title = "The Crown",
            description = "Follows the political rivalries and romance of Queen Elizabeth II's reign and the events that shaped the second half of the twentieth century.",
            thumbnailUrl = "https://picsum.photos/seed/crown2/300/450",
            backdropUrl = "https://picsum.photos/seed/crown2/1280/720",
            rating = 8.6f,
            year = 2016,
            duration = "58m",
            genre = listOf("Drama", "History", "Biography"),
            isTopTen = true,
            cast = listOf("Claire Foy", "Olivia Colman", "Imelda Staunton")
        ),
        Movie(
            id = 3,
            title = "Ozark",
            description = "A financial advisor drags his family from Chicago to the Ozarks, where he must launder money to appease a drug boss.",
            thumbnailUrl = "https://picsum.photos/seed/ozark3/300/450",
            backdropUrl = "https://picsum.photos/seed/ozark3/1280/720",
            rating = 8.4f,
            year = 2017,
            duration = "1h",
            genre = listOf("Crime", "Drama", "Thriller"),
            cast = listOf("Jason Bateman", "Laura Linney", "Sofia Hublitz")
        ),
        Movie(
            id = 4,
            title = "Money Heist",
            description = "An unusual group of robbers attempt to carry out the most perfect robbery in Spanish history - stealing 2.4 billion euros from the Royal Mint of Spain.",
            thumbnailUrl = "https://picsum.photos/seed/heist4/300/450",
            backdropUrl = "https://picsum.photos/seed/heist4/1280/720",
            rating = 8.2f,
            year = 2017,
            duration = "45m",
            genre = listOf("Action", "Crime", "Mystery"),
            isNew = true,
            isTopTen = true,
            cast = listOf("Álvaro Morte", "Itziar Ituño", "Pedro Alonso")
        ),
        Movie(
            id = 5,
            title = "Dark",
            description = "A family saga with a supernatural twist, set in a German town where the disappearance of two young children exposes the relationships among four families.",
            thumbnailUrl = "https://picsum.photos/seed/dark5/300/450",
            backdropUrl = "https://picsum.photos/seed/dark5/1280/720",
            rating = 8.8f,
            year = 2017,
            duration = "52m",
            genre = listOf("Sci-Fi", "Thriller", "Mystery"),
            cast = listOf("Louis Hofmann", "Oliver Masucci", "Karoline Eichhorn")
        ),
        Movie(
            id = 6,
            title = "The Witcher",
            description = "Geralt of Rivia, a solitary monster hunter, struggles to find his place in a world where people often prove more wicked than beasts.",
            thumbnailUrl = "https://picsum.photos/seed/witcher6/300/450",
            backdropUrl = "https://picsum.photos/seed/witcher6/1280/720",
            rating = 8.1f,
            year = 2019,
            duration = "1h",
            genre = listOf("Fantasy", "Action", "Adventure"),
            isNew = false,
            cast = listOf("Henry Cavill", "Freya Allan", "Anya Chalotra")
        ),
        Movie(
            id = 7,
            title = "Narcos",
            description = "A chronicled look at the criminal exploits of Colombian drug lord Pablo Escobar, as well as the many other drug kingpins who plagued the country through the years.",
            thumbnailUrl = "https://picsum.photos/seed/narcos7/300/450",
            backdropUrl = "https://picsum.photos/seed/narcos7/1280/720",
            rating = 8.8f,
            year = 2015,
            duration = "50m",
            genre = listOf("Biography", "Crime", "Drama"),
            cast = listOf("Wagner Moura", "Boyd Holbrook", "Pedro Pascal")
        ),
        Movie(
            id = 8,
            title = "Bridgerton",
            description = "Wealth, lust, and betrayal set against the backdrop of Regency Era England, seen through the eyes of the powerful Bridgerton family.",
            thumbnailUrl = "https://picsum.photos/seed/bridge8/300/450",
            backdropUrl = "https://picsum.photos/seed/bridge8/1280/720",
            rating = 7.3f,
            year = 2020,
            duration = "55m",
            genre = listOf("Drama", "Romance", "History"),
            isNew = true,
            isTopTen = true,
            cast = listOf("Regé-Jean Page", "Phoebe Dynevor", "Jonathan Bailey")
        ),
        Movie(
            id = 9,
            title = "Squid Game",
            description = "Hundreds of cash-strapped players accept a strange invitation to compete in children's games. Inside, a tempting prize awaits with deadly high stakes.",
            thumbnailUrl = "https://picsum.photos/seed/squid9/300/450",
            backdropUrl = "https://picsum.photos/seed/squid9/1280/720",
            rating = 8.0f,
            year = 2021,
            duration = "55m",
            genre = listOf("Action", "Drama", "Mystery"),
            isNew = false,
            isTopTen = true,
            cast = listOf("Lee Jung-jae", "Park Hae-soo", "Wi Ha-jun")
        ),
        Movie(
            id = 10,
            title = "Lucifer",
            description = "Lucifer Morningstar has decided he's had enough of being the dutiful ruler of Hell and decides to spend time on Earth in Los Angeles.",
            thumbnailUrl = "https://picsum.photos/seed/lucifer10/300/450",
            backdropUrl = "https://picsum.photos/seed/lucifer10/1280/720",
            rating = 8.1f,
            year = 2016,
            duration = "45m",
            genre = listOf("Crime", "Drama", "Fantasy"),
            cast = listOf("Tom Ellis", "Lauren German", "Kevin Alejandro")
        ),
        Movie(
            id = 11,
            title = "Breaking Bad",
            description = "A chemistry teacher diagnosed with inoperable lung cancer turns to manufacturing and selling methamphetamine to secure his family's future.",
            thumbnailUrl = "https://picsum.photos/seed/bb11/300/450",
            backdropUrl = "https://picsum.photos/seed/bb11/1280/720",
            rating = 9.5f,
            year = 2008,
            duration = "49m",
            genre = listOf("Crime", "Drama", "Thriller"),
            isTopTen = true,
            cast = listOf("Bryan Cranston", "Aaron Paul", "Anna Gunn")
        ),
        Movie(
            id = 12,
            title = "Wednesday",
            description = "Smart, sarcastic and a little dead inside, Wednesday Addams investigates a murder spree while making new friends — and foes — at Nevermore Academy.",
            thumbnailUrl = "https://picsum.photos/seed/wed12/300/450",
            backdropUrl = "https://picsum.photos/seed/wed12/1280/720",
            rating = 8.1f,
            year = 2022,
            duration = "50m",
            genre = listOf("Comedy", "Fantasy", "Horror"),
            isNew = true,
            isTopTen = true,
            cast = listOf("Jenna Ortega", "Gwendoline Christie", "Riki Lindhome")
        ),
        Movie(
            id = 13,
            title = "You",
            description = "A dangerously charming, intensely obsessive young man goes to extreme measures to insert himself into the lives of those he is transfixed by.",
            thumbnailUrl = "https://picsum.photos/seed/you13/300/450",
            backdropUrl = "https://picsum.photos/seed/you13/1280/720",
            rating = 7.7f,
            year = 2018,
            duration = "45m",
            genre = listOf("Crime", "Thriller", "Drama"),
            isNew = true,
            cast = listOf("Penn Badgley", "Victoria Pedretti", "Elizabeth Lail")
        ),
        Movie(
            id = 14,
            title = "Peaky Blinders",
            description = "A gangster family epic set in 1900s England, centering on a gang who sew razor blades in the peaks of their caps, and their fierce boss Tommy Shelby.",
            thumbnailUrl = "https://picsum.photos/seed/peaky14/300/450",
            backdropUrl = "https://picsum.photos/seed/peaky14/1280/720",
            rating = 8.8f,
            year = 2013,
            duration = "1h",
            genre = listOf("Crime", "Drama", "History"),
            cast = listOf("Cillian Murphy", "Helen McCrory", "Paul Anderson")
        ),
        Movie(
            id = 15,
            title = "Cobra Kai",
            description = "Decades after their 1984 All Valley Karate Tournament bout, a chance encounter reignites the rivalry between Johnny Lawrence and Daniel LaRusso.",
            thumbnailUrl = "https://picsum.photos/seed/cobra15/300/450",
            backdropUrl = "https://picsum.photos/seed/cobra15/1280/720",
            rating = 8.5f,
            year = 2018,
            duration = "30m",
            genre = listOf("Action", "Comedy", "Drama"),
            isNew = true,
            cast = listOf("William Zabka", "Ralph Macchio", "Courtney Henggeler")
        )
    )

    fun getHeroMovie(): Flow<Movie> = flow {
        delay(300)
        emit(sampleMovies.first { it.isTopTen }.copy(
            description = "When a young boy vanishes, a small town uncovers a mystery involving secret experiments, terrifying supernatural forces, and one strange little girl. A Netflix Original Series."
        ))
    }

    fun getContentRows(): Flow<List<ContentRow>> = flow {
        delay(500)
        emit(
            listOf(
                ContentRow(
                    id = 1,
                    title = "Continue Watching for You",
                    movies = sampleMovies.shuffled().take(6)
                ),
                ContentRow(
                    id = 2,
                    title = "Top 10 in India Today",
                    movies = sampleMovies.filter { it.isTopTen }
                ),
                ContentRow(
                    id = 3,
                    title = "New on Netflix",
                    movies = sampleMovies.filter { it.isNew }
                ),
                ContentRow(
                    id = 4,
                    title = "Trending Now",
                    movies = sampleMovies.shuffled().take(8)
                ),
                ContentRow(
                    id = 5,
                    title = "Crime & Thrillers",
                    movies = sampleMovies.filter { it.genre.contains("Crime") || it.genre.contains("Thriller") }
                ),
                ContentRow(
                    id = 6,
                    title = "Award-Winning TV",
                    movies = sampleMovies.filter { it.rating >= 8.5f }
                ),
                ContentRow(
                    id = 7,
                    title = "Sci-Fi & Fantasy",
                    movies = sampleMovies.filter { it.genre.contains("Sci-Fi") || it.genre.contains("Fantasy") }
                ),
                ContentRow(
                    id = 8,
                    title = "International Series",
                    movies = sampleMovies.shuffled().take(6)
                )
            )
        )
    }

    fun searchMovies(query: String): Flow<List<Movie>> = flow {
        delay(300)
        if (query.isBlank()) {
            emit(sampleMovies)
        } else {
            emit(
                sampleMovies.filter {
                    it.title.contains(query, ignoreCase = true) ||
                            it.genre.any { g -> g.contains(query, ignoreCase = true) } ||
                            it.description.contains(query, ignoreCase = true)
                }
            )
        }
    }

    fun getMovieById(id: Int): Flow<Movie?> = flow {
        emit(sampleMovies.find { it.id == id })
    }

    fun getUserProfiles(): List<UserProfile> = listOf(
        UserProfile(1, "Joy", 0xFFE50914),
        UserProfile(2, "Palpa", 0xFF2563EB),
        UserProfile(3, "Kids", 0xFF16A34A, isKidsProfile = true)
    )

    fun getMyList(): Flow<List<Movie>> = flow {
        emit(sampleMovies.take(5))
    }
}