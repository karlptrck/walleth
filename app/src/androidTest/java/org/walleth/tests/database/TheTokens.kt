package org.walleth.tests.database

import com.google.common.truth.Truth
import org.junit.Test
import org.walleth.data.tokens.Token
import org.walleth.testdata.DEFAULT_TEST_ADDRESS
import org.walleth.testdata.DEFAULT_TEST_ADDRESS2
import java.math.BigInteger
import java.math.BigInteger.ONE

class TheTokens : AbstractDatabaseTest() {

    val CHAIN1 = ONE
    val CHAIN2 = BigInteger.valueOf(2L)
    val DEFAULT_TOKEN = Token(name = "foo",
            symbol = "foo",
            decimals = 1,
            address = DEFAULT_TEST_ADDRESS,
            chain = CHAIN1,
            fromUser = false,
            softDeleted = false,
            starred = false,
            order = 0)

    @Test
    fun isEmptyInitially() {
        Truth.assertThat(database.tokens.all().size).isEqualTo(0)
    }

    @Test
    fun weCanInsertTwo() {
        database.tokens.upsert(DEFAULT_TOKEN.copy(name = "foo", address = DEFAULT_TEST_ADDRESS, chain = CHAIN1))
        database.tokens.upsert(DEFAULT_TOKEN.copy(name = "foo", address = DEFAULT_TEST_ADDRESS2, chain = CHAIN1))

        Truth.assertThat(database.tokens.all().size).isEqualTo(2)
    }


    @Test
    fun weCanQueryForOneChain() {
        database.tokens.upsert(DEFAULT_TOKEN.copy(name = "foo", address = DEFAULT_TEST_ADDRESS, chain = CHAIN1))
        database.tokens.upsert(DEFAULT_TOKEN.copy(name = "foo", address = DEFAULT_TEST_ADDRESS, chain = CHAIN2))

        Truth.assertThat(database.tokens.all().size).isEqualTo(2)
    }

    @Test
    fun weCanUpsert() {
        val token1 = DEFAULT_TOKEN.copy(name = "foo", address = DEFAULT_TEST_ADDRESS, chain = CHAIN1)
        database.tokens.upsert(token1)
        val token2 = DEFAULT_TOKEN.copy(name = "bar", address = DEFAULT_TEST_ADDRESS, chain = CHAIN1)
        database.tokens.upsert(token2)

        Truth.assertThat(database.tokens.all()).hasSize(1)
        Truth.assertThat(database.tokens.all()).containsExactly(token2)
    }


}