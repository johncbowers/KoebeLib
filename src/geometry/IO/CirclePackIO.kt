
package geometry.IO

import packing.PackData
import dcel.PackDCEL
import java.io.BufferedReader
import java.io.FileReader

/**
 * Utilities for loading *.p CirclePack files.
 *
 * Created by John C. Bowers on 3/12/18.
 */

fun loadCirclePack(filepath: String) {

    val reader = BufferedReader(FileReader(filepath)) // Create a reader for the file.
    val pData = PackData(null)  // Create a blank PackData
    val readCode = pData.readpack(reader, filepath) // Read the packing data.
    if (readCode != 0 && readCode != -1) {
        // Then the packing loaded correctly
        val packDCEL = PackDCEL(pData)


    }

}