package gql.plt.server.components.schema.queries

import com.expediagroup.graphql.server.operations.Query

class TestQuery : Query {
    fun test() = Double.MAX_VALUE
}