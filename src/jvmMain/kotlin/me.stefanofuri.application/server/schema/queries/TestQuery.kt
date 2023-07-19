package me.stefanofuri.application.server.schema.queries

import com.expediagroup.graphql.server.operations.Query

class TestQuery : Query {
    fun test() = Double.MAX_VALUE
}