package it.alexs.photocommunityuser.utils.criteria

import org.springframework.data.domain.Sort
import javax.validation.constraints.Min

data class RequestCriteria(
    val search: String? = null,
    @get:Min(0)
    val offset: Int = 0,
    @get:Min(1)
    val limit: Int = 15,

    val orderBy: String? = null
) {

    private val QUERY = "(\\w+)([%=IN<>]+)(.+),".toRegex()

    fun toSort(): Sort {
        if (orderBy.isNullOrEmpty()) return Sort.unsorted()

        var sort: Sort = Sort.unsorted()
        orderBy.split(",").forEach {
            val split = it.split("|")

            val key = split[0]
            val sortType = split[1]

            sort = if (sort.isUnsorted) {
                Sort.by(key)
            } else{
                sort.and(Sort.by(key))
            }

            sort = when {
                sortType.lowercase() == "asc" -> sort.ascending()
                sortType.lowercase() == "desc" -> sort.descending()
                else -> sort
            }
        }

        return sort
    }

    fun toSearchCriteria(): SearchCriteria? {
        if (search.isNullOrEmpty()) return null

        val find = QUERY.find("$search,")
        return SearchCriteria(find!!.groupValues[1], find!!.groupValues[2], find!!.groupValues[3])
    }
}
