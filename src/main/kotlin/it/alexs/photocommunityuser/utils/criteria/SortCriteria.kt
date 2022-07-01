package it.alexs.photocommunityuser.utils.criteria

import javax.swing.SortOrder
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class SortCriteria(
    @NotNull
    val order: SortOrder,
    @NotBlank
    val attribute: String
)
