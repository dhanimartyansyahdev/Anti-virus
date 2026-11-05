package com.example

import com.example.model.MalwareThreat
import com.example.model.ScanCategory
import com.example.model.Severity
import com.example.model.ThreatStatus
import com.example.ui.MalwareViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

class ExampleRobolectricTest {

    @Test
    fun verifyMalwareViewModelInitialStateAndThreats() {
        val viewModel = MalwareViewModel()
        val state = viewModel.uiState.value
        assertNotNull(state)
        assertTrue(state.totalBugs > 0)
        assertEquals(425, state.fixedBugs)
        assertEquals(43567, state.totalFiles)
    }

    @Test
    fun verifyMalwareThreatFormatting() {
        val threat = MalwareThreat(
            id = "T1",
            name = "Trojan.Dropper",
            category = ScanCategory.FILES,
            filePath = "D:/Users/Files/yandex.cmd",
            signature = "MD5:1234",
            severity = Severity.CRITICAL,
            sizeBytes = 2 * 1024 * 1024
        )
        assertEquals("2.0 MB", threat.formattedSize)
        assertEquals(ThreatStatus.DETECTED, threat.status)
    }
}
