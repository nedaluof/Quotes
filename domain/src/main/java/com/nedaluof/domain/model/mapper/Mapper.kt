package com.nedaluof.domain.model.mapper

/**
 * Created by NedaluOf on 8/13/2021.
 */
interface Mapper<INPUT_MODEL, OUTPUT_MODEL> {

    fun from(inputModel: INPUT_MODEL): OUTPUT_MODEL

    fun to(outputModel: OUTPUT_MODEL): INPUT_MODEL

    fun fromList(listOfInputModels: List<INPUT_MODEL>) =
        listOfInputModels.map { from(it) }
}