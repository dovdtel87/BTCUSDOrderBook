package com.dmgdavid2109.btcusdorderbook.common.data

interface Mapper<I, O> {
    fun map(input: I): O
}
