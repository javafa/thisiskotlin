package net.flow9.googlemapsclustering.data

import net.flow9.googlemapsclustering.data.RESULT
import net.flow9.googlemapsclustering.data.Row

data class SeoulPublicLibraryInfo(
    val RESULT: RESULT,
    val list_total_count: Int,
    val row: List<Row>
)