package com.example.data.models.remote.saavn

data class Bio(
    val text: String = "",
    val title: String = "",
    val sequence: Int = 0,
) {
    companion object {
        fun default(): Bio {
            return Bio(
                text = "Dua Lipa is an English singer, songwriter, and model." +
                        " After working as a model, she signed with Warner Music Group in 2015" +
                        " and released her eponymous debut album in 2017. The album spawned " +
                        "seven singles, including the UK top-ten singles 'Be the One'," +
                        " 'IDGAF', and 'New Rules'," +
                        " which also peaked at number six on the US Billboard Hot 100.",
                title = "English Singer and Songwriter",
                sequence = 1
            )
        }
    }
}
