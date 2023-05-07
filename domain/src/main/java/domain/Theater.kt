package domain

data class Theater(
    val name: String,
    val screeningTime: Map<Int, List<String>>,
)
