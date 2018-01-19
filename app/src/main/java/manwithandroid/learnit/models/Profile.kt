package manwithandroid.learnit.models

/**
 * Created by roi-amiel on 1/10/18.
 */
class Profile {

    companion object {
        val MOVIE_GENRE_COMEDY = 0
        val MOVIE_GENRE_ACTION = 1
        val MOVIE_GENRE_HORROR = 2
        val MOVIE_GENRE_ROMANCE = 3
        val MOVIE_GENRE_DRAMA = 4
        val MOVIE_GENRE_MYSTERY = 5
        val MOVIE_GENRE_CRIME = 6
        val MOVIE_GENRE_ANIMATION = 7
        val MOVIE_GENRE_ADVENTURE = 8
        val MOVIE_GENRE_FANTASY = 9

        val GENDER_MALE = 0
        val GENDER_FEMALE = 1

        val LESSON_MATH = 0
        val LESSON_HISTORY = 1
        val LESSON_ENGLISH = 2
        val LESSON_HEBREW = 3
        val LESSON_CITIZENSHIP = 4
        val LESSON_LITERATURE = 5
        val LESSON_SPORT = 6
        val LESSON_GEOGRAPHY = 7
        val LESSON_BIOLOGY = 8
        val LESSON_CHEMISTRY = 9
        val LESSON_PHYSICS = 10
        val LESSON_COMPUTERS = 11
        val LESSON_ARABIC = 12
        val LESSON_MUSIC = 13
        val LESSON_ART = 14
    }

    var birthday: String = ""
    var gender: Int = GENDER_MALE

    var numberOfBrothers: Int = 0

    var favoriteMovieGenre: Int = MOVIE_GENRE_COMEDY
    var favoriteLesson: Int = LESSON_MATH

}
