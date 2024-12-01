package com.duitddu.study.circuitpokdex.data.repository.mapper

interface Mapper<FROM, TO> {
    fun invoke(from: FROM): TO
}
