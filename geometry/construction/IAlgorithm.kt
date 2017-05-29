package geometry.construction

/**
 * Created by johnbowers on 5/28/17.
 */

interface IAlgorithm<OutputType> {
    fun run(node: ConstructionNode<OutputType>): OutputType
}

class InvalidConstructionParametersException(msg: String): Exception(msg)