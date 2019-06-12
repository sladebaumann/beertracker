package com.example.beertracker

import java.util.*

class Beer {
    var id: Long = 0
    var count: Int = 0
    var timestamp: Date = Date()

    constructor(id: Long, count: Int, timestamp: Date) {
        this.id = id
        this.count = count
        this.timestamp = timestamp
    }

    constructor(count: Int, timestamp: Date) {
        this.count = count
        this.timestamp = timestamp
    }
}