package it.alexs.photocommunityuser.utils.wrappers

data class ResponseWrapper<T>(
    val result: List<T>,
    val totalPages: Int,
    val totalElements: Long
)
