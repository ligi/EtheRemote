package org.ligi.etheremote.model

public class SyncState(
        val highestBlock: Long,
        val currentBlock: Long,
        val startingBlock: Long) {}
