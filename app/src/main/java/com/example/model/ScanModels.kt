package com.example.model

enum class ScanCategory(val displayName: String, val indonesianName: String) {
    RESULT("Result", "Hasil"),
    MUSIC("Music", "Musik"),
    FILES("Files", "Berkas"),
    VIDEOS("Videos", "Video"),
    APPS("Apps", "Aplikasi")
}

enum class Severity(val label: String, val levelNumber: Int) {
    CRITICAL("Kritis", 4),
    HIGH("Tinggi", 3),
    MEDIUM("Sedang", 2),
    LOW("Rendah", 1)
}

enum class ThreatStatus(val label: String) {
    DETECTED("Terdeteksi"),
    QUARANTINED("Di Karantina"),
    REMOVED("Dihapus"),
    WHITELISTED("Dipercaya")
}

data class MalwareThreat(
    val id: String,
    val name: String,
    val category: ScanCategory,
    val filePath: String,
    val signature: String,
    val severity: Severity,
    val status: ThreatStatus = ThreatStatus.DETECTED,
    val sizeBytes: Long = 1024 * 450,
    val detectedAt: String = "Baru Saja",
    val description: String = "Program mencurigakan berpotensi mencuri data pribadi dan membebani memori sistem."
) {
    val formattedSize: String
        get() = when {
            sizeBytes >= 1024 * 1024 -> String.format("%.1f MB", sizeBytes / (1024.0 * 1024.0))
            else -> String.format("%d KB", sizeBytes / 1024)
        }
}

enum class ScanMode(val title: String, val subtitle: String, val estimatedTime: String) {
    QUICK("Pemindaian Cepat", "Pindai memori, aplikasi, dan file penting", "~30 detik"),
    DEEP("Pemindaian Mendalam", "Pindai seluruh penyimpanan dan arsip", "~2 menit"),
    CUSTOM("Pemindaian Kategori", "Pilih kategori media yang ingin dipindai", "~15 detik")
}

data class ScanUiState(
    val isScanning: Boolean = false,
    val isCleaning: Boolean = false,
    val progress: Int = 0,
    val activeCategory: ScanCategory = ScanCategory.RESULT,
    val currentFilePath: String = "Ready to scan",
    val categoryProgress: Int = 0,
    val totalFiles: Int = 43567,
    val totalBugs: Int = 567,
    val fixedBugs: Int = 425,
    val threats: List<MalwareThreat> = emptyList(),
    val quarantinedThreats: List<MalwareThreat> = emptyList(),
    val hasCompletedInitialScan: Boolean = true,
    val realTimeShieldEnabled: Boolean = true,
    val scanMode: ScanMode = ScanMode.QUICK,
    val showThreatsSheet: Boolean = false,
    val showQuarantineSheet: Boolean = false,
    val showSettingsSheet: Boolean = false,
    val showCleanCelebration: Boolean = false,
    val lastScanTimestamp: String = "Hari ini, 10:24"
)
