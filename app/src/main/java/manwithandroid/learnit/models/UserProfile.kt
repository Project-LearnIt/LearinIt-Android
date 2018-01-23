package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/10/18.
 */
class UserProfile {

    companion object {
        const val MOVIE_GENRE_COMEDY = 0
        const val MOVIE_GENRE_ACTION = 1
        const val MOVIE_GENRE_HORROR = 2
        const val MOVIE_GENRE_ROMANCE = 3
        const val MOVIE_GENRE_DRAMA = 4
        const val MOVIE_GENRE_MYSTERY = 5
        const val MOVIE_GENRE_CRIME = 6
        const val MOVIE_GENRE_ANIMATION = 7
        const val MOVIE_GENRE_ADVENTURE = 8
        const val MOVIE_GENRE_FANTASY = 9

        const val GENDER_MALE = 0
        const val GENDER_FEMALE = 1

        const val LESSON_MATH = 0
        const val LESSON_HISTORY = 1
        const val LESSON_ENGLISH = 2
        const val LESSON_HEBREW = 3
        const val LESSON_CITIZENSHIP = 4
        const val LESSON_LITERATURE = 5
        const val LESSON_SPORT = 6
        const val LESSON_GEOGRAPHY = 7
        const val LESSON_BIOLOGY = 8
        const val LESSON_CHEMISTRY = 9
        const val LESSON_PHYSICS = 10
        const val LESSON_COMPUTERS = 11
        const val LESSON_ARABIC = 12
        const val LESSON_MUSIC = 13
        const val LESSON_ART = 14
    }

    var birthday: String = ""
    var gender: Int = GENDER_MALE

    var numberOfBrothers: Int = 0

    var favoriteMovieGenre: Int = MOVIE_GENRE_COMEDY
    var favoriteLesson: Int = LESSON_MATH

}
